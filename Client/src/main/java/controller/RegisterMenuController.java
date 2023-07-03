package controller;

import com.google.gson.Gson;
import model.User;
import model.chat.Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class RegisterMenuController {
    private static final int PORT = 2000;
    private static User userToRegister;

    public static User getUserToRegister() {
        return userToRegister;
    }

    public static void addUser(User registeringUser, String userAnswer, int userQuestionNumber) {
        registeringUser.setAnswer(userAnswer);
        registeringUser.setQuestionNumber(userQuestionNumber);
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"addUser", new Gson().toJson(registeringUser)};
            writer.writeUTF(new Gson().toJson(userJson));
            writer.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String register(String username, String password, String passwordConfirmation, String email, String nickname, String slogan) {
        String emailToUpper = email.toUpperCase();
        if (!isCorrectUsername(username)) {
            return "invalid username format!";
        }
        if (!isPasswordWeak(password).equals("true")) {
            return isPasswordWeak(password);
        }
        if (!password.equals(passwordConfirmation)) {
            return "password confirmation does not equal password!";
        }
        if (!isEmailFormatCorrect(emailToUpper)) {
            return "email format is incorrect!";
        }
        try {
            Socket socket = new Socket("localhost", PORT);
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            String[] userJson = {"register", username, password, nickname, email, slogan};
            writer.writeUTF(new Gson().toJson(userJson));
            writer.flush();
            String response = reader.readUTF();
            if (response.equals("register successfully!")) {
                User user = new Gson().fromJson(reader.readUTF(), User.class);
                userToRegister = user;
            }
            socket.close();
            return response;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public static String isPasswordWeak(String password) {
        Pattern capitalLetterPattern = Pattern.compile("[A-Z]");
        Pattern smallLetterPattern = Pattern.compile("[a-z]");
        Pattern digitPattern = Pattern.compile("[0-9]");
        Pattern elseCharacterPattern = Pattern.compile("[^a-zA-Z0-9]");
        if (password.length() < 6) {
            return "Password is too short";
        }
        if (!capitalLetterPattern.matcher(password).find()) {
            return "Password must contain\na capital letter!";
        }
        if (!smallLetterPattern.matcher(password).find()) {
            return "Password must contain\na small letter!";
        }
        if (!digitPattern.matcher(password).find()) {
            return "Password must contain\na digit!";
        }
        if (!elseCharacterPattern.matcher(password).find()) {
            return "Password must contain\na non-word character!";
        }
        return "true";
    }

    public static boolean isEmailFormatCorrect(String email) {
        email = email.toUpperCase();
        String emailValid = "^[A-Z0-9_.]+@[A-Z0-9_]+\\.[A-Z0-9_.]+$";
        return Pattern.matches(emailValid, email);
    }

    public static boolean isCorrectUsername(String username) {
        String usernameValid = "^[a-zA-Z0-9|_]+$";
        return Pattern.matches(usernameValid, username);
    }

    public static String generateRandomPassword() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        ArrayList<Character> characters = new ArrayList<>();
        String allCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789!@#$%^&'()*+,-./:;<=>?@[]`~{}'\"\\|";
        String allCapitalCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String allSmallCharacters = "abcdefghijklmnopqrstuvxyz";
        String allNumberCharacters = "0123456789";
        String allElseCharacter = "!@#$%^&'()*+,-./:;<=>?@[]`~{}\"\\|";
        StringBuilder sb = new StringBuilder(7);
        int ch;
        ch = (int) (allCapitalCharacters.length() * Math.random());
        characters.add(allCapitalCharacters.charAt(ch));
        ch = (int) (allSmallCharacters.length() * Math.random());
        characters.add(allSmallCharacters.charAt(ch));
        ch = (int) (allNumberCharacters.length() * Math.random());
        characters.add(allNumberCharacters.charAt(ch));
        ch = (int) (allElseCharacter.length() * Math.random());
        characters.add(allElseCharacter.charAt(ch));
        for (int i = 0; i <= 3; i++) {
            ch = (int) (allCharacters.length() * Math.random());
            characters.add(allCharacters.charAt(ch));
        }
        for (int i : list) {
            sb.append(characters.get(i));
        }
        return sb.toString();
    }

    public static String generateRandomSlogan() {
        ArrayList<String> slogans = new ArrayList<>();
        String slogan;
        slogan = "I don't die unless, well unless I die!";
        slogans.add(slogan);
        slogan = "I smile so that no one knows my pain, otherwise this world that we saw wasn't funny";
        slogans.add(slogan);
        slogan = "رنده میکنم همه هویجا رو ریز ریز";
        slogans.add(slogan);
        slogan = "Game over? Never heard of it.";
        slogans.add(slogan);
        slogan = "Play, win, repeat";
        slogans.add(slogan);
        slogan = "You are your only limit";
        slogans.add(slogan);
        slogan = "This is a kingdom, with me the king and you the dumb";
        slogans.add(slogan);
        slogan = "Stronghold? More like low quality game made by college newbies";
        slogans.add(slogan);
        int index = (int) (Math.random() * slogans.size());
        return slogans.get(index);
    }

    public static String getFamousSlogans() {
        ArrayList<String> slogans = new ArrayList<>();
        slogans.add("I fight till I die");
        slogans.add("Never hesitate to kill the wounded bear");
        slogans.add("I am the best");
        Collections.shuffle(slogans);
        String output = "";
        for (int i = 0; i < 3; i++) {
            output += (i + 1) + ". " + slogans.get(i) + "\n";
        }
        return output;
    }
}
