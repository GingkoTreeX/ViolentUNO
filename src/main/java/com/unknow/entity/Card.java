package com.unknow.entity;

import com.unknow.entity.data.CardData;
import de.gurkenlabs.litiengine.entities.Entity;
import de.gurkenlabs.litiengine.entities.EntityRenderListener;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.sound.Sound;

import java.awt.*;
import java.awt.geom.Point2D;

// Card.java - 通用卡牌实体(集成鼠标交互）
public class Card extends Entity {
    private final CardData data;
    private Image image;
    private boolean selected;

    private Point originalPosition;

    public Card(CardData data, int x, int y, int width, int height) {
        super("card");
        originalPosition = new Point(x,y);
        this.data = data;
        this.setSize(width, height);
        this.setLocation(x, y);
        // 加载图片
        this.image = Resources.images().get("images/Uno/individual/"+ data.getColor().toLowerCase() +"/" + data.getImageName());
        if (image != null) {
            this.image = this.image.getScaledInstance(width, height ,Image.SCALE_REPLICATE);
        }

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // 添加悬停状态判断
    public boolean isHovered() {
        Point2D mouseLoc = Input.mouse().getLocation();
        return getBoundingBox().contains(mouseLoc);
    }

    public CardData getData() {
        return data;
    }

    public Image getImage() {
        return image;
    }

    public void render(Graphics2D g) {
        int x = (int) getX();
        int y = (int) getY();

        // 绘制卡牌背景
        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, (int)getWidth(), (int)getHeight(), 15, 15);

        // 绘制图片
        if (image != null) {
            g.drawImage(image, x, y, null);
        }

        // 悬停效果
        if (isHovered()) {
            g.setColor(new Color(255, 255, 0, 100));
            g.fillRoundRect(x, y, (int)getWidth(), (int)getHeight(), 15, 15);
        }

        // 选中效果
        if (selected) {
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke(3));
            g.drawRoundRect(x + 2, y + 2, (int)getWidth() - 4, (int)getHeight() - 4, 12, 12);
        }
    }
    public void reset() {
        selected = false;
        setLocation(originalPosition);
    }

    public void setOriginalPosition(Point originalPosition) {
        this.originalPosition = originalPosition;
    }

    public Point getOriginalPosition() {
        return originalPosition;
    }
}