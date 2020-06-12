package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.view.Constants;
import com.g12.golddigger.view.LanternaGUI;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LanternaGameOver extends LanternaGUI {
    private final LanternaGame levelDrawer;
    public static final String gameOverBackGround = "#991A2A";
    public static final String gameOverForeground = "#FFFFFF";
    public static final String selectedForeground = "#E5E81D";
    public List<String> items = new ArrayList<>();


    public LanternaGameOver(Screen screen, LanternaGame levelDrawer) {
        super(screen);
        this.levelDrawer = levelDrawer;
    }

    @Override
    public void draw(Model model) {
        this.levelDrawer.draw(model);

        TextGraphics graphics = this.screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(gameOverBackGround));
        graphics.setForegroundColor(TextColor.Factory.fromString(gameOverForeground));

        items.clear();
        items.add("Game Over");

        Menu<String> options = model.getOptionalMenu();
        items.addAll(options.getOptions());

        String maxLength = Collections.max(items, Comparator.comparing(String::length));
        Position upperLeft = new Position((Constants.GAME_WIDTH - maxLength.length()) / 2 - 1,  Constants.GAME_HEIGHT/3 - 1);
        Position lowerRight = new Position(maxLength.length() + 2,  items.size() + 3);;
        graphics.fillRectangle(new TerminalPosition(upperLeft.getX(), upperLeft.getY()), new TerminalSize(lowerRight.getX(), lowerRight.getY()), ' ');

        graphics.putString((Constants.GAME_WIDTH - items.get(0).length()) / 2, Constants.GAME_HEIGHT/3 - 1, items.get(0), SGR.BOLD);
        for(int i = 1; i < items.size(); i++) {
            if((i - 1) == options.getSelected()) {
                graphics.setForegroundColor(TextColor.Factory.fromString(selectedForeground));
            } else {
                graphics.setForegroundColor(TextColor.Factory.fromString(gameOverForeground));
            }
            graphics.putString((Constants.GAME_WIDTH - maxLength.length()) / 2, Constants.GAME_HEIGHT/3 + 1 + i, items.get(i));
        }
    }

    public List<String> getItems() {
        return items;
    }
}
