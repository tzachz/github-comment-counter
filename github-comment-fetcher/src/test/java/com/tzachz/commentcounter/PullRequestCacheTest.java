package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 17/08/13
 * Time: 10:34
 */
public class PullRequestCacheTest {

    @Mock
    private GitHubApiFacade facade;

    @InjectMocks
    private PullRequestCache cache;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void getReturnsFacadeResult() throws Exception {
        GHPullRequest expected = new GHPullRequest(new GHUser(1, "user1", ""));
        when(facade.getPullRequest("url")).thenReturn(expected);
        GHPullRequest result = cache.get("url");
        assertThat(result, equalTo(expected));
    }

    @Test
    public void successfulFetchOccursOnce() throws Exception {
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest(new GHUser(1, "user1", "")));
        cache.get("url");
        cache.get("url");
        verify(facade, times(1)).getPullRequest("url");
        verifyNoMoreInteractions(facade);
    }

    @Test
    public void failedFetchRetriesEveryTime() throws Exception {
        when(facade.getPullRequest("url")).thenThrow(new RuntimeException("can't access url"));
        cache.get("url");
        cache.get("url");
        verify(facade, times(2)).getPullRequest("url");
    }

    @Test
    public void failedFetchReturnsUnknownUserPullRequest() throws Exception {
        when(facade.getPullRequest("url")).thenThrow(new RuntimeException("can't access url"));
        GHPullRequest result = cache.get("url");
        assertThat(result, equalTo(PullRequestCache.UNKNOWN));
    }
}
