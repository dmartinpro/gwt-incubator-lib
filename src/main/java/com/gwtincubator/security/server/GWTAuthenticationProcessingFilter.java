/**
 * 
 */
package com.gwtincubator.security.server;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

/**
 * In order to return an error page with a HTTP status code 4xx, AuthenticationProcessingFilter should have its failureUrl set to 'null'.
 * <br>
 * This is the way I found to have it set to null.
 * <br>
 * You can use it like this :<br>
 * <code>
 *  <authentication-manager alias="authenticationManager"/>
 *	<beans:bean id="gwtFilter" class="com.gwtincubator.security.server.GWTAuthenticationProcessingFilter">
 *    	<custom-filter position="AUTHENTICATION_PROCESSING_FILTER"/>
 *    	<beans:property name="defaultTargetUrl" value="/" />
 *    	<beans:property name="authenticationManager" ref="authenticationManager" />
 *      <beans:property name="filterProcessesUrl" value="/j_spring_security_check"/>
 *	</beans:bean>
 *
 * </code>
 * And do NOT add <code>&lt;form-login/&gt;</code> to &lt;http&gt; node as it is a replacement.
 *
 * @author David MARTIN
 *
 */
public class GWTAuthenticationProcessingFilter extends
		AuthenticationProcessingFilter {

	@Override
	protected String determineFailureUrl(HttpServletRequest request,
			AuthenticationException failed) {
		return null;
	}

}
