package Characters;

import javafx.scene.image.ImageView;

public class Mage extends GameCharacter {
    public Mage(String name) {
        super(name, 80, 5, 5, 25,"mage");
    }

    @Override
    public int specialAbility(boolean isMagic) {
        isMagic = true;
        return magicDamage + 10; // Magic ability adds extra damage
    }

    @Override
    public String specialAbilityPhrase(){
        return getName() + " casts Fireball! EXPLOOOOOOOOOSION!!!";
    }


}
