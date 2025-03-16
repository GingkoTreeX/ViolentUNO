package com.unknow.client.screen;

import com.unknow.client.ClientInfo;
import com.unknow.entity.Card;
import com.unknow.entity.data.CardData;
import com.unknow.server.UnoGame;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Screen {
    private Image background;
    private List<Image> playerCards;
    private int selectedCardIndex = -1;

    public GameScreen(ClientInfo unoGame) {
        super("GAME_SCREEN");
        this.background = Resources.images().get("images/Uno/game_background.jpg");
        this.playerCards = new ArrayList<>();

        List<Card> playerHands = unoGame.getHand();
        CardData cardData = new CardData(0, 1 + "_" + "blue" + ".png", 1, "BLUE");
        Card card1 = new Card(cardData, 0, 0, 80, 120);
        playerCards.add(card1.getImage());
        // 初始化玩家手牌
        for (Card card : playerHands) {
            this.playerCards.add(card.getImage());
        }

    }

    @Override
    public void render(Graphics2D g) {
        // 绘制背景
        ImageRenderer.render(g, this.background, 0, 0);

        // 绘制玩家手牌
        int cardX = 100;
        for (int i = 0; i < this.playerCards.size(); i++) {
            Image card = this.playerCards.get(i);
            if (i == this.selectedCardIndex) {
                // 高亮选中的牌
                g.setColor(Color.YELLOW);
                g.fillRect(cardX - 5, 500, card.getWidth(null) + 10, card.getHeight(null) + 10);
            }
            ImageRenderer.render(g, card, cardX, 500);
            cardX += 150;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 点击手牌
        int cardX = 100;
        for (int i = 0; i < this.playerCards.size(); i++) {
            if (e.getX() >= cardX && e.getX() <= cardX + this.playerCards.get(i).getWidth(null)
                    && e.getY() >= 500 && e.getY() <= 500 + this.playerCards.get(i).getHeight(null)) {
                if (this.selectedCardIndex == i) {
                    // 再次点击出牌
                    playCard(i);
                    this.selectedCardIndex = -1;
                } else {
                    this.selectedCardIndex = i;
                }
                break;
            }
            cardX += 150;
        }
    }

    private void playCard(int index) {
        Image card = this.playerCards.remove(index);
        // 计算移动到屏幕中间的位置
        int centerX = Game.window().getWidth() / 2 - card.getWidth(null) / 2;
        int centerY = Game.window().getHeight() / 2 - card.getHeight(null) / 2;
        //发送数据包
        sendDataPacket(card);
        // 处理出牌后的逻辑
        moveCardToCenter(card, centerX, centerY);
        // 玩家手牌位置补齐
        rearrangePlayerCards();
    }

    private void moveCardToCenter(Image card, int x, int y) {
        //玩家出的牌移动到屏幕中间
    }

    private void rearrangePlayerCards() {
        // 玩家手牌位置补齐的逻辑
        int cardX = 100;
        for (Image card : this.playerCards) {
            cardX += 150;
        }
    }

    private void sendDataPacket(Image card) {
        //TODO:发送数据包
    }
}
