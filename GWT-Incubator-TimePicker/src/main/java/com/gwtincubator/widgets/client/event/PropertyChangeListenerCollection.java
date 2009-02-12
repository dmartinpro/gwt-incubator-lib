package com.gwtincubator.widgets.client.event;

import java.util.ArrayList;
import java.util.List;

/**
 * "ListenerCollection" helper.
 * @author David MARTIN
 *
 */
public class PropertyChangeListenerCollection {

	private final List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}
	
	public void firePropertyChangeEvent(final PropertyChangeSource source, final String propertyName,  
          final Object oldValue, final Object newValue) {  
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChanged(source, propertyName, oldValue, newValue);
		}
	}
	
}
