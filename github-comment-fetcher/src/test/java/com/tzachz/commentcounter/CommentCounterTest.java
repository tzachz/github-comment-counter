package com.tzachz.commentcounter;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 01:20
 */
public class CommentCounterTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    @Test
    public void printLeaderBoard() throws Exception {
        CommentCounter counter = new CommentCounter(credentials.getUsername(), credentials.getPassword());
        counter.printCommentsLeaderBoard("kenshoo", 7);
    }
}
