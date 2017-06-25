

package cn.yangtengfei.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cn.yangtengfei.*")
public class Application {


	/*@Bean
	public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
		return new ApplicationSecurity();
	}*/
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
