package view;

import Commands.MapMenuCommands;
import controller.mapmenu.MapMenuController;
import model.map.Map;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    private final Map map;

    public MapMenu(int size, int kingdomNumber) {
        map = new Map(size, kingdomNumber);
    }

    public MapMenu(Map map) {
        this.map = map;
    }

    public void run(Scanner scanner) {
        System.out.println("Welcome to map menu!");
        String input;
        Matcher setCellTexture, setCellsTexture, clear, dropRock, dropTree, showMap, mapUp, showDetail, selectMap;
        MapMenuController controller = new MapMenuController(map);
        while (true) {
            input = scanner.nextLine();
            setCellTexture = MapMenuCommands.getMatcher(input, MapMenuCommands.SET_TEXTURE_OF_CELL);
            setCellsTexture = MapMenuCommands.getMatcher(input, MapMenuCommands.SET_TEXTURE_OF_CELLS);
            clear = MapMenuCommands.getMatcher(input, MapMenuCommands.CLEAR_CELL);
            dropRock = MapMenuCommands.getMatcher(input, MapMenuCommands.DROP_ROCK);
            dropTree = MapMenuCommands.getMatcher(input, MapMenuCommands.DROP_TREE);
            showMap = MapMenuCommands.getMatcher(input, MapMenuCommands.SHOW_MAP);
            mapUp = MapMenuCommands.getMatcher(input, MapMenuCommands.MAP_UP);
            showDetail = MapMenuCommands.getMatcher(input, MapMenuCommands.SHOW_DETAILS);
            selectMap = MapMenuCommands.getMatcher(input, MapMenuCommands.SELECT_MAP);
            if (setCellTexture.find()) {
                String type = setCellTexture.group("type");
                System.out.println(controller.setCellTexture(setCellTexture.group("xCoordinate"),
                        setCellTexture.group("yCoordinate"), type));
            } else if (setCellsTexture.find()) {
                String type = setCellsTexture.group("type");
                System.out.println(controller.setCellsTexture(setCellsTexture.group("x1Coordinate"),
                        setCellsTexture.group("y1Coordinate"), setCellsTexture.group("x2Coordinate"),
                        setCellsTexture.group("y2Coordinate"), type));
            } else if (clear.find()) {
                System.out.println(controller.clear(clear.group("xCoordinate"), clear.group("yCoordinate")));
            } else if (dropRock.find()) {
                System.out.println(controller.dropRock(dropRock.group("xCoordinate"), dropRock.group("yCoordinate"),
                        dropRock.group("direction")));
            } else if (dropTree.find()) {
                System.out.println(controller.dropTree(dropTree.group("xCoordinate"), dropTree.group("yCoordinate"),
                        dropTree.group("type")));
            } else if (showMap.find()) {
                System.out.println(controller.showMap(showMap.group("x"), showMap.group("y")));
            } else if (mapUp.find()) {
                System.out.println(controller.mapUp(mapUp.group("direction"), mapUp.group("number")));
            } else if (showDetail.find()) {
                System.out.println(controller.showDetail(showDetail.group("x"), showDetail.group("y")));
            } else if (selectMap.find()) {
                System.out.println(controller.selectMap());
            } else if (input.equalsIgnoreCase("back")) {
                return;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }
}
