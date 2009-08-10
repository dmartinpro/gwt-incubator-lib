/**
 * 
 */
package com.gwtincubator.sample.client;

import org.springframework.security.annotation.Secured;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwtincubator.security.exception.ApplicationSecurityException;

/**
 * @author David MARTIN
 *
 */
@RemoteServiceRelativePath("gwt-rpc/test.rpc")
public interface TestService extends RemoteService {

	@Secured({"ROLE_USER"})
	String testUser(final String input) throws ApplicationSecurityException;

	@Secured({"ROLE_ADMIN"})
	String testAdmin(final String input) throws ApplicationSecurityException;

}
