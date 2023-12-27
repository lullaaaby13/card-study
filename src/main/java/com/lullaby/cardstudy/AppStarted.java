package com.lullaby.cardstudy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//@Component
public class AppStarted implements ApplicationListener<ApplicationStartedEvent> {

    @Value("${test}")
    private String test;
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        System.out.println("test: " + test);
    }
}
