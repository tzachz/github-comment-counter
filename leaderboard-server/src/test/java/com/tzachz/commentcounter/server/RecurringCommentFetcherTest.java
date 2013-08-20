package com.tzachz.commentcounter.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.tzachz.commentcounter.CommentFetcher;
import com.tzachz.commentcounter.Commenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 16/08/13
 * Time: 16:54
 */
public class RecurringCommentFetcherTest {

    public static final int REFRESH_RATE_MS = 5;

    @Mock
    private LeaderBoardStore store;

    @Mock
    private CommentFetcher fetcher;

    private RecurringCommentFetcher recurringFetcher;
    private List<Commenter> commenterList = Lists.newArrayList();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        recurringFetcher = new RecurringCommentFetcher(ImmutableMap.of("today", fetcher), store, REFRESH_RATE_MS, TimeUnit.MILLISECONDS);
        when(fetcher.getCommentsByUser()).thenReturn(commenterList);
    }

    @Test
    public void callsFetcherEveryPeriodAndStores() throws Exception {
        int rounds = 3;
        recurringFetcher.start();
        Thread.sleep((REFRESH_RATE_MS * rounds) + 1);
        recurringFetcher.stop();
        verify(fetcher, atLeast(rounds)).getCommentsByUser();
        verify(store, atLeast(rounds)).set("today", commenterList);
    }
}
