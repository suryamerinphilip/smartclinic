package com.hospital.app.service;

import java.util.Map;

public interface TokenValidationService {
    Map<String, Object> validateToken(String token, String expectedRole) ;
}

}
