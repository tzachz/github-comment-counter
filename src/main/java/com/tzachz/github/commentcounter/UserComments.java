package com.tzachz.github.commentcounter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: tzachz
 * Date: 8/8/13
 */
public class UserComments {

    private Set<String> repoNames = new HashSet<>();
    private int count = 0;

    public void addAll(Collection<GHEvent> comments) {
        for (GHEvent comment : comments) {
            repoNames.add(comment.getRepo().getName());
            count++;
        }
    }

    public int getReposCount() {
        return repoNames.size();
    }

    public int getCount() {
        return count;
    }
}
