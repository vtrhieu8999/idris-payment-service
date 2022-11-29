package com.example.demo.service.factory.testEnum;

import io.vavr.control.Try;
import lombok.SneakyThrows;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public enum EncryptEnum {
    RSA("RSA", 2048);

    private static final int ENCRYPT_MODE = Cipher.ENCRYPT_MODE;
    private static final int DECRYPT_MODE = Cipher.DECRYPT_MODE;
    private static final Base64.Decoder decoder = Base64.getUrlDecoder();
    private static final Base64.Encoder encoder = Base64.getUrlEncoder();
    private final KeyFactory keyFactory;
    private final KeyPairGenerator keyPairGenerator;
    private final String encryptAlgorithm;

    @SneakyThrows
    EncryptEnum(String encryptAlgorithm, int keySize) {
        this.encryptAlgorithm = encryptAlgorithm;
        this.keyPairGenerator = KeyPairGenerator.getInstance(encryptAlgorithm);
        this.keyPairGenerator.initialize(keySize);
        this.keyFactory = KeyFactory.getInstance(encryptAlgorithm);
    }

    public KeyPair generateKeyPair() {
        return keyPairGenerator.generateKeyPair();
    }

    private <T extends Key> Try<byte[]> encryptDecryptPrototype(int mode, byte[] message, T key) {
        try {
            Cipher cipher = Cipher.getInstance(encryptAlgorithm);
            cipher.init(mode, key);
            return Try.success(cipher.doFinal(message));
        } catch (NoSuchPaddingException
                 | IllegalBlockSizeException
                 | NoSuchAlgorithmException
                 | BadPaddingException
                 | InvalidKeyException e) {
            return Try.failure(e);
        }
    }

    public Try<byte[]> encryptByte(byte[] secretMessageByte, PublicKey publicKey) {
        return encryptDecryptPrototype(ENCRYPT_MODE, secretMessageByte, publicKey);
    }

    public Try<byte[]> decryptByte(byte[] encryptedMessage, PrivateKey privateKey) {
        return encryptDecryptPrototype(DECRYPT_MODE, encryptedMessage, privateKey);
    }

    public Try<PublicKey> convertBytesToPublicKey(byte[] decodeKey) {
        try {
            return Try.success(keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey)));
        } catch (InvalidKeySpecException e) {
            return Try.failure(e);
        }
    }

    public Try<PrivateKey> convertBytesToPrivateKey(byte[] privateKeyData) {
        try {
            return Try.success(keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyData)));
        } catch (InvalidKeySpecException e) {
            return Try.failure(e);
        }
    }

    public static String convertBytesToString(byte[] messageBytes) {
        return encoder.encodeToString(messageBytes);
    }

    public static byte[] convertStringToBytes(String message) {
        return decoder.decode(message);
    }
}
