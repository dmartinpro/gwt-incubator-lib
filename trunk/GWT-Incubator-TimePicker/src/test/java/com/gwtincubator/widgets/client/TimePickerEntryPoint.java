package com.gwtincubator.widgets.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtincubator.widgets.client.event.PropertyChangeListener;
import com.gwtincubator.widgets.client.event.PropertyChangeSource;
import com.gwtincubator.widgets.client.timepicker.TimePicker;

/**
 * 
 * TimePicker simple showcase...
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
		final HorizontalPanel listenersPanel = new HorizontalPanel();
		listenersPanel.setSpacing(100);

		final TimePicker timePickerField = new TimePicker();
		timePickerField.setLabel("Time Picker");
		timePickerField.setTime(0, 59);
		timePickerField.setWidth("80px");
		timePickerField.setTooltip("Pick a time value...");
		timePickerField.setAccessKey('t');
		panel.add(timePickerField);
		final TextBox tb = new TextBox();
		tb.setReadOnly(true);
		tb.setText(timePickerField.getValue());
		listenersPanel.add(new Label("Internal model value : "));
		listenersPanel.add(tb);

		timePickerField.addModelListener(new PropertyChangeListener() {
			public void propertyChanged(final PropertyChangeSource source,
					final String propertyName, final Object oldValue, final Object newValue) {
				if (source != null) {
					tb.setText(source.toString());
				}
			}
		});

		final Button button = new Button("Get TimePicker's value");
		button.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Window.alert("TimePicker's value is : " + timePickerField.getValue());
			}
		});
		commandPanel.add(button);

		mainPanel.add(commandPanel);
		mainPanel.add(panel);
		mainPanel.add(listenersPanel);
		RootPanel.get().add(mainPanel);
	}

}
