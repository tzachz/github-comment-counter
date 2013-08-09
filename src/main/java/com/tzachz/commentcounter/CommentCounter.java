package com.tzachz.commentcounter;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:46
 */
public class CommentCounter {

    private final OrgRepositoriesResource repositoriesResource;
    private final RepositoryCommentsResource commentsResource;

    protected CommentCounter(String username, String password) {
        repositoriesResource = new OrgRepositoriesResource(username, password);
        commentsResource = new RepositoryCommentsResource(username, password);
    }

    public void printCommentsLeaderBoard(String organization, int daysBack) {
        Date since = LocalDate.now().minusDays(daysBack).toDate();
        Set<String> repoNames = repositoriesResource.getRepoNames(organization);
        UserComments comments = new UserComments();
        for (String name : repoNames) {
            comments.addAll(commentsResource.getUserComments(organization, name, since));
        }
        System.out.println(comments.getLeaderBoard().toString());
    }


}
