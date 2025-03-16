package com.unknow.client.screen;

import com.unknow.client.ClientInfo;
import com.unknow.client.network.NetworkService;
import com.unknow.entity.Card;
import com.unknow.entity.data.CardData;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Collections;
import java.util.logging.Level;

public class GameTableScreen extends Screen {
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;
    private static final int MIN_SPACING = 30;
    private static final int MAX_SPACING = 100;

    private final ClientInfo info;
    private Rectangle deckArea;
    private Rectangle discardArea;

    public GameTableScreen(ClientInfo info) {
        super("GAME_SCREEN");
        this.info = info;
    }

    @Override
    public void prepare() {
        super.prepare();
        try {
            initGameElements();
            setupInputListeners();
        } catch (Exception e) {
            Game.log().log(Level.SEVERE, "屏幕初始化失败", e);
        }
    }

    private void initGameElements() {
        int screenWidth = Game.window().getWidth();
        int screenHeight = Game.window().getHeight();

        // 牌堆区域（右侧10%边距）
        deckArea = new Rectangle(
                screenWidth - (int)(screenWidth * 0.1) - CARD_WIDTH,
                screenHeight / 2 - CARD_HEIGHT/2,
                CARD_WIDTH,
                CARD_HEIGHT
        );

        // 弃牌堆区域（左侧10%边距）
        discardArea = new Rectangle(
                (int)(screenWidth * 0.1),
                screenHeight/2 - CARD_HEIGHT/2,
                CARD_WIDTH,
                CARD_HEIGHT
        );
    }

    private void setupInputListeners() {
        Input.mouse().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (deckArea.contains(e.getPoint())) {
                    Game.log().info("尝试从牌堆抽卡");
                    // 实际应调用网络服务
                    NetworkService.drawCardRequest();
                }
            }
        });
    }

    private void arrangeHandCards() {
        List<Card> hand = getSafeHandList();
        int totalCards = hand.size();

        // 空手牌保护
        if (totalCards == 0) {
            return;
        }

        int screenWidth = Game.window().getWidth();
        int baseY = Game.window().getHeight() - 150; // 底部安全距离

        // 安全间距计算
        int spacingDivisor = Math.max(totalCards, 1);
        int dynamicWidth = screenWidth - 200;
        int spacing = safeDivide(dynamicWidth, spacingDivisor);
        spacing = Math.min(MAX_SPACING, Math.max(MIN_SPACING, spacing));

        int totalWidth = totalCards * spacing;
        int startX = Math.max(0, (screenWidth - totalWidth) / 2);

        for (int i = 0; i < totalCards; i++) {
            Card card = hand.get(i);
            int targetX = startX + i * spacing;
            int targetY = baseY - (card.isSelected() ? 40 : 0);
            card.setOriginalPosition(new Point(targetX, targetY));
        }
    }

    @Override
    public void render(Graphics2D g) {
        try {
            renderBackground(g);
            renderDeck(g);
            renderPlayerInfo(g);
            renderHandCards(g);
        } catch (Exception e) {
            Game.log().log(Level.SEVERE, "渲染过程异常", e);
        }
    }

    private void renderBackground(Graphics2D g) {
        g.setColor(new Color(0, 100, 0));
        g.fillRect(0, 0, Game.window().getWidth(), Game.window().getHeight());
    }

    private void renderDeck(Graphics2D g) {
        g.setFont(Resources.fonts().get("fonts/uno.ttf", 21f));
        g.setColor(new Color(0, 0, 0, 150));
        String text = "摸一张↓";
        g.drawString(text,deckArea.x, deckArea.y-50);
        g.drawImage(new Card(new CardData(-1,"card_back.png",-1,"CARDBACK"),deckArea.x, deckArea.y,deckArea.width,deckArea.height ).getImage(),deckArea.x, deckArea.y,null);
    }

    private void drawCardArea(Graphics2D g, Rectangle area, String text, Color color) {
        g.setColor(color);
        g.fillRoundRect(area.x, area.y, area.width, area.height, 10, 10);

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = area.x + (area.width - textWidth) / 2;
        int textY = area.y + area.height/2 + fm.getAscent()/2;

        g.setColor(Color.WHITE);
        g.drawString(text, textX, textY);
    }

    private void renderPlayerInfo(Graphics2D g) {
        String infoText = String.format("%s | 手牌: %d ",
                info.getName(),
                info.getHand().size()
        );

        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g.drawString(infoText, 20, 30);
    }

    private void renderHandCards(Graphics2D g) {
        List<Card> hand = getSafeHandList();
        if (hand.isEmpty()) {
            drawEmptyHandPrompt(g);
            return;
        }

        try {
            arrangeHandCards();
            hand.forEach(card -> card.render(g));
        } catch (Exception e) {
            Game.log().log(Level.WARNING, "手牌渲染异常", e);
        }
    }

    private void drawEmptyHandPrompt(Graphics2D g) {
        String text = "当前没有手牌";
        Font font = new Font("微软雅黑", Font.ITALIC, 24);
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        int x = (Game.window().getWidth() - fm.stringWidth(text)) / 2;
        int y = Game.window().getHeight() - 100;

        // 半透明背景
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(x - 10, y - fm.getHeight(),
                fm.stringWidth(text) + 20, fm.getHeight() + 10, 15, 15);

        // 文字
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }

    // 防御性编程工具方法
    private List<Card> getSafeHandList() {
        return info.getHand() != null ? info.getHand() : Collections.emptyList();
    }

    private int safeDivide(int dividend, int divisor) {
        return divisor == 0 ? 0 : dividend / divisor;
    }
}
