package dev.sakura.client.account.msa.exception;

public final class MSAAuthException extends Exception {
    private final String message;

    public MSAAuthException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
