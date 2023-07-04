package model.Property;

import model.Building.Storage;
import model.Kingdom;

import java.awt.image.Kernel;

public class Property {
    private int value;

    public Property(int value) {
        this.value = value;
    }

    public void addValue(int amount) {
        value += amount;
    }

    public int getValue() {
        return value;
    }
}
