package com.tzachz.commentcounter;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.transform;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 01:20
 */
public class CommentFetcherTest extends BaseCommentFetcherTest {

    public static final String ORG_NAME = "org";

    private final GHUser owner = new GHUser(1, ORG_NAME, "https://github.mycompany.io/avatars/u/207?");

    @Test
    public void aggregatesRepos() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos(owner, "repo1", "repo2"));
        when(facade.getRepoComments(any(GHRepo.class), any(Date.class)))
                .thenReturn(commentBuilder.createEmptyComments("user1", "user2", "user1"));
        when(facade.getPullRequest("")).thenReturn(new GHPullRequest(new GHUser(3, "user3", "")));

        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(6));
        verify(facade).getRepoComments(new GHRepo("repo1",owner), now.minusDays(1).toDate());
        verify(facade).getRepoComments(new GHRepo("repo2",owner), now.minusDays(1).toDate());
    }

    @Test
    public void commentOnSelfPullRequestIgnored() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos(owner, "repo1"));
        when(facade.getRepoComments(new GHRepo("repo1",owner), now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createCommentCollection("user1", "url"));
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest(new GHUser("user1".hashCode(), "user1", "")));
        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(0));
    }

    @Test
    public void pullRequestNotFoundMeansCommentCounted() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos(owner, "repo1"));
        when(facade.getRepoComments(new GHRepo("repo1",owner), now.minusDays(1).toDate()))
                .thenReturn(commentBuilder.createCommentCollection("user1", "url"));
        when(facade.getPullRequest("url")).thenThrow(new RuntimeException("bad url"));
        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(1));
    }

    @Test
    public void oldCommentFilteredOut() throws Exception {
        when(facade.getOrgRepos(ORG_NAME)).thenReturn(getRepos(owner, "repo1"));
        when(facade.getPullRequest("url")).thenReturn(new GHPullRequest(new GHUser(2, "user2", "")));
        when(facade.getRepoComments(new GHRepo("repo1",owner), now.minusDays(1).toDate()))
                .thenReturn(Arrays.asList(commentBuilder.createComment("user1", "url", now.minusDays(2).toDate())));
        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(0));
    }

    @Override
    protected String getLogin() {
        return ORG_NAME;
    }
}
