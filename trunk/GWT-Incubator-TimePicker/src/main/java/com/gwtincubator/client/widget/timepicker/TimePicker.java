package com.gwtincubator.client.widget.timepicker;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtincubator.client.event.PropertyChangeListener;
import com.gwtincubator.client.event.PropertyChangeListenerCollection;
import com.gwtincubator.client.event.PropertyChangeSource;
import com.gwtincubator.client.widget.button.SensitiveButton;
import com.gwtincubator.client.widget.button.TimeButton;
import com.gwtincubator.client.widget.label.WidgetLabel;

/**
 * This is a pure GWT Time Picker widget.
 * <br>
 * @author David MARTIN
 *
 */
public class TimePicker extends Composite implements ClickListener, PropertyChangeListener {

	private final Panel innerPanel = new HorizontalPanel();

	private final PopupPanel popupPanel = new PopupPanel(true);

	private final TimeModel timeModel = new TimeModel();

	// This is a temporary clone of the time model if restore is needed (closing the popup without clicking a value)
	private TimeModel timeModelClone;

	private TimePopupFlexTable timePopupFlexTable;

	private ScrollPanel popupScrollPanel;

	private TimeTextBox timeTextBox;

	private WidgetLabel timeBoxLabel;

	private String property;

	private boolean required = false;

	private boolean restoreModel = true;
	
	private boolean validateOnClickOnly = true;

	/**
	 * Default Constructor (without label)
	 */
	public TimePicker() {
		this(null);
	}

	public TimePicker(final String label) {
		this.initPopup();

		this.innerPanel.add(getTimeBoxLabel());
		this.innerPanel.add(getTimeTextBox());
		this.innerPanel.addStyleName("TimePicker");

		this.setLabel(label);
		this.initWidget(innerPanel);
	}

	protected void initPopup() {
		this.popupPanel.setVisible(false);
		this.popupPanel.addStyleName("DatePickerPopup");
		this.popupPanel.add(getTimePopupFlexTable());
		this.popupPanel.addPopupListener(new PopupListener() {
			public void onPopupClosed(final PopupPanel sender, final boolean autoClosed) {
				if (validateOnClickOnly && restoreModel) {
					final TimeModel cloneModel = TimePicker.this.timeModelClone;
					if (cloneModel != null) {
						TimePicker.this.timeModel.setTime(cloneModel.getHour(), cloneModel.getMinute());
					}
				}
				TimePicker.this.timeModelClone = null;
				restoreModel = true;
			}
		});
	}

	protected void hideOnClick() {
		this.restoreModel = false;
		this.popupPanel.hide();
	}

	protected TimePopupFlexTable getTimePopupFlexTable() {
		if (this.timePopupFlexTable == null) {
			this.timePopupFlexTable = new TimePopupFlexTable();
			this.timePopupFlexTable.addPropertyChangeListener(getTimeModel());
			this.timePopupFlexTable.addPropertyChangeListener(this);
			getTimeModel().addPropertyChangeListener(this.timePopupFlexTable);
		}
		return this.timePopupFlexTable;
	}

	protected ScrollPanel getPopupScrollPanel() {
		if (popupScrollPanel == null) {
			popupScrollPanel = new ScrollPanel();
		}
		return popupScrollPanel;
	}

	protected WidgetLabel getTimeBoxLabel() {
		if (timeBoxLabel == null) {
			timeBoxLabel = new WidgetLabel();
			timeBoxLabel.addStyleName("TimePickerLabel");
		}
		return timeBoxLabel;
	}

	protected TimeTextBox getTimeTextBox() {
		if (timeTextBox == null) {
			timeTextBox = new TimeTextBox();
			timeTextBox.setMaxLength(5);
			timeTextBox.setWidth("60px");
			timeTextBox.addStyleName("TimePickerTextField");
			final TextFieldFocusListener listener = new TextFieldFocusListener();
			timeTextBox.addFocusListener(listener);
			timeTextBox.addMouseListener(listener);
			getTimeModel().addPropertyChangeListener(timeTextBox);
		}
		return timeTextBox;
	}

