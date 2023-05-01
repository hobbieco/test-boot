package hobbieco.test.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix="awx-props")
@Data
public class AwxProps {

	public String baseUrl;
	public String apiId;
	public String apiPwd;
}
