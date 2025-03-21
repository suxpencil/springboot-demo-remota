package com.b2b.spring.boot.demo.service;

import com.b2b.spring.boot.demo.DTO.ModificaUser;
import com.b2b.spring.boot.demo.DTO.NuovoUser;
import com.b2b.spring.boot.demo.DTO.UserRecord;
import com.b2b.spring.boot.demo.entity.User;
import java.util.ArrayList;
import java.util.List;


public interface UserService {

     List<UserRecord> getAllUsers ();

     void insertUser (NuovoUser oUser);

     void updateUser (ModificaUser oUser, Long id);

     void  deleteById (Long id);
}
