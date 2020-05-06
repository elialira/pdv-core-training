package br.com.totvs.product.query.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductQueryDBApplication {

    private static final int SUCCESSFULLY_COMPLETED = 0;

    public static void main(String[] args) {
        SpringApplication.run(ProductQueryDBApplication.class, args);

        System.exit(SUCCESSFULLY_COMPLETED);
    }

}
