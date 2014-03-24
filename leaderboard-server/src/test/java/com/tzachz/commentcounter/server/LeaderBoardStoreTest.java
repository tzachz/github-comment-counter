package com.tzachz.commentcounter.server;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.Clock;
import com.tzachz.commentcounter.Comment;
import com.tzachz.commentcounter.GHCommentBuilder;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 16/08/13
 * Time: 16:51
 */
public class LeaderBoardStoreTest {

    private final LocalDate now = LocalDate.now();
    private final GHCommentBuilder commentBuilder = new GHCommentBuilder();

    @Mock
    private Clock clock;

    @Mock
    private EmojiStore emojiStore;

    @InjectMocks
    private LeaderBoardStore store;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(clock.getLocalDateNow()).thenReturn(now);
    }

    @Test
    public void storeStartsUnloaded() throws Exception {
        assertThat(store.isLoaded("today"), is(false));
        assertThat(store.isLoaded("week"), is(false));
        assertThat(store.isLoaded("month"), is(false));
    }

    @Test
    public void setLoadsAllPeriods() throws Exception {
        store.set(Lists.<Comment>newArrayList());
        assertThat(store.isLoaded("today"), is(true));
        assertThat(store.isLoaded("week"), is(true));
        assertThat(store.isLoaded("month"), is(true));
    }

    @Test
    public void setCallsEmojiStoreLoad() throws Exception {
        store.set(Lists.<Comment>newArrayList());
        verify(emojiStore).load();
    }

    @Test
    public void todayExcludesEarlierComments() throws Exception {
        ArrayList<Comment> comments = Lists.newArrayList(
                createComment("user1", now.toDate(), "repo1"),
                createComment("user1", now.minusDays(2).toDate(), "repo1"));
        store.set(comments);
        List<GHComment> user1Comments = store.get("today").get(0).getComments();
        assertThat(user1Comments, hasSize(1));
    }

    @Test
    public void usersAggregatedSeparately() throws Exception {
        ArrayList<Comment> comments = Lists.newArrayList(
                createComment("user1", now.toDate(), "repo1"),
                createComment("user1", now.toDate(), "repo2"),
                createComment("user2", now.toDate(), "repo1"));
        store.set(comments);
        assertThat(store.get("month"), hasSize(2));
    }

    private Comment createComment(String user, Date date, String repoName) {
        return new Comment(commentBuilder.createComment(user, "url", date), new GHRepo(repoName));
    }
}
