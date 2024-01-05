package com.lullaby.cardstudy.study;


import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReadTest {

    @Test
    void name() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("test.txt");
        File file = classPathResource.getFile();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isBlank()) {
                stringBuffer.append("@@");
            } else {
                stringBuffer.append(line);
                stringBuffer.append("##");
            }
        }

        String total = stringBuffer.toString();
        String[] split = total.split("@@");
        String[] split1 = split[0].split("##");
        System.out.println(split1[0]);
    }

    @Test
    void name2() {
        String textContent = """
                Apple
                사과
                @@
                Banana
                바나나
                @@
                Citron
                유자
                """;

        String[] split = textContent.split("@@");
        String front = StringUtils.substringBefore(split[0], "\n");
        String back = StringUtils.substringAfter(split[0], "\n");
        System.out.println(front);
        System.out.println("---");
        System.out.println(back);
    }

    public record Card(String front, String back) {

    }
}
