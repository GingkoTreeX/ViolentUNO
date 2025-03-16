package com.unknow.server;

import com.unknow.entity.Card;
import com.unknow.entity.data.CardData;
import com.unknow.entity.data.ParticularCardData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UnoGame {
    private List<Card> deck;
    private List<Player> players;
    private final long UUID;

    public UnoGame(int number) {
        this.players = new ArrayList<>();
        this.UUID = new Random().nextInt(10000000);

        deck = new ArrayList<>();

        // 初始化牌堆
        initializeDeck();

        // 洗牌
        Collections.shuffle(deck);

        // 初始化玩家
        for (int i = 0; i < number; i++) {
            Player player = new Player(i);
            dealCardsToPlayer(player);
            players.add(player);
        }
    }

    private void initializeDeck() {
        // 添加数字卡
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 2; k++) {
                    String color = getColor(j).toLowerCase();
                    CardData cardData = new CardData(0, i + "_" + color + ".png", i, color);
                    Card card = new Card(cardData, 0, 0, 80, 120);
                    deck.add(card);
                }
            }
        }

        // 添加功能牌
        for (int i = 0; i < 4; i++) {
            ParticularCardData specialCardData = new ParticularCardData("special_" + i, i);
            Card specialCard = new Card(specialCardData, 0, 0, 80, 120);
            deck.add(specialCard);
        }
    }

    private void dealCardsToPlayer(Player player) {
        // 发牌给玩家，玩家手牌数量为7张
        for (int i = 0; i < 7; i++) {
            Card card = deck.remove(0); // 从牌堆中移除一张牌
            player.addCardToHand(card); // 将牌添加到玩家手中
        }
    }

    private String getColor(Integer colorIndex) {
        return switch (colorIndex) {
            case 0 -> "RED";
            case 1 -> "BLUE";
            case 2 -> "GREEN";
            case 3 -> "YELLOW";
            default -> "BLACK";
        };
    }

    public long getUUID() {
        return UUID;
    }

    // 内部Player类表示玩家
    private class Player {
        private int id;
        private List<Card> hand;

        public Player(int id) {
            this.id = id;
            this.hand = new ArrayList<>();
        }

        public void addCardToHand(Card card) {
            hand.add(card);
        }

        public int getId() {
            return id;
        }

        public List<Card> getHand() {
            return hand;
        }
    }
}
