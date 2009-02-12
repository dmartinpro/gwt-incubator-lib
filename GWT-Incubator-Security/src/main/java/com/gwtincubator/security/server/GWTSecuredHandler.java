package com.gwtincubator.security.server;

import javax.servlet.http.HttpServletRequest;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.beans.BeanToBeanMapping;

import org.gwtwidgets.server.spring.GWTHandler;
import org.gwtwidgets.server.spring.GWTRPCServiceExporter;
import org.springframework.web.servlet.HandlerExecutionChain;

/**
 * Specific version of George Georgovassilis useful GWTHandler class.
 *
 * @author David MARTIN
 */
public class GWTSecuredHandler extends GWTHandler {

	/**
	 * Thanks to Transmorph, it's easy to load a new different bean based on the content of another.
	 * Thus, a new security enhanced GWTRPCServiceExporter class is returned into the HandlerExecutionChain.
	 * @param request the HttpServletRequest
	 * @return a HandlerExecutionChain object
	 */
	@Override
	protected Object getHandlerInternal(final HttpServletRequest request) throws Exception {
		final Object handlerWrapper = super.getHandlerInternal(request);
		if (handlerWrapper instanceof HandlerExecutionChain) {
			final Object handler = ((HandlerExecutionChain) handlerWrapper).getHandler();
			if (handler instanceof GWTRPCServiceExporter) {
				final DefaultConverters defaultConverters = new DefaultConverters();
				final Transmorph transmorph = new Transmorph(this.getClass().getClassLoader(), defaultConverters);

				BeanToBeanMapping beanToBeanMapping = null;
				beanToBeanMapping = new BeanToBeanMapping(
						GWTRPCSecuredServiceExporter.class,
						GWTRPCServiceExporter.class);
				defaultConverters.getBeanToBean().addBeanToBeanMapping(beanToBeanMapping);

				beanToBeanMapping = new BeanToBeanMapping(
						GWTRPCServiceExporter.class,
						GWTRPCSecuredServiceExporter.class);
				defaultConverters.getBeanToBean().addBeanToBeanMapping(beanToBeanMapping);

				final GWTRPCSecuredServiceExporter wrapper = (GWTRPCSecuredServiceExporter) transmorph.convert(handler, GWTRPCSecuredServiceExporter.class);
				wrapper.afterPropertiesSet();

				return new HandlerExecutionChain(wrapper, ((HandlerExecutionChain) handlerWrapper).getInterceptors());
			}
		}
		return handlerWrapper;
	}

}
