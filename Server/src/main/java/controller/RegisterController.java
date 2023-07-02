package controller;

import com.google.gson.Gson;
import model.User;
import model.UserDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RegisterController {
    private User userRegister;

    public void handle(Socket client) {
        try {
            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            String input = dataInputStream.readUTF();
            String[] inputSplit = new Gson().fromJson(input, String[].class);
            for (int i = 0; i < inputSplit.length; i++) {
                System.out.print(inputSplit[i] + " ");
            }
            if (inputSplit[0].equals("register")) {
                String username = inputSplit[1];
                String password = inputSplit[2];
                String nickname = inputSplit[3];
                String email = inputSplit[4];
                String slogan = inputSplit[5];
                String output = register(username, password, email, nickname, slogan);
                DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                dataOutputStream.writeUTF(output);
                if (output.equals("register successfully!")) {
                    DataOutputStream user = new DataOutputStream(client.getOutputStream());
                    user.writeUTF(new Gson().toJson(userRegister));
                    UserDatabase.setAreWeLoggingIn(false);
                }
            }
            else if (inputSplit[0].equals("addUser")) {
                User user = new Gson().fromJson(inputSplit[1], User.class);
                UserDatabase.addUser(user);
            }
            else {
                System.out.println("invalid command");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String register(String username, String password, String email, String nickname, String slogan) {
        String emailToUpper = email.toUpperCase();
        if (UserDatabase.getUserByUsername(username) != null) {
            return "A player with this username already exist!";
        }
        if (UserDatabase.isEmailExists(emailToUpper)) {
            return "player with this email already exists!";
        }
        slogan = slogan.replace("\"", "");
        userRegister = new User(username, password, nickname, email, slogan);
        return "register successfully!";
    }
}
