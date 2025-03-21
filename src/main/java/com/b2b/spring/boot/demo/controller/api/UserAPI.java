package com.b2b.spring.boot.demo.controller.api;

import com.b2b.spring.boot.demo.DTO.ModificaUser;
import com.b2b.spring.boot.demo.DTO.NuovoUser;
import com.b2b.spring.boot.demo.DTO.UserRecord;
import com.b2b.spring.boot.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping(path="/user")
public interface UserAPI {

    @GetMapping(produces="application/json")
    ResponseEntity<List<UserRecord>> getAllUsers(HttpServletRequest request);

    @PostMapping(produces="application/json", consumes ="application/json")
    public ResponseEntity<?> insertUser (HttpServletRequest request, @RequestBody NuovoUser oUser);

    @PutMapping(path="/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateUser (HttpServletRequest request, @RequestBody ModificaUser oUser, @PathVariable Long id);

    @DeleteMapping(path="/{id}")
    public ResponseEntity<?> deleteUser (HttpServletRequest request, @PathVariable Long id);
}
