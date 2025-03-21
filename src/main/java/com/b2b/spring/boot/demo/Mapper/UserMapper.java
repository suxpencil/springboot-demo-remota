package com.b2b.spring.boot.demo.Mapper;

import com.b2b.spring.boot.demo.DTO.IndirizzoRecord;
import com.b2b.spring.boot.demo.DTO.ModificaUser;
import com.b2b.spring.boot.demo.DTO.NuovoUser;
import com.b2b.spring.boot.demo.DTO.UserRecord;
import com.b2b.spring.boot.demo.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Mapping(target = "id", ignore = true)
    @Mapping(target="dettaglio", source="dettaglio", qualifiedByName = "setDettaglioFromIndirizzoRecord")
    User toEntity(NuovoUser dto);


    @Mapping(target = "dettaglio", source = "dettaglio", qualifiedByName = "setIndirizzoRecordFromDettaglio")
    UserRecord toRecord(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="dettaglio", source="dettaglio", qualifiedByName = "setDettaglioFromIndirizzoRecord")
    User partialUpdate(@MappingTarget User entity, ModificaUser dto);

    @Named("setDettaglioFromIndirizzoRecord")
    default String setDettaglioFromIndirizzoRecord(IndirizzoRecord indirizzo){
        try {
            return OBJECT_MAPPER.writeValueAsString(indirizzo);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    @Named("setIndirizzoRecordFromDettaglio")
    default IndirizzoRecord setIndirizzoRecordFromDettaglio(String dettaglio){
        try {
            return OBJECT_MAPPER.readValue(dettaglio, IndirizzoRecord.class);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }





}
