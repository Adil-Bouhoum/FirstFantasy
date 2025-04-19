package Characters;

import javafx.scene.image.ImageView;

public class Cleric extends GameCharacter {
    public Cleric(String name) {
        super(name, 100, 12, 8, 10, "cleric"); // Cleric has some magic damage (healing, buffs)
    }

    @Override
    public int specialAbility(boolean isMagic) {
        isMagic = true;
        heal(15); // Cleric heals instead of dealing damage
        return 0;
    }

    @Override
    public String specialAbilityPhrase(){
        return getName() + " uses Divine Healing! PURRIIFICAAATION T-T!!!";
    }


}
