package com.tzachz.commentcounter.apifacade.jsonobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 09/08/13
 * Time: 00:38
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GHRepo {

    private final String name;

    @JsonCreator
    public GHRepo(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GHRepo ghRepo = (GHRepo) o;

        if (name != null ? !name.equals(ghRepo.name) : ghRepo.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
