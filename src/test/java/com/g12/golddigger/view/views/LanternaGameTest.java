package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.movable.Boulder;
import com.g12.golddigger.model.level.movable.Digger;
import com.g12.golddigger.model.level.unmovable.Delimiter;
import com.g12.golddigger.model.level.unmovable.destructible.Dirt;
import com.g12.golddigger.model.level.unmovable.pickable.Gold;
import com.g12.golddigger.model.level.unmovable.pickable.Key;
import com.g12.golddigger.model.level.unmovable.pickable.Pickable;
import com.g12.golddigger.model.level.unmovable.walkable.Door;
import com.g12.golddigger.model.level.unmovable.walkable.Scaffold;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.g12.golddigger.view.views.LanternaGame.*;
import static org.mockito.Mockito.*;

public class LanternaGameTest {
    private TextGraphics graphics;
    private LanternaGame game;
    private Model model;
    private Level level;

    @BeforeEach
    public void gameTest() {
        graphics = mock(TextGraphics.class);
        TerminalSize size = mock(TerminalSize.class);
        when(graphics.getSize()).thenReturn(size);
        Screen screen = mock(TerminalScreen.class);
        when(screen.newTextGraphics()).thenReturn(graphics);

        game = new LanternaGame(screen);
        model = mock(Model.class);

        Position position = mock(Position.class);
        when(position.getX()).thenReturn(2);
        when(position.getY()).thenReturn(2);

        List<Boulder> boulderList = new ArrayList<>();
        Boulder boulder = mock(Boulder.class);
        when(boulder.getPosition()).thenReturn(position);
        boulderList.add(boulder);

        List<Dirt> dirtList = new ArrayList<>();
        Dirt dirt1 = mock(Dirt.class);
        when(dirt1.getPosition()).thenReturn(position);
        when(dirt1.getHP()).thenReturn(0);
        dirtList.add(dirt1);
        Dirt dirt2 = mock(Dirt.class);
        when(dirt2.getPosition()).thenReturn(position);
        when(dirt2.getHP()).thenReturn(1);
        dirtList.add(dirt2);

        List<Key> keyList = new ArrayList<>();
        Key key1 = mock(Key.class);
        when(key1.getPosition()).thenReturn(position);
        when(key1.isPicked()).thenReturn(false);
        keyList.add(key1);
        Key key2 = mock(Key.class);
        when(key2.getPosition()).thenReturn(position);
        when(key1.isPicked()).thenReturn(true);
        keyList.add(key2);

        List<Delimiter> delimiterList = new ArrayList<>();
        Delimiter delimiter = mock(Delimiter.class);
        when(delimiter.getPosition()).thenReturn(position);
        delimiterList.add(delimiter);

        List<Scaffold> scaffoldList = new ArrayList<>();
        Scaffold scaffold = mock(Scaffold.class);
        when(scaffold.getPosition()).thenReturn(position);
        scaffoldList.add(scaffold);

        List<Gold> goldList = new ArrayList<>();
        Gold gold1 = mock(Gold.class);
        when(gold1.getPosition()).thenReturn(position);
        when(gold1.isPicked()).thenReturn(false);
        goldList.add(gold1);
        Gold gold2 = mock(Gold.class);
        when(gold2.getPosition()).thenReturn(position);
        when(gold2.isPicked()).thenReturn(true);
        goldList.add(gold2);

        Digger digger = mock(Digger.class);
        when(digger.getPosition()).thenReturn(position);

        Door door = mock(Door.class);
        when(door.getPosition()).thenReturn(position);
        when(door.isOpen()).thenReturn(false);

        level = mock(Level.class);
        when(level.getLevelName()).thenReturn("ozark");
        when(level.getScore()).thenReturn(999);
        when(level.getBoulders()).thenReturn(boulderList);
        when(level.getGoldPieces()).thenReturn(goldList);
        when(level.getDelimiters()).thenReturn(delimiterList);
        when(level.getDirtBlocks()).thenReturn(dirtList);
        when(level.getDigger()).thenReturn(digger);
        when(level.getDoor()).thenReturn(door);
        when(level.getKeys()).thenReturn(keyList);
        when(level.getScaffolds()).thenReturn(scaffoldList);
        when(level.getGoldCollected()).thenReturn(1);

        when(model.getLevel()).thenReturn(level);

        game = new LanternaGame(screen);
    }

    @Test
    public void drawInfoBarTest() {
        int curPosition = 0;

        game.drawInfoBar(level);

        verify(graphics, atLeastOnce()).setBackgroundColor(TextColor.Factory.fromString(infoBarColour));
        verify(graphics, atLeastOnce()).fillRectangle(new TerminalPosition(curPosition, 0), graphics.getSize(), ' ');

        String levelName = level.getLevelName();
        verify(graphics).putString(new TerminalPosition(curPosition, 0), levelName);

        verify(graphics).putString(new TerminalPosition(0, 1), "00");
        verify(graphics).putString(new TerminalPosition(2, 1), ":");
        verify(graphics).putString(new TerminalPosition(3, 1), "00");
        curPosition = 7;

        for (Pickable pickable : level.getKeys()) {
            verify(graphics).setForegroundColor(TextColor.Factory.fromString(pickable.isPicked() ? pickedKeyColour : notPickedKeyColour));
            verify(graphics).putString(new TerminalPosition(curPosition, 1), key);
            curPosition++;
        }


        curPosition += 2;

        verify(graphics).setForegroundColor(TextColor.Factory.fromString(goldColour));
        verify(graphics).putString(new TerminalPosition(curPosition, 1), "1" + gold);
        verify(graphics).putString(new TerminalPosition(curPosition + 3, 1), life);
    }

    @Test
    public void drawElementTest() {
        String pattern = "a";
        String color = "#FFFFFF";
        Position pos = new Position(1,1);

        game.drawElement(graphics, pattern, color, pos);

        verify(graphics).setForegroundColor(TextColor.Factory.fromString(color));
        verify(graphics).putString(new TerminalPosition(pos.getX(),pos.getY() + 2), pattern);
    }
}
