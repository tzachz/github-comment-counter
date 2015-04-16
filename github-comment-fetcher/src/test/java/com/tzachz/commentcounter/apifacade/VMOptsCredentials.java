package com.tzachz.commentcounter.apifacade;

import com.google.common.base.Preconditions;
import org.junit.rules.ExternalResource;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:12
 *
 * This rule reads username and password passed as JVM options to the tests -
 * useful to avoid committing credentials required for integration tests
 */
public class VMOptsCredentials extends ExternalResource {

    private String password;
    private String username;
    private String token;

    @Override
    protected void before() throws Throwable {
        // expecting credentials to be passed as property to tests:
        username = System.getProperty("username");
        password = System.getProperty("password");
        token = System.getProperty("token");
        Preconditions.checkArgument(isTokenBased() || (username != null && !username.isEmpty()),
                "You must provide either a token or a username and password as VM options for these tests, e.g. 'gradle test -Dusername=u -Dpassword=p'");
        super.before();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public boolean isTokenBased() {
        return token != null && token != "";
    }
}
