package com.gwtincubator.widgets.client.event;

/**
 * Simple listener interface for property modifications
 * @author David MARTIN
 *
 */
public interface PropertyChangeListener {

	void propertyChanged(final PropertyChangeSource source, final String propertyName,
			final Object oldValue, final Object newValue);

}
