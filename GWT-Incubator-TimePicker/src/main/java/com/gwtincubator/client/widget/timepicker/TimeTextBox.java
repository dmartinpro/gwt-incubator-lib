package com.gwtincubator.client.widget.timepicker;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.TextBox;
import com.gwtincubator.client.event.PropertyChangeListener;
import com.gwtincubator.client.event.PropertyChangeSource;

/**
 * This is a dedicated TextBox to display "Time" data (Hours and minutes).
 * @author David MARTIN
 *
 */
/* package */ class TimeTextBox extends TextBox implements SourcesMouseEvents, PropertyChangeListener {

	private static final String DEFAULT_TIME_SEPARATOR = " : ";

	/** Mouse listeners collection. */
	private MouseListenerCollection mouseListeners = new MouseListenerCollection();

	public TimeTextBox() {
		super();
		init();
	}

	public TimeTextBox(final Element element) {
		super(element);
		init();
	}

	private void init() {
		this.unsinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
		sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
	}

	public void setText(final int hour, final int minute) {
		final StringBuffer text = new StringBuffer();
		if (hour < 10) {
			text.append("0");
		}
		text.append(hour);
		text.append(DEFAULT_TIME_SEPARATOR);
		if (minute < 10) {
			text.append("0");
		}
		text.append(minute);

		this.setText(text.toString());
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

	public void propertyChanged(final PropertyChangeSource source,
			final String propertyName, final Object oldValue, final Object newValue) {

		if (source != null && source instanceof TimeModel) {
			final TimeModel timeModel = (TimeModel) source;
			this.setText(timeModel.getHour(), timeModel.getMinute());
		}
	}

}