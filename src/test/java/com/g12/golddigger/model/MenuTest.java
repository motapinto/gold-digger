package com.g12.golddigger.model;

import com.g12.golddigger.exception.IndexBoundsException;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class MenuTest<T> {
    @Property
    public <T> void setSelectedTest(@ForAll List<T> options, @ForAll Integer selected) {
        Menu<T> menu = new Menu<T>(options);
        if(selected >= menu.getOptions().size() || selected < 0 || selected > menu.getMax()) {
            Assertions.assertThrows(IndexBoundsException.class, () -> {
                menu.setSelected(selected);
            });
        } else {
            Assertions.assertDoesNotThrow(() -> {
                menu.setSelected(selected);
            });
        }
    }

    @Property
    public <T> void setMaxTest(@ForAll List<T> options,@ForAll Integer max) {
        Menu<T> menu = new Menu<T>(options);
        if(max < menu.getSelected()) {
            Assertions.assertThrows(IndexBoundsException.class, () -> {
                menu.setMax(max);
            });
        } else {
            Assertions.assertDoesNotThrow(() -> {
                menu.setMax(max);
            });
        }
    }
}
