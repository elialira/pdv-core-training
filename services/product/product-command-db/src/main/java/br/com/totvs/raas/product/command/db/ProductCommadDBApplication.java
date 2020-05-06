package br.com.totvs.raas.product.command.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductCommadDBApplication {

	private static final int SUCCESSFULLY_COMPLETED = 0;

	public static void main(String[] args) {
		SpringApplication.run(ProductCommadDBApplication.class, args);

		System.exit(SUCCESSFULLY_COMPLETED);
	}

}
