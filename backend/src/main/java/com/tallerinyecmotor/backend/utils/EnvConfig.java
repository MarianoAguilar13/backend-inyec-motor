package com.tallerinyecmotor.backend.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class EnvConfig {
    private final Dotenv dotenv = Dotenv.load();

    public String getKey() {
        return dotenv.get("KEY");
    }

    public String getPass() {
        return dotenv.get("PASS_APP_MOBILE");
    }
}
