package view;

//import controller.GameController.BuildingController;
//import controller.GameController.GameMenuController;
//import controller.mapmenu.MapMenuController;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//import model.Game;
//import model.Kingdom;
//import model.map.Cell;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.map.Cell;

import java.util.ArrayList;

public class MapViewController {
    private static MapViewController controller = null;
//    private Game game;

    public static synchronized MapViewController getInstance(//Game game
    ) {
//        if (controller == null)
//            controller = new MapViewController();
//        controller.game = game;
//        return controller;
        return null;
    }

    public int getCurrentKingdomFoodRate() {
//        return game.getCurrentKingdom().getFoodRate();
        return 0;
    }

    public int getCurrentKingdomTaxRate() {
//        return game.getCurrentKingdom().getTaxRate();
        return 0;
    }

    public int getCurrentKingdomReligiousPeople() {
//        return game.getCurrentKingdom().getReligiousPeople();
        return 0;
    }

    public int getCurrentKingdomPopulation() {
//        return game.getCurrentKingdom().getPopulation();
        return 0;
    }

    public int getCurrentKingdomFearRate() {
//        return game.getCurrentKingdom().getFearRate();
        return 0;
    }

    public int getCurrentMapDimension() {
//        return game.getCurrentMap().getDimension();
        return 0;
    }

    public Pane getCellPane(int x, int y) {
//        return game.getCurrentMap().getMap()[x][y].getPane();
        return null;
    }


    public String getCellDetail(int i, int j) {
//        MapMenuController mapController = new MapMenuController(game.getCurrentMap());
//        return mapController.showDetail(String.valueOf(i + 1), String.valueOf(j + 1));
        return null;
    }

    public String getDropBuildingAlert(int i, int j, String buildingName) {
//        return (new GameMenuController(game)).dropBuilding(String.valueOf(i), String.valueOf(j), buildingName);
        return null;
    }

    public ImageView getCellImageByPane(Pane pane) {
//        return getCellByPane(pane).getImage();
        return null;
    }

    private Cell getCellByPane(Pane pane) {
//        for (Cell[] cells : game.getCurrentMap().getMap()) {
//            for (Cell cell : cells) {
//                if (cell.getPane() == pane) {
//                    return cell;
//                }
//            }
//        }
        return null;
    }

    public Image getCellImage(int i, int j) {
//        return game.getCurrentMap().getMap()[i][j].getTheImage();
        return null;
    }

    public int getCellXCoordinateByPane(Pane pane) {
        return getCellByPane(pane).getxCoordinate();
    }

    public int getCellYCoordinateByPane(Pane pane) {
        return getCellByPane(pane).getyCoordinate();
    }

    public String getPaneBuildingName(Pane pane) {
        return getCellByPane(pane).getBuilding().getBuildingType().getBuildingName();
    }

    public void setBuildingNullByPane(Pane pane) {
        getCellByPane(pane).setBuilding(null);
    }

    public void minusNumberOfWorkers() {
//        game.getCurrentKingdom().setNumberOfWorkers(game.getCurrentKingdom().getNumberOfWorkers() - 1);
    }

    public void goToPauseMenu(Stage stage) throws Exception {
//        (new PauseMenu(game, stage)).start(stage);
    }

    public String getGameBriefing() {
        String text = "";
//        for (Kingdom kingdom : game.getPlayers()) {
//            text += "kingdom name = " + kingdom.getOwner().getUsername() + "\n" +
//                    "kingdom gold = " + kingdom.getGold() + "\n";
//        }
        return text;
    }

    public double getCurrentKingdomGold() {
//        return game.getCurrentKingdom().getGold();
        return 0;
    }

    public int getNumberOfWorkers() {
//        return game.getCurrentKingdom().getNumberOfWorkers();
        return 0;
    }

    public int getCurrentKingdomPopularity() {
//        return game.getCurrentKingdom().getPopularity();
        return 0;
    }

    public String getDetailText(ArrayList<Pane> selectedPain) {
//        ArrayList<Cell> cells = new ArrayList<>();
//        for (Pane pane1 : selectedPain)
//            cells.add(getCellByPane(pane1));
//        return (new MapMenuController(game.getCurrentMap())).showDetails(cells, game.getCurrentKingdom());
        return null;
    }

    public String getBuildingControllerRepairText() {
//        return (new BuildingController(game)).repair();
        return null;
    }
}
