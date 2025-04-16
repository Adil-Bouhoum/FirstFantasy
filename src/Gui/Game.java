package Gui;

import Characters.GameCharacter;
import Characters.IGameCharacter;
import Characters.Mage;
import Characters.Rogue;
import Characters.Cleric;
import Characters.Barbarian;
import Battle.IBattleManager;
import Battle.BattleManagerImpl;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application implements IGameUI {

    private IGameCharacter player;
    private IGameCharacter enemy;
    private boolean playerUsesMagic;
    private Text battleLog;
    private Label playerStatsLabel;
    private Label enemyStatsLabel;
    private IBattleManager battleManager;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("First Fantasy");

        // Character selection screen
        showCharacterSelection();
    }

    @Override
    public void showCharacterSelection() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Title
        Text title = new Text("Choose Your Character");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 24));

        // Character buttons
        HBox characterBox = new HBox(20);
        characterBox.setAlignment(Pos.CENTER);

        // Create a button for each character class
        createCharacterButton(characterBox, "Barbarian");
        createCharacterButton(characterBox, "Mage");
        createCharacterButton(characterBox, "Rogue");
        createCharacterButton(characterBox, "Cleric");

        VBox contentBox = new VBox(40);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(title, characterBox);

        root.setCenter(contentBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createCharacterButton(HBox parent, String characterClass) {
        VBox characterVBox = new VBox(10);
        characterVBox.setAlignment(Pos.CENTER);

        // Image placeholder
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(100);

        Label classLabel = new Label(characterClass);
        classLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        Button selectButton = new Button("Select " + characterClass);
        selectButton.setPrefWidth(150);
        selectButton.setOnAction(e -> {
            chooseCharacter(characterClass);
        });

        characterVBox.getChildren().addAll(imageView, classLabel, selectButton);
        parent.getChildren().add(characterVBox);
    }

    private void chooseCharacter(String characterClass) {
        switch (characterClass) {
            case "Barbarian":
                player = new Barbarian("Hero");
                playerUsesMagic = false;
                break;
            case "Mage":
                player = new Mage("Hero");
                playerUsesMagic = true;
                break;
            case "Rogue":
                player = new Rogue("Hero");
                playerUsesMagic = false;
                break;
            case "Cleric":
                player = new Cleric("Hero");
                playerUsesMagic = true;
                break;
        }

        // Create enemy
        enemy = new Barbarian("Goblin");

        // Initialize battle manager
        battleManager = new BattleManagerImpl(this, player, enemy, playerUsesMagic);

        // Show battle screen
        showBattleScreen();
    }

    @Override
    public void showBattleScreen() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Top section - Battle title
        Text battleTitle = new Text("Battle: " + player.getName() + " vs " + enemy.getName());
        battleTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        HBox titleBox = new HBox(battleTitle);
        titleBox.setAlignment(Pos.CENTER);
        root.setTop(titleBox);

        // Center section - Characters and battle log
        HBox centerBox = new HBox(50);
        centerBox.setAlignment(Pos.CENTER);

        // Player side
        VBox playerBox = new VBox(15);
        playerBox.setAlignment(Pos.CENTER);
        ImageView playerImageView = new ImageView();
        playerImageView.setFitHeight(200);
        playerImageView.setFitWidth(150);
        playerStatsLabel = new Label(getStatsDisplay(player));
        playerBox.getChildren().addAll(new Label(player.getName()), playerImageView, playerStatsLabel);

        // Enemy side
        VBox enemyBox = new VBox(15);
        enemyBox.setAlignment(Pos.CENTER);
        ImageView enemyImageView = new ImageView();
        enemyImageView.setFitHeight(200);
        enemyImageView.setFitWidth(150);
        enemyStatsLabel = new Label(getStatsDisplay(enemy));
        enemyBox.getChildren().addAll(new Label(enemy.getName()), enemyImageView, enemyStatsLabel);

        // Battle log area
        battleLog = new Text("Battle Start: " + player.getName() + " vs " + enemy.getName());
        battleLog.setWrappingWidth(700);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(battleLog);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(150);

        VBox battleLogBox = new VBox(10);
        battleLogBox.setAlignment(Pos.CENTER);
        battleLogBox.getChildren().add(scrollPane);

        centerBox.getChildren().addAll(playerBox, enemyBox);

        VBox centralContent = new VBox(30);
        centralContent.setAlignment(Pos.CENTER);
        centralContent.getChildren().addAll(centerBox, battleLogBox);

        root.setCenter(centralContent);

        // Bottom section - Action buttons
        HBox actionBox = new HBox(20);
        actionBox.setAlignment(Pos.CENTER);

        Button attackButton = new Button("Normal Attack");
        attackButton.setPrefWidth(120);
        attackButton.setOnAction(e -> battleManager.performAttack());

        Button specialButton = new Button("Special Ability");
        specialButton.setPrefWidth(120);
        specialButton.setOnAction(e -> battleManager.performSpecialAbility());

        Button skipButton = new Button("Skip Turn");
        skipButton.setPrefWidth(120);
        skipButton.setOnAction(e -> battleManager.skipTurn());

        actionBox.getChildren().addAll(attackButton, specialButton, skipButton);

        root.setBottom(actionBox);
        BorderPane.setMargin(actionBox, new Insets(20, 0, 0, 0));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    private String getStatsDisplay(IGameCharacter character) {
        return "HP: " + character.getHP() + "\n";
    }

    @Override
    public void updateBattleUI() {
        playerStatsLabel.setText(getStatsDisplay(player));
        enemyStatsLabel.setText(getStatsDisplay(enemy));

        if (battleManager.isBattleOver()) {
            IGameCharacter winner = battleManager.getWinner();
            if (winner == player) {
                showVictoryScreen(player.getName());
            } else {
                showDefeatScreen();
            }
        }
    }

    @Override
    public void appendToBattleLog(String text) {
        battleLog.setText(battleLog.getText() + "\n" + text);
    }

    @Override
    public void disableButtons() {
        // Find and disable all buttons in the scene
        Scene scene = battleLog.getScene();
        scene.getRoot().lookupAll(".button").forEach(node -> {
            if (node instanceof Button) {
                ((Button) node).setDisable(true);
            }
        });
    }

    @Override
    public void showVictoryScreen(String winner) {
        appendToBattleLog(enemy.getName() + " is defeated!");
        disableButtons();
        // Could expand with a proper victory screen
    }

    @Override
    public void showDefeatScreen() {
        appendToBattleLog("Game Over! You have been defeated.");
        disableButtons();
        // Could expand with a proper defeat screen
    }

    public static void main(String[] args) {
        launch(args);
    }
}