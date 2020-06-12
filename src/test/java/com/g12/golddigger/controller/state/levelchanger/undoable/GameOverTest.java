package com.g12.golddigger.controller.state.levelchanger.undoable;

import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.MainMenu;
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
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class GameOverTest {
    private State state;
    private Model model;
    private LevelReader levelReader;
    private Level level;
    private Menu<String> optional = mock(Menu.class);

    @BeforeEach
    public void setUp() throws LevelReadException {
        // Level
        level = mock(Level.class);

        // Model
        model = Mockito.mock(Model.class);
        when(model.getLevel()).thenReturn(level);
        when(model.getOptionalMenu()).thenReturn(optional);

        // LevelReader
        levelReader = Mockito.mock(LevelReader.class);
        when(levelReader.readLevel(0)).thenReturn(level);

        // GameOver
        state =  new GameOver(model, mock(GUIFactory.class), levelReader, mock(IOManager.class));
    }

    @Test
    public void handleUpTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Up) instanceof GameOver;
            verify(optional).previousOption();
        });
    }

    @Test
    public void handleDownTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Down) instanceof GameOver;
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
    public void handleRestartTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Restart) instanceof Playing;
            verify(level).getFileIndex();
            verify(model).setLevel(level);
            verify(levelReader).readLevel(0);
        });
    }

    @Test
    public void handleUndoTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Undo) instanceof Playing;
        });
    }

    @Test
    public void handleQuitTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Quit) instanceof MainMenu;
        });
    }
}