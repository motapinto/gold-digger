package com.g12.golddigger.view;

import com.g12.golddigger.view.views.LanternaGame;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LanternaGUITest {
    private List<KeyStroke> keyStrokes = new ArrayList<>();
    private GUI gui;
    private Screen screen;

    @BeforeEach
    void setUpInputTest() {
        screen = mock(TerminalScreen.class);
        gui = new LanternaGame(screen);

        KeyStroke keyStroke1 = mock(KeyStroke.class);
        when(keyStroke1.getKeyType()).thenReturn(KeyType.EOF);

        KeyStroke keyStroke2 = mock(KeyStroke.class);
        when(keyStroke2.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke2.getCharacter()).thenReturn('q');

        KeyStroke keyStroke3 = mock(KeyStroke.class);
        when(keyStroke3.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke3.getCharacter()).thenReturn('r');

        KeyStroke keyStroke4 = mock(KeyStroke.class);
        when(keyStroke4.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke4.getCharacter()).thenReturn('p');

        KeyStroke keyStroke5 = mock(KeyStroke.class);
        when(keyStroke5.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke5.getCharacter()).thenReturn('u');

        KeyStroke keyStroke6 = mock(KeyStroke.class);
        when(keyStroke6.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke6.getCharacter()).thenReturn('a');
        KeyStroke keyStroke7 = mock(KeyStroke.class);
        when(keyStroke7.getKeyType()).thenReturn(KeyType.ArrowLeft);

        KeyStroke keyStroke8 = mock(KeyStroke.class);
        when(keyStroke8.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke8.getCharacter()).thenReturn('w');
        KeyStroke keyStroke9 = mock(KeyStroke.class);
        when(keyStroke9.getKeyType()).thenReturn(KeyType.ArrowUp);

        KeyStroke keyStroke10 = mock(KeyStroke.class);
        when(keyStroke10.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke10.getCharacter()).thenReturn('d');
        KeyStroke keyStroke11 = mock(KeyStroke.class);
        when(keyStroke11.getKeyType()).thenReturn(KeyType.ArrowRight);

        KeyStroke keyStroke12= mock(KeyStroke.class);
        when(keyStroke12.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke12.getCharacter()).thenReturn('s');
        KeyStroke keyStroke13 = mock(KeyStroke.class);
        when(keyStroke13.getKeyType()).thenReturn(KeyType.ArrowDown);

        keyStrokes.add(keyStroke1);
        keyStrokes.add(keyStroke2);
        keyStrokes.add(keyStroke3);
        keyStrokes.add(keyStroke4);
        keyStrokes.add(keyStroke5);
        keyStrokes.add(keyStroke6);
        keyStrokes.add(keyStroke7);
        keyStrokes.add(keyStroke8);
        keyStrokes.add(keyStroke9);
        keyStrokes.add(keyStroke10);
        keyStrokes.add(keyStroke11);
        keyStrokes.add(keyStroke12);
        keyStrokes.add(keyStroke13);
    }

    @Test
    public void LanternaTestEOF() throws IOException {
        when(screen.pollInput()).thenReturn(keyStrokes.get(0));
        assertEquals(InputKey.EOF, gui.getInput());
    }

    @Test
    public void LanternaTestQuit() throws IOException {
        when(screen.pollInput()).thenReturn(keyStrokes.get(1));
        assertEquals(InputKey.Quit, gui.getInput());
    }

    @Test
    public void LanternaTestRestart() throws IOException {
        when(screen.pollInput()).thenReturn(keyStrokes.get(2));
        assertEquals(InputKey.Restart, gui.getInput());
    }

    @Test
    public void LanternaTestPause() throws IOException {
        when(screen.pollInput()).thenReturn(keyStrokes.get(3));
        assertEquals(InputKey.Pause, gui.getInput());
    }

    @Test
    public void LanternaTestUndo() throws IOException {
        when(screen.pollInput()).thenReturn(keyStrokes.get(4));
        assertEquals(InputKey.Undo, gui.getInput());
    }

    @Test
    public void LanternaTestLeft() throws IOException {
        when(screen.pollInput()).thenReturn(keyStrokes.get(5));
        assertEquals(InputKey.Left, gui.getInput());
        when(screen.pollInput()).thenReturn(keyStrokes.get(6));
        assertEquals(InputKey.Left, gui.getInput());
    }

    @Test
    public void LanternaTestUp() throws IOException {
        when(screen.pollInput()).thenReturn(keyStrokes.get(7));
        assertEquals(InputKey.Up, gui.getInput());
        when(screen.pollInput()).thenReturn(keyStrokes.get(8));
        assertEquals(InputKey.Up, gui.getInput());
    }

    @Test
    public void LanternaTestRight() throws IOException {
        when(screen.pollInput()).thenReturn(keyStrokes.get(9));
        assertEquals(InputKey.Right, gui.getInput());
        when(screen.pollInput()).thenReturn(keyStrokes.get(10));
        assertEquals(InputKey.Right, gui.getInput());
    }

    @Test
    public void LanternaTestDown() throws IOException {
        when(screen.pollInput()).thenReturn(keyStrokes.get(11));
        assertEquals(InputKey.Down, gui.getInput());
        when(screen.pollInput()).thenReturn(keyStrokes.get(12));
        assertEquals(InputKey.Down, gui.getInput());
    }

    @Test
    public void refreshTest() {
        assertThrows(NullPointerException.class, () -> {
            doThrow(NullPointerException.class).when(screen).refresh();
            gui.refresh();
        });
    }

    @Test
    public void closeTest() {
        assertThrows(NullPointerException.class, () -> {
            doThrow(NullPointerException.class).when(screen).close();
            gui.close();
        });
    }
}
