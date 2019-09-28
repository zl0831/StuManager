package zl.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class StuManagerApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(StuManagerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(StuManagerApplication.class, args);
	}

}
