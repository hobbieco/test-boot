package hobbieco.test.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix="jedis-props")
@Data
public class JedisProps {

	private String ip;
	private int port;
	private String auth;
}
