package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.menu.Option;
import com.g12.golddigger.view.Constants;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.g12.golddigger.view.views.LanternaMenu.*;
import static org.mockito.Mockito.*;

public class LanternaMenuTest {
    private TextGraphics graphics;
    private Model model;
    private LanternaMenu menu;
    private final Menu<Option> mainMenu = mock(Menu.class);

    @BeforeEach
    public void setUp() {
        graphics = mock(TextGraphics.class);
        when(graphics.getSize()).thenReturn(mock(TerminalSize.class));
        Screen screen = mock(TerminalScreen.class);
        when(screen.newTextGraphics()).thenReturn(graphics);

        menu = new LanternaMenu(screen);
        model = mock(Model.class);

        List<Option> options = new ArrayList<>() {
            {
                add(Option.Start);
                add(Option.Scratch);
                add(Option.Selector);
            }
        };

        when(model.getMainMenu()).thenReturn(mainMenu);
        when(model.getMainMenu().getOptions()).thenReturn(options);
    }

    @Test
    public void drawTest() {
        menu.draw(model);

        verify(graphics, atLeastOnce()).setBackgroundColor(TextColor.Factory.fromString(menuBackGround));
        verify(graphics, atLeastOnce()).setForegroundColor(TextColor.Factory.fromString(menuForeground));
        verify(graphics).putString((Constants.GAME_WIDTH - Constants.GAME.length()) / 2, 1, Constants.GAME, SGR.BOLD);

        verify(graphics).putString((Constants.GAME_WIDTH - ("Start " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK).length()) / 2,  Constants.GAME_HEIGHT/2, "Start " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK);
        verify(graphics).putString((Constants.GAME_WIDTH - ("Scratch " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK).length()) / 2,  1 + Constants.GAME_HEIGHT/2, "Scratch " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK);
        verify(graphics).putString((Constants.GAME_WIDTH - ("Selector " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK).length()) / 2,  2 + Constants.GAME_HEIGHT/2, "Selector " + Symbols.TRIANGLE_RIGHT_POINTING_BLACK);

        verify(graphics, atLeast(2)).setBackgroundColor(TextColor.Factory.fromString(menuBackGround));
        verify(graphics, atLeast(2)).setForegroundColor(TextColor.Factory.fromString(otherForeground));
        verify(graphics, atMost(1)).setBackgroundColor(TextColor.Factory.fromString(selectedBackGround));
        verify(graphics, atMost(1)).setForegroundColor(TextColor.Factory.fromString(selectedForeground));
    }
}
