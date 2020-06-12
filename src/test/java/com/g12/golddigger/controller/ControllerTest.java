package com.g12.golddigger.controller;

import com.g12.golddigger.controller.state.State;
import com.g12.golddigger.exception.ExitGameException;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.GUI;
import com.g12.golddigger.view.InputKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ControllerTest {
    private Controller controller;
    private Model model;
    private State state;
    private GUI gui;

    @BeforeEach
    public void setUp() {
        model = mock(Model.class);
        state = mock(State.class);
        gui = mock(GUI.class);

        when(state.getGUI()).thenReturn(gui);

        controller = new Controller(model, state);
    }

    @Test
    public void runTest() throws Exception {
        when(gui.getInput()).thenReturn(InputKey.Left);

        Assertions.assertDoesNotThrow(() -> controller.run());
        verify(state).getGUI();
        verify(gui).getInput();
        verify(state).handle(InputKey.Left);
    }

    @Test
    public void runTestEOF() {
        when(gui.getInput()).thenReturn(InputKey.EOF);

        Assertions.assertThrows(ExitGameException.class, () -> controller.run());
        verify(state).getGUI();
        verify(gui).getInput();
    }

    @Test
    public void updateTest() throws Exception {
        Assertions.assertDoesNotThrow(() -> controller.update());
        verify(state).handle(InputKey.Update);
    }

    @Test
    public void drawTest() {
        controller.draw();
        verify(state).getGUI();
        verify(gui).clear();
        verify(gui).draw(model);
        verify(gui).refresh();
    }
}