package com.unknow.client.screen;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.unknow.client.ClientInfo;
import com.unknow.entity.ImageResources;
import com.unknow.entity.Player;
import com.unknow.server.UnoGame;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;

public class StartScreen extends Screen {
    private final BufferedImage background;
    private CustomButton startButton;
    private CustomButton exitButton;
    private Player player;

    public StartScreen(Player player) {
        super("START_SCREEN");
        Image image = ImageResources.getImage("images/Uno/uno_cards.png");
        this.background = convertToBufferedImage(image);
        this.player = player;
    }

    @Override
    public void initializeComponents() {
        super.initializeComponents();

        // 按钮尺寸和位置
        int buttonWidth = 200;
        int buttonHeight = 50;
        double centerX = Game.window().getResolution().getWidth() / 2.0 - buttonWidth / 2.0;
        double centerY = Game.window().getResolution().getHeight() * 0.6;

        // 开始游戏按钮
        this.startButton = new CustomButton(centerX, centerY, buttonWidth, buttonHeight, "开始游戏");
        this.startButton.onClicked(e -> {
            Game.screens().display("GAME_SCREEN"); // 切换到游戏主界面
            joinGame(); // 调用加入游戏逻辑
        });

        // 退出游戏按钮
        this.exitButton = new CustomButton(centerX, centerY + 70, buttonWidth, buttonHeight, "退出游戏");
        this.exitButton.onClicked(e -> System.exit(0));

        this.getComponents().add(this.startButton);
        this.getComponents().add(this.exitButton);
    }

    @Override
    public void render(Graphics2D g) {
        // 优先绘制背景（最底层）
        if (this.background != null) {
            // 使用高质量缩放
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(this.background,
                    0, 0,
                    Game.window().getWidth(),
                    Game.window().getHeight(),
                    0, 0,
                    background.getWidth(),
                    background.getHeight(),
                    null);
        }

        // 调用父类渲染（中间层：按钮、控件等）
        super.render(g);

        // 绘制标题（最顶层）
        g.setFont(Resources.fonts().get("fonts/uno.ttf", 72f));

        // 添加文字阴影效果
        g.setColor(new Color(0, 0, 0, 150));
        String title = "UNO CARD GAME";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (Game.window().getWidth() - titleWidth)/2 + 3, 153); // 阴影偏移

        // 绘制主文字
        g.setColor(new Color(255, 215, 0));
        g.drawString(title, (Game.window().getWidth() - titleWidth)/2, 150);

        // 添加抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    // 加入游戏逻辑
    private void joinGame() {
        //这里模拟
        Game.screens().add(new GameScreen(new ClientInfo(player.getId(),player.getName())));
        // TODO:添加网络连接逻辑
    }

    // 自定义按钮样式
    private static class CustomButton extends GuiComponent {
        public CustomButton(double x, double y, double width, double height, String text) {
            super(x, y, width, height);
            this.setText(text);
            this.setFont(Resources.fonts().get("fonts/uno.ttf", 24f));
            this.setTextShadowColor(Color.YELLOW);
        }
    }
    public static BufferedImage convertToBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // 创建新的BufferedImage
        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        // 绘制图像到BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }

}
