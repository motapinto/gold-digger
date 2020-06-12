package com.g12.golddigger.utils;

import com.g12.golddigger.exception.IndexBoundsException;
import com.g12.golddigger.exception.LevelReadException;
import com.g12.golddigger.model.Menu;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.menu.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ModelBuilderTest {
    private ModelBuilder builder;
    private IOManager manager;
    private Model model;

    @BeforeEach
    public void setUp() throws IndexBoundsException {
        manager = mock(IOManager.class);
        builder = new ModelBuilder(manager);

        List<String> levels = new ArrayList<>();
        levels.add("Test0");
        levels.add("Test1");

        Menu<String> selector = new Menu<>(levels);
        selector.setMax(0);

        List<Option> options = new ArrayList<>();
        options.add(Option.Start);
        options.add(Option.Scratch);
        options.add(Option.Selector);
        Menu<Option> mainMenu = new Menu<>(options);

        model = new Model(-1 , null, mainMenu, selector);
    }

    @Test
    public void buildTest(){
        assertDoesNotThrow(() -> {
            Model modelBuilt = builder.build("levels");
            verify(manager).getProperty(anyString());
            assert modelBuilt.equals(model);
        });
    }

    @Test
    public void buildFailTest(){
        Assertions.assertThrows(LevelReadException.class, () -> {
            Model modelBuilt = builder.build("fail");
            verify(manager).getProperty(anyString());
            assert modelBuilt.equals(model);
        });
    }

    @Test
    public void buildFailLevelsTest(){
        Assertions.assertThrows(LevelReadException.class, () -> {
            builder.build("");
        });
    }
}
