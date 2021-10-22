package com.tzachz.commentcounter.apifacade;


import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class EmojisMap {

    private final Map<String, String> nameToLink;

    public EmojisMap(Map<String, String> nameToLink) {
        this.nameToLink = nameToLink;
    }

    public String getLink(String emojiCode) {
        return this.nameToLink.get(emojiCode);
    }

    public Set<String> getEmojiCodes() {
        return Set.copyOf(this.nameToLink.keySet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmojisMap emojisMap = (EmojisMap) o;

        return Objects.equals(nameToLink, emojisMap.nameToLink);
    }

    @Override
    public int hashCode() {
        return nameToLink != null ? nameToLink.hashCode() : 0;
    }
}
