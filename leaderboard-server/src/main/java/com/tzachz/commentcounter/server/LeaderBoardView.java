package com.tzachz.commentcounter.server;

import com.google.common.base.Function;
import com.tzachz.commentcounter.Commenter;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.yammer.dropwizard.views.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private final static Random random = new Random();

    private final List<LeaderBoardRecord> records;
    private final String orgName;
    private final boolean loaded;
    private final String period;

    protected LeaderBoardView(List<Commenter> commenters, String orgName, boolean loaded, String period) {
        super("leaderboard.mustache");
        this.orgName = orgName;
        this.loaded = loaded;
        this.period = period;
        this.records = new ArrayList<>(transformToRecords(commenters));
        sort(records);
    }

    private List<LeaderBoardRecord> transformToRecords(List<Commenter> commenters) {
        return transform(commenters, new Function<Commenter, LeaderBoardRecord>() {
            @Override
            public LeaderBoardRecord apply(Commenter commenter) {
                List<GHComment> comments = commenter.getComments();
                GHComment randomComment = comments.get(random.nextInt(comments.size()));
                String avatarUrl = randomComment.getUser().getAvatarUrl();
                return new LeaderBoardRecord(commenter.getUsername(),
                        commenter.getScore(), comments.size(), commenter.getRepos().size(),
                        randomComment.getBody(), randomComment.getHtmlUrl(), avatarUrl);
            }
        });
    }

    public List<LeaderBoardRecord> getRecords() {
        return records;
    }

    public String getOrgName() {
        return orgName;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public String getPeriod() {
        return period;
    }
}
