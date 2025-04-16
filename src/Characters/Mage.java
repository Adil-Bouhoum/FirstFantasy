package Characters;

public class Mage extends GameCharacter {
    public Mage(String name) {
        super(name, 80, 5, 5, 25);
    }

    @Override
    public int specialAbility(boolean isMagic) {
        isMagic = true;
        System.out.println(name + " casts Fireball! EXPLOOOOOOOOOSION!!!");
        return magicDamage + 10; // Magic ability adds extra damage
    }
}
