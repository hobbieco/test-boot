package hobbieco.test.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import hobbieco.test.common.R;

@Configuration
public class WebServerConfig {

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		
		//Error Page
		factory.addErrorPages(
				new ErrorPage(HttpStatus.NOT_FOUND, R.ERROR_URL.NOT_FOUND),
				new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, R.ERROR_URL.METHOD_NOT_ALLOWED),
				new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, R.ERROR_URL.INTERNAL_SERVER_ERROR),
				new ErrorPage(HttpStatus.UNAUTHORIZED, R.ERROR_URL.UNAUTHORIZED),
				new ErrorPage(HttpStatus.FORBIDDEN, R.ERROR_URL.FORBIDDEN)
				);
		
		//HTTP method deactive
		factory.addContextCustomizers(context -> {
			SecurityConstraint securityConstraint = new SecurityConstraint();
			securityConstraint.setUserConstraint("CONFIDENTIAL");
			SecurityCollection securityCollection = new SecurityCollection();
			securityCollection.addPattern("/*");
			
			//add method
			securityCollection.addMethod("TRACE");
			securityCollection.addMethod("COPY");
			
			securityConstraint.addCollection(securityCollection);
			context.addConstraint(securityConstraint);
		});
		
		//AJP
		factory.addAdditionalTomcatConnectors(ajpConnector());
		
		return factory;
	}
	
	private Connector ajpConnector() {
		Connector con = new Connector("AJP/1.3");
		con.setPort(8009);
		con.setSecure(false);
		con.setAllowTrace(false);
		con.setScheme("http");
		((AbstractAjpProtocol<?>) con.getProtocolHandler()).setSecretRequired(false);
		return con;
	}
}
