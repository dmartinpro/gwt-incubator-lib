package com.gwtincubator.client.widget.label;

import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple label for widgets
 * @author David MARTIN
 */
public class WidgetLabel extends Widget {

	public static final String DEFAULT_SEPARATOR = "&nbsp;:&nbsp;";

	/** optional string between the label and the field associated. */
	private String separator = DEFAULT_SEPARATOR;

	/** the value displayed */
	private String text;

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
		this.text = text;
		setInnerHTML(this.text, this.separator);
	}

	/**
	 * The property to bound to.
	 * @param property a property
	 */
	public void setProperty(final String property) {
		LabelElement.as(getElement()).setHtmlFor(property);
	}

	/**
	 * @return the separator
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * @param separator the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
		setInnerHTML(this.text, this.separator);
	}

	protected void setInnerHTML(final String text, final String separator) {
		final StringBuffer sb = new StringBuffer(text == null ? "" : text).append(separator == null ? "" : separator);
		LabelElement.as(getElement()).setInnerHTML(sb.toString());
	}
}
