package Battle;

import Characters.IGameCharacter;

public interface IBattleSystem {
    void initiateBattle(IGameCharacter player, IGameCharacter enemy, boolean playerUsesMagic);
    boolean processTurn(IGameCharacter attacker, IGameCharacter defender, boolean usesMagic, int actionChoice);
    void displayBattleStatus(IGameCharacter player, IGameCharacter enemy);
    void announceResult(IGameCharacter winner, IGameCharacter loser);
}