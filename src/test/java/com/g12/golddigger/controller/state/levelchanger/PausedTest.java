package com.g12.golddigger.controller.state.levelchanger;

import com.g12.golddigger.controller.state.levelchanger.undoable.playing.Playing;
import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PausedTest {
    private Paused state;
    private Model model;
    private LevelReader levelReader;
    private Level level;
    private Menu<String> optional = mock(Menu.class);

    @BeforeEach
    public void setUp() throws LevelReadException {
        // Level
        level = mock(Level.class);

        // Model
        model = mock(Model.class);
        when(model.getLevel()).thenReturn(level);
        when(model.getOptionalMenu()).thenReturn(optional);

        // LevelReader
        levelReader = mock(LevelReader.class);
        when(levelReader.readLevel(0)).thenReturn(level);

        // Paused
        state =  new Paused(model, mock(GUIFactory.class), levelReader, mock(IOManager.class));
    }

    @Test
    public void handleUpTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Up ) instanceof Paused;
            verify(optional).previousOption();
        });
    }

    @Test
    public void handleDownTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Down) instanceof Paused;
            verify(optional).nextOption();
        });
    }

    @Test
    public void handleRightCase0Test() {
        when(optional.getSelected()).thenReturn(0);
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Right) instanceof Playing;
        });
    }

    @Test
    public void handleRightCase1Test() {
        when(optional.getSelected()).thenReturn(1);
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Right) instanceof Playing;
            verify(level).getFileIndex();
            verify(model).setLevel(level);
            verify(levelReader).readLevel(0);
        });
    }

    @Test
    public void handleRightCase2Test() {
        when(optional.getSelected()).thenReturn(2);
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Right) instanceof MainMenu;
        });
    }

    @Test
    public void handleRightDefaultTest() {
        when(optional.getSelected()).thenReturn(10);
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Right) instanceof Paused;
        });
    }

    @Test
    public void handlePauseTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Pause) instanceof Playing;
        });
    }

    @Test
    public void handleRestartTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Restart) instanceof Playing;
            verify(level).getFileIndex();
            verify(model).setLevel(level);
            verify(levelReader).readLevel(0);
        });
    }

    @Test
    public void handleQuitTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Quit) instanceof MainMenu;
        });
    }

    @Test
    public void handleDefaultTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Left) instanceof Paused;
        });
    }
}
