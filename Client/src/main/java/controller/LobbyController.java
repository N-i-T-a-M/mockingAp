package controller;

import com.google.gson.Gson;
import model.GameRequest;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LobbyController {

    public static GameRequest getGameRequestById(long id) {
        try {
            Socket socket = new Socket("localhost", 8003);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String[] json = {"getGameRequestById", String.valueOf(id)};
            dos.writeUTF(new Gson().toJson(json));
            dos.flush();
            String response = dis.readUTF();
            if (response == "null") {
                return null;
            }
            return new Gson().fromJson(response, GameRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GameRequest createGameRequest(int cap, User currentUser) {
        GameRequest gameRequest = new GameRequest(cap, currentUser);
        currentUser.setGameRequest(gameRequest);
        try {
            Socket socket = new Socket("localhost", 8003);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String[] json = {"createGameRequest", new Gson().toJson(gameRequest), new Gson().toJson(currentUser)};
            dos.writeUTF(new Gson().toJson(json));
            dos.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return gameRequest;
    }

    public static ArrayList<GameRequest> getGames() {
        try {
         Socket socket = new Socket("localhost", 8003);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String[] json = {"getGames"};
            dos.writeUTF(new Gson().toJson(json));
            dos.flush();
            String response = dis.readUTF();
            return new Gson().fromJson(response, ArrayList.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
