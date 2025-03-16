package com.unknow.client.screen.componet;

import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomButton extends GuiComponent {
    public CustomButton(double x, double y, double width, double height, String text) {
        super(x, y, width, height);
        this.setText(text);
        this.setFont(Resources.fonts().get("fonts/uno.ttf", 24f));
        this.setTextShadowColor(Color.YELLOW);
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
