package Characters;

import javafx.scene.image.ImageView;

public class Rogue extends GameCharacter {
    public Rogue(String name) {
        super(name, 90, 17, 6, 0,"rogue");
    }

    @Override
    public int specialAbility(boolean isMagic) {
        isMagic = false;
        return Math.random() > 0.5 ? attack * 2 : 0;
    }

    @Override
    public String specialAbilityPhrase(){
        return getName() + " uses Shadow Strike! Domain Expansion...Idle Death Gamble..";
    }


}

