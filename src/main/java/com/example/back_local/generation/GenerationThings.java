package com.example.back_local.generation;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class GenerationThings {

    public String generateInviteCode(String group_name) {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid + " -> ");
        byte[] uuidBytes = uuid.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hashBytes = messageDigest.digest(uuidBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 4; j++) {
            sb.append(String.format("%02x", hashBytes[j]));
        }
        System.out.println(sb);
        return group_name + "_" + sb;
    }

}
