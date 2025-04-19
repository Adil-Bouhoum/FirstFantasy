package Gui;

import Characters.GameCharacter;
import Characters.IGameCharacter;
import Characters.Mage;
import Characters.Rogue;
import Characters.Cleric;
import Characters.Barbarian;
import Battle.IBattleManager;
import Battle.BattleManagerImpl;
import Inventory.Inventory;
import Inventory.Items.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.net.URL;


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

        //Starter Items
        player.getInventory().addItem(new HealthPotion(30));
        player.getInventory().addItem(new HealthPotion(30));

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
        player.playIdleAnimation();
        VBox playerBox = new VBox(15);
        playerBox.setAlignment(Pos.CENTER);
        ImageView playerImageView = player.getCharacterView();
        playerStatsLabel = new Label(getStatsDisplay(player));
        playerBox.getChildren().addAll(new Label(player.getName()), playerImageView, playerStatsLabel);

        // Enemy side
        enemy.playIdleAnimation();
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

        Button attackButton = new Button("Attack");
        Button specialButton = new Button("Special");
        Button inventoryButton = new Button("Inventory");  // New button
        Button skipButton = new Button("Skip");

        // Set button actions
        attackButton.setOnAction(e -> battleManager.performAttack());
        specialButton.setOnAction(e -> battleManager.performSpecialAbility());
        inventoryButton.setOnAction(e -> battleManager.showInventory());  // Connect to manager
        skipButton.setOnAction(e -> battleManager.skipTurn());

        actionBox.getChildren().addAll(attackButton, specialButton, inventoryButton, skipButton);

        root.setBottom(actionBox);
        BorderPane.setMargin(actionBox, new Insets(20, 0, 0, 0));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    private String getStatsDisplay(IGameCharacter character) {
        return "HP: " + character.getHP() + "\n";
    }

    public void showInventory() {
        Stage inventoryStage = new Stage();
        inventoryStage.initModality(Modality.APPLICATION_MODAL); // Block main window
        inventoryStage.setTitle("Inventory");

        ListView<Item> itemList = new ListView<>();
        itemList.setItems(FXCollections.observableArrayList(player.getInventory().getItems()));

        // Display item details
        Label detailLabel = new Label();
        itemList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                detailLabel.setText(newVal.getDescription());
            }
        });

        // Use button
        Button useButton = new Button("Use Item");
        useButton.setOnAction(e -> {
            Item selected = itemList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                battleManager.useItem(selected);  // Call through battle manager
                itemList.setItems(FXCollections.observableArrayList(player.getInventory().getItems()));
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Select an item to use:"),
                itemList,
                detailLabel,
                useButton
        );
        layout.setPadding(new Insets(15));

        inventoryStage.setScene(new Scene(layout, 300, 400));
        inventoryStage.showAndWait(); // Wait until closed
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
        BorderPane victoryPane = new BorderPane();
        victoryPane.setPadding(new Insets(20));
        victoryPane.setStyle("-fx-background-color: linear-gradient(to bottom, #000000, #003300);");

        // Victory message
        Label victoryLabel = new Label("VICTORY!\n" + winner + " has won the battle!");
        victoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        victoryLabel.setTextFill(Color.GOLD);
        victoryLabel.setTextAlignment(TextAlignment.CENTER);
        victoryLabel.setAlignment(Pos.CENTER);

        // Return to menu button
        Button menuButton = new Button("Return to Main Menu");
        menuButton.setStyle("-fx-font-size: 16; -fx-padding: 10 20;");
        menuButton.setOnAction(e -> returnToMainMenu());

        VBox centerBox = new VBox(30, victoryLabel, menuButton);
        centerBox.setAlignment(Pos.CENTER);
        victoryPane.setCenter(centerBox);

        Scene victoryScene = new Scene(victoryPane, 800, 600);
        primaryStage.setScene(victoryScene);
    }

    @Override
    public void showDefeatScreen() {
        BorderPane defeatPane = new BorderPane();
        defeatPane.setPadding(new Insets(20));
        defeatPane.setStyle("-fx-background-color: linear-gradient(to bottom, #000000, #330000);");

        // Defeat message
        Label defeatLabel = new Label("DEFEAT!\nYou have been vanquished...");
        defeatLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        defeatLabel.setTextFill(Color.RED);
        defeatLabel.setTextAlignment(TextAlignment.CENTER);
        defeatLabel.setAlignment(Pos.CENTER);

        Button menuButton = new Button("Return to Main Menu");
        menuButton.setStyle("-fx-font-size: 16; -fx-padding: 10 20;");
        menuButton.setOnAction(e -> returnToMainMenu());

        VBox centerBox = new VBox(30, defeatLabel, menuButton);
        centerBox.setAlignment(Pos.CENTER);
        defeatPane.setCenter(centerBox);

        Scene defeatScene = new Scene(defeatPane, 800, 600);
        primaryStage.setScene(defeatScene);
    }

    @Override
    public void returnToMainMenu() {
        showCharacterSelection();
    }

    public static void main(String[] args) {
        System.out.println("Testing resource loading:");
        URL testResource = Game.class.getResource("/characters/mage/idle_1.png");
        System.out.println("Found idle_1.png: " + (testResource != null));
        launch(args);
    }
}