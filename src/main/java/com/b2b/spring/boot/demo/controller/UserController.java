package com.b2b.spring.boot.demo.controller;

import com.b2b.spring.boot.demo.DTO.ModificaUser;
import com.b2b.spring.boot.demo.DTO.NuovoUser;
import com.b2b.spring.boot.demo.DTO.UserRecord;
import com.b2b.spring.boot.demo.controller.api.UserAPI;
import com.b2b.spring.boot.demo.entity.User;
import com.b2b.spring.boot.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserAPI {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<List<UserRecord>> getAllUsers(HttpServletRequest request) {
        return new ResponseEntity<List<UserRecord>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> insertUser(HttpServletRequest request, NuovoUser oUser) {
        userService.insertUser(oUser);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateUser(HttpServletRequest request, ModificaUser oUser, Long id) {
        userService.updateUser(oUser, id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteUser(HttpServletRequest request, Long id) {
        userService.deleteById(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
