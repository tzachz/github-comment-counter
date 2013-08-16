package com.tzachz.commentcounter.server;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.Commenter;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 16/08/13
 * Time: 16:51
 */
public class LeaderBoardStoreTest {

    @Test
    public void storesCommenters() throws Exception {
        ArrayList<Commenter> commenters = Lists.newArrayList(new Commenter("user1", 3), new Commenter("user2", 5));
        LeaderBoardStore store = new LeaderBoardStore();
        store.set(commenters);
        assertThat(store.get(), hasItems(commenters.get(0), commenters.get(1)));
    }
}
