/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package jsystem.publisher.plugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import jsystem.runner.ErrorLevel;
import jsystem.treeui.error.ErrorPanel;
import jsystem.utils.StringUtils;

/**
 * a panel for the test properties in the publisher tab
 * 
 * @author nelly_c
 * 
 */
public class TestProperties extends JTable implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Properties properties;
	private static Logger log = Logger.getLogger(TestProperties.class.getName());
	private JTable propPanel;

	public TestProperties() {
		super();
		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(100, 100));
		setBackground(new Color(0xf6, 0xf6, 0xf6));
		init();
	}

	/**
	 * initialize the panel
	 * 
	 */
	private void init() {
		
		propPanel = new JTable();
		propPanel.setBackground(new Color(0xf6, 0xf6, 0xf6));
		propPanel.setLayout(new BoxLayout(propPanel, BoxLayout.Y_AXIS));

	}

	/**
	 * signal that the properties have changed and the panel should be
	 * reconstructed
	 * 
	 */
	private void updatePanel() {
		propPanel.removeAll();
		Enumeration<Object> e = properties.keys();
		String key, value;
		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			value = properties.getProperty(key);
			propPanel.add(getPropAsPanel(key, value));
		}
		//int num = propPanel.getComponentCount();
		this.revalidate();
	}

	/**
	 * creates a JSplitPane of a property couple
	 * 
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return a JSplitPane of the property
	 */
	private JSplitPane getPropAsPanel(String key, String value) {
		JTextField keyField = new JTextField(key);
		keyField.setColumns(20);
		JTextField valField = new JTextField(value);
		valField.setColumns(25);
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, keyField, valField);
		sp.setDividerLocation(160);
		sp.setDividerSize(0);
		return sp;
	}

	/**
	 * get the current properties on the panel
	 * 
	 * @return test properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * set this panel properties
	 * 
	 * @param propString
	 *            the properties string
	 */
	public void setProperties(String propString) {
		if (propString == null)
			propString = "";
		properties = StringUtils.stringToProperties(propString);
		updatePanel();
	}

	/**
	 * saves all the properties from the text fields. if a field key is empty
	 * (meaning key.trim() equals "" then it is not added)
	 * 
	 */
	public void saveAllTextFields() {
		log.log(Level.INFO, "IN FUNC");
		int num = propPanel.getRowCount() - 1;
		properties.clear();
		String emptyProperties = "";
		String characterKeys = "";
		for (int i = 0; i < num; i++) {
			log.log(Level.INFO, "IN FIELD " + num);
			Object key = propPanel.getValueAt(i, 0);
			Object value = propPanel.getValueAt(i, 1);
			if (key != null && value != null) {
				log.log(Level.INFO, "KEY & VALUE != NULL");
				String keyS = String.valueOf(key);
				String valueS = String.valueOf(value);
				// key or value are empty
				if (keyS.trim().equals("") || valueS.trim().equals("")) {
					emptyProperties += "\n" + keyS + "=" + valueS;
				}
				// dont allow chars in key or value
				else if (StringUtils.hasNotAllowedSpecialCharacters(keyS)
						|| StringUtils.hasNotAllowedSpecialCharacters(valueS)) {
					characterKeys += "\n" + keyS + "=" + valueS;
				// Add property if has a value
				} else {
						properties.setProperty(keyS, valueS);
				}
			}
		}
		if (!emptyProperties.equals("")) {
			ErrorPanel.showErrorDialog("Property with Empty keys/values were not added", "empty values were found in the following Properties: "
					+ emptyProperties, ErrorLevel.Warning);
		}
		if (!characterKeys.equals("")) {
			ErrorPanel.showErrorDialog("Properties are not allowed Special Characters from "
					+ StringUtils.notAllowedCharacters, "found at properties: " + characterKeys, ErrorLevel.Warning);
		}
		//updatePanel();
		//TestInfoPanel.updateFile();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

