package com.g12.golddigger.model;

import com.g12.golddigger.exception.IndexBoundsException;
import com.g12.golddigger.model.menu.Option;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Model {
    private final List<byte[]> states = new ArrayList<>();
    private Level level;
    private int achieved;
    private final Menu<Option> mainMenu;
    private final Menu<String> selectorMenu;
    private Menu<String> optionalMenu;

    public Model(int achieved, Level level, Menu<Option> mainMenu, Menu<String> selector) {
        this.achieved = achieved;
        this.level = level;
        this.mainMenu = mainMenu;
        this.selectorMenu = selector;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
        this.states.clear();
    }

    public int getAchieved() {
        return achieved;
    }

    public void setAchieved(int achieved) {
        this.achieved = achieved;
    }

    public Menu<String> getOptionalMenu() {
        return optionalMenu;
    }

    public void setOptionalMenu(Menu<String> optionalMenu) {
        this.optionalMenu = optionalMenu;
    }

    public Menu<Option> getMainMenu() {
        return mainMenu;
    }

    public Menu<String> getSelectorMenu() {
        return selectorMenu;
    }

    public List<byte[]> getStates() {
        return states;
    }

    public void cloneState() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this.level);
            oos.flush();
            oos.close();
            bos.close();
            byte[] byteData = bos.toByteArray();
            states.add(byteData);
        } catch (IOException e) {
            System.err.println("Failed to clone state");
        }
    }

    public int getNextLevel() throws IndexBoundsException {
        List<String> levels = getSelectorMenu().getOptions();
        int next = 1 + (getLevel() != null && getLevel().getFileIndex() + 1 <= getAchieved() ? getLevel().getFileIndex() : getAchieved());

        if (next >= levels.size())
            throw new IndexBoundsException("All levels completed");

        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return achieved == model.achieved &&
                states.equals(model.states) &&
                Objects.equals(level, model.level) &&
                mainMenu.equals(model.mainMenu) &&
                selectorMenu.equals(model.selectorMenu) &&
                Objects.equals(optionalMenu, model.optionalMenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(states, level, achieved, mainMenu, selectorMenu, optionalMenu);
    }
}
