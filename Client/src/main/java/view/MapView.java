package view;

import Enums.BuildingImages;
import Enums.Tree;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import Enums.BuildingType;
import model.people.UnitType;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MapView extends Application {
    private ScrollPane unitScrollPane;

    private Pane soldierMode;
    private Text detailText;

    public Text populationText;
    public Text coinText;
    private Text secondPopularityText;
    private VBox textVBox;
    private VBox buttomVBox;

    public double stageWidth;
    public double stageHeight;

    public HBox barMenu;
    public GridPane miniMap;
    public HBox buildingSelection;
    public Circle church;
    public Circle resourceBuilding;
    public Circle foodBuilding;
    public Circle militaryBuilding;
    public Circle buildBuilding;

    public ArrayList<Pane> selectedPain = new ArrayList<>();
    public Stage stage;

    public ScrollPane scrollPane;
    public Pane pane;

    public GridPane showMap;
    private BuildingType selectedBuilding = null;
    private Scene scene;
    private MapViewController viewController;

    public MapView(MapViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setFullScreen(true);
        Screen screen = Screen.getPrimary();
        stageWidth = screen.getBounds().getWidth();
        stageHeight = screen.getBounds().getHeight();
        stage.setWidth(stageWidth);
        stage.setHeight(stageHeight);


        pane = new Pane();
        showMap = new GridPane();
        scrollPane = new ScrollPane();
        pane.setPrefSize(stageWidth, stageHeight);
        showMap.setPrefSize(30 * viewController.getCurrentMapDimension(),
                30 * viewController.getCurrentMapDimension());
        scrollPane.setPrefSize(stageWidth, stageHeight * 4 / 5);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        showMap.setGridLinesVisible(true);

        createCell();
        createBarMenu();
        setUnitScrollPane();
//        zoomHandler();
        selectCellsHandler();


        buildingSelection = new HBox();
        buildingSelection.setSpacing(30);
        buildingSelection.setPrefHeight(100);
        buildingSelection.setPrefWidth(978);
        initBuilding(buildingSelection);
        church = new Circle(12);
        foodBuilding = new Circle(12);
        buildBuilding = new Circle(12);
        resourceBuilding = new Circle(12);
        militaryBuilding = new Circle(12);
        ScrollPane buildMenu = new ScrollPane();
        buildMenu.setMaxHeight(182);
        buildMenu.setMinWidth(500);
        buildMenu.setMaxWidth(300);
        buildMenu.setContent(buildingSelection);

        church.setFill(new ImagePattern(new Image(EnterMenu.class.getResource("/images/churchSym.jpeg").toExternalForm())));
        foodBuilding.setFill(new ImagePattern(new Image(EnterMenu.class.getResource("/images/farming.png").toExternalForm())));
        buildBuilding.setFill(new ImagePattern(new Image(EnterMenu.class.getResource("/images/house.png").toExternalForm())));
        resourceBuilding.setFill(new ImagePattern(new Image(EnterMenu.class.getResource("/images/resourceSym.png").toExternalForm())));
        militaryBuilding.setFill(new ImagePattern(new Image(EnterMenu.class.getResource("/images/championcaribs.png").toExternalForm())));
        church.setOnMouseClicked(this::clickChurch);
        foodBuilding.setOnMouseClicked(this::clickFoodBuilding);
        buildBuilding.setOnMouseClicked(this::clickBuildBuilding);
        resourceBuilding.setOnMouseClicked(this::clickResource);
        militaryBuilding.setOnMouseClicked(this::clickMilitary);
        VBox vBox = new VBox();
        vBox.getChildren().add(church);
        vBox.getChildren().add(foodBuilding);
        vBox.getChildren().add(buildBuilding);
        vBox.getChildren().add(resourceBuilding);
        vBox.getChildren().add(militaryBuilding);
        barMenu.getChildren().add(buildMenu);
        barMenu.getChildren().add(vBox);
        barMenu.getChildren().add(miniMap);
        barMenu.getChildren().add(buttomVBox);
        barMenu.getChildren().add(textVBox);
        barMenu.getChildren().add(soldierMode);
        barMenu.getChildren().add(detailText);
        barMenu.getChildren().add(unitScrollPane);


        scrollPane.setContent(showMap);
        scene = new Scene(pane);
        zoomHandler();
        stage.setScene(scene);
        stage.show();


        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.C) && keyEvent.isControlDown()) {
                if (selectedPain != null && selectedPain.size() == 1) {
                    StringSelection stringSelection = new StringSelection(viewController.getPaneBuildingName(selectedPain.get(0)));
                    java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }
            } else if (keyEvent.getCode().equals(KeyCode.V) && keyEvent.isControlDown()) {
                if (selectedPain != null && selectedPain.size() == 1) {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable transferable = clipboard.getContents(null);
                    if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        try {
                            (new Alert(Alert.AlertType.INFORMATION,
                                    viewController.getDropBuildingAlert(viewController.getCellXCoordinateByPane(selectedPain.get(0)),
                                            viewController.getCellYCoordinateByPane(selectedPain.get(0)), viewController.getPaneBuildingName(pane)))).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (keyEvent.getCode().equals(KeyCode.A)) {
                if (selectedPain != null) {
                    for (Pane pane1 : selectedPain) {
                        viewController.setBuildingNullByPane(pane1);
                        viewController.minusNumberOfWorkers();
                    }
                }
            } else if (keyEvent.getCode().equals(KeyCode.B)) {
                try {
                    viewController.goToPauseMenu(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


//    private void initMiniMap() {
//        tiles = Game.getMapInGame().getMap();
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < 100; j++) {
//                ImageView imageView = new ImageView(tiles[j][i].getImage());
//                imageView.setFitHeight(0.8);
//                imageView.setFitWidth(0.8);
//                miniMap.add(imageView, j, i);
//            }
//        }
//    }

    private void selectCellsHandler() {
        AtomicReference<Double> firstX = new AtomicReference<>((double) 0);
        AtomicReference<Double> firstY = new AtomicReference<>((double) 0);
        showMap.setOnMousePressed(mouseEvent -> {
            resetSelected();
            firstX.set(mouseEvent.getX());
            firstY.set(mouseEvent.getY());
//            System.out.println(x + " " + y);
//            for (Node child : showMap.getChildren()) {
//                if(child instanceof Pane){
//                    Bounds bounds = child.getBoundsInParent();
//                    if(bounds.intersects(mouseEvent.getX(), mouseEvent.getY(), 0, 0)){
//                        selectedPain.add((Pane) child);
//                    }
//                }
//            }
        });

//        AtomicReference<Rectangle> rectangle = new AtomicReference<Rectangle>();
//        showMap.setOnMouseDragged(mouseEvent -> {
//            double x = mouseEvent.getX();
//            double y = mouseEvent.getY();
//            rectangle.set(new Rectangle(Math.min(firstX.get(), x), Math.min(firstY.get(), y), Math.abs(x - firstX.get()), Math.abs(y - firstY.get())));
//            rectangle.get().setFill(Color.TRANSPARENT);
//            rectangle.get().setStroke(Color.WHITE);
//            rectangle.get().getStrokeDashArray().setAll(5d, 5d);
//            showMap.getChildren().add(rectangle.get());
//        });

        showMap.setOnMouseReleased(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            Rectangle rect = new Rectangle(Math.min(firstX.get(), x), Math.min(firstY.get(), y), Math.abs(x - firstX.get()), Math.abs(y - firstY.get()));
            rect.setFill(Color.TRANSPARENT);
            for (Node child : showMap.getChildren()) {
                if (child instanceof Pane) {
                    Bounds bounds = child.getBoundsInParent();
                    if (bounds.intersects(rect.getBoundsInParent())) {
                        Pane pane = (Pane) child;
                        selectedPain.add((Pane) child);
//                        viewController.getCellImageByPane(pane).setOpacity(0.5);
//                        BorderStroke borderStroke = new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2));
//                        Rectangle clip = new Rectangle();
//                        clip.setWidth(pane.getWidth());
//                        clip.setHeight(pane.getHeight());
//                        clip.setLayoutX(pane.getLayoutX());
//                        clip.setLayoutY(pane.getLayoutY());
//                        clip.setFill(Color.TRANSPARENT);
//                        pane.setClip(clip);
//                        pane.setBorder(new Border(borderStroke));
//                        System.out.println("salam1");
//                        getCellByPane((Pane)child).getImageView().setStyle("-fx-border-color: blue;");
                    }
                }
            }

        });
    }

    private void resetSelected() {
        for (Pane pane1 : selectedPain) {
//            viewController.getCellImageByPane(pane1).setOpacity(1);
        }
        selectedPain.clear();
    }

    private void zoomHandler() {
        AtomicReference<Double> scale = new AtomicReference<>(1.0);
        AtomicReference<Double> translateX = new AtomicReference<>(0.0);
        AtomicReference<Double> translateY = new AtomicReference<>(0.0);

        // Zoom in
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().getName().equals("Up") && keyEvent.isShiftDown() && scale.get() < 1.5) {
                    scale.updateAndGet(v -> (double) (v * 1.1));
                    translateX.updateAndGet(v -> (double) (v + showMap.getBoundsInParent().getWidth() / 20 / scale.get()));
                    translateY.updateAndGet(v -> (double) (v + showMap.getBoundsInParent().getHeight() / 20 / scale.get()));
                    showMap.setScaleX(scale.get());
                    showMap.setScaleY(scale.get());
                    showMap.setTranslateX(translateX.get());
                    showMap.setTranslateY(translateY.get());

                    // Get the current viewport of the scroll pane
                    Bounds viewportBounds = scrollPane.getViewportBounds();
                    double contentWidth = showMap.getBoundsInParent().getWidth();
                    double contentHeight = showMap.getBoundsInParent().getHeight();

                    // Calculate the new scroll position based on the current zoom level
                    double hValue = (translateX.get() + viewportBounds.getWidth() / 2) / (contentWidth * scale.get());
                    double vValue = (translateY.get() + viewportBounds.getHeight() / 2) / (contentHeight * scale.get());

                    // Set the new scroll position and content
                    scrollPane.setHvalue(hValue);
                    scrollPane.setVvalue(vValue);
                    scrollPane.setContent(showMap);
                } else if (keyEvent.getCode().getName().equals("Down") && keyEvent.isShiftDown() && scale.get() > 0.7) {
                    scale.updateAndGet(v -> (double) (v / 1.1));
                    translateX.updateAndGet(v -> (double) (v - showMap.getBoundsInParent().getWidth() / 20 / scale.get()));
                    translateY.updateAndGet(v -> (double) (v - showMap.getBoundsInParent().getHeight() / 20 / scale.get()));
                    showMap.setScaleX(scale.get());
                    showMap.setScaleY(scale.get());
                    showMap.setTranslateX(translateX.get());
                    showMap.setTranslateY(translateY.get());

                    // Get the current viewport of the scroll pane
                    Bounds viewportBounds = scrollPane.getViewportBounds();
                    double contentWidth = showMap.getBoundsInParent().getWidth();
                    double contentHeight = showMap.getBoundsInParent().getHeight();

                    // Calculate the new scroll position based on the current zoom level
                    double hValue = (translateX.get() + viewportBounds.getWidth() / 2) / (contentWidth * scale.get());
                    double vValue = (translateY.get() + viewportBounds.getHeight() / 2) / (contentHeight * scale.get());

                    // Set the new scroll position and content
                    scrollPane.setHvalue(hValue);
                    scrollPane.setVvalue(vValue);
                    scrollPane.setContent(showMap);
                }
            }
        });
    }

    private void createBarMenu() {
        soldierMode = new Pane();
        barMenu = new HBox();
        barMenu.setSpacing(20);
        miniMap = new GridPane();
        VBox vBox = new VBox();
        vBox.getChildren().add(scrollPane);
        vBox.getChildren().add(barMenu);
        pane.getChildren().add(vBox);
        setMapBar();
    }


    private void createCell() {
        for (int i = 0; i < viewController.getCurrentMapDimension(); i++) {
            for (int j = 0; j < viewController.getCurrentMapDimension(); j++) {
                Pane cell = viewController.getCellPane(i, j);
                showMap.add(cell, i, j);
                dragAndDrop(cell);
                dragEntered(cell);
                dragExited(cell);
                dragOver(cell);
                selectEvent(cell, i, j);
                Tooltip cellTooltip = new Tooltip(viewController.getCellDetail(i, j));
                setTooltipOnHover(cellTooltip, cell, i, j);
                Tooltip.install(cell, cellTooltip);
            }
        }
    }

    private void setTooltipOnHover(Tooltip cellTooltip, Pane cell, int i, int j) {
        cell.setOnMouseEntered(mouseEvent -> {
            cellTooltip.setText(viewController.getCellDetail(i, j));
            resetDetailText();
            resetPopulationText();
            resetCoinText();
            resetSecondPopularityText();
        });
    }

    private void selectEvent(Pane cell, int i, int j) {
        cell.setOnMouseClicked(mouseEvent -> {
            if (selectedBuilding != null) {
                (new Alert(Alert.AlertType.INFORMATION,
                        viewController.getDropBuildingAlert(i, j, selectedBuilding.getBuildingName()))).show();
                mouseEvent.consume();
            }
            selectedBuilding = null;
        });
    }

    private void createMiniMap() {
        for (int i = 0; i < viewController.getCurrentMapDimension(); i++) {
            for (int j = 0; j < viewController.getCurrentMapDimension(); j++) {
                ImageView imageView = new ImageView(viewController.getCellImage(i, j));
                imageView.setFitWidth(1);
                imageView.setFitHeight(1);
                miniMap.add(imageView, i, j);
            }
        }
    }

    private void setMapBar() {
        setPopulationText();
        setCoinText();
        setSecondPopularityText();
        setDetailText();
        createMiniMap();
        textVBox = new VBox(populationText, coinText, secondPopularityText);
        textVBox.setSpacing(10);
        Circle undoButton = new Circle(12);
        Circle deleteButton = new Circle(12);
        Circle briefingButton = new Circle(12);
        Circle optionButton = new Circle(12);
        undoButton.setFill(new ImagePattern(
                new Image(MapView.class.getResource("/images/chatBack.png").toExternalForm())));
        deleteButton.setFill(new ImagePattern(
                new Image(MapView.class.getResource("/images/blackCross.png").toExternalForm())));
        briefingButton.setFill(new ImagePattern(
                new Image(MapView.class.getResource("/images/search.png").toExternalForm())));
        optionButton.setFill(new ImagePattern(
                new Image(MapView.class.getResource("/images/settings.png").toExternalForm())));
        buttomVBox = new VBox();
        buttomVBox.setSpacing(10);
        buttomVBox.getChildren().addAll(undoButton, deleteButton, briefingButton, optionButton);

        deleteButton.setOnMouseClicked(mouseEvent -> {
            if (selectedPain != null) {
                for (Pane pane1 : selectedPain) {
                    viewController.setBuildingNullByPane(pane1);
                    viewController.minusNumberOfWorkers();
                }
            }
        });

        briefingButton.setOnMouseClicked(mouseEvent -> {
            Tooltip tooltip = new Tooltip(viewController.getGameBriefing());
            Tooltip.install(briefingButton, tooltip);
        });
        optionButton.setOnMouseClicked(mouseEvent -> {
            try {
                viewController.goToPauseMenu(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setDetailText() {
        detailText = new Text(viewController.getDetailText(selectedPain));
        detailText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        detailText.setFill(Color.BLUE);
    }

    private void resetDetailText() {
        detailText.setText(viewController.getDetailText(selectedPain));
    }

    private void setPopulationText() {
        populationText = new Text(String.format("popularity / population = %d/%d",
                viewController.getNumberOfWorkers(), viewController.getCurrentKingdomPopulation()));
        populationText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        populationText.setFill(Color.GREEN);
    }

    private void setCoinText() {
        coinText = new Text(String.format("gold = %.0f", viewController.getCurrentKingdomGold()));
        coinText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        coinText.setFill(Color.RED);
    }

    private void setSecondPopularityText() {
        secondPopularityText = new Text(String.format("scribes report = %d", viewController.getCurrentKingdomPopularity()));
        secondPopularityText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        secondPopularityText.setFill(Color.GREEN);
    }

    private void resetSecondPopularityText() {
        secondPopularityText.setText(String.format("scribes report = %d", viewController.getCurrentKingdomPopularity()));
    }

    private void resetPopulationText() {
        populationText.setText(String.format("popularity / population = %d/%d", viewController.getNumberOfWorkers(),
                viewController.getCurrentKingdomPopulation()));
    }

    private void resetCoinText() {
        coinText.setText(String.format("gold = %.0f", viewController.getCurrentKingdomGold()));
    }

    private void handleDragBuilding(MouseEvent mouseEvent) {
        ImageView source = (ImageView) mouseEvent.getSource();
        Dragboard dragboard = source.startDragAndDrop(TransferMode.COPY);


        ClipboardContent content = new ClipboardContent();
        content.putString(source.getImage().getUrl());
        dragboard.setContent(content);
        ImageView draggedContent = new ImageView(source.getImage());
        draggedContent.setFitWidth(source.getFitWidth());
        draggedContent.setFitHeight(source.getFitWidth());
        dragboard.setDragView(draggedContent.snapshot(null, null));

        mouseEvent.consume();
    }

    private void initBuilding(HBox building) {
        for (Image image : BuildingImages.getMilitaryBuilding().keySet()) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            Tooltip tooltip = new Tooltip(BuildingImages.getMilitaryBuilding().get(image));
            Tooltip.install(imageView, tooltip);
            building.getChildren().add(imageView);
            imageView.setOnMouseClicked(mouseEvent -> {
                String buildingName = BuildingImages.getNameOfBuildingByImage(image.getUrl());
                this.selectedBuilding = BuildingImages.getBuildingTypeByName(buildingName);
            });
        }
    }


    public void clickMilitary(MouseEvent mouseEvent) {
        buildingSelection.getChildren().clear();
        for (Image image : BuildingImages.getMilitaryBuilding().keySet()) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setOnDragDetected(this::handleDragBuilding);
            Tooltip tooltip = new Tooltip(BuildingImages.getMilitaryBuilding().get(image));
            Tooltip.install(imageView, tooltip);
            buildingSelection.getChildren().add(imageView);
            imageView.setOnMouseClicked(mouseEvent1 -> {
                String buildingName = BuildingImages.getNameOfBuildingByImage(image.getUrl());
                this.selectedBuilding = BuildingImages.getBuildingTypeByName(buildingName);
            });
        }
    }

    public void clickBuildBuilding(MouseEvent mouseEvent) {
        buildingSelection.getChildren().clear();
        for (Image image : BuildingImages.getBuildBuilding().keySet()) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setOnDragDetected(this::handleDragBuilding);
            Tooltip tooltip = new Tooltip(BuildingImages.getBuildBuilding().get(image));
            Tooltip.install(imageView, tooltip);
            buildingSelection.getChildren().add(imageView);
            imageView.setOnMouseClicked(mouseEvent1 -> {
                String buildingName = BuildingImages.getNameOfBuildingByImage(image.getUrl());
                this.selectedBuilding = BuildingImages.getBuildingTypeByName(buildingName);
            });
        }
    }

    public void clickFoodBuilding(MouseEvent mouseEvent) {
        buildingSelection.getChildren().clear();
        for (Image image : BuildingImages.getFoodBuilding().keySet()) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setOnDragDetected(this::handleDragBuilding);
            Tooltip tooltip = new Tooltip(BuildingImages.getFoodBuilding().get(image));
            Tooltip.install(imageView, tooltip);
            buildingSelection.getChildren().add(imageView);
            imageView.setOnMouseClicked(mouseEvent1 -> {
                String buildingName = BuildingImages.getNameOfBuildingByImage(image.getUrl());
                this.selectedBuilding = BuildingImages.getBuildingTypeByName(buildingName);
            });
        }
    }

    public void clickResource(MouseEvent mouseEvent) {
        buildingSelection.getChildren().clear();
        for (Image image : BuildingImages.getSourceBuilding().keySet()) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setOnDragDetected(this::handleDragBuilding);
            Tooltip tooltip = new Tooltip(BuildingImages.getSourceBuilding().get(image));
            Tooltip.install(imageView, tooltip);
            buildingSelection.getChildren().add(imageView);
            imageView.setOnMouseClicked(mouseEvent1 -> {
                String buildingName = BuildingImages.getNameOfBuildingByImage(image.getUrl());
                this.selectedBuilding = BuildingImages.getBuildingTypeByName(buildingName);
            });
        }
    }

    public void clickChurch(MouseEvent mouseEvent) {
        buildingSelection.getChildren().clear();
        for (Image image : BuildingImages.getChurches().keySet()) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setOnDragDetected(this::handleDragBuilding);
            Tooltip tooltip = new Tooltip(BuildingImages.getChurches().get(image));
            Tooltip.install(imageView, tooltip);
            buildingSelection.getChildren().add(imageView);
            imageView.setOnMouseClicked(mouseEvent1 -> {
                String buildingName = BuildingImages.getNameOfBuildingByImage(image.getUrl());
                this.selectedBuilding = BuildingImages.getBuildingTypeByName(buildingName);
            });
        }
    }

    private void dragEntered(Pane pane) {
        pane.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getDragboard().hasString()) {
                    pane.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                }
            }
        });
    }

    private void dragExited(Pane pane) {
        pane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getDragboard().hasString())
                    pane.setStyle("");
            }
        });
    }

    private void dragOver(Pane pane) {
        pane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getDragboard().hasString())
                    dragEvent.acceptTransferModes(TransferMode.COPY);

            }
        });
    }


    private void dragAndDrop(Pane thisPane) {
        thisPane.setOnDragDropped(dragEvent -> {
            Dragboard dragboard = dragEvent.getDragboard();
            dragEvent.setDropCompleted(true);
            if (dragboard.hasString()) {
                String buildingName = BuildingImages.getBuildingTypeByName(
                        BuildingImages.getNameOfBuildingByImage(dragboard.getString())).getBuildingName();

                (new Alert(Alert.AlertType.INFORMATION,
                        viewController.getDropBuildingAlert(viewController.getCellXCoordinateByPane(pane),
                                viewController.getCellYCoordinateByPane(pane),
                                buildingName))).show();
            }

        });
    }

    public Pane createPopUp() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        GridPane pane = new GridPane();
        Text food = new Text();
        food.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if (viewController.getCurrentKingdomFoodRate() > 0) {
            food.setText("food: " + "+" + viewController.getCurrentKingdomFoodRate() + " :)");
            food.setFill(Color.GREEN);
        } else {
            food.setText("food: " + viewController.getCurrentKingdomFoodRate() + " :(");
            food.setFill(Color.RED);
        }
        hBox.getChildren().add(food);
        Text tax = new Text();
        tax.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if (viewController.getCurrentKingdomTaxRate() > 0) {
            tax.setText("tax: " + "+" + viewController.getCurrentKingdomTaxRate() + " :)");
            tax.setFill(Color.GREEN);
        } else {
            tax.setText("tax: " + viewController.getCurrentKingdomTaxRate() + " :(");
            tax.setFill(Color.RED);
        }
        hBox.getChildren().add(tax);
        Text religion = new Text();
        religion.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");

        if (viewController.getCurrentKingdomReligiousPeople() > 0) {
            religion.setText("religion: " + "+" + viewController.getCurrentKingdomReligiousPeople() + " :)");
            religion.setFill(Color.GREEN);
        } else {
            religion.setText("religion: " + viewController.getCurrentKingdomReligiousPeople() + " :(");
            religion.setFill(Color.RED);
        }
        hBox.getChildren().add(religion);
        Text population = new Text();
        population.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if (viewController.getCurrentKingdomPopulation() > 0) {
            population.setText("population: " + "+" + viewController.getCurrentKingdomPopulation() + " :)");
            population.setFill(Color.GREEN);
        } else {
            population.setText("population: " + viewController.getCurrentKingdomPopulation() + " :(");
            population.setFill(Color.RED);
        }
        hBox.getChildren().add(population);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().add(hBox);
        HBox hBox1 = new HBox();
        Text fear = new Text();
        fear.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if (viewController.getCurrentKingdomFearRate() > 0) {
            fear.setText("fear: " + "+" + viewController.getCurrentKingdomFearRate() + " :)");
            fear.setFill(Color.GREEN);
        } else {
            fear.setText("fear: " + viewController.getCurrentKingdomFearRate() + " :(");
            fear.setFill(Color.RED);
        }
        Slider slider = new Slider();
        slider.setMin(-5);
        slider.setMax(5);
        slider.setValue(0);

        hBox1.getChildren().add(fear);
        hBox1.getChildren().add(slider);
        vbox.getChildren().add(hBox1);
        pane.getChildren().add(vbox);

        return pane;
    }

    public void repairBuilding() {
        String respond = viewController.getBuildingControllerRepairText();
        Alert alert;
        if (respond.equals("this building is repaired!")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("this building is repaired!");
            alert.show();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(respond);
            alert.show();
        }
    }

    public void createUnit() {

    }

    private void setUnitScrollPane() {
        for (UnitType unitType : UnitType.values()) {
            HBox unitHBox = new HBox();
            unitHBox.setSpacing(10);
            ImageView imageView = new ImageView(unitType.getImage());
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            unitHBox.getChildren().add(imageView);
            unitScrollPane.setContent(unitHBox);

            Tooltip tooltip = new Tooltip(unitType.name());
            Tooltip.install(imageView, tooltip);
        }
    }
}