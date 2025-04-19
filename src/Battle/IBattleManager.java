package Battle;

import Characters.IGameCharacter;
import Inventory.Inventory;
import Inventory.Items.Item;

public interface IBattleManager {
    void initializeBattle(IGameCharacter player, IGameCharacter enemy, boolean playerUsesMagic);
    void performAttack();
    void performSpecialAbility();
    void skipTurn();
    void processTurn(int actionChoice);
    void enemyTurn();
    boolean isBattleOver();
    IGameCharacter getWinner();
    void showInventory();
    void useItem(Item item);
}