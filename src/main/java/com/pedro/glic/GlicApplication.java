package com.pedro.glic;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GlicApplication {

    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.load();
            System.setProperty("DB_URL", dotenv.get("DB_URL"));
            System.setProperty("DB_USER", dotenv.get("DB_USER"));
            System.setProperty("DB_PASS", dotenv.get("DB_PASS"));
        } catch (Exception e) {
            System.out.println("Arquivo .env não encontrado. Usando variáveis de ambiente do sistema.");
        }

        SpringApplication.run(GlicApplication.class, args);
    }

}
