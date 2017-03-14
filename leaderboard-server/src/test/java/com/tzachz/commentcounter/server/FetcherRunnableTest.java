package com.tzachz.commentcounter.server;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.Comment;
import com.tzachz.commentcounter.CommentFetcher;
import com.tzachz.commentcounter.GHCommentBuilder;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 10/2/13
 */
public class FetcherRunnableTest {

    @Mock
    private LeaderBoardStore store;

    @Mock
    private CommentFetcher fetcher;

    @InjectMocks
    private FetcherRunnable runnable;

    private GHCommentBuilder commentBuilder = new GHCommentBuilder();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void fetchesAndStores() throws Exception {
        ArrayList<Comment> comments = Lists.newArrayList(new Comment(commentBuilder.createComment("u1", "url"),
                new GHRepo("r", new GHUser(7, "ml", "https://github.mycompany.io/avatars/u/207?"))));
        when(fetcher.getComments()).thenReturn(comments);
        runnable.run();
        verify(store).set(comments);
    }

    @Test
    public void exceptionInFetcherIsSwallowed() throws Exception {
        when(fetcher.getComments()).thenThrow(new RuntimeException());
        runnable.run();
        verifyNoMoreInteractions(store);
    }

    @Test
    public void exceptionInStoreIsSwallowed() throws Exception {
        ArrayList<Comment> comments = Lists.newArrayList();
        when(fetcher.getComments()).thenReturn(comments);
        doThrow(new RuntimeException()).when(store).set(comments);
        runnable.run();
    }
}
