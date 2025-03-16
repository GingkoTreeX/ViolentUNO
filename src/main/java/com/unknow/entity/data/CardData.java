package com.unknow.entity.data;

// CardData.java - 卡牌数据基类（封装属性）
public class CardData {
    private final String imageName; // 对应resources/images下的图片文件名
    private final Integer num;
    private final String color;
    private final int type;
    public CardData(int type,String imageName, Integer num, String color) {
        this.type = type;
        this.imageName = imageName;
        this.num = num;
        this.color = color;
    }
    public String getImageName() {
        return imageName;
    }

    public Integer getNum() {
        return num;
    }

    public String getColor() {
        return color;
    }

    public int getType() {
        return type;
    }
}