	/* *************************************** */
	/* ***      Public methods...          *** */
	/* *************************************** */

	public String getValue() {
		return getTimeModel().toString();
	}

	public int getHour() {
		return getTimeModel().getHour();
	}

	public int getMinute() {
		return getTimeModel().getMinute();
	}

	public void setName(final String name) {
		getTimeTextBox().setName(name);
	}

	public String getName() {
		return getTimeTextBox().getName();
	}

	/**
	 * If no text is specified (ie null or empty), the label value is set to empty ("").
	 * @param text the text to display as a label
	 */
	public void setLabel(final String text) {
		getTimeBoxLabel().setText(text);
	}

	public void setWidth(final String width) {
		getTimeTextBox().setWidth(width);
	}

	public void setHeight(final String height) {
		getTimeTextBox().setHeight(height);
	}

	public void setTooltip(final String toolTip) {
		getTimeTextBox().setTitle(toolTip);
	}

	public void setReadOnly(final boolean readOnly) {
		getTimeTextBox().setReadOnly(readOnly);
	}

	public boolean isReadOnly() {
		return getTimeTextBox().isReadOnly();
	}

	public void setEnabled(final boolean enabled) {
		getTimeTextBox().setEnabled(enabled);
	}

	public boolean isEnabled() {
		return getTimeTextBox().isEnabled();
	}

	public void addListener(final FocusListener listener) {
		getTimeTextBox().addFocusListener(listener);
	}

	public void addModelListener(final PropertyChangeListener listener) {
		getTimeModel().addPropertyChangeListener(listener);
	}

