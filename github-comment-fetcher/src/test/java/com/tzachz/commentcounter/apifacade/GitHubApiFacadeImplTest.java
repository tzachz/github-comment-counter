package com.tzachz.commentcounter.apifacade;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 17/08/13
 * Time: 10:49
 */
public class GitHubApiFacadeImplTest {

    public static final String ORG_NAME = "org-name";

    private final GHUser owner = new GHUser(7, "ml", "https://github.mycompany.io/avatars/u/207?");

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
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void getOrgsCallsOrgResource() throws Exception {
        when(orgResource.getOrg(ORG_NAME)).thenReturn("1");
        assertThat(apiFacade.getOrg(ORG_NAME), is((Object) "1"));
    }

    @Test
    public void getOrgReposCallsOrgReposResource() throws Exception {
        ImmutableSet<GHRepo> repos = ImmutableSet.of(new GHRepo("repo1", owner), new GHRepo("repo2", owner));
        when(orgReposResource.getRepos(ORG_NAME)).thenReturn(repos);
        Set<GHRepo> result = apiFacade.getOrgRepos(ORG_NAME);
        assertThat(result, containsInAnyOrder(repos.toArray()));
    }

    @Test
    public void getCommentsCallsCommentResource() throws Exception {
        Date since = new Date();
        ArrayList<GHComment> comments = Lists.newArrayList(createCommentBy("user1"), createCommentBy("user2"));
        when(commentsResource.getUserComments(ORG_NAME, "repo1", since)).thenReturn(comments);
        assertThat(apiFacade.getRepoComments(eq(new GHRepo("repo1", owner)), since), containsInAnyOrder(comments.toArray()));
    }

    @Test
    public void getPullRequestCallsPRResource() throws Exception {
        GHPullRequest pullRequest = new GHPullRequest(new GHUser(1, "user1", "http://avatar"));
        when(pullRequestResource.getPullRequest("http://pr")).thenReturn(pullRequest);
        assertThat(apiFacade.getPullRequest("http://pr"), is(pullRequest));
    }

    @Test
    public void getEmojiMapCallsEmojisResource() throws Exception {
        EmojisMap emojisMap = new EmojisMap(ImmutableMap.of(":smile:", "http://smile"));
        when(emojisResource.getEmojisMap()).thenReturn(emojisMap);
        assertThat(emojisResource.getEmojisMap(), is(emojisMap));
    }

    @Test
    public void getLogins() throws Exception {
        HashSet<GHUser> expected = Sets.newHashSet(new GHUser(1, "devops", "http://avatar1"),
                new GHUser(1, "data", "http://avatar2"));
        when(orgResource.getLogins()).thenReturn(expected);
        Set<GHUser> result = apiFacade.getLogins();
        assertThat(result, is(expected));
    }

    private GHComment createCommentBy(String user) {
        return new GHComment(new GHUser(user.hashCode(), user, "http://avatar"), "http://pull", "comment", "http://link", new Date());
    }
}
