package Characters;

public interface IGameCharacter {
    String getName();
    int getHP();
    int getDefense();
    void takeDamage(int damage);
    boolean isAlive();
    int attackEnemy(boolean isMagic);
    void heal(int amount);
    int specialAbility(boolean isMagic);
}