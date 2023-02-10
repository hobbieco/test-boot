package hobbieco.test;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class TestBootApplication {
	
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC+09"));
	}

	public static void main(String[] args) {
		//SpringApplication.run(TestBootApplication.class, args);
		SpringApplication app = new SpringApplication(TestBootApplication.class);
		app.addListeners(new ApplicationPidFileWriter());
		app.run(args);
	}

}
