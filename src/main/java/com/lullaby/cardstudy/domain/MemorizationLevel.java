package com.lullaby.cardstudy.domain;

import java.time.Duration;

public enum MemorizationLevel {
    Difficult(0, Duration.ofMinutes(5L)),
    SomewhatDifficult(1, Duration.ofDays(1L).minusHours(1L)),
    Understand(2, Duration.ofDays(3L).minusHours(1L)),
    Easy(3, Duration.ofDays(7L).minusHours(1L)),
    VeryEasy(4, Duration.ofDays(14L).minusHours(1L)),
    Perfect(5, Duration.ofDays(28L).minusHours(1L));

    MemorizationLevel(int level, Duration duration) {
        this.level = level;
        this.duration = duration;
    }

    private int level;
    private Duration duration;

    public Duration getDuration() {
        return duration;
    }

    public int getLevel() {
        return level;
    }

    public MemorizationLevel getNextLevel() {
        if (this == Perfect) {
            return this;
        }
        return MemorizationLevel.values()[this.level + 1];
    }

    public MemorizationLevel getPreviousLevel() {
        if (this == Difficult) {
            return this;
        }
        return MemorizationLevel.values()[this.level - 1];
    }
}
