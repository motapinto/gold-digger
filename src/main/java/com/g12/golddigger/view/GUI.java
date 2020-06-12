package com.g12.golddigger.view;

import com.g12.golddigger.model.Model;

public interface GUI {
    InputKey getInput();
    void close();
    void clear();
    void refresh();
    void draw(Model model);
}
