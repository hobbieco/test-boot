package hobbieco.test.config;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import hobbieco.test.common.CommonUtil;
import hobbieco.test.common.R;

/**
 * TestApplication - Config
 * Jasypt Config
 * @since 2020.11.09.
 */
@Configuration
@EnableEncryptableProperties
//@EncryptablePropertySource(name = "EncryptedProperties", value = "classpath:application.yml")
public class JasyptConfig {
	
	@Resource(name="commonUtil")
	private CommonUtil util;

	@Bean(name="jasyptStringEncryptor")
	StringEncryptor jasyptStringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		
		if (StringUtils.equals(R.Profile.PRODUCT, util.getProfile())) {
			config.setPassword(R.JasyptConf.PRODUCT.getKey());
			config.setAlgorithm(R.JasyptConf.PRODUCT.getAlgorithm());
		} else if (StringUtils.equals(R.Profile.DEVELOP, util.getProfile())) {
			config.setPassword(R.JasyptConf.DEVELOP.getKey());
			config.setAlgorithm(R.JasyptConf.DEVELOP.getAlgorithm());
		} else {
			config.setPassword(R.JasyptConf.LOCAL.getKey());
			config.setAlgorithm(R.JasyptConf.LOCAL.getAlgorithm());
		}
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
		config.setStringOutputType("base64");
		
		encryptor.setConfig(config);
		return encryptor;
	}
}
