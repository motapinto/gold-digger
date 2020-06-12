package com.g12.golddigger.model;

import com.g12.golddigger.exception.IndexBoundsException;

import java.util.List;
import java.util.Objects;

public class Menu<T> {
    private final List<T> options;
    private int selected;
    private int max;

    public Menu(List<T> options) {
        this.options = options;
        this.max = options.size() - 1;
        this.selected = 0;
    }

    public List<T> getOptions() {
        return options;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) throws IndexBoundsException {
        if(selected >= options.size() || selected < 0 || selected > this.max)
            throw new IndexBoundsException("Value passed is out of bounds");

        this.selected = selected;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) throws IndexBoundsException {
        if(max < this.selected)
            throw new IndexBoundsException("Value passed is out of bounds");

        this.max = Math.min(max, options.size() - 1);
    }

    public T getSelectedOption() {
        return options.get(selected);
    }

    public void previousOption() throws IndexBoundsException {
        this.setSelected(this.selected == 0 ? max : this.selected - 1);
    }

    public void nextOption() throws IndexBoundsException {
        this.setSelected(this.selected == max ? 0 : this.selected + 1);
    }

    public void reset() {
        this.selected = 0;
        this.max = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu<?> menu = (Menu<?>) o;
        return selected == menu.selected &&
                max == menu.max &&
                options.equals(menu.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(options, selected, max);
    }
}
