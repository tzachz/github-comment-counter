package com.tzachz.commentcounter.apifacade;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class EmojisMapTest {

    @Test
    public void getLinkForExistingCodeReturnsMapValue() {
        EmojisMap emojisMap = new EmojisMap(Map.of("key", "value"));
        assertThat(emojisMap.getLink("key"), is("value"));
    }

    @Test
    public void getLinkForMissingValueReturnsNull() {
        EmojisMap emojisMap = new EmojisMap(Map.of("key", "value"));
        assertThat(emojisMap.getLink("missing"), nullValue());
    }

    @Test
    public void getCodesReturnsKeySet() {
        EmojisMap emojisMap = new EmojisMap(Map.of("key1", "value", "key2", "value"));
        assertThat(emojisMap.getEmojiCodes(), containsInAnyOrder("key1", "key2"));
    }
}
