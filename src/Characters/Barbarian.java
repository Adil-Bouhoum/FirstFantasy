package Characters;

public class Barbarian extends GameCharacter {
    public Barbarian(String name) {
        super(name, 120, 15, 10, 0); // Barbarian has no magic damage
    }

    @Override
    public int specialAbility(boolean isMagic) {
        isMagic = false;
        System.out.println(name + " uses Revenge! (Oh Lawd, he comin'...)");
        double HPpercentage = (double) HP / maxHP;
        int damage = (int)((1 - HPpercentage) * attack + attack);
        heal(damage / 2);
        System.out.println(name + " heals " + damage/2 + "\n HP= " + HP);
        return damage;
    }
}
