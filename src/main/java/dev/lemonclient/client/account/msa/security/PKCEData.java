package dev.lemonclient.client.account.msa.security;

public record PKCEData(String challenge, String verifier) {
}
