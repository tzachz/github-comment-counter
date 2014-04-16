package com.tzachz.commentcounter.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.apifacade.EmojisMap;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SuppressWarnings("ConstantConditions")
public class CommenterToRecordTransformerTest {

    public static final String USER_NAME = "user1";
    public static final String AVATAR_URL = "http://avatar";
    public static final String COMMENT_URL = "http://comment";
    public static final int SCORE = 32;

    private EmojisMap emojisMap = new EmojisMap(ImmutableMap.of("smile", "http://smile"));

    private CommenterToRecordTransformer transformer = new CommenterToRecordTransformer(emojisMap);

    @Mock
    private Commenter commenter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        GHRepo onlyRepo = new GHRepo("repo1");
        when(commenter.getUsername()).thenReturn(USER_NAME);
        when(commenter.getComments()).thenReturn(Lists.newArrayList(createCommentBy(USER_NAME)));
        when(commenter.getRepos()).thenReturn(ImmutableSet.of(onlyRepo));
        when(commenter.getRepoFor(any(GHComment.class))).thenReturn(onlyRepo);
        when(commenter.getScore()).thenReturn(SCORE);
    }

    private GHComment createCommentBy(String user) {
        return createCommentBy(user, "comment");
    }

    private GHComment createCommentBy(String user, String body) {
        return new GHComment(new GHUser(user, AVATAR_URL), "http://pr", body, COMMENT_URL, new Date());
    }

    @Test
    public void commenterAttributesCopied() throws Exception {
        LeaderBoardRecord record = transformer.apply(commenter);
        assertThat(record.getUsername(), is(USER_NAME));
        assertThat(record.getAvatarUrl(), is(AVATAR_URL));
        assertThat(record.getScore(), is(SCORE));
    }

    @Test
    public void commentsCounted() throws Exception {
        ArrayList<GHComment> twoComments = Lists.newArrayList(createCommentBy(USER_NAME), createCommentBy(USER_NAME));
        when(commenter.getComments()).thenReturn(twoComments);
        assertThat(transformer.apply(commenter).getCommentCount(), is(2));
    }

    @Test
    public void reposCounted() throws Exception {
        ImmutableSet<GHRepo> twoRepos = ImmutableSet.of(new GHRepo("repo1"), new GHRepo("repo2"));
        when(commenter.getRepos()).thenReturn(twoRepos);
        assertThat(transformer.apply(commenter).getRepoCount(), is(2));
    }

    @Test
    public void singleCommentAttributesChosen() throws Exception {
        LeaderBoardRecord record = transformer.apply(commenter);
        assertThat(record.getSampleCommentUrl(), is(COMMENT_URL));
    }

    @Test
    public void fewCommentsRandomChosen() throws Exception {
        when(commenter.getComments()).thenReturn(Lists.newArrayList(
                createCommentBy(USER_NAME, "body1"),
                createCommentBy(USER_NAME, "body2"),
                createCommentBy(USER_NAME, "body3")));

        int body1Chosen = 0;
        for (int i = 0; i < 100; i++) {
            body1Chosen += transformer.apply(commenter).getSampleComment().equals("body1") ? 1 : 0;
        }
        assertThat(body1Chosen, is(both(greaterThan(10)).and(lessThan(60))));
    }

    @Test
    public void longCommentsChoppedAfter300Chars() throws Exception {
        when(commenter.getComments()).thenReturn(Lists.newArrayList(createCommentBy(USER_NAME, "Lorem ipsum dolor sit amet, " +
                "consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
                "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat " +
                "nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt " +
                "mollit anim id est laborum.")));
        String comment = transformer.apply(commenter).getSampleComment();
        assertThat(comment, endsWith("..."));
        assertThat(comment.length(), CoreMatchers.is(300));
    }

    @Test
    public void emojisReplacedWithHtmlImages() throws Exception {
        when(commenter.getComments()).thenReturn(Lists.newArrayList(createCommentBy(USER_NAME, "Great! :smile: !")));
        String comment = transformer.apply(commenter).getSampleComment();
        assertThat(comment, is("Great! <img alt=\":smile:\" src=\"http://smile\" height=\"20\" width=\"20\" align=\"absmiddle\"> !"));
    }

    @Test
    public void randomCommentRepoNamePassedToRecord() throws Exception {
        GHComment comment = createCommentBy(USER_NAME);
        when(commenter.getComments()).thenReturn(Lists.newArrayList(comment));
        when(commenter.getRepoFor(comment)).thenReturn(new GHRepo("repo2"));
        assertThat(transformer.apply(commenter).getSampleCommentRepo(), is("repo2"));
    }
}
