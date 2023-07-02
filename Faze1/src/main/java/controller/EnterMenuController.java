package controller;

import model.UserDatabase;

public class EnterMenuController {
    public void resetStayLoggedIn() {
        if (UserDatabase.getCurrentUser() != null)
            UserDatabase.getCurrentUser().setStayLoggedIn(false);
    }

    public void saveUsers() {
        UserDatabase.saveUsers();
    }
}
