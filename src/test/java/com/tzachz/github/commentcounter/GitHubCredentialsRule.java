package com.tzachz.github.commentcounter;

import org.junit.rules.ExternalResource;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:12
 */
public class GitHubCredentialsRule extends ExternalResource {

    private String password;
    private String username;

    @Override
    protected void before() throws Throwable {
        // expecting credentials to be passed as property to tests:
        username = System.getProperty("username");
        password = System.getProperty("password");
        super.before();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
