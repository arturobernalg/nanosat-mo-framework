/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
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
package esa.mo.nmf.ctt.services.common;

import esa.mo.common.impl.consumer.ConfigurationConsumerServiceImpl;
import esa.mo.helpertools.connections.ConfigurationConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.ccsds.moims.mo.com.structures.ObjectId;
import org.ccsds.moims.mo.com.structures.ObjectIdList;
import org.ccsds.moims.mo.com.structures.ObjectKey;
import org.ccsds.moims.mo.common.configuration.ConfigurationHelper;
import org.ccsds.moims.mo.common.configuration.consumer.ConfigurationAdapter;
import org.ccsds.moims.mo.common.configuration.structures.ConfigurationType;
import org.ccsds.moims.mo.common.configuration.structures.ServiceProviderKey;
import org.ccsds.moims.mo.common.structures.ServiceKey;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.File;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.parameter.ParameterHelper;

/**
 *
 * @author Cesar Coelho
 */
public class ConfigurationConsumerPanel extends javax.swing.JPanel {

    private ConfigurationConsumerServiceImpl serviceMCConfiguration;
    private ConfigurationTablePanel configurationTable;
    private ConfigurationConsumer configuration = new ConfigurationConsumer();
    private File file;

    /**
     * Creates new formAddModifyParameter ConsumerPanelArchive
     *
     * @param serviceMCConfiguration
     */
    public ConfigurationConsumerPanel(ConfigurationConsumerServiceImpl serviceMCConfiguration) {
        initComponents();

        this.serviceMCConfiguration = serviceMCConfiguration;
        configurationTable = new ConfigurationTablePanel(serviceMCConfiguration.getCOMServices().getArchiveService());
        jScrollPane2.setViewportView(configurationTable);

        this.listAllButtonActionPerformed(null);
    }

