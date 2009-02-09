package com.gwtincubator.client.widget.button;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesMouseEvents;

public class SensitiveButton extends Button implements SourcesMouseEvents {

	private MouseListenerCollection mouseListeners = new MouseListenerCollection();

	public SensitiveButton() {
		super();
		init();
	}

	public SensitiveButton(final Element element) {
		super(element);
		init();
	}

	public SensitiveButton(final String html, final ClickListener listener) {
		super(html, listener);
		init();
	}

	public SensitiveButton(final String html) {
		super(html);
		init();
	}

	private void init() {
		this.setWidth("30px");
		this.unsinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
		sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
	}

	public void addMouseListener(final MouseListener listener) {
		mouseListeners.add(listener);
	}

	public void removeMouseListener(final MouseListener listener) {
		mouseListeners.remove(listener);
	}

	public void onBrowserEvent(final Event event) {
		super.onBrowserEvent(event);
		switch (DOM.eventGetType(event)) {
		case Event.ONMOUSEDOWN:
		case Event.ONMOUSEUP:
		case Event.ONMOUSEMOVE:
		case Event.ONMOUSEOVER: {
			if (mouseListeners != null)
				mouseListeners.fireMouseEvent(this, event);
			break;
		}
		case Event.ONMOUSEOUT:
		}
	}		
}
