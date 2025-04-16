package Characters;

public class Rogue extends GameCharacter {
    public Rogue(String name) {
        super(name, 90, 17, 6, 0);
    }

    @Override
    public int specialAbility(boolean isMagic) {
        isMagic = false;
        System.out.println(name + " uses Shadow Strike! Domain Expansion...Idle Death Gamble..");
        return Math.random() > 0.5 ? attack * 2 : 0;
    }
}