    /**
     * This method is called from within the constructor to initialize the
     * formAddModifyParameter. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        actionDefinitionsTable = new javax.swing.JTable();
        parameterTab = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        activateButton = new javax.swing.JButton();
        getCurrentButton = new javax.swing.JButton();
        storeCurrentButton = new javax.swing.JButton();
        exportXMLButton = new javax.swing.JButton();
        importXMLButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        listAllButton = new javax.swing.JButton();
        removeAllButton = new javax.swing.JButton();

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Configuration Service");
        jLabel6.setToolTipText("");

        jScrollPane2.setHorizontalScrollBar(null);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(796, 380));
        jScrollPane2.setRequestFocusEnabled(false);

        actionDefinitionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null,  new Boolean(true), null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Obj Inst Id", "name", "description", "rawType", "rawUnit", "generationEnabled", "updateInterval"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        actionDefinitionsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        actionDefinitionsTable.setAutoscrolls(false);
        actionDefinitionsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        actionDefinitionsTable.setMaximumSize(null);
        actionDefinitionsTable.setMinimumSize(null);
        actionDefinitionsTable.setPreferredSize(null);
        actionDefinitionsTable.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                actionDefinitionsTableComponentAdded(evt);
            }
        });
        jScrollPane2.setViewportView(actionDefinitionsTable);

        parameterTab.setLayout(new java.awt.GridLayout(2, 1));

        activateButton.setText("activate");
        activateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activateButtonActionPerformed(evt);
            }
        });
        jPanel1.add(activateButton);

        getCurrentButton.setText("getCurrent");
        getCurrentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getCurrentButtonActionPerformed(evt);
            }
        });
        jPanel1.add(getCurrentButton);

        storeCurrentButton.setText("storeCurrent");
        storeCurrentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeCurrentButtonActionPerformed(evt);
            }
        });
        jPanel1.add(storeCurrentButton);

        exportXMLButton.setText("exportXML");
        exportXMLButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportXMLButtonActionPerformed(evt);
            }
        });
        jPanel1.add(exportXMLButton);

        importXMLButton.setText("importXML");
        importXMLButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importXMLButtonActionPerformed(evt);
            }
        });
        jPanel1.add(importXMLButton);

        parameterTab.add(jPanel1);

        addButton.setText("add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jPanel5.add(addButton);

        updateButton.setText("update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });
        jPanel5.add(updateButton);

        removeButton.setText("remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        jPanel5.add(removeButton);

        listAllButton.setText("list(\"*\")");
        listAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listAllButtonActionPerformed(evt);
            }
        });
        jPanel5.add(listAllButton);

        removeAllButton.setText("remove(0)");
        removeAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllButtonActionPerformed(evt);
            }
        });
        jPanel5.add(removeAllButton);

        parameterTab.add(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parameterTab, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parameterTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void activateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activateButtonActionPerformed

        if (configurationTable.getSelectedRow() == -1) { // The row is not selected?
            return;  // Well, then nothing to be done here folks!
        }

        ObjectId objIdDef = (ObjectId) configurationTable.getSelectedCOMObject().getObject();

        try {
            this.serviceMCConfiguration.getConfigurationStub().activate(objIdDef);
        } catch (MALInteractionException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            JOptionPane.showMessageDialog(null, "There was an error", "Error", JOptionPane.PLAIN_MESSAGE);
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(null, "The configuration was successfully activated.", "Success", JOptionPane.PLAIN_MESSAGE);

    }//GEN-LAST:event_activateButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed


    }//GEN-LAST:event_addButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed


    }//GEN-LAST:event_updateButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed

        if (configurationTable.getSelectedRow() == -1) { // The row is not selected?
            return;  // Well, then nothing to be done here folks!
        }

        final ObjectId objIdDef = (ObjectId) configurationTable.getSelectedCOMObject().getObject();
        final ObjectIdList oil = new ObjectIdList();
        oil.add(objIdDef);

        try {
            this.serviceMCConfiguration.getConfigurationStub().remove(oil);
            configurationTable.removeSelectedEntry();
        } catch (MALInteractionException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_removeButtonActionPerformed

    private void listAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listAllButtonActionPerformed

        IdentifierList idList = new IdentifierList();
        idList.add(new Identifier("*")); // Wildcard

        ServiceKey key = new ServiceKey(); // Wildcards
        key.setArea(new UShort(0));
        key.setService(new UShort(0));
        key.setVersion(new UOctet((byte) 0));

        ServiceProviderKey prov = new ServiceProviderKey();
        prov.setDomain(idList);
        prov.setServiceKey(key);

        ObjectIdList output;
        try {
            output = this.serviceMCConfiguration.getConfigurationStub().list(prov, ConfigurationType.SERVICE);
//            configurationTable.refreshTableWithIds(output, serviceMCConfiguration.getConnectionDetails().getDomain(), ActionHelper.ACTIONDEFINITION_OBJECT_TYPE);
        } catch (MALInteractionException ex) {
            JOptionPane.showMessageDialog(null, "There was an error during the listDefinition operation.", "Error", JOptionPane.PLAIN_MESSAGE);
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (MALException ex) {
            JOptionPane.showMessageDialog(null, "There was an error during the listDefinition operation.", "Error", JOptionPane.PLAIN_MESSAGE);
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.INFO, "listDefinition(\"*\") returned {0} object instance identifiers", output.size());

    }//GEN-LAST:event_listAllButtonActionPerformed

    private void removeAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAllButtonActionPerformed

        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));
        ObjectKey objKey = new ObjectKey();
        objKey.setDomain(domain);
        objKey.setInstId((long) 0);

        ObjectId objIdDef = new ObjectId(ConfigurationHelper.CONFIGURATIONOBJECTS_OBJECT_TYPE, objKey);
        final ObjectIdList oil = new ObjectIdList();
        oil.add(objIdDef);

        try {
            this.serviceMCConfiguration.getConfigurationStub().remove(oil);
            configurationTable.removeAllEntries();
        } catch (MALInteractionException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_removeAllButtonActionPerformed

    private void getCurrentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getCurrentButtonActionPerformed

        if (configurationTable.getSelectedRow() == -1) { // The row is not selected?
            return;  // Well, then nothing to be done here folks!
        }

    }//GEN-LAST:event_getCurrentButtonActionPerformed

    private void actionDefinitionsTableComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_actionDefinitionsTableComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_actionDefinitionsTableComponentAdded

    private void storeCurrentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storeCurrentButtonActionPerformed

        ServiceKey key = new ServiceKey(); // Wildcards
        key.setArea(new UShort(MCHelper._MC_AREA_NUMBER));
        key.setService(new UShort(ParameterHelper._PARAMETER_SERVICE_NUMBER));
        key.setVersion(new UOctet(MCHelper._MC_AREA_VERSION));

        ServiceProviderKey prov = new ServiceProviderKey();
        prov.setDomain(this.serviceMCConfiguration.getConnectionDetails().getDomain());
        prov.setServiceKey(key);

        class ConfigAdapter extends ConfigurationAdapter {

            @Override
            public void getCurrentResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader, org.ccsds.moims.mo.com.structures.ObjectId objInstId, java.util.Map qosProperties) {
                String str = "Object instance identifiers on the provider: \n";
                str += objInstId.toString();
                JOptionPane.showMessageDialog(null, str, "Returned ObjectId from the Provider", JOptionPane.PLAIN_MESSAGE);
            }
        }

        try {
            this.serviceMCConfiguration.getConfigurationStub().storeCurrent(prov, ConfigurationType.SERVICE, true, new ConfigAdapter());
        } catch (MALInteractionException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_storeCurrentButtonActionPerformed

    private void exportXMLButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportXMLButtonActionPerformed
        ObjectId confObjId = new ObjectId();
        confObjId.setKey(new ObjectKey(this.serviceMCConfiguration.getConnectionDetails().getDomain(), (long) 7));
        confObjId.setType(ConfigurationHelper.CONFIGURATIONOBJECTS_OBJECT_TYPE);

        try {
            file = this.serviceMCConfiguration.getConfigurationStub().exportXML(confObjId, Boolean.TRUE);
        } catch (MALInteractionException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_exportXMLButtonActionPerformed

    private void importXMLButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importXMLButtonActionPerformed

        try {
            this.serviceMCConfiguration.getConfigurationStub().importXML(file);
        } catch (MALInteractionException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(ConfigurationConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_importXMLButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable actionDefinitionsTable;
    private javax.swing.JButton activateButton;
    private javax.swing.JButton addButton;
    private javax.swing.JButton exportXMLButton;
    private javax.swing.JButton getCurrentButton;
    private javax.swing.JButton importXMLButton;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton listAllButton;
    private javax.swing.JPanel parameterTab;
    private javax.swing.JButton removeAllButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton storeCurrentButton;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
