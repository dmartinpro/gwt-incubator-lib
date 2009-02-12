package com.gwtincubator.widgets.client.button;

public class TimeButton extends SensitiveButton {

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
