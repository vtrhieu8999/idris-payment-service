package com.example.demo.service;

import com.example.demo.service.factory.testEnum.EncryptEnum;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
@Log4j2
public class EncryptService {
    public KeyPair generateKeyPair(){
        return EncryptEnum.RSA.generateKeyPair();
    }

    public String convertKeyToString(Key key) {
        return EncryptEnum.convertBytesToString(key.getEncoded());
    }

    public Try<PublicKey> convertStringToPublicKey(String publicKeyStr){
        return EncryptEnum.RSA.convertBytesToPublicKey(EncryptEnum.convertStringToBytes(publicKeyStr));
    }

    public Try<PrivateKey> convertStringToPrivateKey(String privateKeyStr){
        return EncryptEnum.RSA.convertBytesToPrivateKey(EncryptEnum.convertStringToBytes(privateKeyStr));
    }

    public Try<String> encryptMessage(PublicKey publicKey, String secreteMessage) {
        return EncryptEnum.RSA.encryptByte(
                EncryptEnum.convertStringToBytes(secreteMessage),
                publicKey
        ).map(EncryptEnum::convertBytesToString);
    }

    public Try<String> decryptMessage(PrivateKey privateKey, String encryptedMessage) {
        return EncryptEnum.RSA.decryptByte(
                EncryptEnum.convertStringToBytes(encryptedMessage),
                privateKey
        ).map(EncryptEnum::convertBytesToString);
    }

}
