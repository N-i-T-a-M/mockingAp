import controller.LoginThread;
import controller.RegisterThread;

public class Main {
    public static void main(String[] args) {
        RegisterThread registerThread = new RegisterThread();
        registerThread.start();
        LoginThread loginThread = new LoginThread();
        loginThread.start();
    }
}