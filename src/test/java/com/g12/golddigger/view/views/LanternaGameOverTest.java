package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.view.Constants;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
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

import static com.g12.golddigger.view.views.LanternaGameOver.gameOverBackGround;
import static com.g12.golddigger.view.views.LanternaGameOver.gameOverForeground;
import static org.mockito.Mockito.*;

public class LanternaGameOverTest {
    private TextGraphics graphics;
    private Model model;
    private LanternaGameOver gameOver;

    @BeforeEach
    public void setUp() {
        graphics = mock(TextGraphics.class);
        Screen screen = mock(TerminalScreen.class);
        when(screen.newTextGraphics()).thenReturn(graphics);

        List<String> options = new ArrayList<>();
        options.add("Restart");
        options.add("Undo last move");
        options.add("Quit to menu");

        gameOver = new LanternaGameOver(screen, mock(LanternaGame.class));
        model = mock(Model.class);
        when(model.getLevel()).thenReturn(mock(Level.class));
        Menu<String> optionalMenu = mock(Menu.class);
        when(optionalMenu.getOptions()).thenReturn(options);
        when(optionalMenu.getSelected()).thenReturn(0);
        when(model.getOptionalMenu()).thenReturn(optionalMenu);
    }

    @Test
    public void drawTest() {
        gameOver.draw(model);

        verify(graphics).setBackgroundColor(TextColor.Factory.fromString(gameOverBackGround));
        verify(graphics, times(3)).setForegroundColor(TextColor.Factory.fromString(gameOverForeground));

        String maxLength = Collections.max(gameOver.getItems(), Comparator.comparing(String::length));
        Position upperLeft = new Position((Constants.GAME_WIDTH - maxLength.length()) / 2 - 1,  Constants.GAME_HEIGHT/3 - 1);
        Position lowerRight = new Position(maxLength.length() + 2,  gameOver.getItems().size() + 3);;
        graphics.fillRectangle(new TerminalPosition(upperLeft.getX(), upperLeft.getY()), new TerminalSize(lowerRight.getX(), lowerRight.getY()), ' ');

        verify(graphics).putString((Constants.GAME_WIDTH - gameOver.getItems().get(0).length()) / 2, Constants.GAME_HEIGHT/3 - 1, gameOver.getItems().get(0), SGR.BOLD);
        for(int i = 1; i < gameOver.getItems().size(); i++)
            verify(graphics).putString((Constants.GAME_WIDTH - maxLength.length()) / 2, Constants.GAME_HEIGHT/3 + 1 + i, gameOver.getItems().get(i));
    }
}
