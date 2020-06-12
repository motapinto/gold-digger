package com.g12.golddigger.controller.state.levelchanger.undoable.playing;

import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.Paused;
import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.movable.Digger;
import com.g12.golddigger.model.level.unmovable.walkable.Door;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class InAirTest {
    private State state;
    private Model model;

    @BeforeEach
    public void setUp()  {
        // Model
        model = Mockito.mock(Model.class);
        when(model.getLevel()).thenReturn(Mockito.mock(Level.class));
        // single elements
        when(model.getLevel().getDigger()).thenReturn(Mockito.mock(Digger.class));
        when(model.getLevel().getDoor()).thenReturn(Mockito.mock(Door.class));
        when(model.getLevel().getDoor().getPosition()).thenReturn(Mockito.mock(Position.class));
        when(model.getLevel().getDigger()).thenReturn(Mockito.mock(Digger.class));
        when(model.getLevel().getDigger().getPosition()).thenReturn(Mockito.mock(Position.class));
        // list of elements
        when(model.getLevel().getScaffolds()).thenReturn(new ArrayList<>());
        when(model.getLevel().getBoulders()).thenReturn(new ArrayList<>());
        when(model.getLevel().getDirtBlocks()).thenReturn(new ArrayList<>());
        when(model.getLevel().getGoldPieces()).thenReturn(new ArrayList<>());
        when(model.getLevel().getKeys()).thenReturn(new ArrayList<>());
        when(model.getLevel().getDelimiters()).thenReturn(new ArrayList<>());

        // InAir
        state =  new InAir(model, mock(GUIFactory.class), mock(LevelReader.class), mock(IOManager.class));
    }

    @Test
    public void handleLeftTest() {
        Assertions.assertDoesNotThrow(() -> {
            state.handle(InputKey.Left);
            verify(model).cloneState();
        });
    }

    @Test
    public void handleRightTest() {
        Assertions.assertDoesNotThrow(() -> {
            state.handle(InputKey.Right);
            verify(model).cloneState();
        });
    }

    @Test
    public void handleDownTest() {
        Assertions.assertDoesNotThrow(() -> {
            state.handle(InputKey.Down);
            verify(model).cloneState();
        });
    }

    @Test
    public void handlePauseTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Pause) instanceof Paused;
        });
    }

    @Test
    public void handleQuitTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Quit) instanceof Paused;
        });
    }

    @Test
    public void handleUndoTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Undo) instanceof Playing;
        });
    }
}
