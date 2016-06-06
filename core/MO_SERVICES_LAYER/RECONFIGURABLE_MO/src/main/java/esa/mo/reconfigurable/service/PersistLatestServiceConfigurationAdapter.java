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
package esa.mo.reconfigurable.service;

import esa.mo.com.impl.provider.ArchivePersistenceObject;
import esa.mo.com.impl.util.HelperArchive;
import esa.mo.helpertools.connections.ConfigurationProvider;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.com.archive.provider.ArchiveInheritanceSkeleton;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.common.configuration.ConfigurationHelper;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationObjectDetailsList;
import org.ccsds.moims.mo.common.directory.DirectoryHelper;
import org.ccsds.moims.mo.common.directory.structures.ServiceKeyList;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 *
 * @author Cesar Coelho
 */
public class PersistLatestServiceConfigurationAdapter implements ConfigurationNotificationInterface {

    private ConfigurationProvider configuration = new ConfigurationProvider();
    private final ArchiveInheritanceSkeleton archiveService;
    private Long configObjId;

    public PersistLatestServiceConfigurationAdapter(ReconfigurableServiceImplInterface service, Long confObjId, ArchiveInheritanceSkeleton archiveService) {

        try {
            ConfigurationHelper.init(MALContextFactory.getElementFactoryRegistry());
        } catch (MALException ex) {
            // if it was already initialized, then.. cool bro!
        }

        try {
            DirectoryHelper.init(MALContextFactory.getElementFactoryRegistry());
        } catch (MALException ex) {
            // if it was already initialized, then.. cool bro!
        }

        this.archiveService = archiveService;

        // Store a service Configuration object in the Archive
        this.configObjId = this.storeDefaultServiceConfiguration(this.configObjId, service);
        
    }

    public Long getConfigurationObjectInstId() {
        return this.configObjId;
    }
    
    
    @Override
    public void configurationChanged(ReconfigurableServiceImplInterface serviceImpl) {

        // Update the configuration in the Archive!!
        // Retrieve the COM object of the service
        ArchivePersistenceObject comObject = HelperArchive.getArchiveCOMObject(archiveService,
                ConfigurationHelper.SERVICECONFIGURATION_OBJECT_TYPE, configuration.getDomain(), configObjId);

        // Stuff to feed the update operation from the Archive...
        ArchiveDetailsList details = HelperArchive.generateArchiveDetailsList(null, null, configuration.getNetwork(), new URI(""), comObject.getArchiveDetails().getDetails().getRelated());
        ConfigurationObjectDetailsList confObjsList = new ConfigurationObjectDetailsList();
        confObjsList.add(serviceImpl.getCurrentConfiguration());

        try {
            this.archiveService.update(
                    ConfigurationHelper.CONFIGURATIONOBJECTS_OBJECT_TYPE,
                    configuration.getDomain(),
                    details,
                    confObjsList,
                    null);
        } catch (MALException ex) {
            Logger.getLogger(PersistLatestServiceConfigurationAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(PersistLatestServiceConfigurationAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Long storeDefaultServiceConfiguration(final Long defaultObjId, ReconfigurableServiceImplInterface service) {
        try {
            // Store the Service Configuration objects
            ConfigurationObjectDetailsList archObj1 = new ConfigurationObjectDetailsList();
            archObj1.add(service.getCurrentConfiguration());

            LongList objIds1 = archiveService.store(
                    true,
                    ConfigurationHelper.CONFIGURATIONOBJECTS_OBJECT_TYPE,
                    configuration.getDomain(),
                    HelperArchive.generateArchiveDetailsList(null, null, configuration.getNetwork(), new URI("")),
                    archObj1,
                    null);

            // Store the Service Configuration
            ServiceKeyList serviceKeyList = new ServiceKeyList();
            serviceKeyList.add(service.getServiceKey());

            LongList objIds2 = archiveService.store(
                    true,
                    ConfigurationHelper.SERVICECONFIGURATION_OBJECT_TYPE,
                    configuration.getDomain(),
                    HelperArchive.generateArchiveDetailsList(objIds1.get(0), null, configuration.getNetwork(), new URI(""), defaultObjId),
                    serviceKeyList,
                    null);

            return objIds2.get(0);

        } catch (MALException ex) {
            Logger.getLogger(PersistLatestServiceConfigurationAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALInteractionException ex) {
            Logger.getLogger(PersistLatestServiceConfigurationAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}