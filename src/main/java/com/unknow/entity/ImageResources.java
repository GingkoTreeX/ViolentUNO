package com.unknow.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageResources {
    private static final ImageContainer images = new ImageContainer();

    public static Image getImage(String path) {
        return images.get(path);
    }

    public static class ImageContainer {
        private final Map<String, Image> cache = new HashMap<>();

        public Image get(String path) {
            if (!cache.containsKey(path)) {
                try {
                    BufferedImage img = ImageIO.read(getClass().getResource("/" + path));
                    cache.put(path, img);
                    System.out.println("图片加载成功: " + path);
                } catch (Exception e) {
                    System.err.println("无法加载图片: " + path);
                }
            }
            return cache.get(path);
        }
    }
}