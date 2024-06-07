package com.example.springlesson.config.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class JwtUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> decodeJWT(String token) throws IOException {
        DecodedJWT jwt = JWT.decode(token);
        String payload = jwt.getPayload();

        // Base64Url 디코딩
        byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
        String decodedPayload = new String(decodedBytes);

        // JSON 파싱
        return objectMapper.readValue(decodedPayload, Map.class);
    }
}
