package com.example.demo.controller;

import com.example.demo.dto.message.BasicResponse;
import com.example.demo.model.Partner;
import com.example.demo.repo.PartnerRepo;
import com.example.demo.service.EncryptService;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.util.HashMap;

@RestController
@RequestMapping("partner")
@AllArgsConstructor(onConstructor_={@Autowired})
public class PartnerController {
    private final PartnerRepo partnerRepo;
    private final EncryptService encryptService;

    @PostMapping(value = "signup")
    public ResponseEntity<Object> signup(@RequestBody HashMap<String, String> json){
        KeyPair keyPair = encryptService.generateKeyPair();
        Partner partner = Partner.builder()
                .balance(0L)
                .partnerCode(json.get("partnerCode"))
                .privateKey(encryptService.convertKeyToString(keyPair.getPrivate()))
                .publicKey(encryptService.convertKeyToString(keyPair.getPublic()))
                .partnerPublicKey(json.get("publicKey"))
                .build();
        Try<Partner> result = Try.of(() -> partnerRepo.save(partner));
        if(result.isSuccess()) return new ResponseEntity<>(partner, HttpStatus.OK);
        else return new ResponseEntity<>(result.getCause(), HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value = "checkBalance")
    public BasicResponse checkBalance(@RequestBody HashMap<String, String> json){
        return null;
    }

    @PostMapping(value = "increaseBalance")
    public ResponseEntity<Object> increaseBalance(@RequestBody HashMap<String, Object> json){
        //TODO: Null check partner
        Option<Partner> findingResult = partnerRepo.findByPartnerCode((String) json.get("partnerCode"));
        Partner partner = findingResult.get();
        partner.setBalance(partner.getBalance() + (Integer) json.get("amount"));
        Try<Partner> result = Try.of(() -> partnerRepo.save(partner));
        if(result.isSuccess()) return new ResponseEntity<>(partner, HttpStatus.OK);
        else return new ResponseEntity<>(result.getCause(), HttpStatus.BAD_REQUEST);
    }

}



