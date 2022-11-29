package com.example.demo.service;

import cyclops.control.Either;
import cyclops.control.Option;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_={@Autowired})
@Log4j2
public class BillService {
    public static Integer get(int etc){
        return etc;
    }

    public void etc(){

    }
}
