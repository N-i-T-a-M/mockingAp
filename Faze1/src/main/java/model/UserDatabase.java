package model;

import com.google.gson.Gson;
import model.map.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class UserDatabase {
    private static boolean areWeLoggingIn = false;

    public static void setAreWeLoggingIn(boolean setter) {
        areWeLoggingIn = setter;
    }

    public static boolean isAreWeLoggingIn() {
        return areWeLoggingIn;
    }

    //todo : set this for the captcha menu
    private static User currentUser;
    private static final ArrayList<String> questions;

    private static Map currentMap;

    public static ArrayList<String> getQuestions() {
        return questions;
    }

    static {
        questions = new ArrayList<>();
        questions.add("What is my father’s name?");
        questions.add("What was my first pet’s name?");
        questions.add("What is my mother’s last name?");
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserDatabase.currentUser = currentUser;
    }

    private static final String DATA_BASE = "database.json";
    private static final Gson gson = new Gson();
    private static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getPlayers() {
        return players;
    }

    private static final ArrayList<User> players = new ArrayList<>();// should be cleared after each game

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static int numberOfUsers() {
        return users.size();
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(Map currentMap) {
        UserDatabase.currentMap = currentMap;
    }

    public static boolean isEmailExists(String email) {
        for (User user : users) {
            if (user.getEmail().toUpperCase().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static void addUser(User userRegister) {
        users.add(userRegister);
    }

    public static void loadUsers() {
        try {
            File file = new File(DATA_BASE);
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                String fileContent = fileScanner.useDelimiter("\\Z").next();
                fileScanner.close();
                User[] userArray = gson.fromJson(fileContent, User[].class);
                users = new ArrayList<User>();
                Collections.addAll(users, userArray);
            } else {
                users = new ArrayList<User>();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveUsers() {
        try {
            FileWriter writer = new FileWriter(DATA_BASE);
            String json = gson.toJson(users.toArray());
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean existsEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        byte[] saltedPassword = new byte[salt.length + password.getBytes().length];
        System.arraycopy(salt, 0, saltedPassword, 0, salt.length);
        System.arraycopy(password.getBytes(), 0, saltedPassword, salt.length, password.getBytes().length);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(saltedPassword);
        byte[] hashedPassword = messageDigest.digest();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedPassword) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

    public static boolean verifyPassword(String password, String storedHash, byte[] storedSalt) throws NoSuchAlgorithmException {
        String enteredHash = hashPassword(password, storedSalt);
        return enteredHash.equals(storedHash);
    }

    public static ArrayList<User> rankPlayers() {
        ArrayList<User> sortedUsers = new ArrayList<>(users);
        for (int i = 0; i < sortedUsers.size(); i++) {
            for (int j = 0; j < sortedUsers.size() - 1; j++) {
                if (sortedUsers.get(j).getHighScore() < sortedUsers.get(j + 1).getHighScore()) {
                    User temp = sortedUsers.get(j);
                    sortedUsers.set(j, sortedUsers.get(j + 1));
                    sortedUsers.set(j + 1, temp);
                }
            }
        }
        return sortedUsers;
    }

    public static int playerRank(User user) {
        return rankPlayers().indexOf(user) + 1;
    }
    public static void setOnline (User user) {
        user.setOnline(true);
    }
    public static void setOffline (User user) {
        user.setOnline(false);
    }
    public static String acceptFriend (User you, User them) {
        if (you.getFriends().contains(them)) {
            return "You are already friends with " + them.getUsername();
        }
        if (you.getWaitingForYouToAccept().contains(them)) {
            you.getWaitingForYouToAccept().remove(them);
            you.getFriends().add(them);
            them.getFriends().add(you);
            them.getWaitingForThemToAccept().remove(you);
            return "You are now friends with " + them.getUsername();
        }
        return "You have not received a friend request from " + them.getUsername();
    }
    private static ArrayList<GameRequest> games = new ArrayList<>();

    public static ArrayList<GameRequest> getGames() {
        return games;
    }
    public static GameRequest getGameById (Long id) {
        for (GameRequest gameRequest:games) {
            if (gameRequest.getId() == id) {
                return gameRequest;
            }
        }
        return null;
    }
}

