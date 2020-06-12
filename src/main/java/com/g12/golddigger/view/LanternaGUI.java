package com.g12.golddigger.view;

import com.g12.golddigger.model.Model;
import com.g12.golddigger.view.GUI;
import com.g12.golddigger.view.InputKey;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public abstract class LanternaGUI implements GUI {

    protected final Screen screen;

    protected LanternaGUI(Screen screen) {
        this.screen = screen;
    }

    @Override
    public abstract void draw(Model model);

    @Override
    public InputKey getInput() {
        KeyStroke key;

        try {
            key = this.screen.pollInput();
        } catch (IOException e) {
            return InputKey.EOF;
        }

        if(key == null) return InputKey.Null;

        switch (key.getKeyType()) {
            case ArrowUp: return InputKey.Up;
            case ArrowRight: return InputKey.Right;
            case ArrowDown: return InputKey.Down;
            case ArrowLeft: return InputKey.Left;
            case Escape: return InputKey.Pause;
            case Character:
                switch (key.getCharacter()) {
                    case 'w': return InputKey.Up;
                    case 'd': return InputKey.Right;
                    case 's': return InputKey.Down;
                    case 'a': return InputKey.Left;
                    case 'q': return InputKey.Quit;
                    case 'r': return InputKey.Restart;
                    case 'u': return InputKey.Undo;
                    case 'p': return InputKey.Pause;
                    default: return InputKey.Other;
                }
            case EOF: return InputKey.EOF;
            default: return InputKey.Other;
        }
    }

    @Override
    public void refresh() {
        try {
            this.screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        this.screen.clear();
    }

    @Override
    public void close() {
        try {
            this.screen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
