package com.veljkobogdan.quizzardapp.util;

import java.util.List;
import java.util.Random;

public class ListUtil {
    public static <T> T getRandomListItem(List<T> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
