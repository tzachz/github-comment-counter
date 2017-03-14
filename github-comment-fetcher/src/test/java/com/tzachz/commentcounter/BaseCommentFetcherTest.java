package com.tzachz.commentcounter;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHUser;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Set;

import static com.google.common.collect.Lists.transform;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by imarks on 3/14/2017.
 */
abstract class BaseCommentFetcherTest {

    final GHCommentBuilder commentBuilder = new GHCommentBuilder();
    LocalDate now = new LocalDate(2013, 8, 8);

    @Mock
    GitHubApiFacade facade;

    @Mock
    Clock clock;

    CommentFetcher counter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        counter = new CommentFetcher(facade, getLogin(), 1);
        counter.setClock(clock);
        when(clock.getLocalDateNow()).thenReturn(now);
    }

    protected abstract String getLogin();

    Set<GHRepo> getRepos(GHUser owner, String... repoNames) {
        return Sets.newHashSet(transform(Arrays.asList(repoNames), new Function<String, GHRepo>() {
            @Override
            public GHRepo apply(String name) {
                return new GHRepo(name, owner);
            }
        }));
    }

}
