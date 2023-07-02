package view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import model.Kingdom;
import model.User;
import model.map.Cell;

public class test extends Application {
    public Text sum;
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = createPopUp();
        pane.setPrefSize(800, 800);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public Pane createPopUp(){
        HBox hBox = new HBox();
        hBox.setSpacing(10);
//        Kingdom kingdom = game.getCurrentKingdom();
        Kingdom kingdom = new Kingdom(new User("mmd", "mmd", "mmd", "mmd", "mmd"), new Cell(10, 10));
        GridPane pane = new GridPane();

        Text food = makeFoodText(kingdom);
        hBox.getChildren().add(food);
        plusMinusImage(kingdom, hBox, food, 0);


        Text tax = makeTaxText(kingdom);
        hBox.getChildren().add(tax);
        plusMinusImage(kingdom, hBox, tax,1);

        Text religion = makeReligionText(kingdom);
        hBox.getChildren().add(religion);
        plusMinusImage(kingdom, hBox, religion, 2);

        sum = new Text();
        sum.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        sumOfElement(kingdom);


//        Text population = makePopulationText(kingdom);
//        hBox.getChildren().add(population);
//        plusMinusImage(kingdom, hBox, population, 3);

        Text fear = makeFearText(kingdom);
        ImageView image = new ImageView(new Image(getClass().getResource("/Emoji/"+kingdom.getFearRate() + ".png").toExternalForm()));
        image.setFitHeight(30);
        image.setFitWidth(30);
        Slider slider = makeFearSlider(kingdom, fear, image);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().add(hBox);
        HBox hBox1 = new HBox();
        hBox1.getChildren().add(fear);
        hBox1.getChildren().add(slider);
        hBox1.getChildren().add(image);
        vbox.getChildren().add(hBox1);
        vbox.getChildren().add(sum);
        pane.getChildren().add(vbox);

        return pane;
    }
    public Slider makeFearSlider(Kingdom kingdom, Text fear, ImageView image){
        Slider slider = new Slider();
        slider.setMin(-5);
        slider.setMax(5);
        slider.setValue(kingdom.getFearRate());
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                slider.setValue(newValue.intValue());
                kingdom.setFearRate(newValue.intValue());
                sumOfElement(kingdom);
                image.setImage(new Image(getClass().getResource("/Emoji/"+kingdom.getFearRate() + ".png").toExternalForm()));
                if(kingdom.getFearRate() > 0){
                    fear.setText("fear: " + "+" + kingdom.getFearRate() + " :(");
                    fear.setFill(Color.RED);
                }else if (kingdom.getFearRate() == 0) {
                    fear.setText("religion: " + kingdom.getFearRate() + " :|");
                    fear.setFill(Color.ORANGE);
                }else {
                    fear.setText("fear: " + kingdom.getFearRate() + " :)");
                    fear.setFill(Color.GREEN);
                }
            }
        });
        return slider;
    }
    public Text makeFearText(Kingdom kingdom){
        Text fear = new Text();
        fear.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if(kingdom.getFearRate() > 0){
            fear.setText("fear: " + "+" + kingdom.getFearRate() + " :(");
            fear.setFill(Color.RED);
        }else if (kingdom.getFearRate() == 0) {
            fear.setText("religion: " + kingdom.getFearRate() + " :|");
            fear.setFill(Color.ORANGE);
        }else {
            fear.setText("fear: " + kingdom.getFearRate() + " :)");
            fear.setFill(Color.GREEN);
        }
        return fear;
    }
    public Text makeReligionText(Kingdom kingdom){
        Text religion = new Text();
        religion.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");

        if(kingdom.getReligiousPeople() > 0){
            religion.setText("religion: " + "+" + kingdom.getReligiousPeople() + " :)");
            religion.setFill(Color.GREEN);
        }else if (kingdom.getReligiousPeople() == 0) {
            religion.setText("religion: " + kingdom.getReligiousPeople() + " :|");
            religion.setFill(Color.ORANGE);
        }else {
            religion.setText("religion: " + kingdom.getReligiousPeople() + " :(");
            religion.setFill(Color.RED);
        }
        return religion;
    }
    public Text makeTaxText(Kingdom kingdom){
        Text tax = new Text();
        tax.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if(kingdom.getTaxRate() < 0){
            tax.setText("tax: " +  kingdom.getTaxRate() + " :)");
            tax.setFill(Color.GREEN);
        }else if (kingdom.getTaxRate() == 0) {
            tax.setText("tax: " + kingdom.getTaxRate() + " :|");
            tax.setFill(Color.ORANGE);
        }else {
            tax.setText("tax: +" + kingdom.getTaxRate() + " :(");
            tax.setFill(Color.RED);
        }
        return tax;
    }
    public Text makeFoodText(Kingdom kingdom) {
        Text food = new Text();
        food.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if(kingdom.getFoodRate() > 0){
            food.setText("food: " + "+" + kingdom.getFoodRate() + " :)");
            food.setFill(Color.GREEN);
        }else if (kingdom.getFoodRate() == 0) {
            food.setText("food: " + kingdom.getFoodRate() + " :|");
            food.setFill(Color.ORANGE);
        }else {
            food.setText("food: " + kingdom.getFoodRate() + " :(");
            food.setFill(Color.RED);
        }
        return food;
    }
    public void sumOfElement(Kingdom kingdom){
        int popularity = kingdom.getFoodRate() - kingdom.getTaxRate() - kingdom.getFearRate() + kingdom.getReligiousPeople();
        if(popularity > 0){
            sum.setText("In the coming month: " + "+" + popularity + " :)");
            sum.setFill(Color.GREEN);
        } else if (popularity == 0) {
            sum.setText("In the coming month: " + popularity + " :|");
            sum.setFill(Color.ORANGE);
        } else {
            sum.setText("In the coming month: " + popularity + " :(");
            sum.setFill(Color.RED);
        }
    }
    public void plusMinusImage(Kingdom kingdom, HBox hBox, Text text, int i){
        Image plusImage = new Image(getClass().getResource("/Other/plus.png").toString());
        Image minusImage = new Image(getClass().getResource("/Other/minus.jpg").toString());
        ImageView plusView = new ImageView(plusImage);
        ImageView minusView = new ImageView(minusImage);
        plusView.setFitWidth(20);
        plusView.setFitHeight(20);
        minusView.setFitWidth(20);
        minusView.setFitHeight(20);
        switch (i){
            case 0:
                clickFoodRatePlus(plusView, kingdom, text);
                clickFoodRateMinus(minusView, kingdom, text);
                break;
            case 1:
                clickTaxRatePlus(plusView, kingdom, text);
                clickTaxRateMinus(minusView, kingdom, text);
                break;
            case 2:
                clickReligionRatePlus(plusView, kingdom, text);
                clickReligionRateMinus(minusView, kingdom, text);
                break;
        }
        hBox.getChildren().add(plusView);
        hBox.getChildren().add(minusView);
    }
    private void clickReligionRateMinus(ImageView minusView, Kingdom kingdom, Text religion) {
        minusView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                kingdom.setReligiousPeople(kingdom.getReligiousPeople() - 1);
                sumOfElement(kingdom);
                if(kingdom.getReligiousPeople() > 0){
                    religion.setText("religion: " + "+" + kingdom.getReligiousPeople() + " :)");
                    religion.setFill(Color.GREEN);
                }else if (kingdom.getReligiousPeople() == 0) {
                    religion.setText("religion: " + kingdom.getReligiousPeople() + " :|");
                    religion.setFill(Color.ORANGE);
                }else {
                    religion.setText("religion: " + kingdom.getReligiousPeople() + " :(");
                    religion.setFill(Color.RED);
                }
            }
        });
    }
    private void clickReligionRatePlus(ImageView plusView, Kingdom kingdom, Text religion) {
        plusView.setOnMouseClicked(mouseEvent -> {
            kingdom.setReligiousPeople(kingdom.getReligiousPeople() + 1);
            sumOfElement(kingdom);
            if(kingdom.getReligiousPeople() > 0){
                religion.setText("religion: " + "+" + kingdom.getReligiousPeople() + " :)");
                religion.setFill(Color.GREEN);
            }else if (kingdom.getReligiousPeople() == 0) {
                religion.setText("religion: " + kingdom.getReligiousPeople() + " :|");
                religion.setFill(Color.ORANGE);
            }else {
                religion.setText("religion: " + kingdom.getReligiousPeople() + " :(");
                religion.setFill(Color.RED);
            }
        });
    }
    private void clickTaxRateMinus(ImageView minusView, Kingdom kingdom, Text tax) {
        minusView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                kingdom.setTaxRate(kingdom.getTaxRate() - 1);
                sumOfElement(kingdom);
                if(kingdom.getTaxRate() < 0){
                    tax.setText("tax: " + kingdom.getTaxRate() + " :)");
                    tax.setFill(Color.GREEN);
                }else if (kingdom.getTaxRate() == 0) {
                    tax.setText("tax: " + kingdom.getTaxRate() + " :|");
                    tax.setFill(Color.ORANGE);
                }else {
                    tax.setText("tax: +" + kingdom.getTaxRate() + " :(");
                    tax.setFill(Color.RED);
                }
            }
        });
    }
    private void clickTaxRatePlus(ImageView plusView, Kingdom kingdom, Text tax) {
        plusView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                kingdom.setTaxRate(kingdom.getTaxRate() + 1);
                sumOfElement(kingdom);
                if(kingdom.getTaxRate() < 0){
                    tax.setText("tax: " + kingdom.getTaxRate() + " :)");
                    tax.setFill(Color.GREEN);
                }else if (kingdom.getTaxRate() == 0) {
                    tax.setText("tax: " + kingdom.getTaxRate() + " :|");
                    tax.setFill(Color.ORANGE);
                }else {
                    tax.setText("tax: +" + kingdom.getTaxRate() + " :(");
                    tax.setFill(Color.RED);
                }
            }
        });
    }

    private void clickFoodRateMinus(ImageView minus, Kingdom kingdom, Text food) {
        minus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                kingdom.setFoodRate(kingdom.getFoodRate() - 1);
                sumOfElement(kingdom);
                if(kingdom.getFoodRate() > 0){
                    food.setText("food: " + "+" + kingdom.getFoodRate() + " :)");
                    food.setFill(Color.GREEN);
                }else if (kingdom.getFoodRate() == 0) {
                    food.setText("food: " + kingdom.getFoodRate() + " :|");
                    food.setFill(Color.ORANGE);
                }else {
                    food.setText("food: " + kingdom.getFoodRate() + " :(");
                    food.setFill(Color.RED);
                }
            }
        });
    }

    public void clickFoodRatePlus(ImageView plus, Kingdom kingdom, Text food){
        plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                kingdom.setFoodRate(kingdom.getFoodRate() + 1);
                sumOfElement(kingdom);
                if(kingdom.getFoodRate() > 0){
                    food.setText("food: " + "+" + kingdom.getFoodRate() + " :)");
                    food.setFill(Color.GREEN);
                }else if (kingdom.getFoodRate() == 0) {
                    food.setText("food: " + kingdom.getFoodRate() + " :|");
                    food.setFill(Color.ORANGE);
                }else {
                    food.setText("food: " + kingdom.getFoodRate() + " :(");
                    food.setFill(Color.RED);
                }
            }
        });
    }
}
