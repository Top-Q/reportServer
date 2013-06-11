/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InfoPanel.java
 *
 * Created on 01/09/2011, 10:04:51
 */

package jsystem.publisher.plugin;

import java.awt.event.ItemEvent;

import javax.swing.JPanel;

import jsystem.treeui.interfaces.JSystemTab;

import jsystem.framework.report.Reporter;
/**
 *
 * @author Topq
 */
public class InfoPanel extends javax.swing.JPanel implements JSystemTab{

	private static final long serialVersionUID = -1510054274014713603L;
	
	@Override
	public JPanel init() {
        return this;
    }

	@Override
  	public  String getTabName() {
        return "Test Info Panel";
    }
	  
    /** Creates new form InfoPanel */
    public InfoPanel() {
        initComponents();
        setVisible(true);
    }

    public void setTestParameters(String tname, int tcount, long executionTime, int status, String parameters, String documentation,
			String steps, String properties, String errorCause) {
		testNameField.setText(tname);
		testIndexField.setText(Integer.toString(tcount));
		testTimeField.setText(Long.toString(executionTime / 1000) + "  Sec.");
		if (status == Reporter.PASS) {
			radioPass.setSelected(true);
                        radioFail.setSelected(false);
                        radioWarning.setSelected(false);
		} else if (status == Reporter.FAIL) {
			radioPass.setSelected(false);
                        radioFail.setSelected(true);
                        radioWarning.setSelected(false);
		} else {
			radioPass.setSelected(false);
                        radioFail.setSelected(false);
                        radioWarning.setSelected(true);
		}
//		taParametersValue.setText(parameters.replace(' ', '\n'));
//		taDocumentationValue.setText(documentation);
//		taStepsValue.setText(steps);
//		pnProperties.setProperties(properties);
//		taErrorCauseValue.setText(errorCause);
	}

    	public int getStatus() {
		if (radioPass.isSelected())
                    return 0;
                if (radioFail.isSelected())
                    return 1;
                if (radioWarning.isSelected())
                    return 2;
                return 0;
	}

