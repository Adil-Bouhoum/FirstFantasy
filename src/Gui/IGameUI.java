package Gui;

public interface IGameUI {
    void showCharacterSelection();
    void showBattleScreen();
    void updateBattleUI();
    void appendToBattleLog(String text);
    void disableButtons();
    void showVictoryScreen(String winner);
    void showDefeatScreen();
    void showInventory();
    void returnToMainMenu(); // Add this new method
}