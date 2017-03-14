package com.tzachz.commentcounter.server;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.GHCommentBuilder;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 17/08/13
 * Time: 18:36
 */
public class LeaderBoardViewTest {

    private GHCommentBuilder commentBuilder = new GHCommentBuilder();

    private final GHUser owner = new GHUser(7, "ml", "https://github.mycompany.io/avatars/u/207?");

    private GHRepo repo = new GHRepo("my-repo", owner);

    @Test
    public void commentersSortedByScore() throws Exception {
        Commenter commenterScore6 = new Commenter("user1");
        commenterScore6.addComment(commentBuilder.createComment("user1", "url"), repo);
        commenterScore6.addComment(commentBuilder.createComment("user1", "url"), new GHRepo("anotherRepo", owner));
        Commenter commenterScore5 = new Commenter("user2");
        for (int i = 0; i < 5; i++) {
            commenterScore5.addComment(commentBuilder.createComment("user2", "url"), repo);
        }
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenterScore6, commenterScore5), getTransformer(), "org1", true, "today");
        assertThat(view.getRecords().get(0).getUsername(), is("user1"));
    }

    private CommenterToRecordTransformer getTransformer() {
        return new CommenterToRecordTransformer(Lists.<CommentRenderer>newArrayList());
    }

}
