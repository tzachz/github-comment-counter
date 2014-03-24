package com.tzachz.commentcounter.apifacade;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class EmojisMapTest {

    @Test
    public void getLinkForExistingCodeReturnsMapValue() throws Exception {
        EmojisMap emojisMap = new EmojisMap(ImmutableMap.of("key", "value"));
        assertThat(emojisMap.getLink("key"), is("value"));
    }

    @Test
    public void getLinkForMissingValueReturnsNull() throws Exception {
        EmojisMap emojisMap = new EmojisMap(ImmutableMap.of("key", "value"));
        assertThat(emojisMap.getLink("missing"), nullValue());
    }

    @Test
    public void getCodesReturnsKeySet() throws Exception {
        EmojisMap emojisMap = new EmojisMap(ImmutableMap.of("key1", "value", "key2", "value"));
        assertThat(emojisMap.getEmojiCodes(), containsInAnyOrder("key1", "key2"));
    }
}
