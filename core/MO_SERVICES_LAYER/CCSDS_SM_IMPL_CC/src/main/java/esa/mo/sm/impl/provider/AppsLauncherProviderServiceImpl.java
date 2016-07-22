/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA NanoSat MO Framework
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.sm.impl.provider;

import esa.mo.com.impl.consumer.EventConsumerServiceImpl;
import esa.mo.com.impl.util.COMServicesProvider;
import esa.mo.com.impl.util.EventCOMObject;
import esa.mo.com.impl.util.EventReceivedListener;
import esa.mo.com.impl.util.HelperArchive;
import esa.mo.com.impl.util.HelperCOM;
import esa.mo.helpertools.connections.ConfigurationProvider;
import esa.mo.helpertools.connections.ConnectionConsumer;
import esa.mo.helpertools.connections.ConnectionProvider;
import esa.mo.helpertools.connections.SingleConnectionDetails;
import esa.mo.helpertools.helpers.HelperTime;
import esa.mo.reconfigurable.service.ConfigurationNotificationInterface;
import esa.mo.reconfigurable.service.ReconfigurableServiceImplInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.com.COMHelper;
import org.ccsds.moims.mo.com.COMService;
import org.ccsds.moims.mo.com.event.EventHelper;
import org.ccsds.moims.mo.com.structures.ObjectIdList;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectDetails;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSet;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectSetList;
import org.ccsds.moims.mo.common.directory.provider.DirectoryInheritanceSkeleton;
import org.ccsds.moims.mo.common.directory.structures.AddressDetails;
import org.ccsds.moims.mo.common.directory.structures.AddressDetailsList;
import org.ccsds.moims.mo.common.directory.structures.ProviderSummaryList;
import org.ccsds.moims.mo.common.directory.structures.ServiceFilter;
import org.ccsds.moims.mo.common.directory.structures.ServiceKey;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.BooleanList;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.URIList;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.structures.UpdateType;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.softwaremanagement.SoftwareManagementHelper;
import org.ccsds.moims.mo.softwaremanagement.appslauncher.AppsLauncherHelper;
import org.ccsds.moims.mo.softwaremanagement.appslauncher.body.ListAppResponse;
import org.ccsds.moims.mo.softwaremanagement.appslauncher.provider.AppsLauncherInheritanceSkeleton;
import org.ccsds.moims.mo.softwaremanagement.appslauncher.provider.MonitorExecutionPublisher;
import org.ccsds.moims.mo.softwaremanagement.appslauncher.provider.StopAppInteraction;
import org.ccsds.moims.mo.softwaremanagement.appslauncher.structures.AppDetails;
import org.ccsds.moims.mo.softwaremanagement.appslauncher.structures.AppDetailsList;

/**
 *
 */
public class AppsLauncherProviderServiceImpl extends AppsLauncherInheritanceSkeleton implements ReconfigurableServiceImplInterface {

    private MALProvider appsLauncherServiceProvider;
    private MonitorExecutionPublisher publisher;
    private boolean initialiased = false;
    private boolean running = false;
    private boolean isRegistered = false;
    private AppsLauncherManager manager;
    private final ConfigurationProvider configuration = new ConfigurationProvider();
    private final ConnectionProvider connection = new ConnectionProvider();
    private COMServicesProvider comServices;
    private DirectoryInheritanceSkeleton directoryService;
    private ConfigurationNotificationInterface configurationAdapter;

