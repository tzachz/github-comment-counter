package com.tzachz.commentcounter.server;

import com.google.common.collect.ImmutableMap;
import com.tzachz.commentcounter.apifacade.EmojisMap;
import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmojiStoreTest {

    public static final EmojisMap EMOJIS_MAP = new EmojisMap(ImmutableMap.of(":emoji:", "http://link"));

    @Mock
    private GitHubApiFacade apiFacade;

    @InjectMocks
    private EmojiStore store;

    @Before
    public void setUp() {
        initMocks(this);
        when(apiFacade.getEmojiMap()).thenReturn(EMOJIS_MAP);
    }

    @Test
    public void constructorDoesNotCallFacade() {
        verifyNoMoreInteractions(apiFacade);
    }

    @Test
    public void getWithoutLoadReturnsEmptyMap() {
        assertThat(store.getMap().getEmojiCodes(), hasSize(0));
    }

    @Test
    public void getAfterLoadReturnsLoadedValue() {
        store.load();
        assertThat(store.getMap(), is(EMOJIS_MAP));
    }

    @Test
    public void secondLoadDoesNothing() {
        store.load();
        store.load();
        verify(apiFacade, times(1)).getEmojiMap();
    }
}
