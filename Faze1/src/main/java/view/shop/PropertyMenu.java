package view.shop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Property.DefenseType;
import model.Property.FoodType;
import model.Property.ResourceType;
import model.Property.WeaponType;

import java.awt.*;


public class PropertyMenu extends Application {
    private String type;

    public PropertyMenu(String type) {
        this.type = type;
    }

    public static Text getProperty() {
        return property;
    }

    private static Text property = new Text();
    private static Text amount = new Text("0");
    private static Text buyPrice = new Text();
    private static Text sellPrice = new Text();

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        root.setPrefSize(width, height);
        Button back = getBack(stage);
        root.getChildren().add(back);
        GridPane gridPane = getGridPane(width, height);
        Button add = getAdd(height);
        Button remove = getRemove(height);
        Button buy = getBuy(width, height);
        Button sell = getSell(width, height);
        property = getType(width, height);
        amount = getAmount(width, height);
        buyPrice = getBuyPrice(width, height);
        sellPrice = getSellPrice(width, height);
        root.getChildren().add(property);
        root.getChildren().add(amount);
        root.getChildren().addAll(buy, sell);
        root.getChildren().addAll(gridPane, add, remove, buyPrice, sellPrice);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static Button getSell(double width, double height) {
        Button sell = new Button("Sell");
        sell.setLayoutX(width - 100);
        sell.setLayoutY(height - 100);
        return sell;
    }


    public Button getBuy(double width, double height) {
        Button buy = new Button("Buy");
        buy.setLayoutX(width - 200);
        buy.setLayoutY(height - 100);
        buy.setOnMouseClicked(event -> {

        });
        return buy;
    }


    public Button getRemove(double height) {
        Button remove = new Button("Remove");
        remove.setLayoutY(height - 100);
        remove.setLayoutX(100);
        remove.setOnMouseClicked(event -> {
            if (property.textProperty().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("You should choose a food first!");
            }
            try {
                int x = Integer.parseInt(amount.getText());
                x--;
                if (x < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("You can't buy or sell negative amount of food!");
                    alert.showAndWait();
                } else
                    amount.textProperty().set(String.valueOf(x));
                //set total
            } catch (NumberFormatException e) {
                amount.textProperty().set("0");
            }
        });
        return remove;
    }


    public Button getAdd(double height) {
        Button add = new Button("Add");
        add.setLayoutY(height - 100);
        add.setLayoutX(50);
        add.setOnMouseClicked(event -> {
            if (property.textProperty().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("You should choose a food first!");
            }
            try {
                int x = Integer.parseInt(amount.getText());
                x++;
                amount.textProperty().set(String.valueOf(x));
                //set total
            } catch (NumberFormatException e) {
                amount.textProperty().set("1");
            }
        });
        return add;
    }


    public Text getType(double width, double height) {
        Text type = new Text();
        type.setLayoutX(width - 200);
        type.setLayoutY(height - 350);
        return type;
    }

    public static Text getBuyPrice(double width, double height) {
        Text buyPrice = new Text();
        buyPrice.setLayoutX(width - 200);
        buyPrice.setLayoutY(height - 300);
        return buyPrice;
    }

    public static Text getSellPrice(double width, double height) {
        Text sellPrice = new Text();
        sellPrice.setLayoutX(width - 200);
        sellPrice.setLayoutY(height - 250);
        return sellPrice;
    }

    public static Text getAmount(double width, double height) {
        Text amount = new Text();
        amount.setLayoutX(width - 200);
        amount.setLayoutY(height - 200);
        return amount;
    }

    public GridPane getGridPane(double width, double height) {
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(width - 100, height - 100);
        gridPane.setLayoutX(50);
        gridPane.setLayoutY(50);
        gridPane.setHgap(10);
        if (type.equals("food")) {
            addBread(gridPane, 0, 0);
            addMeat(gridPane, 0, 1);
            addCheese(gridPane, 1, 0);
            addApples(gridPane, 1, 1);
        } else if (type.equals("resources")) {
            addAle(gridPane, 0, 0);
            addBarley(gridPane, 0, 1);
            addIron(gridPane, 0, 2);
            addFlour(gridPane, 1, 0);
            addPitch(gridPane, 1, 1);
            addStone(gridPane, 1, 2);
            addWheat(gridPane, 2, 0);
            addWood(gridPane, 2, 1);
        } else if (type.equals("weapons")) {
            addBow(gridPane, 0, 0);
            addSword(gridPane, 0, 1);
            addPetroleum(gridPane, 0, 2);
            addPike(gridPane, 1, 0);
            addMace(gridPane, 1, 1);
            addSpear(gridPane, 1, 2);
            addCrossBow(gridPane, 2, 0);
        } else {
            addLeatherArmour(gridPane, 0, 0);
            addMetalArmour(gridPane, 0, 1);
        }
        return gridPane;
    }

