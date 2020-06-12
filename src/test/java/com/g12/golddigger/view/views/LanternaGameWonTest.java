package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Level;
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

import static com.g12.golddigger.view.views.LanternaGameWon.gameWonBackGround;
import static com.g12.golddigger.view.views.LanternaGameWon.gameWonForeground;
import static org.mockito.Mockito.*;

public class LanternaGameWonTest {
    private TextGraphics graphics;
    private Model model;
    private LanternaGameWon gameWon;

    @BeforeEach
    public void setUp() {
        graphics = mock(TextGraphics.class);
        Screen screen = mock(TerminalScreen.class);
        when(screen.newTextGraphics()).thenReturn(graphics);

        gameWon = new LanternaGameWon(screen, mock(LanternaGame.class));
        model = mock(Model.class);
        when(model.getLevel()).thenReturn(mock(Level.class));
        when(model.getLevel().getScore()).thenReturn(20);
        when(model.getLevel().getTime()).thenReturn((long) 20);

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
        gameWon.draw(model);

        String maxLength = Collections.max(gameWon.getItems(), Comparator.comparing(String::length));

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString(gameWonBackGround));
        verify(graphics, times(4)).setForegroundColor(TextColor.Factory.fromString(gameWonForeground));

        verify(graphics).putString((Constants.GAME_WIDTH - gameWon.getItems().get(0).length()) / 2, Constants.GAME_HEIGHT/3 - 1, gameWon.getItems().get(0), SGR.BOLD);
        for(int i = 1; i < gameWon.getItems().size(); i++)
            verify(graphics).putString((Constants.GAME_WIDTH - maxLength.length()) / 2, Constants.GAME_HEIGHT/3 + 1 + i, gameWon.getItems().get(i));

    }
}
