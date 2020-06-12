package com.g12.golddigger.controller.state.levelchanger.undoable.playing;

import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.controller.state.levelchanger.undoable.GameOver;
import com.g12.golddigger.controller.state.levelchanger.undoable.GameWon;
import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.movable.Boulder;
import com.g12.golddigger.model.level.movable.Digger;
import com.g12.golddigger.model.level.unmovable.walkable.Door;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayingStateTest {
    private static class PlayingStub extends PlayingState {
        public PlayingStub(Model model, GUIFactory guiFactory, LevelReader reader, IOManager manager) {
            super(model, guiFactory, reader, manager);
        }

        @Override
        protected State getStateCondition() {
            return this;
        }

        @Override
        public State handle(InputKey action) {
            return this;
        }
    }

    private PlayingStub state;
    private Model model;
    private Level level;

    @BeforeEach
    public void setUp() {
        level = mock(Level.class);
        model = mock(Model.class);
        when(model.getLevel()).thenReturn(level);

        state = new PlayingStub(model, mock(GUIFactory.class), mock(LevelReader.class), mock(IOManager.class));
    }

    @Test
    public void nextStateGameOverTest() {
        Position position = mock(Position.class);
        when(position.getX()).thenReturn(0);
        when(position.getY()).thenReturn(0);

        Digger digger = mock(Digger.class);
        Boulder boulder = mock(Boulder.class);

        when(digger.getPosition()).thenReturn(position);
        when(boulder.getPosition()).thenReturn(position);

        ArrayList<Boulder> boulders = new ArrayList<>();
        boulders.add(boulder);
        when(level.getDigger()).thenReturn(digger);
        when(level.getBoulders()).thenReturn(boulders);

        assert state.getNextState() instanceof GameOver;
    }

    @Test
    public void nextStateGameWonTest() {
        Position position = mock(Position.class);
        when(position.getX()).thenReturn(0);
        when(position.getY()).thenReturn(0);

        Digger digger = mock(Digger.class);
        Door door = mock(Door.class);

        when(digger.getPosition()).thenReturn(position);
        when(door.getPosition()).thenReturn(position);
        when(door.isOpen()).thenReturn(true);

        ArrayList<Boulder> boulders = new ArrayList<>();
        when(level.getDigger()).thenReturn(digger);
        when(level.getBoulders()).thenReturn(boulders);
        when(level.getDoor()).thenReturn(door);

        assert state.getNextState() instanceof GameWon;
    }

    @Test
    public void nextStateContinueTest() {
        Position diggerPosition = mock(Position.class);
        when(diggerPosition.getX()).thenReturn(0);
        when(diggerPosition.getY()).thenReturn(0);

        Position doorPosition = mock(Position.class);
        when(doorPosition.getX()).thenReturn(0);
        when(doorPosition.getY()).thenReturn(1);

        Digger digger = mock(Digger.class);
        Door door = mock(Door.class);

        when(digger.getPosition()).thenReturn(diggerPosition);
        when(door.getPosition()).thenReturn(doorPosition);
        when(door.isOpen()).thenReturn(true);

        ArrayList<Boulder> boulders = new ArrayList<>();
        when(level.getDigger()).thenReturn(digger);
        when(level.getBoulders()).thenReturn(boulders);
        when(level.getDoor()).thenReturn(door);

        assert state.getNextState() instanceof PlayingStub;
    }
}
