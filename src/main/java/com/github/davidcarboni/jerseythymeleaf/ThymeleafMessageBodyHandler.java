package com.github.davidcarboni.jerseythymeleaf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.thymeleaf.context.WebContext;

/**
 * From <a href=
 * "http://eclipsesource.com/blogs/2012/11/02/integrating-gson-into-a-jax-rs-based-application/"
 * >http://eclipsesource.com/blogs/2012/11/02/integrating-gson-into-a-jax-rs-
 * based-application/</a>
 */
@Provider
@Produces(MediaType.TEXT_HTML)
public class ThymeleafMessageBodyHandler implements MessageBodyWriter<Object> {

	private static final String UTF_8 = "UTF-8";

	@Context
	ServletContext servletContext;
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return type.isAssignableFrom(TemplateSpecification.class);
	}

	@Override
	public long getSize(Object object, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object object, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {

		try (OutputStreamWriter writer = new OutputStreamWriter(entityStream,
				UTF_8)) {

			// Get values from the specification:
			TemplateSpecification templateSpecification = (TemplateSpecification) object;
			String templateName = templateSpecification.templateName;

			// Render the template:
			WebContext ctx = new WebContext(request, response, servletContext,
					request.getLocale());
			for (Entry<String, Object> variable : templateSpecification.data
					.entrySet()) {
				ctx.setVariable(variable.getKey(), variable.getValue());
			}
			Application.templateEngine.process(templateName, ctx, writer);
		}
	}
}