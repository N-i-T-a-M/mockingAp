package controller;

import model.User;
import model.UserDatabase;
import view.SecurityQuestionMenu;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;


public class RegisterMenuController {
    private User userRegister;

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

    public String register(String username, String password, String passwordConfirmation, String email, String nickname, String slogan) {
        String emailToUpper = email.toUpperCase();
        if (!isCorrectUsername(username)) {
            return "invalid username format!";
        }
        if (UserDatabase.getUserByUsername(username) != null) {
            return "A player with this username already exist! but you might find these interesting:";
        }
        if (!isPasswordWeak(password).equals("true")) {
            return isPasswordWeak(password);
        }
        if (!password.equals(passwordConfirmation)) {
            return "password confirmation does not equal password!";
        }

        if (UserDatabase.isEmailExists(emailToUpper)) {
            return "player with this email already exists!";
        }
        if (!isEmailFormatCorrect(emailToUpper)) {
            return "email format is incorrect!";
        }
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);
        try {
            password = UserDatabase.hashPassword(password, salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        slogan = slogan.replace("\"", "");
        userRegister = new User(username, password, nickname, email, slogan);
        userRegister.setSalt(salt);
        SecurityQuestionMenu.setUser(userRegister);
        return "register successfully!";
    }
    public static boolean isUsernameUsed (String username) {
        if (UserDatabase.getUserByUsername(username) != null) {
            return true;
        }
        return false;
    }
    public String showQuestions() {
        String output = "Please select your security question and answer it.\n";
        for (int i = 0; i < 3; i++) {
            output += (i + 1) + ". " + UserDatabase.getQuestions().get(i) + "\n";
        }
        return output;
    }

    public String securityQuestionErrors(int number, String answer, String answerConfirm) {
        if (number > 3 || number < 1) {
            return "incorrect number question!";
        }
        if (!answer.equals(answerConfirm)) {
            return "answer confirmation does not equal answer!";
        }
        return "question and answer selected!";
    }

    public static void addUser(User user, String answer, int questionNumber) {
        user.setAnswer(answer);
        user.setQuestion(questionNumber);
        UserDatabase.addUser(user);

        UserDatabase.saveUsers();
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

    public static boolean isCorrectUsername(String username) {
        String usernameValid = "^[a-zA-Z0-9|_]+$";
        return Pattern.matches(usernameValid, username);
    }

    public static boolean isEmailFormatCorrect(String email) {
        email = email.toUpperCase();
        String emailValid = "^[A-Z0-9_.]+@[A-Z0-9_]+\\.[A-Z0-9_.]+$";
        return Pattern.matches(emailValid, email);
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
}
