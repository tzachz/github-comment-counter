package com.tzachz.commentcounter.apifacade;

/**
 * Created by tzachz on 4/17/15
 */
public class OAuthCredentials implements Credentials {

    private static final String OAUTH_PASSWORD = "x-oauth-basic";

    private final String token;

    OAuthCredentials(String token) {
        this.token = token;
    }

    @Override
    public String getUsername() {
        return token;
    }

    @Override
    public String getPassword() {
        return OAUTH_PASSWORD;
    }
}
