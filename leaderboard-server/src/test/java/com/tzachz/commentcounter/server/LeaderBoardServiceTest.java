package com.tzachz.commentcounter.server;

import com.yammer.dropwizard.config.Environment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
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
        Integer refresh = 5;
        when(configuration.getRefreshRateMinutes()).thenReturn(refresh);
        service.run(configuration, environment);
        verify(executorService).scheduleAtFixedRate(any(Runnable.class), eq(0l), eq(Long.valueOf(refresh)), eq(TimeUnit.MINUTES));
    }

    @Test
    public void runMethodWiresHealthCheck() throws Exception {
        service.run(configuration, environment);
        verify(environment).addHealthCheck(any(GitHubCredentialsHealthCheck.class));
    }

}
