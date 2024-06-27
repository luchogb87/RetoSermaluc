package com.luiscalixto.reto.repositories;


import com.luiscalixto.reto.models.Phone;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<Phone,String> {
}
