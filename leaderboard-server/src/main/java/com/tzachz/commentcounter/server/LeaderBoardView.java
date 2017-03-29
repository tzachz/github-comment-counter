package com.tzachz.commentcounter.server;

import com.tzachz.commentcounter.Commenter;
import com.yammer.dropwizard.views.View;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.transform;
import static java.util.Collections.sort;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 10/08/13
 * Time: 19:13
 */
@SuppressWarnings("UnusedDeclaration")
public class LeaderBoardView extends View {

    private final List<LeaderBoardRecord> records;
    private final String orgName;
    private final boolean loaded;
    private final String period;
    private final String orgURL;

    protected LeaderBoardView(List<Commenter> commenters, CommenterToRecordTransformer transformer, String orgName, String orgURL, boolean loaded, String period) {
        super("leaderboard.mustache");
        this.orgName = orgName;
        this.orgURL = orgURL;
        this.loaded = loaded;
        this.period = period;
        this.records = new ArrayList<>(transformToRecords(commenters, transformer));
        sort(records);
    }

    private List<LeaderBoardRecord> transformToRecords(List<Commenter> commenters, CommenterToRecordTransformer transformer) {
        return transform(commenters, transformer);
    }

    public List<LeaderBoardRecord> getRecords() {
        return records;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getOrgURL() {
        return orgURL;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public String getPeriod() {
        return period;
    }

}
