package Battle;

import Characters.IGameCharacter;

public interface IBattleManager {
    void initializeBattle(IGameCharacter player, IGameCharacter enemy, boolean playerUsesMagic);
    void performAttack();
    void performSpecialAbility();
    void skipTurn();
    void processTurn(int actionChoice);
    void enemyTurn();
    boolean isBattleOver();
    IGameCharacter getWinner();
}