// ICharacterAnimator.java
package Characters;

import javafx.scene.image.ImageView;

public interface ICharacterAnimator {
    void playIdle();
    void playAttack();
    void playHurt();
    void stopAll();
    void setAnimationSpeed(double speedMultiplier);
    ImageView getImageView();
}