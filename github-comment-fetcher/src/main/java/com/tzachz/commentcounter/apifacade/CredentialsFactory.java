package com.tzachz.commentcounter.apifacade;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by tzachz on 4/17/15
 */
public class CredentialsFactory {

    public Credentials build(String username, String password, String token) {
        if (Strings.isNullOrEmpty(token)) {
            checkArgument(!Strings.isNullOrEmpty(username), "either token or username must not be empty");
            checkArgument(!Strings.isNullOrEmpty(password), "password cannot be empty");
            return new UserPasswordCredentials(username, password);
        } else {
            return new OAuthCredentials(token);
        }
    }
}
