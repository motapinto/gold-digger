package com.g12.golddigger.model;

import com.g12.golddigger.exception.IndexBoundsException;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.movable.Boulder;
import com.g12.golddigger.model.level.movable.Digger;
import com.g12.golddigger.model.level.unmovable.Delimiter;
import com.g12.golddigger.model.level.unmovable.destructible.Dirt;
import com.g12.golddigger.model.level.unmovable.pickable.Gold;
import com.g12.golddigger.model.level.unmovable.pickable.Key;
import com.g12.golddigger.model.level.unmovable.walkable.Door;
import com.g12.golddigger.model.menu.Option;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelTest {
    private Model model;
    private Level level;
    private Menu<Option> mainMenu = mock(Menu.class);
    private Menu<String> selectorMenu = mock(Menu.class);

    @BeforeEach
    public void setUp() {
        Position position = new Position(2 ,2);

        List<Boulder> boulderList = new ArrayList<>();
        Boulder boulder = new Boulder(position);
        boulderList.add(boulder);

        List<Dirt> dirtList = new ArrayList<>();
        Dirt dirt1 = new Dirt(3, position);
        dirtList.add(dirt1);
        Dirt dirt2 = new Dirt(3, position);
        dirtList.add(dirt2);

        List<Key> keyList = new ArrayList<>();
        Key key1 = new Key(position);
        keyList.add(key1);
        Key key2 = new Key(position);
        keyList.add(key2);

        List<Delimiter> delimiterList = new ArrayList<>();
        Delimiter delimiter = new Delimiter(position);
        delimiterList.add(delimiter);

        List<Gold> goldList = new ArrayList<>();
        Gold gold1 = new Gold(position);
        goldList.add(gold1);
        Gold gold2 = new Gold(position);
        goldList.add(gold2);

        Digger digger = new Digger(position);
        Door door = new Door(position);

        level = new Level(1, "ozark", digger, boulderList, door, dirtList, keyList, goldList, delimiterList);
        model = new Model(1, level, mainMenu, selectorMenu);
    }

    @Test
    public void cloneStateTest() throws Exception {
        model.cloneState();

        ByteArrayInputStream byteStream = new ByteArrayInputStream(model.getStates().get(model.getStates().size() - 1));
        Level validState = (Level) new ObjectInputStream(byteStream).readObject();

        assertEquals(validState, level);
    }

    @Property
    public void getNextLevelPropertyTest(@ForAll List<String> levels, @ForAll int achieved) {
        when(selectorMenu.getOptions()).thenReturn(levels);
        model = new Model(achieved, level, mainMenu, selectorMenu);

        int next = 1 + (level != null && level.getFileIndex() + 1 <= achieved ?
                level.getFileIndex() : achieved);

        if(next >= levels.size()) {
            Assertions.assertThrows(IndexBoundsException.class, () -> {
               model.getNextLevel();
            });
        } else {
            Assertions.assertDoesNotThrow(() -> {
                assertEquals(model.getNextLevel(), achieved + 1);
            });
        }
    }
}
