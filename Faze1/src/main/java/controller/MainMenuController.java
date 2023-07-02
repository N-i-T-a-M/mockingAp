package controller;

import javafx.stage.Stage;
import model.UserDatabase;
import view.CreateMapMenu;

public class MainMenuController {
    public void removeUserLoggedIn() {
        UserDatabase.getCurrentUser().setStayLoggedIn(false);
    }

    public boolean isMapSelected() {
        return UserDatabase.getCurrentMap() != null;
    }
    public void goToMapMenu(int dimension, int kingdomNumber, Stage stage) throws Exception {
        (new CreateMapMenu(dimension, kingdomNumber)).start(stage);
    }
}
