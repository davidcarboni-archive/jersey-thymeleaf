package com.github.davidcarboni.jerseythymeleaf;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("")
public class Home {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public TemplateSpecification render(@Context ServletContext servletContext,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException {

		TemplateSpecification templateSpecification = new TemplateSpecification();
		templateSpecification.templateName = "home";
		templateSpecification.data.put("key", "value");

		return templateSpecification;
	}

}
