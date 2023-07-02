package model;

import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        String address = getClass().getResource("/Captcha/" + myNumber + ".png").toExternalForm();
        return new Image(address);
    }
}
//    public static String[] zero = {
//            "   ###      ",
//            "  #   #     ",
//            " #     #    ",
//            " #     #    ",
//            " #     #    ",
//            "  #   #     ",
//            "   ###      ",
//            "            "
//    };
//    public static String[] one = {
//            "   #      ",
//            "  ##      ",
//            " # #      ",
//            "   #      ",
//            "   #      ",
//            "   #      ",
//            " #####    ",
//            "          "
//    };
//    public static String[] two = {
//            "   #####    ",
//            "       #    ",
//            "       #    ",
//            " #######    ",
//            " #          ",
//            " #          ",
//            " #####      ",
//            "            "
//    };
//    public static String[] three = {
//            "  #####     ",
//            "       #    ",
//            "       #    ",
//            "  #####     ",
//            "       #    ",
//            "       #    ",
//            "  #####     ",
//            "            "
//    };
//    public static String[] four = {
//            " #          ",
//            " #    #     ",
//            " #    #     ",
//            " #    #     ",
//            " #######    ",
//            "      #     ",
//            "      #     ",
//            "            "
//    };
//    public static String[] five = {
//            " #####      ",
//            " #          ",
//            " #          ",
//            " #######    ",
//            "       #    ",
//            "       #    ",
//            "    ####    ",
//            "            "
//    };
//    public static String[] six = {
//            " #######    ",
//            " #     #    ",
//            " #          ",
//            " #######    ",
//            " #     #    ",
//            " #     #    ",
//            " #######    ",
//            "            "
//    };
//    public static String[] seven = {
//            " #######    ",
//            " #    #     ",
//            "     #      ",
//            "    #       ",
//            "   #        ",
//            "   #        ",
//            "   #        ",
//            "            "
//    };
//    public static String[] eight = {
//            " #######    ",
//            " #     #    ",
//            " #     #    ",
//            " #######    ",
//            " #     #    ",
//            " #     #    ",
//            " #######    ",
//            "            "
//    };
//    public static String[] nine = {
//            "  #####     ",
//            " #     #    ",
//            " #     #    ",
//            "  ######    ",
//            "       #    ",
//            "       #    ",
//            "    ###     ",
//            "            "
//    };
//    public static String[][] digits = new String[10][];
//
//    {
//        digits[0] = zero;
//        digits[1] = one;
//        digits[2] = two;
//        digits[3] = three;
//        digits[4] = four;
//        digits[5] = five;
//        digits[6] = six;
//        digits[7] = seven;
//        digits[8] = eight;
//        digits[9] = nine;
//    }
//
//    private final int minimum = 4;
//    private final int maximum = 8;
//    private final int captchaSize = (int) (Math.random() * (maximum - minimum + 1)) + minimum;
//    String charactersToCreateNoise = "!@#$%^&*()_-+='\":;.>,<?/|`~";
//
//    private String[] createNoise(int number) {
//        int size = digits[number].length;
//        String[] digitPlusNoise = new String[size];
//        Random random = new Random();
//        for (int i = 0; i < size; i++) {
//            String string = digits[number][i];
//            StringBuilder stringBuilder = new StringBuilder(string);
//            int length = stringBuilder.length();
//            for (int j = 0; j < length; j++) {
//                if (random.nextDouble() < 0.08) {
//                    stringBuilder.setCharAt(j, charactersToCreateNoise.charAt(random.nextInt(charactersToCreateNoise.length())));
//                }
//            }
//            digitPlusNoise[i] = stringBuilder.toString();
//        }
//        return digitPlusNoise;
//    }
//
//    private final String[][] createdCaptcha = new String[captchaSize][];
//    private final int[] numbers = new int[captchaSize];
//
//    public Captcha() {
//        Random random = new Random();
//        for (int i = 0; i < captchaSize; i++) {
//            int index = random.nextInt(captchaSize);
//            createdCaptcha[i] = createNoise(index);
//            numbers[i] = index;
//        }
//    }
//
//    public String[][] getCreatedCaptcha() {
//        return createdCaptcha;
//    }
//
//    public int[] getNumbers() {
//        return numbers;
//    }
