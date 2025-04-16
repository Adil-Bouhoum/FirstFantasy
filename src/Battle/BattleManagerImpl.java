package Battle;

import Characters.IGameCharacter;
import Gui.IGameUI;

public class BattleManagerImpl implements IBattleManager {
    private IGameCharacter player;
    private IGameCharacter enemy;
    private boolean playerUsesMagic;
    private IGameUI gameUI;

    public BattleManagerImpl(IGameUI gameUI, IGameCharacter player, IGameCharacter enemy, boolean playerUsesMagic) {
        this.gameUI = gameUI;
        this.player = player;
        this.enemy = enemy;
        this.playerUsesMagic = playerUsesMagic;
    }

    @Override
    public void initializeBattle(IGameCharacter player, IGameCharacter enemy, boolean playerUsesMagic) {
        this.player = player;
        this.enemy = enemy;
        this.playerUsesMagic = playerUsesMagic;
    }

    @Override
    public void performAttack() {
        processTurn(1); // 1 = normal attack
    }

    @Override
    public void performSpecialAbility() {
        processTurn(2); // 2 = special ability
    }

    @Override
    public void skipTurn() {
        processTurn(0); // 0 = skip
    }

    @Override
    public void processTurn(int actionChoice) {
        if (!player.isAlive() || !enemy.isAlive()) {
            return;
        }

        // Player's turn
        switch (actionChoice) {
            case 1: // Normal attack
                int playerDamage = player.attackEnemy(playerUsesMagic);
                enemy.takeDamage(playerDamage);
                gameUI.appendToBattleLog(player.getName() + " deals " + playerDamage + " damage to " + enemy.getName());
                gameUI.appendToBattleLog(enemy.getName() + " has " + enemy.getHP());
                break;

            case 2: // Special ability
                int specialDamage = player.specialAbility(playerUsesMagic);
                enemy.takeDamage(specialDamage);
                gameUI.appendToBattleLog(player.getName() + " uses special ability for " + specialDamage + " damage to " + enemy.getName());
                gameUI.appendToBattleLog(enemy.getName() + " has " + enemy.getHP());
                break;

            default: // Skip turn
                gameUI.appendToBattleLog("You skipped your turn.");
                break;
        }

        // Check if enemy is defeated
        if (!enemy.isAlive()) {
            gameUI.updateBattleUI();
            return;
        }

        // Enemy's turn
        enemyTurn();

        gameUI.updateBattleUI();
    }

    @Override
    public void enemyTurn() {
        int enemyDamage = enemy.attackEnemy(false);
        player.takeDamage(enemyDamage);
        gameUI.appendToBattleLog(enemy.getName() + " deals " + enemyDamage + " damage to " + player.getName());
        gameUI.appendToBattleLog(player.getName() + " has " + player.getHP());
    }

    @Override
    public boolean isBattleOver() {
        return !player.isAlive() || !enemy.isAlive();
    }

    @Override
    public IGameCharacter getWinner() {
        if (!enemy.isAlive()) {
            return player;
        } else if (!player.isAlive()) {
            return enemy;
        }
        return null; // Battle not over yet
    }
}