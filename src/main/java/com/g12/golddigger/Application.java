package com.g12.golddigger;

import com.g12.golddigger.controller.Controller;
import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.MainMenu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.utils.ModelBuilder;
import com.g12.golddigger.view.Constants;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.LanternaFactory;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Application {
    private final static long REFRESH_RATE = 100;
    private final static long UPDATE_RATE = 200;

    public static void main(String[] args) {
        String levelsFolder = "levels";

        Terminal terminal = null;
        Screen screen = null;
        try {
            Font font = new Font("Courier New", Font.PLAIN, 40);
            SwingTerminalFontConfiguration cfg = SwingTerminalFontConfiguration.newInstance(font);
            terminal = new DefaultTerminalFactory(System.out, System.in, StandardCharsets.UTF_8).setTerminalEmulatorFontConfiguration(cfg).setInitialTerminalSize(new TerminalSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT + Constants.INFOBAR_HEIGHT)).createTerminal();
            screen = new TerminalScreen(terminal);

            IOManager manager = new IOManager("src/main/java/com/g12/golddigger/history/history.properties");
            ModelBuilder builder = new ModelBuilder(manager);

            Model model = builder.build(levelsFolder);
            GUIFactory guiFactory = new LanternaFactory(screen);
            LevelReader reader = new LevelReader(levelsFolder);
            State startingState = new MainMenu(model, guiFactory, reader, manager);
            Controller controller = new Controller(model, startingState);

            long curTime, lastRefresh = System.currentTimeMillis();
            long lastUpdate = lastRefresh;

            while(true) {
                controller.run();

                curTime = System.currentTimeMillis();
                if(curTime - lastUpdate >= UPDATE_RATE){
                    lastUpdate = curTime;
                    controller.update();
                }

                curTime = System.currentTimeMillis();
                if(curTime - lastRefresh >= REFRESH_RATE){
                    controller.draw();
                    lastRefresh = curTime;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }


        try {
            if (screen != null) {
                screen.close();
            }
            if (terminal != null) {
                terminal.close();
            }
        } catch (IOException ignored) { }
    }
}