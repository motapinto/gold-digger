package com.g12.golddigger.view.views;

import com.g12.golddigger.model.Level;
import com.g12.golddigger.model.Model;
import com.g12.golddigger.model.level.element.Element;
import com.g12.golddigger.model.level.element.Position;
import com.g12.golddigger.model.level.unmovable.Delimiter;
import com.g12.golddigger.model.level.unmovable.destructible.Destructible;
import com.g12.golddigger.model.level.unmovable.pickable.Pickable;
import com.g12.golddigger.view.LanternaGUI;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class LanternaGame extends LanternaGUI {
    public static final String dirt = String.valueOf(Symbols.BLOCK_DENSE);
    public static final String life = String.valueOf(Symbols.HEART);
    public static final String door = String.valueOf(Symbols.DOUBLE_LINE_T_SINGLE_LEFT);
    public static final String locked = String.valueOf(Symbols.INVERSE_BULLET);
    public static final String key = String.valueOf(Symbols.MALE);
    public static final String gold = String.valueOf(Symbols.CLUB);
    public static final String boulder = String.valueOf(Symbols.INVERSE_WHITE_CIRCLE);
    public static final String digger = String.valueOf(Symbols.FACE_WHITE);
    public static final String delimiter = " ";
    public static final String scaffold = String.valueOf(Symbols.SINGLE_LINE_T_DOWN);
    public static final String gameColour = "#FFFFFF";
    public static final String infoBarColour = "#FFFFFF";
    public static final String boulderColour = "#800000";
    public static final int dirtColour = 9;
    public static final String lifeColor = "#EA3316";
    public static final String levelColour = "#000000";
    public static final String delimiterColour = "#000000";
    public static final String scaffoldColour = "#cc3300";
    public static final String goldColour = "#ffcc00";
    public static final String diggerColour = "#0033cc";
    public static final String doorColour = "#663300";
    public static final String lockedColour = "#663300";
    public static final String notPickedKeyColour = "#404040";
    public static final String pickedKeyColour = "#808080";
    public static final String timerColour = "#E88207";

    private List<List<Integer>> floodFillList;

    public LanternaGame(Screen screen) {
        super(screen);
    }

    @Override
    public void draw(Model model) {
        Level level = model.getLevel();
        Position maxPos = getMaxXY(level.getDelimiters());
        initFloodFill(level, maxPos);
        drawInfoBar(level);

        TextGraphics graphics = this.screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(levelColour));
        graphics.fillRectangle(new TerminalPosition(0, 2), graphics.getSize(), ' ');

        graphics.fillRectangle(new TerminalPosition(0, 2), new TerminalSize(maxPos.getX() + 1, maxPos.getY() + 1), ' ');
        graphics.setBackgroundColor(TextColor.Factory.fromString(gameColour));

        for (int y = 0; y < this.floodFillList.size(); y++) {
            for (int x = 0; x < this.floodFillList.get(y).size(); x++) {
                if(this.floodFillList.get(y).get(x) == 0)
                    drawElement(graphics, delimiter, delimiterColour, new Position(x, y));
            }
        }

        for(Destructible element : level.getDirtBlocks())
            if (isVisible(element.getPosition())) {
                if(element.getHP() > 0) {
                    int backGroundColour = dirtColour - element.getHP() * 2;
                    int firstColor = Math.max(backGroundColour, 0);
                    int secondColor = Math.max(backGroundColour - 3, 0);
                    int thirdColor = Math.max(backGroundColour - 6, 0);
                    String backGround = "#" +
                            firstColor + firstColor +
                            secondColor + secondColor +
                            thirdColor + thirdColor;
                    drawElement(graphics, dirt, backGround, element.getPosition());
                }
            }

        for(Pickable element : level.getKeys())
            if (isVisible(element.getPosition())) {
                if (!element.isPicked())
                    drawElement(graphics, key, pickedKeyColour, element.getPosition());
            }

        for(Element element : level.getScaffolds())
            if(isVisible(element.getPosition()))
                drawElement(graphics, scaffold, scaffoldColour, element.getPosition());

        for(Pickable element : level.getGoldPieces())
            if(isVisible(element.getPosition())) {
                if(!element.isPicked())
                    drawElement(graphics, gold, goldColour, element.getPosition());
            }

        if(isVisible(level.getDoor().getPosition())) {
            if(level.getDoor().isOpen())
                drawElement(graphics, door, doorColour, level.getDoor().getPosition());
            else
                drawElement(graphics, locked, lockedColour, level.getDoor().getPosition());
        }

        for(Element element : level.getBoulders())
            if(isVisible(element.getPosition()))
                drawElement(graphics, boulder, boulderColour, element.getPosition());

        drawElement(graphics, digger, diggerColour, level.getDigger().getPosition());
    }

    public void drawInfoBar(Level level) {
        int curPosition = 0;
        TextGraphics graphics = this.screen.newTextGraphics();

        graphics.setBackgroundColor(TextColor.Factory.fromString(infoBarColour));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(graphics.getSize().getColumns(), 2), ' ');

        String levelName = level.getLevelName();
        graphics.putString(new TerminalPosition(curPosition, 0), levelName);

        long seconds = level.getTime() / 1000;
        String minutesString = (((seconds / 60) >= 10) ? "" : "0") + seconds / 60;
        String secondsString = (((seconds % 60) >= 10) ? "" : "0") + seconds % 60;

        graphics.setForegroundColor(TextColor.Factory.fromString(timerColour));
        graphics.putString(new TerminalPosition(curPosition, 1), minutesString);
        curPosition += minutesString.length();
        graphics.putString(new TerminalPosition(curPosition, 1), ":");
        graphics.putString(new TerminalPosition(curPosition + 1, 1), secondsString);

        curPosition+=5;
        for (Pickable pickable : level.getKeys()) {
            graphics.setForegroundColor(TextColor.Factory.fromString(pickable.isPicked() ? pickedKeyColour : notPickedKeyColour));
            graphics.putString(new TerminalPosition(curPosition, 1), key);
            curPosition++;
        }
        curPosition+=2;

        String goldCollected = String.valueOf(level.getGoldCollected());
        graphics.setForegroundColor(TextColor.Factory.fromString(goldColour));
        graphics.putString(new TerminalPosition(curPosition, 1), goldCollected + gold);
        graphics.setForegroundColor(TextColor.Factory.fromString(lifeColor));
        graphics.putString(new TerminalPosition(curPosition + 3, 1), life);
    }

    public void drawElement(TextGraphics graphics, String pattern, String color, Position position) {
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.putString(new TerminalPosition(position.getX(),position.getY() + 2), pattern);
    }

    private void initFloodFill(Level level, Position maxPos) {
        floodFillList = new ArrayList<>();
        for (int i = 0; i < maxPos.getY() + 2; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < maxPos.getX() + 2; j++) {
                row.add(j, -1);
            }
            floodFillList.add(row);
        }

        for (Destructible destructible : level.getDirtBlocks()) {
            Position pos = destructible.getPosition();
            floodFillList.get(pos.getY()).set(pos.getX(), destructible.getHP() == 0 ? -1 : -2);
        }

        for (Element element : level.getDelimiters()) {
            Position pos = element.getPosition();
            floodFillList.get(pos.getY()).set(pos.getX(), -2);
        }

        for (Element element : level.getBoulders()) {
            Position pos = element.getPosition();
            floodFillList.get(pos.getY()).set(pos.getX(), -2);
        }

        for (Element element : level.getKeys()) {
            Position pos = element.getPosition();
            floodFillList.get(pos.getY()).set(pos.getX(), -1);
        }

        for (Element element : level.getGoldPieces()) {
            Position pos = element.getPosition();
            floodFillList.get(pos.getY()).set(pos.getX(), -1);
        }

        for (Element element : level.getScaffolds()) {
            Position pos = element.getPosition();
            floodFillList.get(pos.getY()).set(pos.getX(), -1);
        }

        Position doorPos = level.getDoor().getPosition();
        floodFillList.get(doorPos.getY()).set(doorPos.getX(), -1);

        Position diggerPos = level.getDigger().getPosition();
        floodFillList.get(diggerPos.getY()).set(diggerPos.getX(), -1);

        floodFill(diggerPos);
    }

    private Position getMaxXY(List<Delimiter> delimiters) {
        int maxX = 0, maxY = 0;
        for(Delimiter del : delimiters) {
            int x = del.getPosition().getX();
            int y = del.getPosition().getY();
            if(x > maxX) maxX = x;
            if(y > maxY) maxY = y;
        }
        return new Position(maxX, maxY);
    }

    private boolean isVisible(Position position) {
        int y = position.getY(), x = position.getX();

        if(y + 1 >= floodFillList.size()) {
            if(x + 1 >= floodFillList.get(y).size())
                return floodFillList.get(y - 1).get(x) == 0 || floodFillList.get(y).get(x - 1) == 0;

            if(x <= 0)
                return floodFillList.get(y - 1).get(x) == 0 || floodFillList.get(y).get(x + 1) == 0;

            return floodFillList.get(y - 1).get(x) == 0 || floodFillList.get(y).get(x + 1) == 0
                    || floodFillList.get(y).get(x - 1) == 0;
        }

        if(y <= 0) {
            if(x + 1 >= floodFillList.get(y).size())
                return floodFillList.get(y + 1).get(x) == 0 || floodFillList.get(y).get(x - 1) == 0;

            if(x <= 0)
                return floodFillList.get(y + 1).get(x) == 0 || floodFillList.get(y).get(x + 1) == 0;

            return floodFillList.get(y + 1).get(x) == 0 || floodFillList.get(y).get(x + 1) == 0
                    || floodFillList.get(y).get(x - 1) == 0;
        }

        if(x + 1 >= floodFillList.get(y).size())
            return floodFillList.get(y + 1).get(x) == 0 || floodFillList.get(y - 1).get(x) == 0 ||
                    floodFillList.get(y).get(x - 1) == 0;

        if(x <= 0)
            return floodFillList.get(y + 1).get(x) == 0 || floodFillList.get(y - 1).get(x) == 0 ||
                    floodFillList.get(y).get(x + 1) == 0;

        return floodFillList.get(y + 1).get(x) == 0 || floodFillList.get(y - 1).get(x) == 0
                || floodFillList.get(y).get(x + 1) == 0 || floodFillList.get(y).get(x - 1) == 0;
    }

    private void floodFill(Position position) {
        floodFillRec(position, -1, 0);
    }

    private void floodFillRec(Position position, int prevC, int newC) {
        // Base cases
        if (position.getX() < 0 || position.getX() >= floodFillList.get(0).size() || position.getY() < 0 || position.getY() >= floodFillList.size())
            return;
        if (floodFillList.get(position.getY()).get(position.getX()) != prevC)
            return;

        // Replace the color at (x, y)
        floodFillList.get(position.getY()).set(position.getX(), newC);

        // Recur for north, east, south and west
        floodFillRec(new Position(position.getX() + 1, position.getY()), prevC, newC);
        floodFillRec(new Position(position.getX() - 1, position.getY()), prevC, newC);
        floodFillRec(new Position(position.getX(), position.getY() + 1), prevC, newC);
        floodFillRec(new Position(position.getX(), position.getY() - 1), prevC, newC);
    }
}
