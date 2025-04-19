package Inventory.Items;
import Characters.IGameCharacter;

public class HealthPotion implements Item {
    private final int healAmount;

    public HealthPotion(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public String getName() {
        return "Health Potion (+" + healAmount + " HP)";
    }

    @Override
    public String getDescription() {
        return "Restores " + healAmount + " health points";
    }

    @Override
    public void use(IGameCharacter character) {
        character.heal(healAmount);
    }
}
