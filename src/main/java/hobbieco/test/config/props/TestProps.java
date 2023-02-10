package hobbieco.test.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix="test-props")
@Data
public class TestProps {

	private String test;

}
