/*
 * Copyright 2005-2010 Ignis Software Tools Ltd. All rights reserved.
 */
package jsystem.publisher.plugin;

import javax.swing.tree.MutableTreeNode;

import jsystem.treeui.publisher.ElementNode;

import org.w3c.dom.Element;

/**
 * @author guy.arieli
 * 
 */
public class TestNode extends TestStepNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 302214892447465169L;

	/**
	 * @param element
	 * @param parent
	 * @param viewMode
	 */
	public TestNode(Element element, MutableTreeNode parent, int viewMode) {
		super(element, parent, viewMode);
	}

	public void warningError(String message) {
		if (getStatus() == TEST_FAIL) {
			setStatus(TEST_WARNING);
			setMessage(message);
			((ElementNode) parent).recalcStatus();
		}
	}

	public void setMessage(String message) {
		super.setMessage(message);
		getElement().setAttribute("message", message);
	}

}
