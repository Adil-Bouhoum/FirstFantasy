package Battle;

import Characters.IGameCharacter;
import java.util.Scanner;

public class BattleSystem implements IBattleSystem {
    private Scanner scanner;

    public BattleSystem() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void initiateBattle(IGameCharacter player, IGameCharacter enemy, boolean playerUsesMagic) {
        System.out.println("Battle Start: " + player.getName() + " vs " + enemy.getName());

        while (player.isAlive() && enemy.isAlive()) {
            displayBattleMenu();
            int playerChoice = scanner.nextInt();

            // Player's turn
            boolean battleContinues = processTurn(player, enemy, playerUsesMagic, playerChoice);
            if (!battleContinues) {
                announceResult(player, enemy);
                break;
            }

            // Enemy's turn
            battleContinues = processTurn(enemy, player, false, 1); // Enemy always uses normal attack
            if (!battleContinues) {
                announceResult(enemy, player);
                break;
            }

            displayBattleStatus(player, enemy);
        }
    }

    private void displayBattleMenu() {
        System.out.println("\nIt's your turn! Choose your action:");
        System.out.println("1: Normal Attack");
        System.out.println("2: Special Ability");
        System.out.println("Any: Skip");
    }

    @Override
    public boolean processTurn(IGameCharacter attacker, IGameCharacter defender, boolean usesMagic, int actionChoice) {
        int damage = 0;

        if (actionChoice == 1) {
            damage = attacker.attackEnemy(usesMagic);
            defender.takeDamage(damage);
            System.out.println(attacker.getName() + " deals " + damage + " damage to " + defender.getName());
        } else if (actionChoice == 2) {
            damage = attacker.specialAbility(usesMagic);
            defender.takeDamage(damage);
            System.out.println(attacker.getName() + " uses special ability for " + damage + " damage to " + defender.getName());
        } else {
            System.out.println(attacker.getName() + " skipped their turn.");
        }

        return defender.isAlive(); // Return false if defender is defeated
    }

    @Override
    public void displayBattleStatus(IGameCharacter player, IGameCharacter enemy) {
        System.out.println(player.getName() + " HP: " + player.getHP());
        System.out.println(enemy.getName() + " HP: " + enemy.getHP());
    }

    @Override
    public void announceResult(IGameCharacter winner, IGameCharacter loser) {
        if (!loser.isAlive()) {
            if (loser.getName().equals("Player")) {
                System.out.println("Game Over! You have been defeated.");
            } else {
                System.out.println(loser.getName() + " is defeated!");
            }
        }
    }

    // Legacy method for backward compatibility
    public static void battle(IGameCharacter player, IGameCharacter enemy, boolean playerUsesMagic) {
        BattleSystem battleSystem = new BattleSystem();
        battleSystem.initiateBattle(player, enemy, playerUsesMagic);
    }
}