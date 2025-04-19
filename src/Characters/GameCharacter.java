package Characters;
import Inventory.Inventory;
import Inventory.Items.Item;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public abstract class GameCharacter implements IGameCharacter {
    protected String name;
    protected int HP, maxHP, attack, defense;
    protected int magicDamage;
    protected ImageView characterView;
    protected Image idleImage;
    protected String characterType;
    private Inventory<Item> inventory = new Inventory<>(10);
    protected ICharacterAnimator animator;


    public GameCharacter(String name, int HP, int attack, int defense, int magicDamage, String characterType) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
        this.magicDamage = magicDamage;
        this.characterType = characterType;
        this.characterView = new ImageView();
        this.animator = new CharacterAnimator(characterType);
        loadAssets();
    }

    @Override
    public void loadAssets() {
        String basePath = "/resources/characters/" + characterType + "/";
        try {
            idleImage = new Image(getClass().getResourceAsStream(basePath + "idle.png"));

            // Set default image
            characterView.setImage(idleImage);
            characterView.setFitHeight(200);
            characterView.setFitWidth(150);
            characterView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Failed to load character assets: " + e.getMessage());
            // Fallback to a placeholder image if available
        }
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

    public Inventory<Item> getInventory() {
        return inventory;
    }

    public void useItem(Item item) {
        if (inventory.contains(item)) {
            item.use(this);
            inventory.removeItem(item);
        }
    }

    //Animation Stuff
    @Override
    public ImageView getCharacterView() {
        return animator.getImageView();
    }

    public void playIdleAnimation() {
        animator.playIdle();
    }

    public void playAttackAnimation() {
        animator.playAttack();
    }

    public void playHurtAnimation() {
        animator.playHurt();
    }
}
