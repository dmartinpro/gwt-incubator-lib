/**
 * 
 */
package com.gwtincubator.widgets.client.timepicker;

/**
 * AM/PM implementation able to manage time format like "HH:mm" or "HH:mm {am|pm}"
 * 
 * @author David MARTIN
 *
 */
public class AmPmTimeModelConverter implements TimeModelConverter {

	private static final String TIME_SEPARATOR = ":";

	private static final String AM = "am";

	private static final String PM = "pm";

	/**
	 * @see com.gwtincubator.widgets.client.timepicker.TimeModelConverter#toModel(java.lang.String)
	 */
	public TimeModel toModel(final String time) {
		if (time == null || time.indexOf(TIME_SEPARATOR) < 0) {
			return null;
		}
		TimeModel model = null;

		int index = time.indexOf(TIME_SEPARATOR);
		final String hourPart = time.substring(0, index);
		final String minutePart = time.substring(index+1);
		try {
			int hour = Integer.parseInt(hourPart);
			// minutepart can embed a AM/PM information
			boolean pm = minutePart.contains(PM);
			if (pm) {
				if (hour > 12) {
					throw new IllegalArgumentException("PM Time value can't exceed 12 for the hour value");
				} else if (hour == 12) {
					hour = 0;
				} else {
					hour += 12;
				}
			}
			int minute = Integer.parseInt(minutePart.substring(0, 2));
			model = new TimeModel(hour, minute);
		} catch (NumberFormatException nfe) {
			model = null;
		}
		return model;
	}

	/**
	 * @see com.gwtincubator.widgets.client.timepicker.TimeModelConverter#toString(com.gwtincubator.widgets.client.timepicker.TimeModel)
	 */
	public String toString(final TimeModel model) {
		if (model == null) {
			return null;
		}
		final StringBuffer sb = new StringBuffer();
		int hour = model.getHour();
		String ampmSuffix = AM;
		if (hour > 12) {
			hour -= 12;
			ampmSuffix = PM;
		} else if (hour == 12) { // noon
			ampmSuffix = PM;
		} else if (hour == 0) {
			hour = 12;
		}

		if (hour < 10) {
			sb.append("0");
		}
		sb.append(hour);
		sb.append(TIME_SEPARATOR);
		if (model.getMinute() < 10) {
			sb.append("0");
		}
		sb.append(model.getMinute());
		sb.append(" ").append(ampmSuffix);
		return sb.toString();
	}

}
