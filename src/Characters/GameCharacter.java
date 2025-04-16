package Characters;

public abstract class GameCharacter implements IGameCharacter {
    protected String name;
    protected int HP, maxHP, attack, defense;
    protected int magicDamage;

    public GameCharacter(String name, int HP, int attack, int defense, int magicDamage) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
        this.magicDamage = magicDamage;
    }
    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public int getDefense(){
        return defense;
    }
    // Setter for name (optional, if you need to modify it outside)
    public void setName(String name) {
        this.name = name;
    }

    public abstract int specialAbility(boolean isMagic);

    public void takeDamage(int damage) {
        HP -= Math.max(damage - defense, 1);
        if (HP < 0) HP = 0;
    }

    public boolean isAlive() {
        return HP > 0;
    }

    public int attackEnemy(boolean isMagic) {
        if (isMagic) {
            return magicDamage;
        } else {
            return attack;
        }
    }

    public void heal(int amount) {
        HP = Math.min(HP + amount, maxHP);
    }
}
