package Inventory.Items;
import Characters.IGameCharacter;

public interface Item {
    String getName();
    String getDescription();
    void use(IGameCharacter character);
}
