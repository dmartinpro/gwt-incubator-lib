/**
 * 
 */
package com.gwtincubator.sample.server.hosted;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwtincubator.sample.client.TestService;

/**
 * @author David MARTIN
 *
 */
public class TestServiceHosted extends RemoteServiceServlet implements TestService {

	/** serialVersionUID. */
	private static final long serialVersionUID = -7584872300164711256L;

	public String testUser(final String input) {
		return input.toLowerCase();
	}

	public String testAdmin(final String input) {
		return input.toUpperCase();
	}

}
