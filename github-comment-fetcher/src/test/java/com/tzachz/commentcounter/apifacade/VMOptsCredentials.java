package com.tzachz.commentcounter.apifacade;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.junit.rules.ExternalResource;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:12
 *
 * This rule reads username and password passed as JVM options to the tests -
 * useful to avoid committing credentials required for integration tests
 */
public class VMOptsCredentials extends ExternalResource implements Credentials {

    public final static String GH_URL = "https://api.github.com";

    private Credentials credentials;

    @Override
    protected void before() throws Throwable {
        // expecting credentials to be passed as property to tests:
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        String token = System.getProperty("token");
        Preconditions.checkArgument(!isNullOrEmpty(token) || !isNullOrEmpty(username),
                "You must provide either a token or a username and password as VM options for these tests, e.g. 'gradle test -Dusername=u -Dpassword=p'");
        credentials = new CredentialsFactory().build(username, password, token);
    }

    @Override
    public String getPassword() {
        return credentials.getPassword();
    }

    @Override
    public String getUsername() {
        return credentials.getUsername();
    }

    public String getURL() {
        return Optional.fromNullable(System.getProperty("github.url")).or(GH_URL);
    }

}
