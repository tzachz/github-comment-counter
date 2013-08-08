package com.tzachz.github.commentcounter;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class UserCommentsTest {

    @Test
    public void commentsAndReposCounted() throws Exception {
        UserComments userComments = new UserComments();
        List<GHEvent> comments = Lists.newArrayList(new GHEvent("repo1"), new GHEvent("repo1"), new GHEvent("repo2"));
        userComments.addAll(comments);
        assertThat(userComments.getReposCount(), is(2));
        assertThat(userComments.getCount(), is(3));
    }
}
