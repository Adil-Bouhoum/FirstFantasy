package Characters;
import Inventory.Items.Item;
import Inventory.Inventory;
import javafx.scene.image.ImageView;

public interface IGameCharacter extends IAnimatable {
    String getName();
    int getHP();
    int getDefense();
    void takeDamage(int damage);
    boolean isAlive();
    int attackEnemy(boolean isMagic);
    void heal(int amount);
    int specialAbility(boolean isMagic);
    String specialAbilityPhrase();
    Inventory<Item> getInventory();
    void useItem(Item item);
}