    public static void addBread(GridPane gridPane, int i, int j) {
        ImageView bread = new ImageView(new Image(PropertyMenu.class.getResource("/foods/bread.jpg").toExternalForm()));
        gridPane.setVgap(10);
        bread.setFitWidth(100);
        bread.setFitHeight(100);
        gridPane.add(bread, i, j);
        bread.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Bread");
            buyPrice.setText("buy: " + FoodType.getBuyPrice(FoodType.BREAD) + "");
            sellPrice.setText("sell: " + FoodType.getSellPrice(FoodType.BREAD) + "");
        });
    }

    public static void addMeat(GridPane gridPane, int i, int j) {
        ImageView meat = new ImageView(new Image(PropertyMenu.class.getResource("/foods/meat.jpg").toExternalForm()));
        gridPane.setVgap(10);
        meat.setFitWidth(100);
        meat.setFitHeight(100);
        gridPane.add(meat, i, j);
        meat.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Meat");
            buyPrice.setText("buy: " + FoodType.getBuyPrice(FoodType.MEAT) + "");
            sellPrice.setText("sell: " + FoodType.getSellPrice(FoodType.MEAT) + "");
        });
    }

    public static void addCheese(GridPane gridPane, int i, int j) {
        ImageView cheese = new ImageView(new Image(PropertyMenu.class.getResource("/foods/cheese.png").toExternalForm()));
        gridPane.setVgap(10);
        cheese.setFitWidth(100);
        cheese.setFitHeight(100);
        gridPane.add(cheese, i, j);
        cheese.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Cheese");
            buyPrice.setText("buy: " + FoodType.getBuyPrice(FoodType.CHEESE) + "");
            sellPrice.setText("sell: " + FoodType.getSellPrice(FoodType.CHEESE) + "");
        });
    }

    public static void addApples(GridPane gridPane, int i, int j) {
        ImageView apple = new ImageView(new Image(PropertyMenu.class.getResource("/foods/apple.jpg").toExternalForm()));
        gridPane.setVgap(10);
        apple.setFitWidth(100);
        apple.setFitHeight(100);
        gridPane.add(apple, i, j);
        apple.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Apple");
            buyPrice.setText("buy: " + FoodType.getBuyPrice(FoodType.APPLES) + "");
            sellPrice.setText("sell: " + FoodType.getSellPrice(FoodType.APPLES) + "");
        });
    }

    public static void addBow(GridPane gridPane, int i, int j) {
        ImageView bow = new ImageView(new Image(PropertyMenu.class.getResource("/weapons/bow.png").toExternalForm()));
        gridPane.setVgap(10);
        bow.setFitWidth(100);
        bow.setFitHeight(100);
        gridPane.add(bow, i, j);
        bow.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Bow");
            buyPrice.setText("buy: " + WeaponType.getBuyPrice(WeaponType.BOW) + "");
            sellPrice.setText("sell: " + WeaponType.getSellPrice(WeaponType.BOW) + "");
        });
    }

    public static void addCrossBow(GridPane gridPane, int i, int j) {
        ImageView crossBow = new ImageView(new Image(PropertyMenu.class.getResource("/weapons/crossbow.jpg").toExternalForm()));
        gridPane.setVgap(10);
        crossBow.setFitWidth(100);
        crossBow.setFitHeight(100);
        gridPane.add(crossBow, i,j);
        crossBow.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("CrossBow");
            buyPrice.setText("buy: " + WeaponType.getBuyPrice(WeaponType.CROSS_BOW) + "");
            sellPrice.setText("sell: " + WeaponType.getSellPrice(WeaponType.CROSS_BOW) + "");
        });
    }

    public static void addSpear(GridPane gridPane, int i, int j) {
        ImageView spear = new ImageView(new Image(PropertyMenu.class.getResource("/weapons/spear.png").toExternalForm()));
        gridPane.setVgap(10);
        spear.setFitWidth(100);
        spear.setFitHeight(100);
        gridPane.add(spear, i,j);
        spear.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Spear");
            buyPrice.setText("buy: " + WeaponType.getBuyPrice(WeaponType.SPEAR) + "");
            sellPrice.setText("sell: " + WeaponType.getSellPrice(WeaponType.SPEAR) + "");
        });
    }

    public static void addPike(GridPane gridPane, int i, int j) {
        ImageView pike = new ImageView(new Image(PropertyMenu.class.getResource("/weapons/pike.png").toExternalForm()));
        gridPane.setVgap(10);
        pike.setFitWidth(100);
        pike.setFitHeight(100);
        gridPane.add(pike, i,j);
        pike.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Pike");
            buyPrice.setText("buy: " + WeaponType.getBuyPrice(WeaponType.PIKE) + "");
            sellPrice.setText("sell: " + WeaponType.getSellPrice(WeaponType.PIKE) + "");
        });
    }

    public static void addMace(GridPane gridPane, int i, int j) {
        ImageView mace = new ImageView(new Image(PropertyMenu.class.getResource("/weapons/mace.png").toExternalForm()));
        gridPane.setVgap(10);
        mace.setFitWidth(100);
        mace.setFitHeight(100);
        gridPane.add(mace, i,j);
        mace.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Mace");
            buyPrice.setText("buy: " + WeaponType.getBuyPrice(WeaponType.MACE) + "");
            sellPrice.setText("sell: " + WeaponType.getSellPrice(WeaponType.MACE) + "");
        });
    }

    public static void addSword(GridPane gridPane, int i, int j) {
        ImageView sword = new ImageView(new Image(PropertyMenu.class.getResource("/weapons/sword.png").toExternalForm()));
        gridPane.setVgap(10);
        sword.setFitWidth(100);
        sword.setFitHeight(100);
        gridPane.add(sword, i,j);
        sword.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Sword");
            buyPrice.setText("buy: " + WeaponType.getBuyPrice(WeaponType.SWORD) + "");
            sellPrice.setText("sell: " + WeaponType.getSellPrice(WeaponType.SWORD) + "");
        });
    }

    public static void addPetroleum(GridPane gridPane, int i, int j) {
        ImageView petroleum = new ImageView(new Image(PropertyMenu.class.getResource("/weapons/petroleum.png").toExternalForm()));
        gridPane.setVgap(10);
        petroleum.setFitWidth(100);
        petroleum.setFitHeight(100);
        gridPane.add(petroleum, i,j);
        petroleum.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Petroleum");
            buyPrice.setText("buy: " + WeaponType.getBuyPrice(WeaponType.PETROLEUM) + "");
            sellPrice.setText("sell: " + WeaponType.getSellPrice(WeaponType.PETROLEUM) + "");
        });
    }

    public static void addWheat(GridPane gridPane, int i, int j) {
        ImageView wheat = new ImageView(new Image(PropertyMenu.class.getResource("/resources/wheat.png").toExternalForm()));
        gridPane.setVgap(10);
        wheat.setFitWidth(100);
        wheat.setFitHeight(100);
        gridPane.add(wheat, i,j);
        wheat.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Wheat");
            buyPrice.setText("buy: " + ResourceType.getBuyPrice(ResourceType.WHEAT) + "");
            sellPrice.setText("sell: " + ResourceType.getSellPrice(ResourceType.WHEAT) + "");
        });
    }

    public static void addFlour(GridPane gridPane, int i, int j) {
        ImageView flour = new ImageView(new Image(PropertyMenu.class.getResource("/resources/flour.png").toExternalForm()));
        gridPane.setVgap(10);
        flour.setFitWidth(100);
        flour.setFitHeight(100);
        gridPane.add(flour, i,j);
        flour.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Flour");
            buyPrice.setText("buy: " + ResourceType.getBuyPrice(ResourceType.FLOUR) + "");
            sellPrice.setText("sell: " + ResourceType.getSellPrice(ResourceType.FLOUR) + "");
        });
    }

    public static void addBarley(GridPane gridPane, int i, int j) {
        ImageView barley = new ImageView(new Image(PropertyMenu.class.getResource("/resources/barley.png").toExternalForm()));
        gridPane.setVgap(10);
        barley.setFitWidth(100);
        barley.setFitHeight(100);
        gridPane.add(barley, i,j);
        barley.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Barely");
            buyPrice.setText("buy: " + ResourceType.getBuyPrice(ResourceType.BARELY) + "");
            sellPrice.setText("sell: " + ResourceType.getSellPrice(ResourceType.BARELY) + "");
        });
    }

    public static void addAle(GridPane gridPane, int i, int j) {
        ImageView ale = new ImageView(new Image(PropertyMenu.class.getResource("/resources/ale.jpg").toExternalForm()));
        gridPane.setVgap(10);
        ale.setFitWidth(100);
        ale.setFitHeight(100);
        gridPane.add(ale, i,j);
        ale.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Ale");
            buyPrice.setText("buy: " + ResourceType.getBuyPrice(ResourceType.ALE) + "");
            sellPrice.setText("sell: " + ResourceType.getSellPrice(ResourceType.ALE) + "");
        });
    }

    public static void addStone(GridPane gridPane, int i, int j) {
        ImageView stone = new ImageView(new Image(PropertyMenu.class.getResource("/resources/stone.png").toExternalForm()));
        gridPane.setVgap(10);
        stone.setFitWidth(100);
        stone.setFitHeight(100);
        gridPane.add(stone, i,j);
        stone.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Stone");
            buyPrice.setText("buy: " + ResourceType.getBuyPrice(ResourceType.STONE) + "");
            sellPrice.setText("sell: " + ResourceType.getSellPrice(ResourceType.STONE) + "");
        });
    }

    public static void addIron(GridPane gridPane, int i, int j) {
        ImageView iron = new ImageView(new Image(PropertyMenu.class.getResource("/resources/iron.png").toExternalForm()));
        gridPane.setVgap(10);
        iron.setFitWidth(100);
        iron.setFitHeight(100);
        gridPane.add(iron, i, j);
        iron.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Iron");
            buyPrice.setText("buy: " + ResourceType.getBuyPrice(ResourceType.IRON) + "");
            sellPrice.setText("sell: " + ResourceType.getSellPrice(ResourceType.IRON) + "");
        });
    }

    public static void addWood(GridPane gridPane, int i, int j) {
        ImageView wood = new ImageView(new Image(PropertyMenu.class.getResource("/resources/wood.png").toExternalForm()));
        gridPane.setVgap(10);
        wood.setFitWidth(100);
        wood.setFitHeight(100);
        gridPane.add(wood, i,j);
        wood.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Wood");
            buyPrice.setText("buy: " + ResourceType.getBuyPrice(ResourceType.WOOD) + "");
            sellPrice.setText("sell: " + ResourceType.getSellPrice(ResourceType.WOOD) + "");
        });
    }

    public static void addPitch(GridPane gridPane, int i, int j) {
        ImageView pitch = new ImageView(new Image(PropertyMenu.class.getResource("/resources/pitch.jpg").toExternalForm()));
        gridPane.setVgap(10);
        pitch.setFitWidth(100);
        pitch.setFitHeight(100);
        gridPane.add(pitch, i,j);
        pitch.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Pitch");
            buyPrice.setText("buy: " + ResourceType.getBuyPrice(ResourceType.PITCH) + "");
            sellPrice.setText("sell: " + ResourceType.getSellPrice(ResourceType.PITCH) + "");
        });
    }

    public static void addLeatherArmour(GridPane gridPane, int i, int j) {
        ImageView leatherArmour = new ImageView(new Image(PropertyMenu.class.getResource("/armours/leather_armour.png").toExternalForm()));
        gridPane.setVgap(10);
        leatherArmour.setFitWidth(100);
        leatherArmour.setFitHeight(100);
        gridPane.add(leatherArmour, i, j);
        leatherArmour.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Leather Armour");
            buyPrice.setText("buy: " + DefenseType.getBuyPrice(DefenseType.LEATHER_ARMOR) + "");
            sellPrice.setText("sell: " + DefenseType.getSellPrice(DefenseType.LEATHER_ARMOR) + "");
        });
    }

    public static void addMetalArmour(GridPane gridPane, int i, int j) {
        ImageView metalArmour = new ImageView(new Image(PropertyMenu.class.getResource("/armours/metal_armour.png").toExternalForm()));
        gridPane.setVgap(10);
        metalArmour.setFitWidth(100);
        metalArmour.setFitHeight(100);
        gridPane.add(metalArmour, i,j);
        metalArmour.setOnMouseClicked(event -> {
            amount.textProperty().set("0");
            property.textProperty().set("Metal Armour");
            buyPrice.setText("buy: " + DefenseType.getBuyPrice(DefenseType.METAL_ARMOR) + "");
            sellPrice.setText("sell: " + DefenseType.getSellPrice(DefenseType.METAL_ARMOR) + "");
        });
    }

    public static Button getBack(Stage stage) {
        Button back = new Button("Back");
        back.setOnMouseClicked(event -> {
            try {
                new ShopMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return back;
    }
}
