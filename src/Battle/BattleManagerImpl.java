package Battle;

import Characters.IGameCharacter;
import Inventory.Items.Item;
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
    public void showInventory() {
        // Delegate inventory display to the UI
        gameUI.showInventory();

        // Don't progress to enemy turn when viewing inventory
        gameUI.appendToBattleLog(player.getName() + " is checking inventory...");
        gameUI.updateBattleUI();
    }

    @Override
    public void useItem(Item item) {
        if (player.getInventory().contains(item)) {
            item.use(player);
            player.getInventory().removeItem(item);
            gameUI.appendToBattleLog(player.getName() + " used " + item.getName());

            // Enemy still gets their turn after item use
            enemyTurn();
            gameUI.updateBattleUI();
        }
    }

    @Override
    public void skipTurn() {
        processTurn(0); // 0 = skip
    }

    @Override
    public void processTurn(int actionChoice) {
        if (!player.isAlive() || !enemy.isAlive()) {
            checkBattleEnd();
            return;
        }

        // Player's turn
        switch (actionChoice) {
            case 1: // Normal attack
                int playerDamage = player.attackEnemy(playerUsesMagic);
                enemy.takeDamage(playerDamage);
                gameUI.appendToBattleLog(player.getName() + " deals " + playerDamage + " damage to " + enemy.getName());
                break;

            case 2: // Special ability
                int specialDamage = player.specialAbility(playerUsesMagic);
                enemy.takeDamage(specialDamage);
                gameUI.appendToBattleLog(player.specialAbilityPhrase());
                break;

            case 3: // Show inventory
                gameUI.showInventory();
                return; // Don't progress turn

            default: // Skip turn
                gameUI.appendToBattleLog("You skipped your turn.");
                break;
        }

        // Check if enemy is defeated
        if (!enemy.isAlive()) {
            checkBattleEnd();
            return;
        }

        // Enemy's turn
        enemyTurn();

        // Check if player is defeated
        if (!player.isAlive()) {
            checkBattleEnd();
            return;
        }

        gameUI.updateBattleUI();
    }

    private void checkBattleEnd() {
        if (!enemy.isAlive()) {
            gameUI.showVictoryScreen(player.getName());
        } else if (!player.isAlive()) {
            gameUI.showDefeatScreen();
        }
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