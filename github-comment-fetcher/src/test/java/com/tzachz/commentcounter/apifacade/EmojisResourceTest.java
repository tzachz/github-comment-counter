package com.tzachz.commentcounter.apifacade;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class EmojisResourceTest {

    @Rule
    public VMOptsCredentials credentials = new VMOptsCredentials();

    public static final String JSON = "{\n" +
            "  \"+1\": \"https://github.global.ssl.fastly.net/images/icons/emoji/+1.png?v5\",\n" +
            "  \"-1\": \"https://github.global.ssl.fastly.net/images/icons/emoji/-1.png?v5\",\n" +
            "  \"100\": \"https://github.global.ssl.fastly.net/images/icons/emoji/100.png?v5\",\n" +
            "  \"1234\": \"https://github.global.ssl.fastly.net/images/icons/emoji/1234.png?v5\",\n" +
            "  \"8ball\": \"https://github.global.ssl.fastly.net/images/icons/emoji/8ball.png?v5\",\n" +
            "  \"a\": \"https://github.global.ssl.fastly.net/images/icons/emoji/a.png?v5\",\n" +
            "  \"ab\": \"https://github.global.ssl.fastly.net/images/icons/emoji/ab.png?v5\"\n" +
            "}";

    @Test
    public void answerDeserializedIntoMap() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Object result = mapper.readValue(JSON, Object.class);
        assertThat(result, instanceOf(Map.class));
    }

    @Test
    public void emojisFetched() throws Exception {
        EmojisResource resource = null;
        if (credentials.isTokenBased()) {
            resource = new EmojisResource(credentials.getToken());
        } else {
            resource = new EmojisResource(credentials.getUsername(), credentials.getPassword());
        }
        EmojisMap emojisMap = resource.getEmojisMap();
        assertThat(emojisMap.getEmojiCodes(), hasSize(greaterThan(100)));
    }
}
