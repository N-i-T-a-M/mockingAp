package model;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Captcha {
    private static int myNumber;

    private static ArrayList<Integer> numbers = new ArrayList<Integer>();

    private static void setNumbers() {
        try {
            FileReader fileReader = new FileReader("D:\\matin\\secondSemester\\AP\\project\\Faze1\\src\\main\\resources\\Captcha\\numbers.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int number = Integer.parseInt(line);
                numbers.add(number);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getMyNumber() {
        return myNumber;
    }

    public Image createCaptcha() {
        setNumbers();
        Random random = new Random();
        int n = random.nextInt(numbers.size());
        myNumber = numbers.get(n);
        String address;
        try {
        address = getClass().getResource("/Captcha/" + myNumber + ".png").toExternalForm();
        }
        catch (NullPointerException e) {
            address = getClass().getResource("/Captcha/2177.png").toExternalForm();
        }
        return new Image(address);
    }
}
