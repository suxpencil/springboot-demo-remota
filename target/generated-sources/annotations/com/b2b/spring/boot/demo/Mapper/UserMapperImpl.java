package com.b2b.spring.boot.demo.Mapper;

import com.b2b.spring.boot.demo.DTO.IndirizzoRecord;
import com.b2b.spring.boot.demo.DTO.ModificaUser;
import com.b2b.spring.boot.demo.DTO.NuovoUser;
import com.b2b.spring.boot.demo.DTO.UserRecord;
import com.b2b.spring.boot.demo.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-20T17:39:03+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(NuovoUser dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setDettaglio( setDettaglioFromIndirizzoRecord( dto.dettaglio() ) );
        user.setNome( dto.nome() );
        user.setEmail( dto.email() );

        return user;
    }

    @Override
    public UserRecord toRecord(User entity) {
        if ( entity == null ) {
            return null;
        }

        IndirizzoRecord dettaglio = null;
        Long id = null;
        String nome = null;
        String email = null;

        dettaglio = setIndirizzoRecordFromDettaglio( entity.getDettaglio() );
        id = entity.getId();
        nome = entity.getNome();
        email = entity.getEmail();

        UserRecord userRecord = new UserRecord( id, nome, email, dettaglio );

        return userRecord;
    }

    @Override
    public User partialUpdate(User entity, ModificaUser dto) {
        if ( dto == null ) {
            return entity;
        }

        if ( dto.dettaglio() != null ) {
            entity.setDettaglio( setDettaglioFromIndirizzoRecord( dto.dettaglio() ) );
        }
        if ( dto.id() != null ) {
            entity.setId( dto.id() );
        }
        if ( dto.nome() != null ) {
            entity.setNome( dto.nome() );
        }
        if ( dto.email() != null ) {
            entity.setEmail( dto.email() );
        }

        return entity;
    }
}
