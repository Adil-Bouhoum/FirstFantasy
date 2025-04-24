package Characters;

public class Goblin extends GameCharacter {
    public Goblin(String name) {
        super(name, 80, 15, 5, 0, "goblin"); // Adjust stats as needed
    }

    @Override
    public int specialAbility(boolean isMagic) {
        // Goblin's special ability - maybe a double attack or poison?
        return attack += (int) (attack*0.25); // Example: double damage
    }

    @Override
    public String specialAbilityPhrase() {
        return "Skull Crushed !!!";
    }
}