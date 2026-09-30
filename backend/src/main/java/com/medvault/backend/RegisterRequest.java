package com.medvault.backend;

public record RegisterRequest(String fullName, String email, String password) {
}