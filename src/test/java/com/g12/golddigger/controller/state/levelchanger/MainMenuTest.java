package com.g12.golddigger.controller.state.levelchanger;

import com.g12.golddigger.controller.state.AllCompleted;
import com.g12.golddigger.controller.state.levelchanger.undoable.playing.Playing;
import com.g12.golddigger.exception.ExitGameException;
import com.g12.golddigger.exception.IndexBoundsException;
import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.menu.Option;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.g12.golddigger.model.menu.Option.Selector;
import static com.g12.golddigger.model.menu.Option.*;
import static org.mockito.Mockito.*;

public class MainMenuTest {
    private MainMenu state;
    private Model model;
    private LevelReader levelReader;
    private Level level;
    private Menu<Option> menu = mock(Menu.class);;
    private Menu<String> selector = mock(Menu.class);;

    @BeforeEach
    public void setUp() throws LevelReadException {
        // Level
        level = mock(Level.class);

        // Model
        model = Mockito.mock(Model.class);
        when(model.getMainMenu()).thenReturn(menu);
        when(model.getSelectorMenu()).thenReturn(selector);

        // LevelReader
        levelReader = mock(LevelReader.class);
        when(levelReader.readLevel(0)).thenReturn(null);

        // Selector
        state =  new MainMenu(model, mock(GUIFactory.class), levelReader, mock(IOManager.class));
    }

    @Test
    public void handleUpTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Up) instanceof MainMenu;
            verify(menu).previousOption();
        });
    }

    @Test
    public void handleUpType2Test() {
        Assertions.assertDoesNotThrow(() -> {
            when(state.handle(InputKey.Up)).thenThrow(IndexBoundsException.class);
            assert state.handle(InputKey.Up) instanceof MainMenu;
            verify(menu).previousOption();
        });
    }

    @Test
    public void handleRightScratchTest() {
        when(menu.getSelectedOption()).thenReturn(Scratch);

        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Right) instanceof Playing;
            verify(model).setAchieved(-1);
            verify(selector).reset();
            verify(model, times(2)).setLevel(null);
        });
    }

    @Test
    public void handleRightStartType1Test() {
        when(menu.getSelectedOption()).thenReturn(Start);

        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Right) instanceof Playing;
        });
    }

    @Test
    public void handleRightStartType2Test() {
        when(menu.getSelectedOption()).thenReturn(Start);

        Assertions.assertDoesNotThrow(() -> {
            when(model.getNextLevel()).thenThrow(IndexBoundsException.class);
            assert state.handle(InputKey.Right) instanceof AllCompleted;
        });
    }

    @Test
    public void handleRightSelectorTest() {
        when(menu.getSelectedOption()).thenReturn(Selector);
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Right) instanceof Selector;
        });
    }

    @Test
    public void handleDownTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Down) instanceof MainMenu;
            verify(menu).nextOption();
        });
    }

    @Test
    public void handlePauseTest() {
        Assertions.assertThrows(ExitGameException.class, () -> state.handle(InputKey.Pause));
    }

    @Test
    public void handleQuitTest() {
        Assertions.assertThrows(ExitGameException.class, () -> state.handle(InputKey.Quit));
    }

    @Test
    public void handleOtherTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Other) instanceof MainMenu;
        });
    }
}
