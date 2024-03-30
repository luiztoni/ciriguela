package br.edu.ciriguela;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//In dev mode use Vm arguments -Dspring.profiles.active=dev
@SpringBootApplication
public class CiriguelaApplication {
	public static void main(String[] args) {
		SpringApplication.run(CiriguelaApplication.class, args);
	}
}
