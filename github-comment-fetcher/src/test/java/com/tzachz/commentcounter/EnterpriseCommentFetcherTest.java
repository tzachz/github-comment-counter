package com.tzachz.commentcounter;

import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHPullRequest;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by imarks on 3/14/2017.
 */
public class EnterpriseCommentFetcherTest extends BaseCommentFetcherTest {

    @Test
    public void aggregateReposFromMultipleLogins() throws Exception {
        GHUser org1 = new GHUser(1, "ml", "url1");
        GHUser org2 = new GHUser(2, "devops", "url1");
        when(facade.getLogins()).thenReturn(Sets.newHashSet(org1, org2));

        when(facade.getOrgRepos(org1.getLogin())).thenReturn(getRepos(org1, "repo1", "repo2"));
        when(facade.getOrgRepos(org2.getLogin())).thenReturn(getRepos(org2, "repo3", "repo4"));
        when(facade.getRepoComments(any(GHRepo.class), any(Date.class)))
                .thenReturn(commentBuilder.createEmptyComments("user1", "user2", "user1"));
        when(facade.getPullRequest("")).thenReturn(new GHPullRequest(new GHUser(3, "user3", "")));

        List<Comment> comments = counter.getComments();
        assertThat(comments, hasSize(12));
        verify(facade).getRepoComments(new GHRepo("repo1", org1), now.minusDays(1).toDate());
        verify(facade).getRepoComments(new GHRepo("repo2", org1), now.minusDays(1).toDate());
        verify(facade).getRepoComments(new GHRepo("repo3", org2), now.minusDays(1).toDate());
        verify(facade).getRepoComments(new GHRepo("repo4", org2), now.minusDays(1).toDate());
    }

    @Override
    protected String getLogin() {
        return null;
    }
}
