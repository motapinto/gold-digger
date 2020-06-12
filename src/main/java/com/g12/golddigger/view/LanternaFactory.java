package com.g12.golddigger.view;

import com.g12.golddigger.view.views.*;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class LanternaFactory implements GUIFactory {

    private final Screen screen;

    public LanternaFactory(Screen screen) throws IOException {
        this.screen = screen;
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
    }

    public LanternaGUI drawMenu() {
        return new LanternaMenu(screen);
    }

    public LanternaGUI drawGame() {
        return new LanternaGame(screen);
    }

    public LanternaGUI drawGameWon() {
        LanternaGame levelDrawer = new LanternaGame(screen);
        return new LanternaGameWon(screen, levelDrawer);
    }

    public LanternaGUI drawGameOver() {
        LanternaGame levelDrawer = new LanternaGame(screen);
        return new LanternaGameOver(screen, levelDrawer);
    }

    public LanternaGUI drawSelector() {
        return new LanternaSelector(screen);
    }

    public LanternaGUI drawPaused() {
        LanternaGame levelDrawer = new LanternaGame(screen);
        return new LanternaPaused(screen, levelDrawer);
    }

    public LanternaGUI drawAllCompleted() {
        return new LanternaAllCompleted(screen);
    }
}
