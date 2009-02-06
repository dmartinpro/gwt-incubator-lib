package com.gwtincubator.client.event;

/**
 * Simple interface for every object able to throw property modifications events
 * @author David MARTIN
 *
 */
public interface PropertyChangeSource {

	void addPropertyChangeListener(final PropertyChangeListener listener);

	void removePropertyChangeListener(final PropertyChangeListener listener);

}
