package com.unknow.client;

import com.unknow.entity.Card;
import com.unknow.entity.data.CardData;

import java.util.ArrayList;
import java.util.List;

public class ClientInfo {
    private int id;
    private String name;
    private final List<Card> hand;
    private CardData lastCardData;

    public ClientInfo(int id, String name) {
        this.id = id;
        this.name = name;
        this.hand = new ArrayList<>();
    }

    // 添加卡牌到手牌
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    // 移除手牌中的卡牌
    public void removeCardFromHand(Card card) {
        hand.remove(card);
    }

    // 获取手牌
    public List<Card> getHand() {
        return hand;
    }

    // 获取上一张出的牌
    public CardData getLastCardData() {
        return lastCardData;
    }

    // 设置上一张出的牌
    public void setLastCardData(CardData lastCardData) {
        this.lastCardData = lastCardData;
    }

    // 其他Getter和Setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
