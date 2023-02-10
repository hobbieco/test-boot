package hobbieco.test.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 *
 */
@Component
@ConfigurationProperties(prefix="tcp-socket-props")
@Data
public class TcpSocketProps {

	private int serverPort;
	private String encoding;
}
