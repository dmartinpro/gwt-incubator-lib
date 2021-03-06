package com.gwtincubator.security.server;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gwtwidgets.server.spring.GWTRPCServiceExporter;
import org.springframework.security.SpringSecurityException;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.UnexpectedException;

/**
 * Security oriented version of George Georgovassilis GWTRPCServiceExporter.
 * @author David MARTIN
 */
public class GWTRPCSecuredServiceExporter extends GWTRPCServiceExporter {

	/** serialVersionUID */
	private static final long serialVersionUID = 2733022902422767233L;

	/** logger. */
	private final static Log LOGGER = LogFactory.getLog(GWTRPCSecuredServiceExporter.class);

	public GWTRPCSecuredServiceExporter() {
	}

	@SuppressWarnings("unchecked")
	public void setServiceInterfaces(final Class[] serviceInterfaces) {
		this.serviceInterfaces = serviceInterfaces;
	}

	@SuppressWarnings("unchecked")
	public Class[] getServiceInterfaces() {
		return this.serviceInterfaces;
	}

	public void setServletContext(final ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return this.servletContext;
	}

	public void setCompressResponse(final int compressResponse) {
		this.compressResponse = compressResponse;
	}

	public int getCompressResponse() {
		return this.compressResponse;
	}

	public void setDisableResponseCaching(final boolean disableResponseCaching) {
		this.disableResponseCaching = disableResponseCaching;
	}

	public boolean getDisableResponseCaching() {
		return this.disableResponseCaching;
	}

	public void setMethodCache(final Map<Method, Method> methodCache) {
		this.methodCache = methodCache;
	}

	public Map<Method, Method> getMethodCache() {
		return this.methodCache;
	}

	/**
	 * Wrap the original method in order to detect a Spring Security specific exception and manage it the way we want.
	 * @param payload 
	 * @see org.gwtwidgets.server.spring.GWTRPCServiceExporter#processCall(java.lang.String)
	 */
	@Override
	public String processCall(final String payload) throws SerializationException {
		String response = null;
		// reported as not working with GWT.1.6.4 : Issue 2
//		final RPCRequest rpcRequest = RPC.decodeRequest(payload);
		final RPCRequest rpcRequest = RPC.decodeRequest(payload, null, this);
		try {
			response = super.processCall(payload);
		} catch (final Throwable e) { // Security Exceptions (preciousException here) are wrapped into an UnexpectedException (cause1), which is wrapped into a RuntimeException (e)...
			final Throwable cause1 = e.getCause();
			if (cause1 != null && cause1 instanceof UnexpectedException) {
				final Throwable preciousException = cause1.getCause();
				if (preciousException != null && preciousException instanceof SpringSecurityException) {
					String failurePayload = null;
					try {
						failurePayload = RPC.encodeResponseForFailure(
							rpcRequest.getMethod(),
							SecurityExceptionFactory.get((SpringSecurityException) preciousException));
					} catch (final UnexpectedException ue) {
						LOGGER.error("You may have forgotten to add a 'throws ApplicationSecurityException' declaration to your service interface.");
						throw ue;
					}
					return failurePayload;
				}
			}
			handleOtherException(e);
		}
		return response;
	}

	protected void handleOtherException(final Throwable e) throws SerializationException {
		if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		} else if (e instanceof SerializationException) {
			throw (SerializationException) e;
		} else {
			throw new SerializationException(e);
		}
	}

}
