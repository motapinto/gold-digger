package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.Constants;
import com.g12.golddigger.view.LanternaGUI;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import static com.googlecode.lanterna.Symbols.TRIANGLE_RIGHT_POINTING_BLACK;

public class LanternaMenu extends LanternaGUI {
    public static final String menuBackGround = "#21455D";
    public static final String menuForeground = "#5DD67E";
    public static final String otherForeground = "#FFFFFF";
    public static final String selectedBackGround = "#1C8EC0";
    public static final String selectedForeground = "#E2DF1A";


    public LanternaMenu(Screen screen) {
        super(screen);
    }

    @Override
    public void draw(Model model) {
        TextGraphics graphics = this.screen.newTextGraphics();

        graphics.setBackgroundColor(TextColor.Factory.fromString(menuBackGround));
        graphics.fillRectangle(new TerminalPosition(0, 0), graphics.getSize(), ' ');

        graphics.setForegroundColor(TextColor.Factory.fromString(menuForeground));
        graphics.putString((Constants.GAME_WIDTH - Constants.GAME.length()) / 2, 1, Constants.GAME, SGR.BOLD);

        for(int i = 0; i < model.getMainMenu().getOptions().size(); i++) {
            if (model.getMainMenu().getSelected() == i) {
                graphics.setBackgroundColor(TextColor.Factory.fromString(selectedBackGround));
                graphics.setForegroundColor(TextColor.Factory.fromString(selectedForeground));
            } else {
                graphics.setBackgroundColor(TextColor.Factory.fromString(menuBackGround));
                graphics.setForegroundColor(TextColor.Factory.fromString(otherForeground));
            }

            String text = model.getMainMenu().getOptions().get(i).toString() + " " + TRIANGLE_RIGHT_POINTING_BLACK;
            TerminalPosition position = new TerminalPosition(0, i + Constants.GAME_HEIGHT/2);
            graphics.fillRectangle(position,  new TerminalSize(graphics.getSize().getColumns(), 1), ' ');
            graphics.putString((Constants.GAME_WIDTH - text.length()) / 2,  i + Constants.GAME_HEIGHT/2,text);
        }
    }
}
