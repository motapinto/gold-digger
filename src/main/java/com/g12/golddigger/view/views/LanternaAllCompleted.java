package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.Constants;
import com.g12.golddigger.view.LanternaGUI;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class LanternaAllCompleted extends LanternaGUI {
    public static final String allCompletedBackGround = "#21455D";
    public static final String allCompletedForeground = "#5DD67E";
    public final static List<String> items = new ArrayList<>() {
        {
            add("All levels completed!!!");
            add("To replay any level go to the Selector");
            add("Press any key to continue");
        }
    };

    public LanternaAllCompleted(Screen screen) {
        super(screen);
    }

    @Override
    public void draw(Model model) {
        TextGraphics graphics = this.screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(allCompletedBackGround));
        graphics.setForegroundColor(TextColor.Factory.fromString(allCompletedForeground));
        graphics.fillRectangle(new TerminalPosition(0,0), graphics.getSize(), ' ');

        graphics.putString((Constants.GAME_WIDTH - items.get(0).length()) / 2, Constants.GAME_HEIGHT/3 - 1, items.get(0), SGR.BLINK);
        for(int i = 1; i < items.size(); i++)
            graphics.putString((Constants.GAME_WIDTH - items.get(i).length()) / 2, Constants.GAME_HEIGHT/3 + 1 + i, items.get(i));
    }
}
