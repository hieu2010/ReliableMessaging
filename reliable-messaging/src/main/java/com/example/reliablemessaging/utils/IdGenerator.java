package com.example.reliablemessaging.utils;

import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Random;

@Service
public class IdGenerator {

    private static final short ID_LEN = 10;
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder().withoutPadding();
    private static final Random RAND = new Random();

    private byte[] getRaw() {
        byte[] raw = new byte[ID_LEN];
        RAND.nextBytes(raw);
        return raw;
    }

    public String generateId() {
        return BASE64_ENCODER.encodeToString(getRaw());
    }
}
