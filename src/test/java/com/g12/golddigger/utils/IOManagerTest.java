package com.g12.golddigger.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class IOManagerTest {
    private IOManager manager;

    @BeforeEach
    public void setUp() throws IOException {
        manager = new IOManager("src/test/java/com/g12/golddigger/history/history.properties");
    }

    @Test
    public void getPropertyTest() {
        assert manager.getProperty("achieved").equals("10");
    }

    @Test
    public void getPropertyFailTest() {
        assert manager.getProperty("a") == null;
    }

    @Test
    public void setAndSaveTest() {
        Assertions.assertDoesNotThrow(() -> manager.setAndSave("test", "1"));

        Assertions.assertDoesNotThrow(() -> {
            assert new IOManager("src/test/java/com/g12/golddigger/history/history.properties").getProperty("test").equals("1");
        });
    }

    @Test
    public void badPathTest() {
        Assertions.assertThrows(IOException.class, () -> new IOManager("a"));
    }
}
