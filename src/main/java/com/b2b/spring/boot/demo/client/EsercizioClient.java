package com.b2b.spring.boot.demo.client;

import com.b2b.spring.boot.demo.entity.ToDoRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@FeignClient(name = "esercizio-service", url ="https://jsonplaceholder.typicode.com/todos" )
public interface EsercizioClient {
    @GetMapping("")
    Set<ToDoRecord> test();
}
