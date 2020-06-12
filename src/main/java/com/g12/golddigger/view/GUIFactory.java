package com.g12.golddigger.view;

public interface GUIFactory {
    GUI drawMenu();
    GUI drawGameOver();
    GUI drawGameWon();
    GUI drawGame();
    GUI drawPaused();
    GUI drawSelector();
    GUI drawAllCompleted();
}
