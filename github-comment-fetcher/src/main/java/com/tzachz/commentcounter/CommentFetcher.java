package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.GitHubApiFacade;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:46
 */
public class CommentFetcher {

    public static final Logger logger = LoggerFactory.getLogger(CommentFetcher.class);
    private final GitHubApiFacade facade;
    private final String organization;
    private final int daysBack;

    public CommentFetcher(GitHubApiFacade facade, String organization, int daysBack) {
        this.facade = facade;
        this.organization = organization;
        this.daysBack = daysBack;
    }

    public List<Commenter> getCommentsLeaderBoard() {
        Date since = LocalDate.now().minusDays(this.daysBack).toDate();
        Set<String> repoNames = facade.getOrgRepoNames(this.organization);
        UserComments comments = new UserComments();
        for (String name : repoNames) {
            comments.addAll(facade.getRepoComments(this.organization, name, since));
        }
        logger.info("Fetched comments by {} users from {} repositories", comments.getSize(), repoNames.size());
        return comments.getLeaderBoard();
    }


}
