package com.github.davidcarboni.jerseythymeleaf;

import org.glassfish.jersey.server.ResourceConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class Application extends ResourceConfig {

	public static TemplateEngine templateEngine;

	public Application() {
		packages("com.github.davidcarboni.jerseythymeleaf");
		// register(org.glassfish.jersey.media.multipart.MultiPartFeature.class);

		// Set up Thymeleaf:

		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		// XHTML is the default mode, but we set it anyway for better
		// understanding of code
		templateResolver.setTemplateMode(StandardTemplateModeHandlers.HTML5
				.getTemplateModeName());
		// This will convert "home" to "/WEB-INF/templates/home.html"
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		// Template cache TTL=1h. If not set, entries would be cached until
		// expelled by LRU
		templateResolver.setCacheTTLMs(1000L);

		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
	}
}
