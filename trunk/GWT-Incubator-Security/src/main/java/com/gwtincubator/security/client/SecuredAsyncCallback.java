/**
 * 
 */
package com.gwtincubator.security.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;
import com.gwtincubator.security.exception.ApplicationSecurityException;

/**
 * This is a secured AsyncCallback class, enabling a distinct security failure management
 *
 * @author David MARTIN
 *
 */
public abstract class SecuredAsyncCallback<T> implements AsyncCallback<T> {

	/**
	 * Override this method to implement your security errors management.
	 * @param exception the security exception thrown
	 */
	protected abstract void onSecurityException(final ApplicationSecurityException exception);

	/**
	 * Acts as the onFailure in a classic AsyncCallback class.
	 * @param exception the exception thrown
	 */
	protected abstract void onOtherException(final Throwable exception);

	/**
	 * Override (and protect) the onFailure method in order to provide a security exception detection.
	 * @param exception the exception thrown, could be a security exception
	 */
	public final void onFailure(final Throwable exception) {
		if (exception != null && exception instanceof ApplicationSecurityException) {
			onSecurityException((ApplicationSecurityException) exception);
		} else { // We have to check if the answer is a security XML one
			final String msg = exception.getMessage();
			try {
				final Document doc = XMLParser.parse(msg);
				final NodeList securityNode = doc.getElementsByTagName("security");
				if (securityNode != null) {
					final NodeList messageNode = doc.getElementsByTagName("message");
					String message = new String("Security Exception");
					if (messageNode != null) {
						message = messageNode.item(0).getNodeValue();
					}
					final ApplicationSecurityException newException = new ApplicationSecurityException(message, exception);
					onSecurityException(newException);
				}
			} catch (DOMParseException  e) {
				Window.alert("can't parse response...");
			}
			onOtherException(exception);
		}
	}

}
