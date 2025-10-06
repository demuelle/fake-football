package com.demuelle.fake_football.exception;

public class BadNicknameException extends RuntimeException {
    public BadNicknameException(String nickname) {
        super("There is no team called \"nickname\"");
    }
}
