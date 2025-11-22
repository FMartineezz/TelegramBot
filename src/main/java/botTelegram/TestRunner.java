package botTelegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class TestRunner implements CommandLineRunner {

    @Autowired
    private OrdenTester tester;

    public static void main(String[] args) {
        SpringApplication.run(TestRunner.class, args);
    }

    @Override
    public void run(String... args) {
        tester.testReal("buscar_hecho_por_palabra_clave aviones");
    }


}
