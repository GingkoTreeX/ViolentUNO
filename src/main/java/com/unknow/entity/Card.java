package com.unknow.entity;

import com.unknow.entity.data.CardData;
import de.gurkenlabs.litiengine.entities.Entity;
import de.gurkenlabs.litiengine.entities.EntityRenderListener;

import com.unknow.entity.ImageResources;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

// Card.java - 通用卡牌实体
public class Card extends Entity implements EntityRenderListener, MouseListener {
    private final CardData data;
    private Image image;
    private boolean selected;

    public Card(CardData data, int x, int y, int width, int height) {
        this.data = data;
        this.selected = false;
        this.setSize(width, height); // 设置实体尺寸
        this.setLocation(x, y); // 设置实体位置

        // 初始化图像
        this.image = ImageResources.getImage("images/Uno/individual/"+ data.getColor().toLowerCase() +"/" + data.getImageName());
        if (image != null) {
            image = image.getScaledInstance(80, 120,Image.SCALE_FAST);
        }

        // 注册渲染监听器
        addEntityRenderListener(this);
        // 注册鼠标监听器
        addMouseListener(this);
    }

    public Image getImage() {
        return image;
    }

    public void render(Graphics2D g) {
        // 绘制卡牌背景
        g.setColor(Color.GRAY);
        g.fillRoundRect(0, 0, 80, 120, 10, 10);

        // 绘制图片
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }

        // 绘制选中状态
        if (selected) {
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke(3));
            g.drawRoundRect(2, 2, 76, 116, 10, 10);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        selected = !selected;
        setY(selected ? getY() - 30 : getY() + 30);
    }

    // 其他必要接口方法
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public CardData getData() {
        return data;
    }
}
