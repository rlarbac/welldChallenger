package it.welld.recognition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is the start point of the SpringBoot project.
 * @author Rodrigo
 *
 */
@SpringBootApplication
//@ComponentScan({"it.weelid.recognition.service", "it.weelid.recognition.controller"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
