package com.tzachz.commentcounter.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@SuppressWarnings("ConstantConditions")
public class CommenterToRecordTransformerTest {

    public static final String USER_NAME = "user1";
    public static final String AVATAR_URL = "http://avatar";
    public static final String USER_URL = "http://user";
    public static final String COMMENT_URL = "http://comment";
    public static final int SCORE = 32;

    @Mock
    private CommentRenderer renderer1;

    @Mock
    private CommentRenderer renderer2;

    @Mock
    private Commenter commenter;

    private CommenterToRecordTransformer transformer;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        GHRepo onlyRepo = new GHRepo("repo1");
        when(commenter.getUsername()).thenReturn(USER_NAME);
        when(commenter.getComments()).thenReturn(Lists.newArrayList(createCommentBy(USER_NAME)));
        when(commenter.getRepos()).thenReturn(ImmutableSet.of(onlyRepo));
        when(commenter.getRepoFor(any(GHComment.class))).thenReturn(onlyRepo);
        when(commenter.getScore()).thenReturn(SCORE);
        when(renderer1.render(anyString())).thenAnswer(answerFirstArgument());
        when(renderer2.render(anyString())).thenAnswer(answerFirstArgument());
        transformer = new CommenterToRecordTransformer(ImmutableList.of(renderer1, renderer2));
    }

    private Answer<String> answerFirstArgument() {
        return new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return (String) invocation.getArguments()[0];
            }
        };
    }

    private GHComment createCommentBy(String user) {
        return createCommentBy(user, "comment");
    }

    private GHComment createCommentBy(String user, String body) {
        return new GHComment(new GHUser(user, USER_URL, AVATAR_URL), "http://pr", body, COMMENT_URL, new Date());
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
    public void randomCommentRepoNamePassedToRecord() throws Exception {
        GHComment comment = createCommentBy(USER_NAME);
        when(commenter.getComments()).thenReturn(Lists.newArrayList(comment));
        when(commenter.getRepoFor(comment)).thenReturn(new GHRepo("repo2"));
        assertThat(transformer.apply(commenter).getSampleCommentRepo(), is("repo2"));
    }

    @Test
    public void renderersCalledInOrder() throws Exception {
        when(renderer1.render(anyString())).thenReturn("first result");
        when(renderer2.render("first result")).thenReturn("end result");
        assertThat(transformer.apply(commenter).getSampleComment(), is("end result"));
    }
}
