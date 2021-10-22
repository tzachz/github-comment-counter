package com.tzachz.commentcounter.apifacade;

import com.tzachz.commentcounter.apifacade.jsonobjects.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 17/08/13
 * Time: 10:49
 */
public class GitHubApiFacadeImplTest {

    private static final String ORG_NAME = "org-name";

    @Mock
    private OrgResource orgResource;

    @Mock
    private OrgRepositoriesResource orgReposResource;

    @Mock
    private RepoCommentsResource commentsResource;

    @Mock
    private PullRequestResource pullRequestResource;

    @Mock
    private EmojisResource emojisResource;

    @InjectMocks
    private GitHubApiFacadeImpl apiFacade;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getOrgsCallsOrgResource() {
        final GHOrg org = new GHOrg(ORG_NAME, "http://a.b");
        when(orgResource.getOrg(ORG_NAME)).thenReturn(org);
        assertThat(apiFacade.getOrg(ORG_NAME), is(org));
    }

    @Test
    public void getOrgReposCallsOrgReposResource() {
        Set<GHRepo> repos = Set.of(new GHRepo("repo1"), new GHRepo("repo2"));
        when(orgReposResource.getRepos(ORG_NAME)).thenReturn(repos);
        Set<GHRepo> result = apiFacade.getOrgRepos(ORG_NAME);
        assertThat(result, containsInAnyOrder(repos.toArray()));
    }

    @Test
    public void getCommentsCallsCommentResource() {
        Date since = new Date();
        List<GHComment> comments = List.of(createCommentBy("user1"), createCommentBy("user2"));
        when(commentsResource.getUserComments(ORG_NAME, "repo1", since)).thenReturn(comments);
        assertThat(apiFacade.getRepoComments(ORG_NAME, "repo1", since), containsInAnyOrder(comments.toArray()));
    }

    @Test
    public void getPullRequestCallsPRResource() {
        GHPullRequest pullRequest = new GHPullRequest(new GHUser("user1", "http://user", "http://avatar"), "");
        when(pullRequestResource.getPullRequest("http://pr")).thenReturn(pullRequest);
        assertThat(apiFacade.getPullRequest("http://pr"), is(pullRequest));
    }

    @Test
    public void getEmojiMapCallsEmojisResource() {
        EmojisMap emojisMap = new EmojisMap(Map.of(":smile:", "http://smile"));
        when(emojisResource.getEmojisMap()).thenReturn(emojisMap);
        assertThat(emojisResource.getEmojisMap(), is(emojisMap));
    }

    private GHComment createCommentBy(String user) {
        return new GHComment(new GHUser(user, "http://user", "http://avatar"), "http://pull", "comment", "http://link", new Date());
    }
}
