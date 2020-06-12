package com.g12.golddigger.controller.state.levelchanger;

import com.g12.golddigger.controller.state.levelchanger.undoable.playing.Playing;
import com.g12.golddigger.exception.IndexBoundsException;
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

public class SelectorTest {
    private Selector state;
    private Menu<String> selector= mock(Menu.class);

    @BeforeEach
    public void setUp() {
        // Model
        Model model = Mockito.mock(Model.class);
        when(model.getSelectorMenu()).thenReturn(selector);

        // Selector
        state =  new Selector(model, mock(GUIFactory.class), mock(LevelReader.class), mock(IOManager.class));
    }

    @Test
    public void handleUpType1Test() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Up) instanceof Selector;
            verify(selector).previousOption();
        });
    }

    @Test
    public void handleUpType2Test() {
        Assertions.assertDoesNotThrow(() -> {
            when(state.handle(InputKey.Up)).thenThrow(IndexBoundsException.class);
            assert state.handle(InputKey.Up) instanceof Selector;
            verify(selector).previousOption();
        });
    }

    @Test
    public void handleRightTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Right) instanceof Playing;
        });
    }

    @Test
    public void handleDownType1Test() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Down) instanceof Selector;
            verify(selector).nextOption();
        });
    }

    @Test
    public void handleDownType2Test() {
        Assertions.assertDoesNotThrow(() -> {
            when(state.handle(InputKey.Down)).thenThrow(IndexBoundsException.class);
            assert state.handle(InputKey.Down) instanceof Selector;
            verify(selector).nextOption();
        });
    }

    @Test
    public void handlePauseTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Pause) instanceof MainMenu;
        });
    }

    @Test
    public void handleQuitTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Quit) instanceof MainMenu;
        });
    }
}
