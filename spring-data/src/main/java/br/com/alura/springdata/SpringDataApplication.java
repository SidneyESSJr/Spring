package br.com.alura.springdata;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.springdata.service.ApplicationService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private final ApplicationService applicationService;

	public SpringDataApplication(ApplicationService applicationService) {

		this.applicationService = applicationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scan = new Scanner(System.in);

		applicationService.menuPrincipal(scan);
	}

}
