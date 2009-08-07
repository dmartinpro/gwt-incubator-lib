/**
 * 
 */
package com.gwtincubator.security.client;

import com.google.gwt.core.client.GWT;

/**
 * Utility class.
 * @author David MARTIN
 *
 */
public final class GWTUtil {

	/**
	 * Constructor.
	 * Private, as needed for an utility class.
	 */
	private GWTUtil() {
		
	}

	/**
	 * Provide the real context URL as it seems GWT.getHostPageBaseURL() does not return what I expect...
	 * @return the web application root context URL.
	 */
	public static String getContextUrl () {
	    if (GWT.getHostPageBaseURL().equals(GWT.getModuleBaseURL())) {
	        final String ret = GWT.getHostPageBaseURL();
	        int indexLast = ret.indexOf(GWT.getModuleName());
	        return ret.substring(0, indexLast);
	    } else {
	        return GWT.getHostPageBaseURL();
	    }
	}

}
