package com.luiscalixto.reto.services;


import com.luiscalixto.reto.models.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneService  {
    Optional<Phone> findById(String id);
    List<Phone> findAll();
}
