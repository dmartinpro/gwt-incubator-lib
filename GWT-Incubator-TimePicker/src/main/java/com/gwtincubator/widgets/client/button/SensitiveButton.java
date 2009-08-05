package com.gwtincubator.widgets.client.button;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.user.client.ui.Button;

/**
 * Sensitive button is useless with GWT 1.6+
 * @author David MARTIN
 *
 */
public class SensitiveButton extends Button implements HasAllMouseHandlers {

	public SensitiveButton() {
		super();
		init();
	}

	public SensitiveButton(final Element element) {
		super(element);
		init();
	}

	public SensitiveButton(final String html, final ClickHandler handler) {
		super(html, handler);
		init();
	}

	public SensitiveButton(final String html) {
		super(html);
		init();
	}

	private void init() {
		this.setWidth("30px");
	}

}
