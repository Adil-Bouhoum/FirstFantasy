package Characters;

import javafx.scene.image.ImageView;

public interface IAnimatable {
    void loadAssets();
    ImageView getCharacterView();
    void playIdleAnimation();
    // We'll add these later
    // void playAttackAnimation();
    // void playSpecialAnimation();
    // void playHurtAnimation();
}