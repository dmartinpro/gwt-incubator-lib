package com.gwtincubator.widgets.client.timepicker;

/**
 * 
 * @author David MARTIN
 *
 */
public interface TimeModelConverter {
	
	String toString(final TimeModel model);
	
	TimeModel toModel(final String time);

}
