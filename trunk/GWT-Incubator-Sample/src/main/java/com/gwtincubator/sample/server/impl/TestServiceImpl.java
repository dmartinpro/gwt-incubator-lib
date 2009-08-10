/**
 * 
 */
package com.gwtincubator.sample.server.impl;

import com.gwtincubator.sample.client.TestService;

/**
 * @author David MARTIN
 *
 */
public class TestServiceImpl implements TestService {

	public String testUser(final String input) {
		return input.toLowerCase();
	}

	public String testAdmin(final String input) {
		return input.toUpperCase();
	}

}
