package br.edu.ciriguela.config.jwt;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyStringToGenerateYourJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String PROFESSOR_URL = "/professors";
    public static final String STUDENT_URL = "/students";

}