	public String getProperty() {
		return this.property;
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isRequired() {
		return this.required;
	}

	public boolean isValid() {
		return false;
	}

	public void setAccessKey(char key) {
		getTimeTextBox().setAccessKey(key);
	}

	public void setInvalidHint(boolean invalid) {
	}

	public void setProperty(final String property) {
		this.property = property;
		getTimeTextBox().setName(property);
		InputElement.as(getElement()).setId(property);
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setTabIndex(int index) {
		getTimeTextBox().setTabIndex(index);
	}

	public int getTabIndex() {
		return getTimeTextBox().getTabIndex();
	}

	public void setValue(final String value) {
		// TODO
		throw new RuntimeException("not implemented");
	}

	public void setTime(int hour, int minute) {
		try {
			getTimeModel().setTime(hour, minute);
		} catch (IllegalArgumentException e) {
			Window.alert("Error while setting TimePicker value : " + e.getMessage());
		}
	}

	/**
	 * @return the validateOnClickOnly
	 */
	public boolean isValidateOnClickOnly() {
		return validateOnClickOnly;
	}

	/**
	 * @param validateOnClickOnly the validateOnClickOnly to set
	 */
	public void setValidateOnClickOnly(boolean validateOnClickOnly) {
		this.validateOnClickOnly = validateOnClickOnly;
	}

	public void setFocus(final boolean focused) {
		getTimeTextBox().setFocus(focused);
	}

	private TimeModel getTimeModel() {
		return this.timeModel;
	}

	public void onClick(final Widget sender) {
		// TODO
	}

	public void propertyChanged(final PropertyChangeSource source,
			final String propertyName, final Object oldValue, final Object newValue) {

		// This component listens to TimePopupFlexTable visibility property modification
		if (source != null && source instanceof TimePopupFlexTable) {
			if ("visibility".equals(propertyName)) {
				// on visibility modification, tell the component to hide the popup panel
				hideOnClick();
			}
		}
	}

	/* ****************************************************** */
	/* ** Some inner classes...                            ** */
	/* ****************************************************** */

	private final class TextFieldFocusListener implements FocusListener, MouseListener {

		private void process(final Widget sender) {
			// If a popup is still open (ie clone is not null)... return !
			if (timeModelClone != null) {
				return;
			}

			// backup the model before modifying it...
			timeModelClone = new TimeModel(getTimeModel().getHour(), getTimeModel().getMinute());
			getTimePopupFlexTable().initialize(getTimeModel());
			final int offsetHeight = sender.getOffsetHeight();

			final int absoluteLeft = sender.getAbsoluteLeft();
			final int absoluteTop = sender.getAbsoluteTop();

			popupPanel.setPopupPosition(absoluteLeft, absoluteTop + offsetHeight);

			popupPanel.setVisible(false);
			popupPanel.show();
			popupPanel.setVisible(true);
		}
		
		public void onFocus(final Widget sender) {
			process(sender);
		}

		public void onLostFocus(final Widget sender) {
			// nothing to do
		}

		public void onMouseDown(final Widget sender, int x, int y) {
			// nothing to do
		}

		public void onMouseEnter(final Widget sender) {
			process(sender);
		}

		public void onMouseLeave(final Widget sender) {
			// nothing to do
		}

		public void onMouseMove(final Widget sender, final int x, final int y) {
			// nothing to do
		}

		public void onMouseUp(final Widget sender, final int x, final int y) {
			// nothing to do
		}

	}

	/**
	 * This is the "mode, hour and minute" buttons container, embedded into the popup panel.
	 * @author David MARTIN
	 *
	 */
	private class TimePopupFlexTable extends FlexTable implements PropertyChangeSource, PropertyChangeListener {

		private final PropertyChangeListenerCollection listeners = new PropertyChangeListenerCollection();

		private final TimePanelState state = new TimePanelState();

		private final MouseListener modeListener = new ModeMouseListener();

		private final MouseListener hourListener = new HourMouseListener();

		private final MouseListener minuteListener = new MinuteMouseListener();

		private TimeButton[] hourButtons = new TimeButton[24];

		private TimeButton[] minuteButtons = new TimeButton[4];

		private SensitiveButton buttonAm;

		private SensitiveButton buttonPm;


		public TimePopupFlexTable() {
			super();
			this.addStyleName("TimePickerPopup");
			this.setWidget(0, 0, getButtonAm());
			this.setWidget(0, 1, getButtonPm());
		}

		public void addPropertyChangeListener(final PropertyChangeListener listener) {
			listeners.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(final PropertyChangeListener listener) {
			listeners.removePropertyChangeListener(listener);
		}

		public void propertyChanged(final PropertyChangeSource source,
				final String propertyName, final Object oldValue, final Object newValue) {

			if (source != null && source instanceof TimeModel) {
				final TimeModel model = (TimeModel) source;

				initialize(model);
			}
		}

		/* package */void initialize(final TimeModel model) {
			getState().setHourValue(model.getHour());
			getState().setMinuteValue(model.getMinute());
			if (model.getHour() < 12) {
				getState().setMode(TimeMode.AM_MODE);
				getState().setHourIndex(model.getHour());
				getButtonAm().addStyleName("timeButton-focus");
				getButtonPm().removeStyleName("timeButton-focus");
			} else {
				getState().setMode(TimeMode.PM_MODE);
				getState().setHourIndex(model.getHour()-11);
				getButtonPm().addStyleName("timeButton-focus");
				getButtonAm().removeStyleName("timeButton-focus");
			}
			getState().setMinuteIndex(getState().getHourIndex() + getState().getMinuteValue()/15);

			displayHours(getState().getMode());
			displayMinutes(getState().getHourIndex());
			
		}

		private TimePanelState getState() {
			return state;
		}

		private void onModeChanged(final TimeMode mode) {
			if (mode == TimeMode.AM_MODE) {
				getButtonAm().addStyleName("timeButton-focus");
				getButtonPm().removeStyleName("timeButton-focus");
			} else {
				getButtonPm().addStyleName("timeButton-focus");
				getButtonAm().removeStyleName("timeButton-focus");
			}
		}

		private void displayHours(final TimeMode mode) {
			if (mode == TimeMode.AM_MODE) {
				if (this.getRowCount() > 1 && this.getCellCount(1) > 12) {
					for (int i = this.getCellCount(1)-1; i >= 12; i--) {
						this.removeCell(1, i);
					}
				}
				TimeButton bt = null;
				for (int i = 0; i < 12; i++) {
					bt = getHourButtons()[i];
					bt.removeStyleName("timeButton-focus");
					this.setWidget(1, i, bt);
				}
			} else {
				if (this.getRowCount() > 1) {
					this.clearCell(1, 0);
				}
				TimeButton bt = null;
				for (int i = 0; i < 12; i++) {
					bt = getHourButtons()[i+12];
					bt.removeStyleName("timeButton-focus");
					this.setWidget(1, i+1, bt);
				}
			}
			final TimeButton bt = getHourButtons()[getState().getAbsoluteHourIndex()];
			Window.alert(""+getState().getAbsoluteHourIndex());
			bt.addStyleName("timeButton-focus");
		}

		private void displayMinutes(final int refIndex) {
			if (refIndex < 0) {
				if (this.getRowCount() == 2) {this.removeRow(2);}
				return;
			}

			if (isCellPresent(2, 0)) {
				int i = 0;
				while (isCellPresent(2, i)) {
					this.clearCell(2, i++);
				}
			}
			TimeButton bt = null;
			for (int i = 0; i < 4; i++) {
				bt = getMinuteButtons()[i];
				bt.removeStyleName("timeButton-focus");
				this.setWidget(2, i+refIndex, bt);
			}
			bt = getMinuteButtons()[getState().getRelativeMinuteIndex()];
			bt.addStyleName("timeButton-focus");
		}

		private int getHourButtonIndex(final TimeButton tb) {
			if (this.getRowCount() == 1) {
				return -1;
			}

			int index = -1;
			for (int i = 0; i < this.getCellCount(1); i++) {
				if (this.getWidget(1, i) == tb) {
					index = i;
					break;
				}
			}
			return index;
		}

		/**
		 * @return the buttonAm
		 */
		protected Button getButtonAm() {
			if (buttonAm == null) {
				buttonAm = new SensitiveButton("am");
				buttonAm.addMouseListener(modeListener);
			}
			return buttonAm;
		}

		/**
		 * @return the buttonPm
		 */
		protected Button getButtonPm() {
			if (buttonPm == null) {
				buttonPm = new SensitiveButton("pm");
				buttonPm.addMouseListener(modeListener);
			}
			return buttonPm;
		}

		protected TimeButton[] getHourButtons() {
			if (hourButtons[0] == null) {
				for (int i = 0; i < 24; i++) {
					hourButtons[i] = new TimeButton(i);
					hourButtons[i].addMouseListener(hourListener);
				}
			}
			return this.hourButtons;
		}

		protected TimeButton[] getMinuteButtons() {
			if (minuteButtons[0] == null) {
				for (int i = 0; i < 4; i++) {
					minuteButtons[i] = new TimeButton(i*15);
					minuteButtons[i].addMouseListener(minuteListener);
				}
			}
			return this.minuteButtons;
		}

		private class ClickMouseListener extends MouseListenerAdapter {
			public void onMouseUp(final Widget sender, int x, int y) {
				TimePopupFlexTable.this.listeners.firePropertyChangeEvent(TimePopupFlexTable.this, "visibility", false, true);
			}
		}

		private class ModeMouseListener extends ClickMouseListener {
			public void onMouseEnter(final Widget sender) {
				final Button bt = (Button) sender;
				final String text = bt.getText();
				final TimeMode mode = TimeMode.getTimeMode(text);
				getState().setMode(mode);
				onModeChanged(mode);
				displayHours(mode);
			}
		}

		private class HourMouseListener extends ClickMouseListener {
			public void onMouseEnter(final Widget sender) {
				final TimeButton tb = (TimeButton) sender;
				int index = getHourButtonIndex(tb);
				displayMinutes(index);
				getState().setHourIndex(index);
				getState().setHourValue(tb.getValue());
			}
		}

		private class MinuteMouseListener extends ClickMouseListener {
			public void onMouseEnter(final Widget sender) {
				final TimeButton tb = (TimeButton) sender;
				int index = getHourButtonIndex(tb);
				getState().setMinuteIndex(index);
				getState().setMinuteValue(tb.getValue());
			}
		}

		private class TimePanelState {

			private TimeMode mode = TimeMode.AM_MODE;
			private int hourIndex = 0;
			private int minuteIndex = 0;
			private int hourValue = 0;
			private int minuteValue = 0;

			/**
			 * @return the mode
			 */
			protected TimeMode getMode() {
				return mode;
			}
			/**
			 * @param mode the mode to set
			 */
			protected void setMode(final TimeMode mode) {
				TimeMode oldValue = this.mode;
				this.mode = mode;
				if (oldValue != mode) {
					TimePopupFlexTable.this.listeners.firePropertyChangeEvent(TimePopupFlexTable.this, "mode", oldValue, mode);
				}
			}
			/**
			 * @return the hourIndex
			 */
			protected int getHourIndex() {
				return hourIndex;
			}
			/**
			 * 
			 * @return hour index with 24H increment if needed
			 */
			protected int getAbsoluteHourIndex() {
				if (this.mode == TimeMode.PM_MODE) {
					return this.hourIndex+11;
				} else {
					return this.hourIndex;
				}
			}
			/**
			 * @param hourIndex the hourIndex to set
			 */
			protected void setHourIndex(int hourIndex) {
				int oldValue = this.hourIndex;
				this.hourIndex = hourIndex;
				if (oldValue != hourIndex) {
					TimePopupFlexTable.this.listeners.firePropertyChangeEvent(TimePopupFlexTable.this, "hourIndex", oldValue, hourIndex);
				}
			}
			protected int getRelativeMinuteIndex() {
				return getMinuteValue() / 15;
			}
			/**
			 * @return the minuteIndex
			 */
			protected int getMinuteIndex() {
				return minuteIndex;
			}
			/**
			 * @param minuteIndex the minuteIndex to set
			 */
			protected void setMinuteIndex(int minuteIndex) {
				int oldValue = this.minuteIndex;
				this.minuteIndex = minuteIndex;
				if (oldValue != minuteIndex) {
					TimePopupFlexTable.this.listeners.firePropertyChangeEvent(TimePopupFlexTable.this, "minuteIndex", oldValue, minuteIndex);
				}
			}
			/**
			 * @return the hourValue
			 */
			protected int getHourValue() {
				return hourValue;
			}
			/**
			 * @param hourValue the hourValue to set
			 */
			protected void setHourValue(int hourValue) {
				if (hourValue < 0 || hourValue > 23) {
					return;
				}
				int oldValue = this.hourValue;
				this.hourValue = hourValue;
				if (oldValue != hourValue) {
					TimePopupFlexTable.this.listeners.firePropertyChangeEvent(TimePopupFlexTable.this, "hourValue", oldValue, hourValue);
				}
			}
			/**
			 * @return the minuteValue
			 */
			protected int getMinuteValue() {
				return minuteValue;
			}
			/**
			 * @param minuteValue the minuteValue to set
			 */
			protected void setMinuteValue(int minuteValue) {
				if (minuteValue < 0 || minuteValue > 59) {
					return;
				}
				int oldValue = this.minuteValue;
				this.minuteValue = minuteValue;
				if (oldValue != minuteValue) {
					TimePopupFlexTable.this.listeners.firePropertyChangeEvent(TimePopupFlexTable.this, "minuteValue", oldValue, minuteValue);
				}
			}

			public String toString() {
				final StringBuffer sb = new StringBuffer();
				sb.append("mode:").append(mode).append(" | ");
				sb.append("hour:").append(hourValue).append(" | ");
				sb.append("minute:").append(minuteValue).append(" | ");
				sb.append("hindex:").append(hourIndex).append(" | ");
				sb.append("mindex:").append(minuteIndex).append(" | ");
				return sb.toString();
			}
		}
	}

}
