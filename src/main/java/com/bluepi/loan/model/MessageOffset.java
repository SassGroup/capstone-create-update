package com.bluepi.loan.model;

import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageOffset implements Comparable {

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageOffset that = (MessageOffset) o;
        return offset == that.offset && partition == that.partition && Objects.equals(topicName, that.topicName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, topicName, partition);
    }

    private long offset;
    private String topicName;
    private int partition;

    @Override
    public int compareTo(Object o) {

        MessageOffset messageOffset = (MessageOffset) o;
        if (this.offset > messageOffset.offset)
            return 1;
        if (this.offset < messageOffset.offset)
            return -1;
        return 0;
    }

}
