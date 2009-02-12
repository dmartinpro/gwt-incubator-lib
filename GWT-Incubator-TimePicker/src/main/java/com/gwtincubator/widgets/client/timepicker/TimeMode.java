package com.gwtincubator.widgets.client.timepicker;

/* package */ enum TimeMode {

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