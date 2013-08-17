package com.tzachz.commentcounter.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 17/08/13
 * Time: 19:05
 */
public class LeaderBoardRecordTest {

    @Test
    public void testSerialization() throws Exception {
        LeaderBoardRecord record = new LeaderBoardRecord("user", 4);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(record);
        LeaderBoardRecord result = mapper.readValue(json, LeaderBoardRecord.class);
        assertThat(result, equalTo(record));
    }
}
