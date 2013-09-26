package com.tzachz.commentcounter;

import com.tzachz.commentcounter.apifacade.jsonobjects.GHComment;
import com.tzachz.commentcounter.apifacade.jsonobjects.GHRepo;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 26/09/13
 * Time: 18:08
 */
public class Comment {

    private final GHComment comment;
    private final GHRepo repo;

    public Comment(GHComment comment, GHRepo repo) {
        this.comment = comment;
        this.repo = repo;
    }

    public GHComment getComment() {
        return comment;
    }

    public GHRepo getRepo() {
        return repo;
    }
}
