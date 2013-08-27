package com.tzachz.commentcounter.server;

import com.yammer.dropwizard.config.Environment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.concurrent.ScheduledExecutorService;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 16/08/13
 * Time: 16:36
 */
public class LeaderBoardServiceTest {

    @Mock
    private Environment environment;

    @Mock
    private GitHubCredentials credentials;

    @Mock
    private LeaderBoardServerConfiguration configuration;

    @Mock
    private ScheduledExecutorService executorService;

    @InjectMocks
    private LeaderBoardService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(credentials.getUsername()).thenReturn("user1");
        when(credentials.getPassword()).thenReturn("pass1");
        when(configuration.getGitHubCredentials()).thenReturn(credentials);
        when(environment.managedScheduledExecutorService(anyString(), anyInt())).thenReturn(executorService);
    }

    @Test
    public void runMethodWiresResource() throws Exception {
        service.run(configuration, environment);
        verify(environment).addResource(any(LeaderBoardResource.class));
    }

    @Test
    public void runMethodWiresScheduledExecutorService() throws Exception {
        service.run(configuration, environment);
        verify(environment, times(3)).managedScheduledExecutorService("comment-fetcher", 1);
    }

    @Test
    public void runMethodWiresHealthCheck() throws Exception {
        service.run(configuration, environment);
        verify(environment).addHealthCheck(any(GitHubCredentialsHealthCheck.class));
    }

}
