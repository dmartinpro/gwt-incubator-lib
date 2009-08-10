/**
 * 
 */
package com.gwtincubator.sample.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author David MARTIN
 *
 */
public interface TestServiceAsync {

	void testUser(final String input, final AsyncCallback<String> callback);

	void testAdmin(final String input, final AsyncCallback<String> callback);

}
