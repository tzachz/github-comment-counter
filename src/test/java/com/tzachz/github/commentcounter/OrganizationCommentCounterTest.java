package com.tzachz.github.commentcounter;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 01:20
 */
public class OrganizationCommentCounterTest {

    @Rule
    public GitHubCredentialsRule credentials = new GitHubCredentialsRule();

    @Test
    public void printLeaderBoard() throws Exception {
        OrganizationCommentCounter counter = new OrganizationCommentCounter(credentials.getUsername(), credentials.getPassword());
        counter.printCommentsLeaderBoard("kenshoo", 7);
    }
}