    /**
     * Initializes the Event service provider
     *
     * @param comServices
     * @param directoryService
     * @throws MALException On initialization error.
     */
    public synchronized void init(COMServicesProvider comServices, DirectoryInheritanceSkeleton directoryService) throws MALException {
        if (!initialiased) {
            if (MALContextFactory.lookupArea(MALHelper.MAL_AREA_NAME, MALHelper.MAL_AREA_VERSION) == null) {
                MALHelper.init(MALContextFactory.getElementFactoryRegistry());
            }

            if (MALContextFactory.lookupArea(COMHelper.COM_AREA_NAME, COMHelper.COM_AREA_VERSION) == null) {
                COMHelper.init(MALContextFactory.getElementFactoryRegistry());
            }

            if (MALContextFactory.lookupArea(SoftwareManagementHelper.SOFTWAREMANAGEMENT_AREA_NAME, SoftwareManagementHelper.SOFTWAREMANAGEMENT_AREA_VERSION) == null) {
                SoftwareManagementHelper.init(MALContextFactory.getElementFactoryRegistry());
            }

            try {
                AppsLauncherHelper.init(MALContextFactory.getElementFactoryRegistry());
            } catch (MALException ex) {
                // nothing to be done..
            }
        }

        publisher = createMonitorExecutionPublisher(configuration.getDomain(),
                configuration.getNetwork(),
                SessionType.LIVE,
                new Identifier("LIVE"),
                QoSLevel.BESTEFFORT,
                null,
                new UInteger(0));

        // Shut down old service transport
        if (null != appsLauncherServiceProvider) {
            connection.close();
        }

        this.comServices = comServices;
        this.directoryService = directoryService;
        manager = new AppsLauncherManager(comServices);
        appsLauncherServiceProvider = connection.startService(AppsLauncherHelper.APPSLAUNCHER_SERVICE_NAME.toString(), AppsLauncherHelper.APPSLAUNCHER_SERVICE, this);
        manager.refreshAvailableAppsList(connection.getConnectionDetails());
        running = true;
        initialiased = true;
        Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.INFO, "Apps Launcher service: READY");
    }

    public ConnectionProvider getConnectionProvider() {
        return this.connection;
    }

    private void publishExecutionMonitoring(final Long appObjId, final String outputText) {
        try {
            if (!isRegistered) {
                final EntityKeyList lst = new EntityKeyList();
                lst.add(new EntityKey(new Identifier("*"), 0L, 0L, 0L));
                publisher.register(lst, new PublishInteractionListener());

                isRegistered = true;
            }

            Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.FINER,
                    "Generating update for the App: {0} (Identifier: {1})",
                    new Object[]{
                        appObjId, new Identifier(manager.get(appObjId).getName().toString())
                    });

            final StringList outputList = new StringList();

            // Should not be store in the Archive... it's too much stuff
            final EntityKey ekey = new EntityKey(new Identifier(manager.get(appObjId).getName().toString()), appObjId, null, null);
            final Time timestamp = HelperTime.getTimestampMillis();

            final UpdateHeaderList hdrlst = new UpdateHeaderList();

            hdrlst.add(new UpdateHeader(timestamp, connection.getConnectionDetails().getProviderURI(), UpdateType.UPDATE, ekey));

            outputList.add(outputText);
            publisher.publish(hdrlst, outputList);

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.WARNING, "Exception during publishing process on the provider {0}", ex);
        } catch (MALException ex) {
            Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.WARNING, "Exception during publishing process on the provider {0}", ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.WARNING, "Exception during publishing process on the provider {0}", ex);
        }
    }

    @Override
    public void runApp(LongList appInstIds, MALInteraction interaction) throws MALInteractionException, MALException {
        UIntegerList unkIndexList = new UIntegerList();
        UIntegerList invIndexList = new UIntegerList();

        if (null == appInstIds) { // Is the input null?
            throw new IllegalArgumentException("appInstIds argument must not be null");
        }

        // Refresh the list of available Apps
        this.manager.refreshAvailableAppsList(connection.getConnectionDetails());

        for (int index = 0; index < appInstIds.size(); index++) {
            AppDetails app = (AppDetails) this.manager.get(appInstIds.get(index)); // get it from the list of available apps

            // The app id could not be identified?
            if (app == null) {
                unkIndexList.add(new UInteger(index)); // Throw an UNKNOWN error
                continue;
            }

            // Is the app already running?
            if (manager.isAppRunning(appInstIds.get(index))) {
                invIndexList.add(new UInteger(index)); // Throw an INVALID error
                continue;
            }
        }

        // Errors
        if (!invIndexList.isEmpty()) {
            throw new MALInteractionException(new MALStandardError(COMHelper.INVALID_ERROR_NUMBER, invIndexList));
        }

        if (!unkIndexList.isEmpty()) {
            throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, unkIndexList));
        }

        // Run the apps!
        for (int i = 0; i < appInstIds.size(); i++) {
            try {
                manager.startAppProcess(new ProcessExecutionHandler(appInstIds.get(i)), interaction);
            } catch (IOException ex) {
                invIndexList.add(new UInteger(i));
                throw new MALInteractionException(new MALStandardError(MALHelper.INTERNAL_ERROR_NUMBER, invIndexList));
            }
        }

    }

    @Override
    public void killApp(LongList appInstIds, MALInteraction interaction) throws MALInteractionException, MALException {
        UIntegerList unkIndexList = new UIntegerList();
        UIntegerList invIndexList = new UIntegerList();

        if (null == appInstIds) { // Is the input null?
            throw new IllegalArgumentException("appInstIds argument must not be null");
        }

        // Refresh the list of available Apps
        this.manager.refreshAvailableAppsList(connection.getConnectionDetails());

        for (int index = 0; index < appInstIds.size(); index++) {
            AppDetails app = (AppDetails) this.manager.get(appInstIds.get(index)); // get it from the list of available apps

            // The app id could not be identified?
            if (app == null) {
                unkIndexList.add(new UInteger(index)); // Throw an UNKNOWN error
                continue;
            }

            // Is the app the app not running?
            if (!manager.isAppRunning(appInstIds.get(index))) {
                invIndexList.add(new UInteger(index)); // Throw an INVALID error
                continue;
            }
        }

        // Errors
        if (!invIndexList.isEmpty()) {
            throw new MALInteractionException(new MALStandardError(COMHelper.INVALID_ERROR_NUMBER, invIndexList));
        }

        if (!unkIndexList.isEmpty()) {
            throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, unkIndexList));
        }

        // Kill the apps!
        for (int i = 0; i < appInstIds.size(); i++) {
            manager.killAppProcess(appInstIds.get(i), connection.getConnectionDetails(), interaction);
        }

    }

    @Override
    public void stopApp(LongList appInstIds, StopAppInteraction interaction) throws MALInteractionException, MALException {
        UIntegerList unkIndexList = new UIntegerList();
        UIntegerList invIndexList = new UIntegerList();
        URIList uris = new URIList();

        if (null == appInstIds) { // Is the input null?
            throw new IllegalArgumentException("appInstIds argument must not be null");
        }

        // Refresh the list of available Apps
        this.manager.refreshAvailableAppsList(connection.getConnectionDetails());

        for (int i = 0; i < appInstIds.size(); i++) {
            AppDetails app = (AppDetails) this.manager.get(appInstIds.get(i)); // get it from the list of available apps

            // The app id could not be identified?
            if (app == null) {
                unkIndexList.add(new UInteger(i)); // Throw an UNKNOWN error
                continue;
            }

            // Is the app the app not running?
            if (!manager.isAppRunning(appInstIds.get(i))) {
                invIndexList.add(new UInteger(i)); // Throw an INVALID error
                continue;
            }

            // Define filter in order to get the Event service URI of the app
            Identifier serviceProviderName = new Identifier("App: " + app.getName());
            IdentifierList domain = new IdentifierList();
            domain.add(new Identifier("*"));
            COMService eventCOM = EventHelper.EVENT_SERVICE;
            ServiceKey serviceKey = new ServiceKey(eventCOM.getArea().getNumber(), eventCOM.getNumber(), eventCOM.getArea().getVersion());
            ServiceFilter sf = new ServiceFilter(serviceProviderName, domain, new Identifier("*"), null, new Identifier("*"), serviceKey, new UIntegerList());

            // Do a lookup on the Central Drectory service for the app that we want
            ProviderSummaryList providersList = this.directoryService.lookupProvider(sf, interaction.getInteraction());

            if (providersList.isEmpty()) {
                // The app could not be found in the Directory service...
                // Possible reasons: Not a NMF app, if so, one needs to use killApp!
                // Throw error!
            }

            if (providersList.size() != 1) {
                // Why do we have a bunch of registrations from the same App? Weirddddd...
                // Throw error!
            }

            // Get the service address details lists
            AddressDetailsList addresses = providersList.get(0).getProviderDetails().getProviderAddresses();

            // How many addresses do we have?
            if (addresses.isEmpty()) {
                // Throw an error
            }

            if (addresses.size() == 1) {
                uris.add(addresses.get(0).getServiceURI()); // Direct match!
                continue;
            }

            uris.add(this.getSingleURIfromList(addresses)); // If we have multiple ones...
        }

        // Errors
        if (!invIndexList.isEmpty()) {
            throw new MALInteractionException(new MALStandardError(COMHelper.INVALID_ERROR_NUMBER, invIndexList));
        }

        if (!unkIndexList.isEmpty()) {
            throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, unkIndexList));
        }

        interaction.sendAcknowledgement();

        Random random = new Random();

        // Register on the Event service with the Adapter
        for (URI uri : uris){
            // Subscribe to events
            // Select all object numbers from the Apps Launcher service Events
            final Long secondEntityKey = 0xFFFFFFFFFF000000L & HelperCOM.generateSubKey(AppsLauncherHelper.APP_OBJECT_TYPE);
            Subscription eventSub = ConnectionConsumer.subscriptionKeys(new Identifier("AppClosingEvent" + random.nextInt()), new Identifier("*"), secondEntityKey, new Long(0), new Long(0));

            SingleConnectionDetails connectionDetails = new SingleConnectionDetails();
            try {
                EventConsumerServiceImpl eventServiceConsumer = new EventConsumerServiceImpl(connectionDetails);
                eventServiceConsumer.addEventReceivedListener(eventSub, new ClosingAppListener(interaction, eventServiceConsumer));
            } catch (MalformedURLException ex) {
                //  Could not connect to the app!
                Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
                       
        }
        
        // Stop the apps...
        ObjectType objType = AppsLauncherHelper.STOPAPP_OBJECT_TYPE;
        ObjectIdList sourceList = new ObjectIdList();

        for (Long appInstId : appInstIds) {
            sourceList.add(comServices.getActivityTrackingService().storeCOMOperationActivity(interaction.getInteraction(), null));
        }

        // Generate, store and publish the events to stop the Apps...
        LongList objIds = comServices.getEventService().generateAndStoreEvents(objType, connection.getConnectionDetails().getDomain(), appInstIds, sourceList, interaction.getInteraction());
        comServices.getEventService().publishEvents(connection.getConnectionDetails().getProviderURI(), objIds, objType, appInstIds, sourceList, null);

    }

    private StringList getAvailableTransports(AddressDetailsList addresses) {
        StringList transports = new StringList(); // List of transport names

        for (AddressDetails address : addresses) {
            // The name of the transport is always before ":"
            String[] parts = address.getServiceURI().toString().split(":");
            transports.add(parts[0]);
        }

        return transports;
    }

    private int getTransportIndex(StringList transports, String findString) {
        for (int i = 0; i < transports.size(); i++) {
            if (findString.equals(transports.get(i))) {
                return i;  // match
            }
        }
        return -1;
    }

    private URI getSingleURIfromList(AddressDetailsList addresses) {
            // Well, if there are more than one, then it means we can pick...
            // My preference would be, in order: tcp/ip, rmi, other, spp
            // SPP is in last because usually this is the transport supposed
            // to be used on the ground-to-space link and not internally.
            StringList availableTransports = this.getAvailableTransports(addresses);

            int index = this.getTransportIndex(availableTransports, "tcpip");
            if (index != -1) {
                return addresses.get(index).getServiceURI();
            }

            index = this.getTransportIndex(availableTransports, "rmi");
            if (index != -1) {
                return  addresses.get(index).getServiceURI();
            }

            index = this.getTransportIndex(availableTransports, "malspp");

            // If could not be found nor it is not the first one
            if (index == -1 || index != 0) { // Then let's pick the first one
                return  addresses.get(0).getServiceURI();
            } else {
                // It was found and it is the first one (0)
                // Then let's select the second (index == 1) transport available...
                return  addresses.get(1).getServiceURI();
            }
    }

    // Create the listeners for the returned events
    private class ClosingAppListener extends EventReceivedListener{
        private final StopAppInteraction interaction;
        private final EventConsumerServiceImpl eventService;

        public ClosingAppListener(StopAppInteraction interaction, EventConsumerServiceImpl eventService){
            this.interaction = interaction;
            this.eventService = eventService;
        }

        @Override
        public void onDataReceived(EventCOMObject eventCOMObject) {
            // Is it the ack from the app?
            
            // If so, then close the connection to the service
            eventService.closeConnection();
            
            try { // Send response to consumer stating that the app is closing...
                interaction.sendResponse();
            } catch (MALInteractionException ex) {
                Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MALException ex) {
                Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public ListAppResponse listApp(IdentifierList appNames, Identifier category, MALInteraction interaction) throws MALInteractionException, MALException {
        UIntegerList unkIndexList = new UIntegerList();
        ListAppResponse outList = new ListAppResponse();

        if (null == appNames) { // Is the input null?
            throw new IllegalArgumentException("IdentifierList argument must not be null");
        }

        // Refresh the list of available Apps
        manager.refreshAvailableAppsList(connection.getConnectionDetails());
        LongList ids = new LongList();
        BooleanList runningApps = new BooleanList();

        for (int index = 0; index < appNames.size(); index++) {
            if ("*".equals(appNames.get(index).getValue())) {
                ids.clear();  // if the wildcard is in the middle of the input list, we clear the output list and...
                ids.addAll(manager.listAll());
                break;
            }

            if (manager.list(appNames.get(index)) == null) {
                // The app does not exist...
                unkIndexList.add(new UInteger(index)); // Throw an UNKNOWN error
            }
        }

        if (!unkIndexList.isEmpty()) {
            throw new MALInteractionException(new MALStandardError(MALHelper.UNKNOWN_ERROR_NUMBER, unkIndexList));
        }

        for (Long id : ids) { // Is the app running?
            runningApps.add(manager.isAppRunning(id));
        }

        outList.setBodyElement0(ids);
        outList.setBodyElement1(runningApps);

        return outList;
    }

    @Override
    public void setConfigurationAdapter(esa.mo.reconfigurable.service.ConfigurationNotificationInterface configurationAdapter) {
        this.configurationAdapter = configurationAdapter;
    }

    @Override
    public Boolean reloadConfiguration(ConfigurationObjectDetails configurationObjectDetails) {
        // Validate the configuration...
        if (configurationObjectDetails == null) {
            return false;
        }

        if (configurationObjectDetails.getConfigObjects() == null) {
            return false;
        }

        // Is the size 1?
        if (configurationObjectDetails.getConfigObjects().size() != 1) {  // 1 because we just have Apps as configuration objects in this service
            return false;
        }

        ConfigurationObjectSet confSet = configurationObjectDetails.getConfigObjects().get(0);

        // Confirm the objType
        if (!confSet.getObjType().equals(AppsLauncherHelper.APP_OBJECT_TYPE)) {
            return false;
        }

        // Confirm the domain
        if (!confSet.getDomain().equals(connection.getConnectionDetails().getDomain())) {
            return false;
        }

        // If the list is empty, reconfigure the service with nothing...
        if (confSet.getObjInstIds().isEmpty()) {
            manager.reconfigureDefinitions(new LongList(), new AppDetailsList());   // Reconfigures the Manager
            return true;
        }

        // ok, we're good to go...
        // Load the App Details from this configuration...
        AppDetailsList pDefs = (AppDetailsList) HelperArchive.getObjectBodyListFromArchive(
                manager.getArchiveService(),
                AppsLauncherHelper.APP_OBJECT_TYPE,
                connection.getConnectionDetails().getDomain(),
                confSet.getObjInstIds());

        manager.reconfigureDefinitions(confSet.getObjInstIds(), pDefs);   // Reconfigures the Manager

        return true;
    }

    @Override
    public ConfigurationObjectDetails getCurrentConfiguration() {
        // Get all the current objIds in the serviceImpl
        // Create a Configuration Object with all the objs of the provider
        HashMap<Long, Element> defObjs = manager.getCurrentDefinitionsConfiguration();

        ConfigurationObjectSet objsSet = new ConfigurationObjectSet();
        objsSet.setDomain(connection.getConnectionDetails().getDomain());
        LongList currentObjIds = new LongList();
        currentObjIds.addAll(defObjs.keySet());
        objsSet.setObjInstIds(currentObjIds);
        objsSet.setObjType(AppsLauncherHelper.APP_OBJECT_TYPE);

        ConfigurationObjectSetList list = new ConfigurationObjectSetList();
        list.add(objsSet);

        // Needs the Common API here!
        ConfigurationObjectDetails set = new ConfigurationObjectDetails();
        set.setConfigObjects(list);

        return set;
    }

    @Override
    public COMService getCOMService() {
        return AppsLauncherHelper.APPSLAUNCHER_SERVICE;
    }

    public static final class PublishInteractionListener implements MALPublishInteractionListener {

        @Override
        public void publishDeregisterAckReceived(final MALMessageHeader header, final Map qosProperties)
                throws MALException {
            Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).fine("PublishInteractionListener::publishDeregisterAckReceived");
        }

        @Override
        public void publishErrorReceived(final MALMessageHeader header, final MALErrorBody body, final Map qosProperties)
                throws MALException {
            Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).fine("PublishInteractionListener::publishErrorReceived");
        }

        @Override
        public void publishRegisterAckReceived(final MALMessageHeader header, final Map qosProperties)
                throws MALException {
            Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).fine("PublishInteractionListener::publishRegisterAckReceived");
//            Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.INFO, "Registration Ack: {0}", header.toString());
        }

        @Override
        public void publishRegisterErrorReceived(final MALMessageHeader header, final MALErrorBody body, final Map qosProperties) throws MALException {
            Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).fine("PublishInteractionListener::publishRegisterErrorReceived");
        }
    }

    public class ProcessExecutionHandler {

        private final Timer timer = new Timer();
        private final static int PERIOD_PUB = 5 * 1000; // Publish every 5 seconds
        private final Long appId;
        private Thread collectStream1;
        private Thread collectStream2;
        private BufferedReader br1;
        private BufferedReader br2;
        private Process process = null;

        public ProcessExecutionHandler(Long appId) {
            this.appId = appId;
        }

        public SingleConnectionDetails getSingleConnectionDetails() {
            return connection.getConnectionDetails();
        }

        public Long getAppInstId() {
            return appId;
        }

        public Process getProcess() {
            return process;
        }

        public void close() {
            timer.cancel();

            try {
                process.destroy();
                br1.close();
                br2.close();
            } catch (IOException ex) {
                Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void startPublishing(final Process process) {
            this.process = process;

            br1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
            br2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            final StringBuilder buffer = new StringBuilder();

            // Every PERIOD_PUB seconds, publish the String data
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // Change the buffer position
                    int size = buffer.length();
                    if (size != 0) {
                        String output = buffer.substring(0, size);
                        buffer.delete(0, size);

                        // Publish what's on the buffer every PERIOD_PUB milliseconds
                        Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.FINE, output);
                        publishExecutionMonitoring(appId, output);
                    }
                }
            }, 0, PERIOD_PUB);

            collectStream1 = createThreadReader(buffer, br1);
            collectStream2 = createThreadReader(buffer, br2);

            collectStream1.start();
            collectStream2.start();
        }

        private Thread createThreadReader(final StringBuilder buf, final BufferedReader br) {
            return new Thread() {
                @Override
                public void run() {
                    try {
                        String line = null;

                        while ((line = br.readLine()) != null) {
//                            buf.append("\t");
                            buf.append(line);
//                            buf.append("\t");
                            buf.append("\n");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AppsLauncherProviderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }

    }

}
