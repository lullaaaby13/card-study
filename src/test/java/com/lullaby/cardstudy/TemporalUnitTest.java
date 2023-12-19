package com.lullaby.cardstudy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemporalUnitTest {

    @DisplayName("Period.ofDays() 테스트")
    @Test
    void periodOfDays() {
        LocalDateTime now = LocalDateTime.now();
        assertEquals(now.plusDays(1), now.plus(Period.ofDays(1)));
    }

    @DisplayName("Duration.ofHours() 테스트")
    @Test
    void name() {
        LocalDateTime now = LocalDateTime.now();
        assertEquals(now.plusHours(1L), now.plus(Duration.ofHours(1)));
    }

}
