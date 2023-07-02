package view;

import Enums.BuildingImages;
import controller.GameController.BuildingController;
import controller.GameController.GameMenuController;
import controller.mapmenu.MapMenuController;
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
import model.Building.Building;
import model.Building.BuildingType;
import model.Game;
import model.Kingdom;
import model.User;
import model.map.Cell;
import model.map.Map;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MapView extends Application {

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
    public Map currentMap;
    public Game game;

    public ScrollPane scrollPane;
    public Pane pane;

    public GridPane showMap;
    private BuildingType selectedBuilding = null;
    private Scene scene;
    private BuildingController buildingController;

    public MapView(Game game) {
        this.game = game;
        this.currentMap = game.getCurrentMap();
        buildingController = new BuildingController(game);
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
        showMap.setPrefSize(30 * currentMap.getDimension(), 30 * currentMap.getDimension());
        scrollPane.setPrefSize(stageWidth, stageHeight * 4 / 5);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        showMap.setGridLinesVisible(true);

        createCell();
        createBarMenu();
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

        church.setFill(new ImagePattern(new Image(MapMenu.class.getResource("/images/churchSym.jpeg").toExternalForm())));
        foodBuilding.setFill(new ImagePattern(new Image(MapMenu.class.getResource("/images/farming.png").toExternalForm())));
        buildBuilding.setFill(new ImagePattern(new Image(MapMenu.class.getResource("/images/house.png").toExternalForm())));
        resourceBuilding.setFill(new ImagePattern(new Image(MapMenu.class.getResource("/images/resourceSym.png").toExternalForm())));
        militaryBuilding.setFill(new ImagePattern(new Image(MapMenu.class.getResource("/images/championcaribs.png").toExternalForm())));
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


        scrollPane.setContent(showMap);
        scene = new Scene(pane);
        zoomHandler();
        stage.setScene(scene);
        stage.show();


        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.C) && keyEvent.isControlDown()) {
                if (selectedPain != null && selectedPain.size() == 1) {
                    StringSelection stringSelection = new StringSelection(getCellByPane(selectedPain.get(0)).getBuilding().getBuildingType().getBuildingName());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }
            } else if (keyEvent.getCode().equals(KeyCode.V) && keyEvent.isControlDown()) {
                if (selectedPain != null && selectedPain.size() == 1) {
                    Cell cell = getCellByPane(selectedPain.get(0));
                    GameMenuController controller = new GameMenuController(game);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable transferable = clipboard.getContents(null);
                    if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        try {
                            (new Alert(Alert.AlertType.INFORMATION, controller.dropBuilding(String.valueOf(cell.getxCoordinate()),
                                    String.valueOf(cell.getyCoordinate()), (String) transferable.getTransferData(DataFlavor.stringFlavor)))).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
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
                        getCellByPane(pane).getImage().setOpacity(0.5);
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
            getCellByPane(pane1).getImage().setOpacity(1);
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
        for (int i = 0; i < currentMap.getDimension(); i++) {
            for (int j = 0; j < currentMap.getDimension(); j++) {
                Pane cell = currentMap.getMap()[i][j].getPane();
                showMap.add(cell, i, j);
                dragAndDrop(cell);
                dragEntered(cell);
                dragExited(cell);
                dragOver(cell);
                selectEvent(cell, i, j);
                MapMenuController controller = new MapMenuController(game.getCurrentMap());
                Tooltip cellTooltip = new Tooltip(controller.showDetail(String.valueOf(i + 1), String.valueOf(j + 1)));
                setTooltipOnHover(cellTooltip, cell, controller, i, j);
                Tooltip.install(cell, cellTooltip);
            }
        }
    }

    private void setTooltipOnHover(Tooltip cellTooltip, Pane cell, MapMenuController controller, int i, int j) {
        cell.setOnMouseEntered(mouseEvent -> {
            cellTooltip.setText(controller.showDetail(String.valueOf(i + 1), String.valueOf(j + 1)));
            resetPopulationText();
            resetCoinText();
            resetSecondPopularityText();
            resetDetailText();
        });
    }

    private void selectEvent(Pane cell, int i, int j) {
        cell.setOnMouseClicked(mouseEvent -> {
            if (selectedBuilding != null) {
                (new Alert(Alert.AlertType.INFORMATION,
                        (new GameMenuController(game)).dropBuilding(String.valueOf(i),
                                String.valueOf(j), selectedBuilding.getBuildingName()))).show();
                mouseEvent.consume();
            }
            selectedBuilding = null;
        });
    }

    private void createMiniMap() {
        for (int i = 0; i < currentMap.getDimension(); i++) {
            for (int j = 0; j < currentMap.getDimension(); j++) {
                ImageView imageView = new ImageView(currentMap.getMap()[i][j].getTheImage());
                imageView.setFitWidth(1);
                imageView.setFitHeight(1);
                miniMap.add(imageView, i, j);
            }
        }
    }

    public Cell getCellByPane(Pane pane) {
        for (Cell[] cells : currentMap.getMap()) {
            for (Cell cell : cells) {
                if (cell.getPane() == pane) {
                    return cell;
                }
            }
        }
        return null;
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
                new Image(Cell.class.getResource("/images/chatBack.png").toExternalForm())));
        deleteButton.setFill(new ImagePattern(
                new Image(Cell.class.getResource("/images/blackCross.png").toExternalForm())));
        briefingButton.setFill(new ImagePattern(
                new Image(Cell.class.getResource("/images/search.png").toExternalForm())));
        optionButton.setFill(new ImagePattern(
                new Image(Cell.class.getResource("/images/settings.png").toExternalForm())));
        buttomVBox = new VBox();
        buttomVBox.setSpacing(10);
        buttomVBox.getChildren().addAll(undoButton, deleteButton, briefingButton, optionButton);

        deleteButton.setOnMouseClicked(mouseEvent -> {
            if (selectedPain != null) {
                for (Pane pane1 : selectedPain) {
                    getCellByPane(pane1).setBuilding(null);
                    game.getCurrentKingdom().setNumberOfWorkers(game.getCurrentKingdom().getNumberOfWorkers() - 1);
                }
            }
        });

        briefingButton.setOnMouseClicked(mouseEvent -> {
            String text = "";
            for (Kingdom kingdom : game.getPlayers()) {
                text += "kingdom name = " + kingdom.getOwner().getUsername() + "\n" +
                        "kingdom gold = " + kingdom.getGold() + "\n";
            }
            Tooltip tooltip = new Tooltip(text);
            Tooltip.install(briefingButton, tooltip);
        });
        optionButton.setOnMouseClicked(mouseEvent -> {
            try {
                (new PauseMenu(game, stage)).start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setDetailText() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Pane pane1 : selectedPain)
            cells.add(getCellByPane(pane1));
        detailText = new Text((new MapMenuController(currentMap)).showDetails(cells, game.getCurrentKingdom()));
        detailText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        detailText.setFill(Color.GREEN);
    }
    private void resetDetailText() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (Pane pane1 : selectedPain)
            cells.add(getCellByPane(pane1));
        detailText.setText((new MapMenuController(currentMap)).showDetails(cells, game.getCurrentKingdom()));
    }

    private void setPopulationText() {
        populationText = new Text(String.format("popularity / population = %d/%d",
                game.getCurrentKingdom().getNumberOfWorkers(), game.getCurrentKingdom().getPopulation()));
        populationText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        populationText.setFill(Color.GREEN);
    }

    private void setCoinText() {
        coinText = new Text(String.format("gold = %.0f", game.getCurrentKingdom().getGold()));
        coinText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        coinText.setFill(Color.RED);
    }

    private void setSecondPopularityText() {
        secondPopularityText = new Text(String.format("scribes report = %d", game.getCurrentKingdom().getPopularity()));
        secondPopularityText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        secondPopularityText.setFill(Color.GREEN);
    }

    private void resetSecondPopularityText() {
        secondPopularityText.setText(String.format("scribes report = %d", game.getCurrentKingdom().getPopularity()));
    }

    private void resetPopulationText() {
        populationText.setText(String.format("popularity / population = %d/%d", game.getCurrentKingdom().getNumberOfWorkers(), game.getCurrentKingdom().getPopulation()));
    }

    private void resetCoinText() {
        coinText.setText(String.format("gold = %.0f", game.getCurrentKingdom().getGold()));
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
                Cell cell = getCellByPane(thisPane);
                String buildingName = BuildingImages.getBuildingTypeByName(
                        BuildingImages.getNameOfBuildingByImage(dragboard.getString())).getBuildingName();

                (new Alert(Alert.AlertType.INFORMATION,
                        (new GameMenuController(game)).dropBuilding(String.valueOf(cell.getxCoordinate()),
                                String.valueOf(cell.getyCoordinate()),
                                buildingName))).show();
            }

        });
    }

    public Pane createPopUp() {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
//        Kingdom kingdom = game.getCurrentKingdom();
        Kingdom kingdom = new Kingdom(new User("mmd", "mmd", "mmd", "mmd", "mmd"), new Cell(10, 10));
        GridPane pane = new GridPane();
        Text food = new Text();
        food.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if (kingdom.getFoodRate() > 0) {
            food.setText("food: " + "+" + kingdom.getFoodRate() + " :)");
            food.setFill(Color.GREEN);
        } else {
            food.setText("food: " + kingdom.getFoodRate() + " :(");
            food.setFill(Color.RED);
        }
        hBox.getChildren().add(food);
        Text tax = new Text();
        tax.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if (kingdom.getTaxRate() > 0) {
            tax.setText("tax: " + "+" + kingdom.getTaxRate() + " :)");
            tax.setFill(Color.GREEN);
        } else {
            tax.setText("tax: " + kingdom.getTaxRate() + " :(");
            tax.setFill(Color.RED);
        }
        hBox.getChildren().add(tax);
        Text religion = new Text();
        religion.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");

        if (kingdom.getReligiousPeople() > 0) {
            religion.setText("religion: " + "+" + kingdom.getReligiousPeople() + " :)");
            religion.setFill(Color.GREEN);
        } else {
            religion.setText("religion: " + kingdom.getReligiousPeople() + " :(");
            religion.setFill(Color.RED);
        }
        hBox.getChildren().add(religion);
        Text population = new Text();
        population.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if (kingdom.getPopulation() > 0) {
            population.setText("population: " + "+" + kingdom.getPopulation() + " :)");
            population.setFill(Color.GREEN);
        } else {
            population.setText("population: " + kingdom.getPopulation() + " :(");
            population.setFill(Color.RED);
        }
        hBox.getChildren().add(population);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().add(hBox);
        HBox hBox1 = new HBox();
        Text fear = new Text();
        fear.setStyle("-fx-font-family: Garamond; -fx-font-size: 15px;");
        if (kingdom.getFearRate() > 0) {
            fear.setText("fear: " + "+" + kingdom.getFearRate() + " :)");
            fear.setFill(Color.GREEN);
        } else {
            fear.setText("fear: " + kingdom.getFearRate() + " :(");
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
    public void repairBuilding(){
        String respond = buildingController.repair();
        Alert alert;
        if (respond.equals("this building is repaired!")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("this building is repaired!");
            alert.show();
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(respond);
            alert.show();
        }
    }
    public void createUnit(){

    }
}