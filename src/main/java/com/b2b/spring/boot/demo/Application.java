package com.b2b.spring.boot.demo;

import com.b2b.spring.boot.demo.client.EsercizioClient;
import com.b2b.spring.boot.demo.controller.api.AuthenticationAPI;
import com.b2b.spring.boot.demo.entity.ToDoRecord;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Set;

@SpringBootApplication
@EnableFeignClients
public class Application {

  @Autowired EsercizioClient esercizioClient;

  @Autowired AuthenticationAPI api;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }



@PostConstruct
public void test() {
  Set<ToDoRecord> test = esercizioClient.test();
  System.out.println("DATI RECUPERATI: ");
  test.forEach(System.out::println);
}

void aggiuntoPerProvareMerge(){

}

}
