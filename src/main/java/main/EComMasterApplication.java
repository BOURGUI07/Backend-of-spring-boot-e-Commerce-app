package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EComMasterApplication {
void probe(Object x) { System.out.println("In Object"); } //3 

    void probe(Number x) { System.out.println("In Number"); } //2

    void probe(Integer x) { System.out.println("In Integer"); } //2
    
    void probe(Long x) { System.out.println("In Long"); } //4
	public static void main(String[] args) {
		SpringApplication.run(EComMasterApplication.class, args);
                double a = 10; 
        new EComMasterApplication().probe(a); 
	}

}
