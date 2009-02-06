package com.gwtincubator.client.timepicker;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtincubator.client.event.PropertyChangeListener;
import com.gwtincubator.client.event.PropertyChangeListenerCollection;
import com.gwtincubator.client.event.PropertyChangeSource;
import com.gwtincubator.client.event.SelectionListener;
import com.gwtincubator.client.misc.WidgetLabel;

/**
 * This is a pure GWT Time Picker widget.
 * <br>
 * @author David MARTIN
 *
 */
public class TimePicker extends Composite implements ClickListener {

	public static final String DEFAULT_SEPARATOR = "&nbsp;:&nbsp;";

	public static final int MIN_POPUP_WIDTH = 40;

	public static final int MIN_POPUP_HEIGHT = 30;

	public static final int MAX_POPUP_WIDTH = 200;

	public static final int MAX_POPUP_HEIGHT = 200;

	public static final int DEFAULT_POPUP_WIDTH = 200;

	public static final int DEFAULT_POPUP_HEIGHT = 100;

	private final Panel innerPanel = new HorizontalPanel();

	private final PopupPanel popupPanel = new PopupPanel(true);

	private TimePopupFlexTable timePopupFlexTable;

	private ScrollPanel popupScrollPanel;

	private TimeTextBox timeTextBox;

	private WidgetLabel timeBoxLabel;

	private String separator = DEFAULT_SEPARATOR;

	private final TimeModel timeModel = new TimeModel();

	private String property;

	private boolean required = false;

	private boolean manualResized = false;

	public TimePicker() {
		this(null);
	}

	public TimePicker(final String label) {
		initPopup();

		innerPanel.add(getTimeBoxLabel());
		innerPanel.add(getTimeTextBox());

		setLabel(label);
		initWidget(innerPanel);
	}

