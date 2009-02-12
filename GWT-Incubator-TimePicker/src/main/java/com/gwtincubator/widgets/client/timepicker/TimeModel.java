package com.gwtincubator.widgets.client.timepicker;

import com.gwtincubator.widgets.client.event.PropertyChangeListener;
import com.gwtincubator.widgets.client.event.PropertyChangeListenerCollection;
import com.gwtincubator.widgets.client.event.PropertyChangeSource;

/**
 * Time (understand "hour & minute") representation.
 */
/* package */ class TimeModel implements PropertyChangeSource, PropertyChangeListener {

	/** String representation separator. */
	private static final String SEPARATOR = ":";

	/** Property change listeners. */
	private final PropertyChangeListenerCollection listeners = new PropertyChangeListenerCollection();

	/** Absolute hour. NOTE : In a consistent state, 0 <= hour < 23 */
	private int hour = Integer.MIN_VALUE;

	/** Minute. NOTE : In a consistent state, 0 <= minute < 59 */
	private int minute = Integer.MIN_VALUE;

	public TimeModel() {
	}

	public TimeModel(final int hour, final int minute) {
		this.setTime(hour, minute);
	}

	public void setTime(final int hour, final int minute) {
		setHour(hour);
		setMinute(minute);
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		if (hour < 0 || hour > 23) {
			throw new IllegalArgumentException("Hour (" + hour + ") can't be out of [0;23]");
		}
		int oldValue = this.hour;
		this.hour = hour;
		if (this.hour != oldValue) {
			listeners.firePropertyChangeEvent(this, "hour", oldValue, hour);
		}
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		if (minute < 0 || minute > 59) {
			throw new IllegalArgumentException("Minute (" + minute + ") can't be out of [0;59]");
		}
		int oldValue = this.minute;
		this.minute = minute;
		if (this.minute != oldValue) {
			listeners.firePropertyChangeEvent(this, "minute", oldValue, minute);
		}
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		listeners.removePropertyChangeListener(listener);
	}

	public void propertyChanged(final PropertyChangeSource source,
			final String propertyName, final Object oldValue, final Object newValue) {

		if (source != null) {
			if ("hourValue".equals(propertyName)) {
				this.setHour((Integer) newValue);
			} else if ("minuteValue".equals(propertyName)) {
				this.setMinute((Integer) newValue);
			}
		}
	}

	public String toString() {
		return new StringBuffer("")
			.append(this.getHour())
			.append(SEPARATOR)
			.append(this.getMinute())
			.toString();
	}

}
