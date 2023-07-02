package controller;

import com.google.gson.Gson;
import model.User;
import model.UserDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginController {
    public void handle(Socket client) {
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            String input = dis.readUTF();
            String[] inputSplit = (new Gson()).fromJson(input, String[].class);
            for (int i = 0; i < inputSplit.length; i++) {
                System.out.print(inputSplit[i] + " ");
            }
            System.out.println();
            if (inputSplit[0].equals("login")) {
                String username = inputSplit[1];
                String password = inputSplit[2];
                String output = login(username, password);
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                dos.writeUTF(output);
                dos.flush();
                if (output.equals("ok")) {
                    DataOutputStream userDos = new DataOutputStream(client.getOutputStream());
                    userDos.writeUTF(new Gson().toJson(UserDatabase.getUserByUsername(username)));
                    userDos.flush();
                    UserDatabase.setAreWeLoggingIn(true);
                }
            } else if (inputSplit[0].equals("question")) {
                String username = inputSplit[1];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                dos.writeUTF(UserDatabase.getUserByUsername(username).getQuestion());
            } else if (inputSplit[0].equals("setNewPassword")) {
                String username = inputSplit[1];
                String newPassword = inputSplit[2];
                UserDatabase.getUserByUsername(username).setPassword(newPassword);
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                User user = UserDatabase.getUserByUsername(username);
                dos.writeUTF((new Gson()).toJson(user));
                dos.flush();
            } else if (inputSplit[0].equals("checkUsername")) {
                String username = inputSplit[1];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                if (UserDatabase.getUserByUsername(username) == null) {
                    dos.writeUTF("User with this username doesn't exist.");
                    dos.flush();
                } else {
                    dos.writeUTF("ok");
                    dos.flush();
                    User user = UserDatabase.getUserByUsername(username);
                    dos.writeUTF((new Gson()).toJson(user));
                    dos.flush();
                }
            } else if (inputSplit[0].equals("isUsernameUsed")) {
                String username = inputSplit[1];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                if (UserDatabase.getUserByUsername(username) == null) {
                    dos.writeBoolean(false);
                } else {
                    dos.writeBoolean(true);
                }
                dos.flush();
            } else if (inputSplit[0].equals("getQuestion")) {
                String username = inputSplit[1];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                dos.writeUTF(UserDatabase.getUserByUsername(username).getQuestion());
                dos.flush();
            } else if (inputSplit[0].equals("checkUserAnswer")) {
                String username = inputSplit[1];
                String answer = inputSplit[2];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                if (UserDatabase.getUserByUsername(username).getAnswer().equals(answer)) {
                    dos.writeBoolean(true);
                } else {
                    dos.writeBoolean(false);
                }
                dos.flush();
            }
            else if (inputSplit[0].equals("checkPassword")) {
                String username = inputSplit[1];
                String password = inputSplit[2];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                if (UserDatabase.getUserByUsername(username).getPassword().equals(password)) {
                    dos.writeBoolean(true);
                } else {
                    dos.writeBoolean(false);
                }
                dos.flush();
            }
            else if (inputSplit[0].equals("getUser")) {
                String username = inputSplit[1];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                dos.writeUTF((new Gson()).toJson(UserDatabase.getUserByUsername(username)));
                dos.flush();
            }
            else if (inputSplit[0].equals("isEmailUsed")) {
                String email = inputSplit[1];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                for (User user:UserDatabase.getUsers()) {
                    if (user.getEmail().toUpperCase().equals(email.toUpperCase())) {
                        dos.writeBoolean(true);
                        dos.flush();
                        return;
                    }
                }
                dos.writeBoolean(false);
                dos.flush();
            }
            else if (inputSplit[0].equals("playerRank")) {
                String username = inputSplit[1];
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                dos.writeInt(UserDatabase.getUserByUsername(username).getRank());
                dos.flush();
            }
            else {
                System.out.println("invalid command");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String login(String username, String password) {
        if (UserDatabase.getUserByUsername(username) == null) {
            return "Username and password didn't match!";
        }
        if (!UserDatabase.getUserByUsername(username).getPassword().equals(password)) {
            return "Username and password didn't match!";
        }
        return "ok";
    }
}
