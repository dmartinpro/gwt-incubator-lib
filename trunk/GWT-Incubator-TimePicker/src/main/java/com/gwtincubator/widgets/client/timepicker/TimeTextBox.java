package com.gwtincubator.widgets.client.timepicker;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.TextBox;
import com.gwtincubator.widgets.client.event.PropertyChangeListener;
import com.gwtincubator.widgets.client.event.PropertyChangeSource;

/**
 * This is a dedicated TextBox to display "Time" data (Hours and minutes).
 * @author David MARTIN
 *
 */
/* package */ class TimeTextBox extends TextBox implements SourcesMouseEvents, PropertyChangeListener {

	/** Mouse listeners collection. */
	private MouseListenerCollection mouseListeners = new MouseListenerCollection();

	private TimeModelConverter timeModelConverter;

	public TimeTextBox() {
		super();
		init();
	}

	public TimeTextBox(final Element element) {
		super(element);
		init();
	}

	/**
	 * @return the timeModelConverter
	 */
	public TimeModelConverter getTimeModelConverter() {
		return timeModelConverter;
	}

	/**
	 * @param timeModelConverter the timeModelConverter to set
	 */
	public void setTimeModelConverter(TimeModelConverter timeModelConverter) {
		this.timeModelConverter = timeModelConverter;
	}

	private void init() {
		this.setMaxLength(10);
		this.setVisibleLength(10);
		this.unsinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
		sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
	}

	public void setText(final TimeModel model) {
		setText(getTimeModelConverter().toString(model));
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
			this.setText((TimeModel) source);
		}
	}

}