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
package esa.mo.nmf.ctt.services.sm;

import esa.mo.sm.impl.consumer.PackageManagementConsumerServiceImpl;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.structures.BooleanList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.softwaremanagement.packagemanagement.consumer.PackageManagementAdapter;

/**
 *
 * @author Cesar Coelho
 */
public class PackageManagementConsumerPanel extends javax.swing.JPanel {

    private final PackageManagementConsumerServiceImpl serviceSMPackageManagement;
    private PackageManagementTablePanel packagesTable;

    /**
     *
     * @param serviceSMPackageManagement
     */
    public PackageManagementConsumerPanel(PackageManagementConsumerServiceImpl serviceSMPackageManagement) {
        initComponents();

        packagesTable = new PackageManagementTablePanel();

        jScrollPane2.setViewportView(packagesTable);

        this.serviceSMPackageManagement = serviceSMPackageManagement;
        this.listAppAllButtonActionPerformed(null);

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
        jScrollPane1 = new javax.swing.JScrollPane();
        appVerboseTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        defaultTable = new javax.swing.JTable();
        parameterTab = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        installButton = new javax.swing.JButton();
        uninstallButton = new javax.swing.JButton();
        upgradeButton = new javax.swing.JButton();
        listAppAllButton = new javax.swing.JButton();

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Package Management Service");
        jLabel6.setToolTipText("");

        appVerboseTextArea.setColumns(20);
        appVerboseTextArea.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        appVerboseTextArea.setRows(5);
        jScrollPane1.setViewportView(appVerboseTextArea);

        jScrollPane2.setHorizontalScrollBar(null);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(796, 380));
        jScrollPane2.setRequestFocusEnabled(false);

        defaultTable.setModel(new javax.swing.table.DefaultTableModel(
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
        defaultTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        defaultTable.setAutoscrolls(false);
        defaultTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        defaultTable.setMaximumSize(null);
        defaultTable.setMinimumSize(null);
        defaultTable.setPreferredSize(null);
        defaultTable.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                defaultTableComponentAdded(evt);
            }
        });
        jScrollPane2.setViewportView(defaultTable);

        parameterTab.setLayout(new javax.swing.BoxLayout(parameterTab, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setMinimumSize(new java.awt.Dimension(419, 23));
        jPanel1.setPreferredSize(new java.awt.Dimension(419, 23));

        installButton.setText("install");
        installButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installButtonActionPerformed(evt);
            }
        });
        jPanel1.add(installButton);

        uninstallButton.setText("uninstall");
        uninstallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uninstallButtonActionPerformed(evt);
            }
        });
        jPanel1.add(uninstallButton);

        upgradeButton.setText("upgrade");
        upgradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upgradeButtonActionPerformed(evt);
            }
        });
        jPanel1.add(upgradeButton);

        listAppAllButton.setText("listApp(\"*\")");
        listAppAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listAppAllButtonActionPerformed(evt);
            }
        });
        jPanel1.add(listAppAllButton);

        parameterTab.add(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parameterTab, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(parameterTab, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void listAppAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listAppAllButtonActionPerformed
        IdentifierList idList = new IdentifierList();
        idList.add(new Identifier("*"));
        /*
        FindPackageResponse output;
        try {
            output = this.serviceSMPackageManagement.getPackageManagementStub().findPackage(idList);
            
            for(int i = 0; i < output.getBodyElement0().size(); i++){
//                if (textAreas.get(objId) == null) {
//                    javax.swing.JTextArea textArea = new javax.swing.JTextArea();
//                    textAreas.put(objId, textArea);
//                    textArea.setColumns(20);
//                    textArea.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
//                    textArea.setRows(2);
//                }
                
                packagesTable.addEntry(output.getBodyElement0().get(i), output.getBodyElement1().get(i));

            }
            
            Logger.getLogger(PackageManagementConsumerPanel.class.getName()).log(Level.INFO, "listDefinition(\"*\") returned {0} object instance identifiers", output.getBodyElement0().size());
        } catch (MALInteractionException ex) {
            JOptionPane.showMessageDialog(null, "There was an error during the listDefinition operation.", "Error", JOptionPane.PLAIN_MESSAGE);
            Logger.getLogger(PackageManagementConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (MALException ex) {
            JOptionPane.showMessageDialog(null, "There was an error during the listDefinition operation.", "Error", JOptionPane.PLAIN_MESSAGE);
            Logger.getLogger(PackageManagementConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
         */

        try {
            this.serviceSMPackageManagement.getPackageManagementStub().asyncFindPackage(idList, new PackageManagementAdapter() {
                @Override
                public void findPackageResponseReceived(MALMessageHeader msgHeader, IdentifierList names, BooleanList installed, Map qosProperties) {
                    packagesTable.removeAllEntries();

                    for (int i = 0; i < names.size(); i++) {
                        packagesTable.addEntry(names.get(i), installed.get(i));
                    }

                    Logger.getLogger(PackageManagementConsumerPanel.class.getName()).log(Level.INFO, "listApp(\"*\") returned {0} object instance identifiers", names.size());
                }

                @Override
                public void findPackageErrorReceived(MALMessageHeader msgHeader, MALStandardError error, Map qosProperties) {
                    JOptionPane.showMessageDialog(null, "There was an error during the findPackage operation.", "Error", JOptionPane.PLAIN_MESSAGE);
                    Logger.getLogger(PackageManagementConsumerPanel.class.getName()).log(Level.SEVERE, null, error);
                }
            }
            );
        } catch (MALInteractionException ex) {
            Logger.getLogger(PackageManagementConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(PackageManagementConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_listAppAllButtonActionPerformed

    private void upgradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upgradeButtonActionPerformed


    }//GEN-LAST:event_upgradeButtonActionPerformed

    private void uninstallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uninstallButtonActionPerformed


    }//GEN-LAST:event_uninstallButtonActionPerformed

    private void installButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_installButtonActionPerformed

        if (packagesTable.getSelectedRow() == -1) { // The row is not selected?
            return;  // Well, then nothing to be done here folks!
        }

        IdentifierList ids = new IdentifierList();
        ids.add(packagesTable.getSelectedPackage());

        try {
            this.serviceSMPackageManagement.getPackageManagementStub().install(ids, new PackageManagementAdapter() {
                @Override
                public void installAckReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader, java.util.Map qosProperties) {
                }

                @Override
                public void installResponseReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader, java.util.Map qosProperties) {
                }

                @Override
                public void installAckErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader, 
                        org.ccsds.moims.mo.mal.MALStandardError error, java.util.Map qosProperties) {

                }

                @Override
                public void installResponseErrorReceived(org.ccsds.moims.mo.mal.transport.MALMessageHeader msgHeader, 
                        org.ccsds.moims.mo.mal.MALStandardError error, java.util.Map qosProperties) {
                }

            }
            );

        } catch (MALInteractionException ex) {
            Logger.getLogger(PackageManagementConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(PackageManagementConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_installButtonActionPerformed

    private void defaultTableComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_defaultTableComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_defaultTableComponentAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea appVerboseTextArea;
    private javax.swing.JTable defaultTable;
    private javax.swing.JButton installButton;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton listAppAllButton;
    private javax.swing.JPanel parameterTab;
    private javax.swing.JButton uninstallButton;
    private javax.swing.JButton upgradeButton;
    // End of variables declaration//GEN-END:variables
}
