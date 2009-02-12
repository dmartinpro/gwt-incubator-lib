package com.gwtincubator.widgets.client.timepicker;

import com.google.gwt.junit.client.GWTTestCase;

public class SimpleTimeModelConverterTest extends GWTTestCase {
	
	private TimeModelConverter converter = new SimpleTimeModelConverter();

	public final void testToModel() {
		final String s1 = new String("12:34");
		final String s2 = new String("12f34");
		final String s3 = new String("1:2:3:4");
		final String s4 = null;
		final String s5 = new String("");
		final String s6 = new String("12:ze");
		
		TimeModel m1 = converter.toModel(s1);
		assertEquals(12, m1.getHour());
		assertEquals(34, m1.getMinute());

		TimeModel m2 = converter.toModel(s2);
		assertNull(m2);

		TimeModel m3 = converter.toModel(s3);
		assertNull(m3);
		
		TimeModel m4 = converter.toModel(s4);
		assertNull(m4);

		TimeModel m5 = converter.toModel(s5);
		assertNull(m5);

		TimeModel m6 = converter.toModel(s6);
		assertNull(m6);

	}

	public final void testToStringTimeModel() {
		final TimeModel m1 = new TimeModel(12, 34);
		final TimeModel m2 = null;

		String s1 = converter.toString(m1);
		assertEquals("12:34", s1);

		String s2 = converter.toString(m2);
		assertNull(s2);

	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "com.gwtincubator.widgets.IncubatorTimepicker";
	}

}
