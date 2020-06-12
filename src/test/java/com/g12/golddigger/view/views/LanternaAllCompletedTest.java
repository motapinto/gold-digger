package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.Constants;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class LanternaAllCompletedTest {
    private TextGraphics graphics;
    private Model model;
    private LanternaAllCompleted allCompleted;

    @BeforeEach
    public void setUp() {
        graphics = mock(TextGraphics.class);
        Screen screen = mock(TerminalScreen.class);
        when(screen.newTextGraphics()).thenReturn(graphics);

        allCompleted = new LanternaAllCompleted(screen);
        model = mock(Model.class);
        when(model.getLevel()).thenReturn(mock(Level.class));
        when(model.getLevel().getScore()).thenReturn(20);
        when(model.getLevel().getTime()).thenReturn((long) 20);
    }

    @Test
    public void drawTest() {
        allCompleted.draw(model);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString(LanternaAllCompleted.allCompletedBackGround));
        verify(graphics).setForegroundColor(TextColor.Factory.fromString(LanternaAllCompleted.allCompletedForeground));

        verify(graphics, atLeastOnce()).putString((Constants.GAME_WIDTH - LanternaAllCompleted.items.get(0).length()) / 2, Constants.GAME_HEIGHT/3 - 1, LanternaAllCompleted.items.get(0), SGR.BLINK);
        for(int i = 1; i < LanternaAllCompleted.items.size(); i++)
            verify(graphics).putString((Constants.GAME_WIDTH - LanternaAllCompleted.items.get(i).length()) / 2, Constants.GAME_HEIGHT/3 + 1 + i, LanternaAllCompleted.items.get(i));

    }

}
