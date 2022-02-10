package io.khaminfo.askmore.security;

public class SecurityConstants {
  public static final String SIGN_UP_URLS = "/api/users/**";
  public static final String DOWNLOAD_URL = "/api/files/**";
  public static final String H2_URL = "/h2-console/**";
  public static final String SECRET = "SecretkeyToGenJWTs";
  public static final String TOKEN_PREFIX="Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final long EXPIRATION_TIME= 3000000;
}
