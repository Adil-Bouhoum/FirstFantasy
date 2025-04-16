package Characters;

public class Cleric extends GameCharacter {
    public Cleric(String name) {
        super(name, 100, 12, 8, 10); // Cleric has some magic damage (healing, buffs)
    }

    @Override
    public int specialAbility(boolean isMagic) {
        isMagic = true;
        System.out.println(name + " uses Divine Healing! PURRIIFICAAATION T-T!!!");
        heal(15); // Cleric heals instead of dealing damage
        return 0;
    }
}
