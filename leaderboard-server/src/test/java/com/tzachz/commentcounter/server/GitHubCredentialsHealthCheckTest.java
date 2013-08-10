package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.yammer.metrics.core.HealthCheck;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 15:03
 */
public class GitHubCredentialsHealthCheckTest {

    private GitHubCredentialsHealthCheck healthCheck;

    @Mock
    private GitHubApiFacade facade;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        healthCheck = new GitHubCredentialsHealthCheck(facade, "orgName");
    }

    @Test
    public void successfulCallReturnsHealthy() throws Exception {
        when(facade.getOrg("orgName")).thenReturn("some valid object");
        assertThat(healthCheck.check(), equalTo(HealthCheck.Result.healthy()));
    }

    @Test
    public void failedCallReturnsUnhealthy() throws Exception {
        final RuntimeException exception = new RuntimeException();
        when(facade.getOrg("orgName")).thenThrow(exception);
        assertThat(healthCheck.check(), new BaseMatcher<HealthCheck.Result>() {
            @Override
            public boolean matches(Object o) {
                //noinspection ThrowableResultOfMethodCallIgnored
                return o instanceof HealthCheck.Result &&
                        !((HealthCheck.Result)o).isHealthy() &&
                        ((HealthCheck.Result)o).getError() == exception;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("unhealthy result with UniformInterfaceException");
            }
        });
    }
}
