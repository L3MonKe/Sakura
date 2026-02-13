package dev.sakura.client.exception;

public final class UsernameEmptyNullPointerException extends NullPointerException {
    public UsernameEmptyNullPointerException() {
        super("username is empty");
    }
}
