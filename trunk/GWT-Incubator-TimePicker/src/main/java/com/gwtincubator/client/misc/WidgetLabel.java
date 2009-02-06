package com.gwtincubator.client.misc;

import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple label for widgets
 * @author David MARTIN
 */
public class WidgetLabel extends Widget {

	/**
	 * Constructor
	 */
	public WidgetLabel() {
		final Element label = DOM.createLabel();
		setElement(label);
	}

	/**
	 * Constructor
	 * @param text set the text label
	 */
	public WidgetLabel(final String text) {
		this(text, null);
	}

	/**
	 * Constructor
	 * @param text text
	 * @param property property bound
	 */
	public WidgetLabel(final String text, final String property) {
		this();
		setText(text);
		setProperty(property);
	}

	/**
	 * Set the text
	 * @param text text
	 */
	public void setText(final String text) {
		LabelElement.as(getElement()).setInnerHTML(text);
	}

	/**
	 * The property to bound to.
	 * @param property a property
	 */
	public void setProperty(final String property) {
		LabelElement.as(getElement()).setHtmlFor(property);
	}

}
