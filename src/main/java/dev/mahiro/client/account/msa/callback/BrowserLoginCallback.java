package dev.mahiro.client.account.msa.callback;

@FunctionalInterface
public interface BrowserLoginCallback {
    void callback(final String accessToken);
}
