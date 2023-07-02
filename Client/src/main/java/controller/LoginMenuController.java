package controller;

import com.google.gson.Gson;
import model.Captcha;
import model.User;
import view.CaptchaMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class LoginMenuController {
    private static User loggingInUser;
    private static User loggedInUser;

    public static User getLoggingInUser() {
        return loggingInUser;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static String login(String username, String password) {
        try {
            Socket socket = new Socket("localhost", 3000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"login", username, password};
            writer.writeUTF((new Gson()).toJson(userJson));
            writer.flush();
            String response = reader.readUTF();
            if (response.equals("ok")) {
                loggedInUser = new Gson().fromJson(reader.readUTF(), User.class);
                CaptchaMenu.setLogginInUsername(username);
            }
            socket.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


//    public String checkUsername(String username) {
//        // empty fields!
//        if (!RegisterMenuController.isCorrectUsername(username)) {
//            return "The username format is incorrect.";
//        }
//        try {
//         Socket socket = new Socket("localhost", 3000);
//            DataInputStream reader = new DataInputStream(socket.getInputStream());
//            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
//            String[] userJson = {"checkUsername", username};
//            writer.writeUTF((new Gson()).toJson(userJson));
//            writer.flush();
//            String response = reader.readUTF();
//            if (response.equals("ok")) {
//                loggingInUser = new Gson().fromJson(reader.readUTF(), User.class);
//            }
//            else {
//                return "User with this username doesn't exist.";
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "Please answer your security question:\n" + user.getQuestion();
//    }
//
//    public String checkAnswer(String username, String answer) {
//        if (UserDatabase.getUserByUsername(username).getAnswer().equals(answer)) {
//            return "Correct. Please set your new password.";
//        }
//        return "Incorrect answer. please try again";
//    }

    public static String setNewPassword(String username, String password, String passwordRepeat) {
        if (!RegisterMenuController.isPasswordWeak(password).equals("true")) {
            return RegisterMenuController.isPasswordWeak(password);
        }
        if (!password.equals(passwordRepeat)) {
            return "password and password confirm don't match.";
        }
        try {
            Socket socket = new Socket("localhost", 3000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"setNewPassword", username, password};
            writer.writeUTF((new Gson()).toJson(userJson));
            writer.flush();
            loggedInUser = new Gson().fromJson(reader.readUTF(), User.class);
            socket.close();
            return "password changed successfully.";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isUsernameUsed(String text) {
        try {
            Socket socket = new Socket("localhost", 3000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"isUsernameUsed", text};
            writer.writeUTF((new Gson()).toJson(userJson));
            writer.flush();
            boolean response = reader.readBoolean();
            socket.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isEmailUsed(String email) {
        try {
            Socket socket = new Socket("localhost", 3000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"isEmailUsed", email};
            writer.writeUTF((new Gson()).toJson(userJson));
            writer.flush();
            boolean response = reader.readBoolean();
            socket.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getQuestion(String username) {
        try {
            Socket socket = new Socket("localhost", 3000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"getQuestion", username};
            writer.writeUTF((new Gson()).toJson(userJson));
            writer.flush();
            String response = reader.readUTF();
            socket.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean checkUserAnswer(String username, String answer) {
        try {
            Socket socket = new Socket("localhost", 3000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"checkUserAnswer", username, answer};
            writer.writeUTF((new Gson()).toJson(userJson));
            writer.flush();
            boolean response = reader.readBoolean();
            socket.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkPassword(String username, String password) {
        try {
            Socket socket = new Socket("localhost", 3000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"checkPassword", username, password};
            writer.writeUTF((new Gson()).toJson(userJson));
            writer.flush();
            boolean response = reader.readBoolean();
            socket.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User getUser(String loggingInUsername) {
        try {
            Socket socket = new Socket("localhost", 3000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"getUser", loggingInUsername};
            writer.writeUTF((new Gson()).toJson(userJson));
            writer.flush();
            User response = new Gson().fromJson(reader.readUTF(), User.class);
            socket.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int playerRank(User user) {
        try {
            Socket socket = new Socket("localhost", 3000);
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            String[] userJson = {"playerRank", user.getUsername()};
            writer.writeUTF((new Gson()).toJson(userJson));
            writer.flush();
            int response = reader.readInt();
            socket.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
