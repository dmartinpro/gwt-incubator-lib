package com.gwtincubator.security.server;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.SpringSecurityException;

import com.gwtincubator.security.exception.ApplicationSecurityException;

/**
 * 
 * @author David MARTIN
 *
 */
public class SecurityExceptionFactory {

	public static ApplicationSecurityException get(final SpringSecurityException springException) {
		ApplicationSecurityException gwtException = null;
		if (springException instanceof AccessDeniedException) {
			gwtException = new com.gwtincubator.security.exception.AccessDeniedException(springException.getMessage(), springException);
		} else if (true) {
			gwtException = new com.gwtincubator.security.exception.AuthenticationException(springException.getMessage(), springException);
		} else {
			gwtException = new com.gwtincubator.security.exception.ApplicationSecurityException(springException.getMessage(), springException);
		}
		return gwtException;
	}

}
