package com.demuelle.fake_football.exception;

public class NoSuchTeamException extends RuntimeException {
    public NoSuchTeamException(Integer id) {
        super("There is no team with id " + id);
    }
}
