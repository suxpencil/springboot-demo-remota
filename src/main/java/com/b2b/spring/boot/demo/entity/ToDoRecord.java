package com.b2b.spring.boot.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ToDoRecord {

    private Long userId;
    private Long id;
    String title;
    Boolean completed;

}
