package com.tzachz.commentcounter.apifacade;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class CredentialsFactoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CredentialsFactory factory = new CredentialsFactory();

    @Test
    public void createsOAuthCredsIfTokenExists() throws Exception {
        assertThat(factory.build("u", "p", "t"), instanceOf(OAuthCredentials.class));
    }

    @Test
    public void createsUserPasswordCredsIfTokenEmpty() throws Exception {
        assertThat(factory.build("u", "p", ""), instanceOf(UserPasswordCredentials.class));
    }

    @Test
    public void failsIfBothUsernameAndTokenEmpty() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("either token or username must not be empty");
        factory.build("", "p", "");
    }

    @Test
    public void failsIfBothPasswordAndTokenEmpty() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("password cannot be empty");
        factory.build("u", "", "");
    }
}