	protected void initPopup() {
		popupPanel.addStyleName("DatePickerPopup");
		popupPanel.add(getTimePopupFlexTable());
		popupPanel.addPopupListener(new PopupListener() {
			public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
				// could help to manage dirty state of text field
			}
		});
	}

	protected void hidePopup() {
		popupPanel.hide();
	}

	protected TimePopupFlexTable getTimePopupFlexTable() {
		if (timePopupFlexTable == null) {
			timePopupFlexTable = new TimePopupFlexTable();
			timePopupFlexTable.addPropertyChangeListener(getTimeModel());
			getTimeModel().addPropertyChangeListener(timePopupFlexTable);
		}
		return timePopupFlexTable;
	}

	protected ScrollPanel getPopupScrollPanel() {
		if (popupScrollPanel == null) {
			popupScrollPanel = new ScrollPanel();
		}
		return popupScrollPanel;
	}

	protected void reset() {
		getTimeTextBox().setText("");
	}

	private void resyncSize() {
		// do not resync if widget was resized by the user
		if (manualResized) {
			return;
		}

		// no need currently...
//		int gridHeight = getTimePopupFlexTable().getOffsetHeight();
//		int gridWidth = getTimePopupFlexTable().getOffsetWidth();
	}

	protected void onAttach() {
		super.onAttach();
		resyncSize();
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
			TextFieldFocusListener listener = new TextFieldFocusListener();
			timeTextBox.addFocusListener(listener);
			timeTextBox.addMouseListener(listener);
			getTimeModel().addPropertyChangeListener(timeTextBox);
		}
		return timeTextBox;
	}

	private void resizePopup(int width, int height) {
		int _width = MIN_POPUP_WIDTH;
		int _height = MIN_POPUP_HEIGHT;
		if (width < MIN_POPUP_WIDTH) {
			_width = MIN_POPUP_WIDTH;
		} else {
			_width = width;
		}

		if (height < MIN_POPUP_HEIGHT) {
			_height = MIN_POPUP_HEIGHT;
		} else {
			_height = height;
		}
		popupPanel.setPixelSize(_width, _height+16); // Magic Numbers : XXX fix this ASAP
		popupScrollPanel.setWidth("100%");
		popupScrollPanel.setHeight(_height + "px");
		popupScrollPanel.setScrollPosition(0);
	}


	public void setPopupSize(int width, int height) {
		resizePopup(width, height);
	}

	/* *************************************** */
	/* ***      Public methods asked...    *** */
	/* *************************************** */
	public String getValue() {
		return getTimeModel().getHour() + ":" + getTimeModel().getMinute();
	}

	public void setName(final String name) {
		getTimeTextBox().setName(name);
	}

	public String getName() {
		return getTimeTextBox().getName();
	}

	public void setLabel(final String text) {
		final StringBuffer sb = new StringBuffer(text == null ? "" : text).append(separator);
		getTimeBoxLabel().setText(sb.toString());
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
	public void setProperty(String property) {
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
	public void setValue(String value) {
		// TODO
		throw new RuntimeException("not implemented");
	}
	public void setTime(int hour, int minute) {
		getTimeModel().setTime(hour, minute);
	}

	/* *************************************** */


	public void onClick(final Widget sender) {
		//
	}

	/* ****************************************************** */

	private final class TextFieldFocusListener implements FocusListener, MouseListener {

		private void process(final Widget sender) {
			final int offsetHeight = sender.getOffsetHeight();

			final int absoluteLeft = sender.getAbsoluteLeft();
			final int absoluteTop = sender.getAbsoluteTop();

			popupPanel.setPopupPosition(absoluteLeft, absoluteTop + offsetHeight);

			popupPanel.setVisible(false);
			popupPanel.show();
			resyncSize();
			popupPanel.setVisible(true);
			
		}
		
		public void onFocus(final Widget sender) {
			process(sender);
		}

		public void onLostFocus(final Widget sender) {
			// TODO Auto-generated method stub
		}

		public void onMouseDown(final Widget sender, int x, int y) {
			// TODO Auto-generated method stub
		}

		public void onMouseEnter(final Widget sender) {
			process(sender);
		}

		public void onMouseLeave(final Widget sender) {
			// TODO Auto-generated method stub
		}

		public void onMouseMove(final Widget sender, final int x, final int y) {
			// TODO Auto-generated method stub
		}

		public void onMouseUp(final Widget sender, final int x, final int y) {
			// TODO Auto-generated method stub
		}

	}

	public void setFocus(final boolean focused) {
		// TODO
	}

	public void addSelectionListener(final SelectionListener<String> listener) {
		// TODO
	}

	private TimeModel getTimeModel() {
		return this.timeModel;
	}

	/** Time (understand "hour & minute") representation. */
	private class TimeModel implements PropertyChangeSource, PropertyChangeListener {

		private final PropertyChangeListenerCollection listeners = new PropertyChangeListenerCollection();

		/** absolute hour. ie format is 24H */
		private int hour;

		/** minute. */
		private int minute;

		public void setTime(final int hour, final int minute) {
			setHour(hour);
			setMinute(minute);
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
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
			int oldValue = this.minute;
			this.minute = minute;
			if (this.minute != oldValue) {
				listeners.firePropertyChangeEvent(this, "minute", oldValue, minute);
			}
		}

		public void addPropertyChangeListener(final PropertyChangeListener listener) {
			listeners.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(final PropertyChangeListener listener) {
			listeners.removePropertyChangeListener(listener);
		}
		
		public void propertyChanged(final PropertyChangeSource source,
				final String propertyName, final Object oldValue, final Object newValue) {

			if (source != null && source instanceof TimePopupFlexTable) {
				if ("hourValue".equals(propertyName)) {
					this.setHour((Integer) newValue);
				} else if ("minuteValue".equals(propertyName)) {
					this.setMinute((Integer) newValue);
				}
			}
		}
	}

	private class SensitiveButton extends Button implements SourcesMouseEvents {

		private MouseListenerCollection mouseListeners = new MouseListenerCollection();

		public SensitiveButton() {
			super();
			init();
		}

		public SensitiveButton(final Element element) {
			super(element);
			init();
		}

		public SensitiveButton(final String html, final ClickListener listener) {
			super(html, listener);
			init();
		}

		public SensitiveButton(final String html) {
			super(html);
			init();
		}

		private void init() {
			this.setWidth("30px");
			this.unsinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
			sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
		}

		public void addMouseListener(final MouseListener listener) {
			mouseListeners.add(listener);
		}

		public void removeMouseListener(final MouseListener listener) {
			mouseListeners.remove(listener);
		}

		public void onBrowserEvent(final Event event) {
			super.onBrowserEvent(event);
			switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEDOWN:
			case Event.ONMOUSEUP:
			case Event.ONMOUSEMOVE:
			case Event.ONMOUSEOVER: {
				if (mouseListeners != null)
					mouseListeners.fireMouseEvent(this, event);
				break;
			}
			case Event.ONMOUSEOUT:
			}
		}		
	}

	private class TimeButton extends SensitiveButton {

		private int value;

		public TimeButton(final int value) {
			super();
			setValue(value);
		}

		public void setValue(final int value) {
			this.value = value;
			this.setHTML(formatValue(value));
		}

		public int getValue() {
			return this.value;
		}

		protected String formatValue(final int value) {
			String txt = "";
			if (value < 0) {
				txt = "00";
			} else if (value < 10) {
				txt = ("0" + value);
			} else if (value < 100) {
				txt = Integer.toString(value);
			} else {
				txt = "00";
			}
			return txt;
		}

		public void setText(final String text) {
			// simply ignore
		}

	}

	private class TimeTextBox extends TextBox implements SourcesMouseEvents, PropertyChangeListener {

		private MouseListenerCollection mouseListeners = new MouseListenerCollection();

		public TimeTextBox() {
			super();
			init();
		}

		public TimeTextBox(final Element element) {
			super(element);
			init();
		}

		private void init() {
			this.unsinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
			sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
		}

		public void setText(final int hour, final int minute) {
			final StringBuffer text = new StringBuffer();
			if (hour < 10) {
				text.append("0");
			}
			text.append(hour);
			text.append(" : ");
			if (minute < 10) {
				text.append("0");
			}
			text.append(minute);

			this.setText(text.toString());
		}

		public void addMouseListener(final MouseListener listener) {
			mouseListeners.add(listener);
		}

		public void removeMouseListener(final MouseListener listener) {
			mouseListeners.remove(listener);
		}

		public void onBrowserEvent(final Event event) {
			super.onBrowserEvent(event);
			switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEDOWN:
			case Event.ONMOUSEUP:
			case Event.ONMOUSEMOVE:
			case Event.ONMOUSEOVER: {
				if (mouseListeners != null)
					mouseListeners.fireMouseEvent(this, event);
				break;
			}
			case Event.ONMOUSEOUT:
			}
		}

		public void propertyChanged(final PropertyChangeSource source,
				final String propertyName, final Object oldValue, final Object newValue) {

			if (source != null && source instanceof TimeModel) {
				final TimeModel timeModel = (TimeModel) source;
				this.setText(timeModel.getHour(), timeModel.getMinute());
			}
		}

	}


	/**
	 * Inner structure to build the hours & minutes buttons ' grid
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
			init();
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

				if (propertyName.equals("hour")) {
					getState().setHourValue(model.getHour());
				}
				if (propertyName.equals("minute")) {
					getState().setMinuteValue(model.getMinute());
				}
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
		}

		private TimePanelState getState() {
			return state;
		}

		private void init() {
			this.setWidget(0, 0, getButtonAm());
			this.setWidget(0, 1, getButtonPm());
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
			TimeButton bt = getHourButtons()[getState().getAbsoluteHourIndex()];
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
			public void onMouseUp(Widget sender, int x, int y) {
				Window.setStatus(getState().toString());
				hidePopup();
			}
		}

		private class ModeMouseListener extends ClickMouseListener {
			public void onMouseEnter(final Widget sender) {
				final Button bt = (Button) sender;
				final String text = bt.getText();
				final TimeMode mode = TimeMode.getTimeMode(text);
				onModeChanged(mode);
				displayHours(mode);
				getState().setMode(mode);
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

	public enum TimeMode {

		AM_MODE("am"),
		PM_MODE("pm");

		private String mode;

		TimeMode(final String mode) {
			this.mode = mode;
		}

		public String getMode() {
			return mode;
		}

		public static TimeMode getTimeMode(final String mode) {
			for (TimeMode m : TimeMode.values()) {
				if (m.getMode().equals(mode)) {
					return m;
				}
			}
			throw new IllegalArgumentException("Unknown mode : " + mode);
		}

	}

}
