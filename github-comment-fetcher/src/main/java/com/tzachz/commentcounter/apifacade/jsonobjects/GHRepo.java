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

    private final GHUser owner;

    @JsonCreator
    public GHRepo(@JsonProperty("name") String name, @JsonProperty("owner") GHUser owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public GHUser getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GHRepo ghRepo = (GHRepo) o;

        if (name != null ? !name.equals(ghRepo.name) : ghRepo.name != null) return false;
        return owner != null ? owner.equals(ghRepo.owner) : ghRepo.owner == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
