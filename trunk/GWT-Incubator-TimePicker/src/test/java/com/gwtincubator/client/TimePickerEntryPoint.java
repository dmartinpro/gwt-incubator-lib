package com.gwtincubator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtincubator.client.timepicker.TimePicker;

/**
 * 
 * Sample entry point
 * 
 * @author David MARTIN
 *
 */
public class TimePickerEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		final VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setSpacing(10);

		final HorizontalPanel commandPanel = new HorizontalPanel();
		commandPanel.setSpacing(5);
		final HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(0);

		final TimePicker datePickerField = new TimePicker();
		datePickerField.setLabel("Date Picker");
		datePickerField.setTime(11, 45);
		datePickerField.setWidth("200px");
		panel.add(datePickerField);

		final Button button = new Button("Get DatePicker's time value");
		button.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Window.alert("datePicker field's value is : " + datePickerField.getValue());
			}
		});
		commandPanel.add(button);

		mainPanel.add(commandPanel);
		mainPanel.add(panel);
		RootPanel.get().add(mainPanel);
	}

}
