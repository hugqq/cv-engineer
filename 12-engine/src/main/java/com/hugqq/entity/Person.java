package com.hugqq.entity;

import lombok.Data;

@Data
public class Person {
    private String name;
    private int age;

    // 是否可以玩游戏，此字段的值，由 drools 引擎计算得出
    private boolean isPlayGame;

}
