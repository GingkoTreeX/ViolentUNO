package com.unknow.client;

import com.unknow.client.screen.StartScreen;
import com.unknow.entity.Player;
import de.gurkenlabs.litiengine.Game;

public class StartClient {
    public static void runStart(){
        Player player = new Player(0,"DEV",100);
        // 初始化引擎
        Game.init();

        // 配置窗口参数
        Game.window().setTitle("UNO");

        Game.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Game.screens().add(new StartScreen(player));


    }
}
