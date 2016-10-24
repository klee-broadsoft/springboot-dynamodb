package com.broadsoft.ums.boot;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.broadsoft.ums.boot.rest.UmsController;

/**
 * Spring Boot application defines a set of dummy REST APIs to help
 * UC clients development.<br/>
 * 
 * Arguments:
 * <ul>
 *   <li>? - help</li?>
 *   <li>-v - version</li>
 *   <li>-e - evil mode</li>
 * </ul>
 * 
 * </br/>
 * When run in the evil mode the emulator will return a random error on every RET call
 * 
 * @author mgeorgiev@broadsoft.com
 *
 */
@EnableSwagger2
@ComponentScan("com.broadsoft.ums.boot")
@SpringBootApplication
public class Application {

	/** if set <code>true</code> emulator runs the evil mode. */
	public static boolean MODE;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if(args.length > 0) {
			logo();
			if("?".equals(args[0])) {
				help();
				System.exit(0);
			} else if("-v".equalsIgnoreCase(args[0])) {
				version();
				System.exit(0);
			} else if("-e".equalsIgnoreCase(args[0])) {
				Application.MODE = true;
			}
		}

		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		logo();

		info(context);

	}

	@Bean
	public Docket umsApi() {

		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("ums")
		.apiInfo(apiInfo())
		.select()
		.paths(PathSelectors.any())
		.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
		.title("UMS REST Sample")
		.description("UMS REST Sample")
		.termsOfServiceUrl("FREE for BSFT devs")
		.contact("mgeorgiev@broadsoft.com")
		.license("BSFT")
		.licenseUrl("http://broadsoft.com")
		.version("1.0")
		.build();
	}

	private static void logo() {
		System.out.println();
		System.out.println();

		System.out.println("UUUUUUUU     UUUUUUUMMMMMMMM               MMMMMMMM  SSSSSSSSSSSSSSS ");
		System.out.println("U::::::U     U::::::M:::::::M             M:::::::MSS:::::::::::::::S");
		System.out.println("U::::::U     U::::::M::::::::M           M::::::::S:::::SSSSSS::::::S");
		System.out.println("UU:::::U     U:::::UM:::::::::M         M:::::::::S:::::S     SSSSSSS");
		System.out.println(" U:::::U     U:::::UM::::::::::M       M::::::::::S:::::S            ");
		System.out.println(" U:::::D     D:::::UM:::::::::::M     M:::::::::::S:::::S            ");
		System.out.println(" U:::::D     D:::::UM:::::::M::::M   M::::M:::::::MS::::SSSS         ");
		System.out.println(" U:::::D     D:::::UM::::::M M::::M M::::M M::::::M SS::::::SSSSS    ");
		System.out.println(" U:::::D     D:::::UM::::::M  M::::M::::M  M::::::M   SSS::::::::SS  ");
		System.out.println(" U:::::D     D:::::UM::::::M   M:::::::M   M::::::M      SSSSSS::::S ");
		System.out.println(" U:::::D     D:::::UM::::::M    M:::::M    M::::::M           S:::::S");
		System.out.println(" U::::::U   U::::::UM::::::M     MMMMM     M::::::M           S:::::S");
		System.out.println(" U:::::::UUU:::::::UM::::::M               M::::::SSSSSSS     S:::::S");
		System.out.println("  UU:::::::::::::UU M::::::M               M::::::S::::::SSSSSS:::::S");
		System.out.println("    UU:::::::::UU   M::::::M               M::::::S:::::::::::::::SS ");
		System.out.println("      UUUUUUUUU     MMMMMMMM               MMMMMMMMSSSSSSSSSSSSSSS   ");

		System.out.println();
		System.out.println();
		System.out.println("Broadsoft Gateway Emulator");
		System.out.println();
		System.out.println("for more information run with \"?\" as command line argument");
		System.out.println();
	}

	private static void help() {
		System.out.println();
		System.out.println();
		System.out.println("Help");
		System.out.println();
		System.out.println("-v version");
		System.out.println("-e evil mode. Use this mode to simulate error conditions randomly");
		System.out.println();
		System.out.println("API is accessible on: http://localhost:8080");
		System.out.println("API doc is accessible on: http://localhost:8080/swagger-ui.html");
		System.out.println();
	}

	private static void version() {
		System.out.println("version 1.0");
		System.out.println();
	}

	private static void info(ConfigurableApplicationContext context) {
		System.out.println();
		System.out.println();
		System.out.println();
		if(Application.MODE) {
			System.out.println("Emulator runs in evil mode");
		} else {
			System.out.println("Emulator runs in normal mode");
		}

		System.out.println();
		try {
			System.out.println("REST API is available on:");
			System.out.println("\thttp://" + Inet4Address.getLocalHost().getHostAddress() + ":8080");
			System.out.println("\thttp://localhost:8080");
			System.out.println("REST API documentation and REST HTML client are available on:");
			System.out.println("\thttp://" + Inet4Address.getLocalHost().getHostAddress() + ":8080/swagger-ui.html");
			System.out.println("\thttp://localhost:8080/swagger-ui.html");
		} catch (UnknownHostException e) {
			System.err.println("Cannot find IP Address of the current machine");
		}

		System.out.println();
		System.out.println("Supported REST APIs:");

		String[] beanNames = context.getBeanNamesForType(UmsController.class);

		for (String beanName : beanNames) {
			UmsController bean = (UmsController) context.getBean(beanName);
			System.out.println("\t" + bean.toString());
		}
	}
}