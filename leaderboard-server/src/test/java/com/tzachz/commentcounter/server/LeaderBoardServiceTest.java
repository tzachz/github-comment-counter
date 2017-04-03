package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.yammer.dropwizard.config.Environment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.*;
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
    private GitHubApiFacade apiFacade;

    @Mock
    private ScheduledExecutorService executorService;

    private LeaderBoardService service;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(credentials.getUsername()).thenReturn("user1");
        when(credentials.getPassword()).thenReturn("pass1");
        when(configuration.getGitHubCredentials()).thenReturn(credentials);
        when(environment.managedScheduledExecutorService(anyString(), anyInt())).thenReturn(executorService);
        this.service = new LeaderBoardService() {
            @Override
            protected GitHubApiFacade getApiFacade(LeaderBoardServerConfiguration configuration) {
                return apiFacade;
            }
        };
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
        verify(executorService).scheduleAtFixedRate(any(Runnable.class), eq(0L), eq(Long.valueOf(refresh)), eq(TimeUnit.MINUTES));
    }

    @Test
    public void runMethodWiresHealthCheck() throws Exception {
        service.run(configuration, environment);
        verify(environment).addHealthCheck(any(GitHubCredentialsHealthCheck.class));
    }

}
