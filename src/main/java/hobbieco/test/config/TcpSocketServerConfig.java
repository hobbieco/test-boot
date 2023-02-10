package hobbieco.test.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.dsl.TcpInboundGatewaySpec;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;

import hobbieco.test.config.props.TcpSocketProps;
import hobbieco.test.socket.TcpSocketDeserializer;
import hobbieco.test.socket.TcpSocketSerializer;
import hobbieco.test.socket.TcpSocketServerHandler;

/**
 * TCP Socket Server Configuration
 *
 */
@Configuration
@EnableIntegration
public class TcpSocketServerConfig {
	
	@Resource(name="tcpSocketProps")
	private TcpSocketProps props;

	@Resource(name="tcpSocketServerHandler")
	private TcpSocketServerHandler tcpSocketServerHandler;
	
	@Resource(name="tcpSocketSerializer")
	private TcpSocketSerializer tcpSocketSerializer;
	
	@Resource(name="tcpSocketDeserializer")
	private TcpSocketDeserializer tcpSocketDeserializer;
	
	@Bean
	IntegrationFlow tcpSocketServer() {
		return IntegrationFlows.from(tcpSocketInboundGatewaySpec())
				.handle(tcpSocketServerHandler::handler)
				.get();
	}
	
	@Bean
	TcpNioServerConnectionFactory tcpSocketServerConnectionFactory() {
		TcpNioServerConnectionFactory factory = new TcpNioServerConnectionFactory(props.getServerPort());
		//InputStream
		factory.setDeserializer(tcpSocketDeserializer);
		//OutputStream
		factory.setSerializer(tcpSocketSerializer);
		factory.setSingleUse(true);
		return factory;
	}
	
	@Bean
	TcpInboundGatewaySpec tcpSocketInboundGatewaySpec() {
		return Tcp.inboundGateway(tcpSocketServerConnectionFactory());
	}
}
