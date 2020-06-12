package com.g12.golddigger.view;

import com.g12.golddigger.view.views.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class LanternaFactoryTest {
    private LanternaFactory factory;
    private TerminalScreen screen;

    @BeforeEach
    public void setUp() throws IOException {
        screen = mock(TerminalScreen.class);
        factory = new LanternaFactory(screen);
    }

    @Test
    public void drawMenuTest() {
        assertTrue(factory.drawMenu() instanceof LanternaMenu);
    }

    @Test
    public void drawGameTest() {
        assertTrue(factory.drawGame() instanceof LanternaGame);
    }

    @Test
    public void drawGameWonTest() {
        assertTrue(factory.drawGameWon() instanceof LanternaGameWon);
    }

    @Test
    public void drawGameOverTest() {
        assertTrue(factory.drawGameOver() instanceof LanternaGameOver);
    }

    @Test
    public void drawSelectorTest() {
        assertTrue(factory.drawSelector() instanceof LanternaSelector);
    }

    @Test
    public void drawPausedTest() {
        assertTrue(factory.drawPaused() instanceof LanternaPaused);
    }

    @Test
    public void drawAllCompletedTest() {
        assertTrue(factory.drawAllCompleted() instanceof LanternaAllCompleted);
    }
}
