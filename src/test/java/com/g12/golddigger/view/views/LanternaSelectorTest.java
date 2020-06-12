package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.Constants;
import com.googlecode.lanterna.SGR;
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
import static com.googlecode.lanterna.Symbols.TRIANGLE_RIGHT_POINTING_BLACK;
import static org.mockito.Mockito.*;

public class LanternaSelectorTest {
    private TextGraphics graphics;
    private Model model;
    private LanternaSelector selector;
    private final Menu<String> levelsOptions = mock(Menu.class);

    @BeforeEach
    public void setUp() {
        graphics = mock(TextGraphics.class);
        Screen screen = mock(TerminalScreen.class);
        when(screen.newTextGraphics()).thenReturn(graphics);
        when(graphics.getSize()).thenReturn(mock(TerminalSize.class));

        selector = new LanternaSelector(screen);
        model = mock(Model.class);

        List<String> levels = new ArrayList<>() {
            {
                add("LVL0");
                add("LVL1");
                add("LVL2");
            }
        };
        when(model.getSelectorMenu()).thenReturn(levelsOptions);
        when(model.getSelectorMenu().getOptions()).thenReturn(levels);
        when(model.getSelectorMenu().getSelected()).thenReturn(0);
        when(model.getAchieved()).thenReturn(2);
        when(model.getSelectorMenu().getMax()).thenReturn(3);
    }

    @Test
    public void drawTest() {
        selector.draw(model);

        verify(graphics, atLeastOnce()).setBackgroundColor(TextColor.Factory.fromString(LanternaSelector.selectorBackGround));
        verify(graphics, atLeastOnce()).setForegroundColor(TextColor.Factory.fromString(LanternaSelector.selectorForeground));
        verify(graphics).putString((Constants.GAME_WIDTH - Constants.GAME.length()) / 2, 1, Constants.GAME, SGR.BOLD);

        int achieved = model.getSelectorMenu().getMax();
        for(int i = 0; i < model.getSelectorMenu().getOptions().size() && i<=achieved; i++) {
            String text = model.getSelectorMenu().getOptions().get(i) + " " + TRIANGLE_RIGHT_POINTING_BLACK;
            verify(graphics).putString((Constants.GAME_WIDTH - text.length()) / 2,  i + Constants.GAME_HEIGHT/2, text);
        }

        verify(graphics, atLeast(2)).setBackgroundColor(TextColor.Factory.fromString(LanternaSelector.selectorBackGround));
        verify(graphics, atLeast(2)).setForegroundColor(TextColor.Factory.fromString(otherForeground));
        verify(graphics, atMost(1)).setBackgroundColor(TextColor.Factory.fromString(selectedBackGround));
        verify(graphics, atMost(1)).setForegroundColor(TextColor.Factory.fromString(selectedForeground));
    }
}
