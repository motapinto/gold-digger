package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.Constants;
import com.g12.golddigger.view.LanternaGUI;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.lanterna.SGR.BLINK;
import static com.googlecode.lanterna.Symbols.TRIANGLE_RIGHT_POINTING_BLACK;

public class LanternaSelector extends LanternaGUI {
    public static final String selectorBackGround = "#21455D";
    public static final String selectorForeground = "#5DD67E";
    public static final String unselectedForeground = "#FFFFFF";
    public static final String selectedBackGround = "#1C8EC0";
    public static final String selectedForeground = "#E2DF1A";

    public LanternaSelector(Screen screen) {
        super(screen);
    }

    @Override
    public void draw(Model model) {
        TextGraphics graphics = this.screen.newTextGraphics();

        graphics.setBackgroundColor(TextColor.Factory.fromString(selectorBackGround));
        graphics.fillRectangle(new TerminalPosition(0, 0), graphics.getSize(), ' ');

        graphics.setForegroundColor(TextColor.Factory.fromString(selectorForeground));
        graphics.putString((Constants.GAME_WIDTH - Constants.GAME.length()) / 2, 1, Constants.GAME, SGR.BOLD);

        Menu<String> menu = model.getSelectorMenu();
        List<Integer> available = drawableOptions(menu);
        setBlinking(graphics, available, menu, selectorBackGround, selectorForeground);

        for (int i = 0; i < available.size(); i++) {
            int option = available.get(i);

            if (menu.getSelected() == option) {
                graphics.setBackgroundColor(TextColor.Factory.fromString(selectedBackGround));
                graphics.setForegroundColor(TextColor.Factory.fromString(selectedForeground));
            } else {
                graphics.setBackgroundColor(TextColor.Factory.fromString(selectorBackGround));
                graphics.setForegroundColor(TextColor.Factory.fromString(unselectedForeground));
            }

            String text = menu.getOptions().get(option) + " " + TRIANGLE_RIGHT_POINTING_BLACK;
            TerminalPosition position = new TerminalPosition(0, i + Constants.GAME_HEIGHT/2);
            graphics.fillRectangle(position,  new TerminalSize(graphics.getSize().getColumns(), 1), ' ');
            graphics.putString((Constants.GAME_WIDTH - text.length()) / 2,  i + Constants.GAME_HEIGHT/2, text);
        }
    }

    protected  <T> List<Integer> drawableOptions(Menu<T> menu) {
        List <Integer> options = new ArrayList<>();

        int selected = menu.getSelected();
        int size = menu.getOptions().size();

        int min = Math.max(selected - 2, 0);
        int max = Math.min(selected + (4 - (selected - min)), menu.getMax());

        for (int i = min; i <= max && i < size; i++) {
            options.add(i);
        }

        return options;
    }

    protected void setBlinking(TextGraphics graphics, List<Integer> available, Menu<String> menu, String backGround, String foreground) {
        if(!available.isEmpty()) {
            int min = available.get(0);
            int max = available.get(available.size() - 1);

            if (min != 0) {
                graphics.setCharacter(
                        Constants.GAME_WIDTH / 2,
                        Constants.GAME_HEIGHT/2 - 2,
                        new TextCharacter(
                                Symbols.TRIANGLE_UP_POINTING_BLACK,
                                TextColor.Factory.fromString(foreground),
                                TextColor.Factory.fromString(backGround),
                                BLINK
                        )
                );
            }

            if (max != menu.getMax()) {
                graphics.setCharacter(
                        Constants.GAME_WIDTH / 2,
                        Constants.GAME_HEIGHT/2 + (max - min) + 2,
                        new TextCharacter(
                                Symbols.TRIANGLE_DOWN_POINTING_BLACK,
                                TextColor.Factory.fromString(foreground),
                                TextColor.Factory.fromString(backGround),
                                BLINK
                        )
                );
            }
        }
    }
}