        public void setEditing(boolean edit) {
		radioPass.setEnabled(edit);
                radioFail.setEnabled(edit);
                radioWarning.setEnabled(edit);
//		taDocumentationValue.setVisible(edit);
//		taParametersValue.setVisible(edit);
//		taStepsValue.setVisible(edit);
//		taErrorCauseValue.setVisible(edit);
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbExeTime = new javax.swing.JLabel();
        testIndexField = new javax.swing.JLabel();
        testTimeField = new javax.swing.JLabel();
        testTabView = new javax.swing.JTabbedPane();
        pnParameters = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taParametersValue = new javax.swing.JTextArea();
        pnDocumentation = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        taDocumentationValue = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        lbTestName = new javax.swing.JLabel();
        testNameField = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        testNameField1 = new javax.swing.JLabel();
        lbTestIndex = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        testNameField2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbStatus = new javax.swing.JLabel();
        radioPass = new javax.swing.JRadioButton();
        radioFail = new javax.swing.JRadioButton();
        radioWarning = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        lbErrorCause1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        errorCauseField = new javax.swing.JTextArea();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(jsystem.publisher.plugin.PublisherPluginApp.class).getContext().getResourceMap(InfoPanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("Form.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("Form.border.titleFont"))); // NOI18N
        setName("Form"); // NOI18N

        lbExeTime.setName("lbExeTime"); // NOI18N

        testIndexField.setName("testIndexField"); // NOI18N

        testTimeField.setName("testTimeField"); // NOI18N

        testTabView.setName("testTabView"); // NOI18N
        testTabView.setPreferredSize(new java.awt.Dimension(400, 400));

        pnParameters.setName("pnParameters"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        taParametersValue.setColumns(20);
        taParametersValue.setRows(5);
        taParametersValue.setName("taParametersValue"); // NOI18N
        jScrollPane3.setViewportView(taParametersValue);

        javax.swing.GroupLayout pnParametersLayout = new javax.swing.GroupLayout(pnParameters);
        pnParameters.setLayout(pnParametersLayout);
        pnParametersLayout.setHorizontalGroup(
            pnParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnParametersLayout.setVerticalGroup(
            pnParametersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnParametersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );

        testTabView.addTab(resourceMap.getString("pnParameters.TabConstraints.tabTitle"), pnParameters); // NOI18N

        pnDocumentation.setName("pnDocumentation"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        taDocumentationValue.setColumns(20);
        taDocumentationValue.setRows(5);
        taDocumentationValue.setName("taDocumentationValue"); // NOI18N
        jScrollPane4.setViewportView(taDocumentationValue);

        javax.swing.GroupLayout pnDocumentationLayout = new javax.swing.GroupLayout(pnDocumentation);
        pnDocumentation.setLayout(pnDocumentationLayout);
        pnDocumentationLayout.setHorizontalGroup(
            pnDocumentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDocumentationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnDocumentationLayout.setVerticalGroup(
            pnDocumentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDocumentationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );

        testTabView.addTab(resourceMap.getString("pnDocumentation.TabConstraints.tabTitle"), pnDocumentation); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        lbTestName.setFont(resourceMap.getFont("lbTestName.font")); // NOI18N
        lbTestName.setText(resourceMap.getString("lbTestName.text")); // NOI18N
        lbTestName.setName("lbTestName"); // NOI18N

        testNameField.setName("testNameField"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTestName, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(testNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(testNameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                    .addComponent(lbTestName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setName("jPanel2"); // NOI18N

        testNameField1.setName("testNameField1"); // NOI18N

        lbTestIndex.setFont(resourceMap.getFont("lbTestIndex.font")); // NOI18N
        lbTestIndex.setText(resourceMap.getString("lbTestIndex.text")); // NOI18N
        lbTestIndex.setMaximumSize(new java.awt.Dimension(63, 14));
        lbTestIndex.setMinimumSize(new java.awt.Dimension(63, 14));
        lbTestIndex.setName("lbTestIndex"); // NOI18N
        lbTestIndex.setPreferredSize(new java.awt.Dimension(63, 14));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTestIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(testNameField1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(testNameField1, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                    .addComponent(lbTestIndex, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setName("jPanel3"); // NOI18N

        testNameField2.setName("testNameField2"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(testNameField2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(testNameField2, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        jPanel4.setName("jPanel4"); // NOI18N

        lbStatus.setFont(resourceMap.getFont("lbStatus.font")); // NOI18N
        lbStatus.setText(resourceMap.getString("lbStatus.text")); // NOI18N
        lbStatus.setName("lbStatus"); // NOI18N

        radioPass.setText(resourceMap.getString("radioPass.text")); // NOI18N
        radioPass.setName("radioPass"); // NOI18N
        radioPass.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radioPassStateChanged(evt);
            }
        });
        radioPass.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioPassItemStateChanged(evt);
            }
        });

        radioFail.setText(resourceMap.getString("radioFail.text")); // NOI18N
        radioFail.setName("radioFail"); // NOI18N
        radioFail.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioFailItemStateChanged(evt);
            }
        });

        radioWarning.setText(resourceMap.getString("radioWarning.text")); // NOI18N
        radioWarning.setName("radioWarning"); // NOI18N
        radioWarning.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioWarningItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbStatus)
                .addGap(94, 94, 94)
                .addComponent(radioPass)
                .addGap(39, 39, 39)
                .addComponent(radioFail)
                .addGap(36, 36, 36)
                .addComponent(radioWarning)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbStatus)
                    .addComponent(radioPass)
                    .addComponent(radioFail)
                    .addComponent(radioWarning))
                .addContainerGap())
        );

        jPanel5.setName("jPanel5"); // NOI18N

        lbErrorCause1.setFont(resourceMap.getFont("lbErrorCause1.font")); // NOI18N
        lbErrorCause1.setText(resourceMap.getString("lbErrorCause1.text")); // NOI18N
        lbErrorCause1.setName("lbErrorCause1"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        errorCauseField.setColumns(20);
        errorCauseField.setRows(5);
        errorCauseField.setName("errorCauseField"); // NOI18N
        errorCauseField.setPreferredSize(new java.awt.Dimension(104, 50));
        jScrollPane5.setViewportView(errorCauseField);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbErrorCause1)
                .addGap(57, 57, 57)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbErrorCause1)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(testTabView, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(555, 555, 555))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(testIndexField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32))
                            .addComponent(lbExeTime, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(testTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(226, 226, 226)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(testIndexField, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbExeTime))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(testTimeField, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(testTabView, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void radioWarningItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioWarningItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            radioPass.setSelected(false);
            radioWarning.setSelected(true);
            radioFail.setSelected(false);
        }
}//GEN-LAST:event_radioWarningItemStateChanged

    private void radioPassStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radioPassStateChanged
        // TODO add your handling code here:
}//GEN-LAST:event_radioPassStateChanged

    private void radioPassItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioPassItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            radioPass.setSelected(true);
            radioWarning.setSelected(false);
            radioFail.setSelected(false);
        }
}//GEN-LAST:event_radioPassItemStateChanged

    private void radioFailItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioFailItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            radioPass.setSelected(false);
            radioWarning.setSelected(false);
            radioFail.setSelected(true);
        }
}//GEN-LAST:event_radioFailItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea errorCauseField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbErrorCause1;
    private javax.swing.JLabel lbExeTime;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbTestIndex;
    private javax.swing.JLabel lbTestName;
    private javax.swing.JPanel pnDocumentation;
    private javax.swing.JPanel pnParameters;
    private javax.swing.JRadioButton radioFail;
    private javax.swing.JRadioButton radioPass;
    private javax.swing.JRadioButton radioWarning;
    private javax.swing.JTextArea taDocumentationValue;
    private javax.swing.JTextArea taParametersValue;
    private javax.swing.JLabel testIndexField;
    private javax.swing.JLabel testNameField;
    private javax.swing.JLabel testNameField1;
    private javax.swing.JLabel testNameField2;
    private javax.swing.JTabbedPane testTabView;
    private javax.swing.JLabel testTimeField;
    // End of variables declaration//GEN-END:variables

}
