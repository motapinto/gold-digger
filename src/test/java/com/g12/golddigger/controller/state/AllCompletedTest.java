package com.g12.golddigger.controller.state;

import com.g12.golddigger.controller.state.levelchanger.MainMenu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.utils.IOManager;
import com.g12.golddigger.utils.LevelReader;
import com.g12.golddigger.view.GUIFactory;
import com.g12.golddigger.view.InputKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class AllCompletedTest {
    private State state;

    @BeforeEach
    public void setUp()  {
        state = new AllCompleted(mock(Model.class), mock(GUIFactory.class), mock(LevelReader.class), mock(IOManager.class));
    }

    @Test
    public void handleAnyTest() {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Restart) instanceof MainMenu;
        });
    }

    @Test
    public void handleNullTest() throws Exception {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Null) instanceof AllCompleted;
        });
    }

    @Test
    public void handleUpdateTest() throws Exception {
        Assertions.assertDoesNotThrow(() -> {
            assert state.handle(InputKey.Update) instanceof AllCompleted;
        });
    }
}
