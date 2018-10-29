package com.tzachz.commentcounter.apifacade;

/**
 * Created by tzachz on 4/17/15
 */
public class UserPasswordCredentials implements Credentials {

    private final String user;
    private final String password;

    UserPasswordCredentials(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
