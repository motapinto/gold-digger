package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.Constants;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.g12.golddigger.view.views.LanternaPaused.*;
import static org.mockito.Mockito.*;

public class LanternaPausedTest {
    private TextGraphics graphics;
    private Model model;
    private LanternaPaused paused;

    @BeforeEach
    public void setUp() {
        graphics = mock(TextGraphics.class);
        Screen screen = mock(TerminalScreen.class);
        when(screen.newTextGraphics()).thenReturn(graphics);

        paused = new LanternaPaused(screen, mock(LanternaGame.class));
        model = mock(Model.class);

        List<String> options = new ArrayList<>();
        options.add("Restart");
        options.add("Undo last move");
        options.add("Quit to menu");

        Menu<String> optionalMenu = mock(Menu.class);
        when(optionalMenu.getOptions()).thenReturn(options);
        when(optionalMenu.getSelected()).thenReturn(0);
        when(model.getOptionalMenu()).thenReturn(optionalMenu);
    }

    @Test
    public void drawTest() {
        paused.draw(model);
        String maxLength = Collections.max(items, Comparator.comparing(String::length));

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString(pausedBackGround));
        verify(graphics, times(3)).setForegroundColor(TextColor.Factory.fromString(pausedForeground));

        verify(graphics, atLeastOnce()).putString((Constants.GAME_WIDTH - items.get(0).length()) / 2, Constants.GAME_HEIGHT/3 - 1, items.get(0), SGR.BOLD);
        for(int i = 1; i < items.size(); i++)
            verify(graphics, atLeast(1)).putString((Constants.GAME_WIDTH - maxLength.length()) / 2, Constants.GAME_HEIGHT/3 + 1 + i, items.get(i));
    }
}
