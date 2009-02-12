/**
 * 
 */
package com.gwtincubator.widgets.client.timepicker;

/**
 * Simple implementation able to manage time format like "HH:mm"
 * 
 * @author David MARTIN
 *
 */
public class SimpleTimeModelConverter implements TimeModelConverter {

	private static final String TIME_SEPARATOR = ":";

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
			int minute = Integer.parseInt(minutePart);
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
		if (model.getHour() < 10) {
			sb.append("0");
		}
		sb.append(model.getHour());
		sb.append(TIME_SEPARATOR);
		if (model.getMinute() < 10) {
			sb.append("0");
		}
		sb.append(model.getMinute());
		return sb.toString();
	}

}
