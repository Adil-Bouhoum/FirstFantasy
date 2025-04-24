package Characters;

import javafx.animation.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.io.*;

public class CharacterAnimator implements ICharacterAnimator {
    private final ImageView imageView;
    private Timeline currentAnimation;
    private double speedMultiplier = 1.0;
    private Image[] idleFrames;
    private int currentDebugFrame = 0; // For debugging

    public CharacterAnimator(String characterType) {
        this.imageView = createConfiguredImageView();
        loadIdleAnimation(characterType.toLowerCase());
    }

    private ImageView createConfiguredImageView() {
        ImageView iv = new ImageView();
        iv.setFitHeight(200);
        iv.setFitWidth(150);
        iv.setPreserveRatio(true);
        iv.setSmooth(false); // Disable anti-aliasing for pixel art
        return iv;
    }

    private void loadIdleAnimation(String characterType) {
        System.out.println("Loading animations for: " + characterType);
        idleFrames = loadFrames(characterType, "idle", 5);

        if (idleFrames == null || idleFrames.length == 0) {
            System.err.println("Failed to load idle animation - using placeholders");
            idleFrames = createColorPlaceholders(150, 200, Color.BLUE, 5);
        }
        playIdle();
    }

    private Image[] loadFrames(String characterType, String animName, int frameCount) {
        System.out.println("Attempting to load frames for: " + characterType);
        Image[] frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format("/characters/%s/%s_%d.png",
                    characterType, animName, i+1);
            System.out.println("Trying to load: " + path);

            try (InputStream stream = getClass().getResourceAsStream(path)) {
                if (stream != null) {
                    frames[i] = new Image(stream, 150, 200, false, false);
                    System.out.println("Loaded: " + path);
                } else {
                    System.err.println("Missing frame: " + path);
                    frames[i] = createDebugFrame(i, frameCount);
                }
            } catch (IOException e) {
                System.err.println("Error loading " + path + ": " + e.getMessage());
                frames[i] = createDebugFrame(i, frameCount);
            }
        }
        return frames;
    }

    private Image createDebugFrame(int frameIndex, int totalFrames) {
        WritableImage img = new WritableImage(150, 200);
        PixelWriter pw = img.getPixelWriter();

        // Create numbered debug frame
        Color bgColor = Color.hsb((double)frameIndex/totalFrames * 360, 0.7, 0.8);
        for (int y = 0; y < 200; y++) {
            for (int x = 0; x < 150; x++) {
                pw.setColor(x, y, bgColor);
            }
        }

        // Add frame number text
        if (frameIndex < 10) { // Simple number visualization
            for (int y = 90; y < 110; y++) {
                for (int x = 70; x < 80; x++) {
                    pw.setColor(x, y, Color.BLACK);
                }
            }
        }
        return img;
    }

    private Image[] createColorPlaceholders(int width, int height, Color color, int count) {
        Image[] placeholders = new Image[count];
        for (int i = 0; i < count; i++) {
            placeholders[i] = createDebugFrame(i, count);
        }
        return placeholders;
    }

    @Override
    public void playIdle() {
        System.out.println("Playing idle animation (" + idleFrames.length + " frames)");
        playAnimation(idleFrames, Duration.millis(300));
    }

    private void playAnimation(Image[] frames, Duration frameDuration) {
        stopAll();
        if (frames == null || frames.length == 0) {
            System.err.println("No frames to animate!");
            return;
        }

        currentAnimation = new Timeline(
                new KeyFrame(frameDuration.divide(speedMultiplier), e -> {
                    currentDebugFrame = (currentDebugFrame + 1) % frames.length;
                    imageView.setImage(frames[currentDebugFrame]);
                    System.out.println("Frame: " + currentDebugFrame); // Debug output
                })
        );
        currentAnimation.setCycleCount(Animation.INDEFINITE);
        currentAnimation.play();
    }

    @Override
    public void stopAll() {
        if (currentAnimation != null) {
            currentAnimation.stop();
            currentDebugFrame = 0;
        }
    }

    @Override
    public void setAnimationSpeed(double speedMultiplier) {
        this.speedMultiplier = Math.max(0.1, speedMultiplier);
        if (currentAnimation != null) {
            Duration currentTime = currentAnimation.getCurrentTime();
            stopAll();
            currentAnimation.playFrom(currentTime);
        }
    }

    // Future expansion stubs
    @Override public void playAttack() {}
    @Override public void playHurt() {}
    @Override public ImageView getImageView() { return imageView; }
}