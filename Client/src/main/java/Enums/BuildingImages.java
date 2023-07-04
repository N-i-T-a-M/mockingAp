package Enums;

import javafx.scene.image.Image;
import Enums.BuildingType;

import java.util.HashMap;

public class BuildingImages {
    private static final HashMap<String, BuildingType> buildings = new HashMap<>();
    private static final HashMap<BuildingType, Image> buildingImages = new HashMap<>();
    private static final HashMap<Image, String> militaryBuilding = new HashMap<>();

    private static final HashMap<Image, String> buildBuilding = new HashMap<>();

    private static final HashMap<Image, String> churches = new HashMap<>();

    private static final HashMap<Image, String> foodBuilding = new HashMap<>();

    private static final HashMap<Image, String> sourceBuilding = new HashMap<>();
    private final static Image armourer = new Image(BuildingImages.class.getResource("/images/armourer.gif").toExternalForm());
    private final static Image gateHouse = new Image(BuildingImages.class.getResource("/images/gatehouse.png").toExternalForm());
    private final static Image dogCage = new Image(BuildingImages.class.getResource("/images/dogcage.png").toExternalForm());
    private final static Image drawBridge = new Image(BuildingImages.class.getResource("/images/drawbridge.png").toExternalForm());
    private final static Image engineerGuild = new Image(BuildingImages.class.getResource("/images/engineerguild.png").toExternalForm());
    private final static Image fletcher = new Image(BuildingImages.class.getResource("/images/fletcher.png").toExternalForm());
    private final static Image hunter = new Image(BuildingImages.class.getResource("/images/hunter.png").toExternalForm());
    private final static Image keep = new Image(BuildingImages.class.getResource("/images/keep10.png").toExternalForm());
    private final static Image mercenary = new Image(BuildingImages.class.getResource("/images/mercenary.png").toExternalForm());

    private final static Image poleturnner = new Image(BuildingImages.class.getResource("/images/poleturnner.png").toExternalForm());
    private final static Image stable = new Image(BuildingImages.class.getResource("/images/sta.png").toExternalForm());
    private final static Image squareTower = new Image(BuildingImages.class.getResource("/images/squareTower.png").toExternalForm());
    private final static Image lookoutTower = new Image(BuildingImages.class.getResource("/images/lookt.png").toExternalForm());
    private final static Image circularTower = new Image(BuildingImages.class.getResource("/images/circularTower.png").toExternalForm());
    private final static Image defensiveTower = new Image(BuildingImages.class.getResource("/images/defensiveTower.png").toExternalForm());
    private final static Image perimeterTower = new Image(BuildingImages.class.getResource("/images/premTower.png").toExternalForm());
    private final static Image blackSmith = new Image(BuildingImages.class.getResource("/images/blackSmith.png").toExternalForm());
    private final static Image barrack = new Image(BuildingImages.class.getResource("/images/barracks3.png").toExternalForm());
    private final static Image killingPit = new Image(BuildingImages.class.getResource("/images/kilintpit.png").toExternalForm());
    private final static Image tent = new Image(BuildingImages.class.getResource("/images/tent1.png").toExternalForm());
    private final static Image pitchDitch = new Image(BuildingImages.class.getResource("/images/pitchditch.gif").toExternalForm());
    private final static Image armoury = new Image(BuildingImages.class.getResource("/images/armoury.png").toExternalForm());
    private final static Image inn = new Image(BuildingImages.class.getResource("/images/inn.png").toExternalForm());
    private final static Image hovel = new Image(BuildingImages.class.getResource("/images/hovel.gif").toExternalForm());
    private final static Image church = new Image(BuildingImages.class.getResource("/images/church.gif").toExternalForm());
    private final static Image catheral = new Image(BuildingImages.class.getResource("/images/catheral.gif").toExternalForm());

    private final static Image appleFarm = new Image(BuildingImages.class.getResource("/images/appleFarm.png").toExternalForm());
    private final static Image bakery = new Image(BuildingImages.class.getResource("/images/bakery.png").toExternalForm());
    private final static Image brewery = new Image(BuildingImages.class.getResource("/images/brewery.png").toExternalForm());
    private final static Image dairy = new Image(BuildingImages.class.getResource("/images/dairy.gif").toExternalForm());
    private final static Image foodStuck = new Image(BuildingImages.class.getResource("/images/foodStock.png").toExternalForm());
    private final static Image hopFarm = new Image(BuildingImages.class.getResource("/images/hopFarm.png").toExternalForm());
    private final static Image mill = new Image(BuildingImages.class.getResource("/images/mill.gif").toExternalForm());
    private final static Image wheatFarm = new Image(BuildingImages.class.getResource("/images/wheatFarm.png").toExternalForm());
    private final static Image ironMine = new Image(BuildingImages.class.getResource("/images/ironmine.gif").toExternalForm());
    private final static Image market = new Image(BuildingImages.class.getResource("/images/Market.png").toExternalForm());
    private final static Image oilSmelter = new Image(BuildingImages.class.getResource("/images/oilsmel.gif").toExternalForm());
    private final static Image oxTether = new Image(BuildingImages.class.getResource("/images/oxTether.gif").toExternalForm());
    private final static Image quarry = new Image(BuildingImages.class.getResource("/images/quarry.gif").toExternalForm());
    private final static Image stockPile = new Image(BuildingImages.class.getResource("/images/stockpile.gif").toExternalForm());
    private final static Image woodCutter = new Image(BuildingImages.class.getResource("/images/WoodCutter.png").toExternalForm());
    private final static Image wall = new Image(BuildingImages.class.getResource("/images/wall.png").toExternalForm());


    public static HashMap<Image, String> getMilitaryBuilding() {
        if (militaryBuilding.size() == 0) {
            militaryBuilding.put(keep, "keep");
            militaryBuilding.put(armourer, "armourer");
            militaryBuilding.put(dogCage, "caged war dog");
            militaryBuilding.put(blackSmith, "blacksmith");
            militaryBuilding.put(barrack, "barrack");
            militaryBuilding.put(killingPit, "killing pit");
            militaryBuilding.put(tent, "tent");
            militaryBuilding.put(pitchDitch, "pitch ditch");
            militaryBuilding.put(armoury, "armoury");
            militaryBuilding.put(engineerGuild, "engineer guild");
            militaryBuilding.put(fletcher, "fletcher");
            militaryBuilding.put(mercenary, "mercenary post");
            militaryBuilding.put(poleturnner, "pole turner");
            militaryBuilding.put(stable, "stable");
        }
        return militaryBuilding;
    }

    public static HashMap<Image, String> getBuildBuilding() {
        if (buildBuilding.size() == 0) {
            buildBuilding.put(gateHouse, "small gate house");
            buildBuilding.put(drawBridge, "draw bridge");
            buildBuilding.put(lookoutTower, "lookout tower");
            buildBuilding.put(circularTower, "circular tower");
            buildBuilding.put(defensiveTower, "defensive tower");
            buildBuilding.put(squareTower, "square tower");
            buildBuilding.put(inn, "inn");
            buildBuilding.put(hovel, "hovel");
            buildBuilding.put(perimeterTower, "perimeter tower");
            buildBuilding.put(wall, "wall");
        }
        return buildBuilding;
    }

    private static HashMap<String, BuildingType> setBuildings() {
        if (buildings.size() == 0) {
            buildings.put("iron mine", BuildingType.IRON_MINE);
            buildings.put("market", BuildingType.MARKET);
            buildings.put("oil smelter", BuildingType.OIL_SMELTER);
            buildings.put("ox tether", BuildingType.OX_TETHER);
            buildings.put("quarry", BuildingType.QUARRY);
            buildings.put("stock pile", BuildingType.STOCKPILE);
            buildings.put("wood cutter", BuildingType.WOODCUTTER);
            buildings.put("apple garden", BuildingType.APPLE_GARDEN);
            buildings.put("bakery", BuildingType.BAKERY);
            buildings.put("brewery", BuildingType.BREWERY);
            buildings.put("diary product", BuildingType.DAIRY_PRODUCTS);
            buildings.put("food stock pile", BuildingType.FOOD_STOCKPILE);
            buildings.put("hop field", BuildingType.HOPS_FARM);
            buildings.put("mill", BuildingType.MILL);
            buildings.put("wheat field", BuildingType.WHEAT_FARM);
            buildings.put("church", BuildingType.CHURCH);
            buildings.put("cathedral", BuildingType.CATHEDRAL);
            buildings.put("keep", BuildingType.KEEP);
            buildings.put("armourer", BuildingType.ARMOURER);
            buildings.put("caged war dog", BuildingType.CAGED_WAR_DOGES);
            buildings.put("blacksmith", BuildingType.BLACKSMITH);
            buildings.put("barrack", BuildingType.BARRACK);
            buildings.put("killing pit", BuildingType.KILLING_PIT);
            buildings.put("tent", BuildingType.SIEGE_TENT);
            buildings.put("pitch ditch", BuildingType.PITCH_DITCH);
            buildings.put("armoury", BuildingType.ARMOURY);
            buildings.put("engineer guild", BuildingType.ENGINEER_GUILD);
            buildings.put("fletcher", BuildingType.FLETCHER);
            buildings.put("mercenary post", BuildingType.MERCENARY_POST);
            buildings.put("pole turner", BuildingType.POLE_TURNER);
            buildings.put("stable", BuildingType.STABLE);
            buildings.put("small gate house", BuildingType.SMALL_STONE_GATEHOUSE);
            buildings.put("draw bridge", BuildingType.DRAWBRIDGE);
            buildings.put("lookout tower", BuildingType.LOOKOUT_TOWER);
            buildings.put("circular tower", BuildingType.CIRCLE_TOWER);
            buildings.put("defensive tower", BuildingType.DEFENCE_TURRET);
            buildings.put("square tower", BuildingType.SQUARE_TOWER);
            buildings.put("inn", BuildingType.INN);
            buildings.put("hovel", BuildingType.HOVEL);
            buildings.put("perimeter tower", BuildingType.PERIMETER_TOWER);
            buildings.put("wall", BuildingType.WALL);
        }
        return buildings;
    }

    private static HashMap<BuildingType, Image> setBuildingImages() {
            if (buildingImages.size() == 0) {
                buildingImages.put(BuildingType.IRON_MINE, getImageByName("iron mine"));
                buildingImages.put(BuildingType.MARKET, getImageByName("market"));
                buildingImages.put(BuildingType.OIL_SMELTER, getImageByName("oil smelter"));
                buildingImages.put(BuildingType.OX_TETHER, getImageByName("ox tether"));
                buildingImages.put(BuildingType.QUARRY, getImageByName("quarry"));
                buildingImages.put(BuildingType.STOCKPILE, getImageByName("stock pile"));
                buildingImages.put(BuildingType.WOODCUTTER, getImageByName("wood cutter"));
                buildingImages.put(BuildingType.APPLE_GARDEN, getImageByName("apple garden"));
                buildingImages.put(BuildingType.BAKERY, getImageByName("bakery"));
                buildingImages.put(BuildingType.BREWERY, getImageByName("brewery"));
                buildingImages.put(BuildingType.DAIRY_PRODUCTS, getImageByName("diary product"));
                buildingImages.put(BuildingType.FOOD_STOCKPILE, getImageByName("food stock pile"));
                buildingImages.put(BuildingType.HOPS_FARM, getImageByName("hop field"));
                buildingImages.put(BuildingType.MILL, getImageByName("mill"));
                buildingImages.put(BuildingType.WHEAT_FARM, getImageByName("wheat field"));
                buildingImages.put(BuildingType.CHURCH, getImageByName("church"));
                buildingImages.put(BuildingType.CATHEDRAL, getImageByName("cathedral"));
                buildingImages.put(BuildingType.KEEP, getImageByName("keep"));
                buildingImages.put(BuildingType.ARMOURER, getImageByName("armourer"));
                buildingImages.put(BuildingType.CAGED_WAR_DOGES, getImageByName("caged war dog"));
                buildingImages.put(BuildingType.BLACKSMITH, getImageByName("blacksmith"));
                buildingImages.put(BuildingType.BARRACK, getImageByName("barrack"));
                buildingImages.put(BuildingType.KILLING_PIT, getImageByName("killing pit"));
                buildingImages.put(BuildingType.SIEGE_TENT, getImageByName("tent"));
                buildingImages.put(BuildingType.PITCH_DITCH, getImageByName("pitch ditch"));
                buildingImages.put(BuildingType.ARMOURY, getImageByName("armoury"));
                buildingImages.put(BuildingType.ENGINEER_GUILD, getImageByName("engineer guild"));
                buildingImages.put(BuildingType.FLETCHER, getImageByName("fletcher"));
                buildingImages.put(BuildingType.MERCENARY_POST, getImageByName("mercenary post"));
                buildingImages.put(BuildingType.POLE_TURNER, getImageByName("pole turner"));
                buildingImages.put(BuildingType.STABLE, getImageByName("stable"));
                buildingImages.put(BuildingType.SMALL_STONE_GATEHOUSE, getImageByName("small gate house"));
                buildingImages.put(BuildingType.DRAWBRIDGE, getImageByName("draw bridge"));
                buildingImages.put(BuildingType.LOOKOUT_TOWER, getImageByName("lookout tower"));
                buildingImages.put(BuildingType.CIRCLE_TOWER, getImageByName("circular tower"));
                buildingImages.put(BuildingType.DEFENCE_TURRET, getImageByName("defensive tower"));
                buildingImages.put(BuildingType.SQUARE_TOWER, getImageByName("square tower"));
                buildingImages.put(BuildingType.INN, getImageByName("inn"));
                buildingImages.put(BuildingType.HOVEL, getImageByName("hovel"));
                buildingImages.put(BuildingType.PERIMETER_TOWER, getImageByName("perimeter tower"));
                buildingImages.put(BuildingType.WALL, getImageByName("wall"));
            }
            return buildingImages;
    }


    public static HashMap<Image, String> getChurches() {
        if (churches.size() == 0) {
            churches.put(church, "church");
            churches.put(catheral, "cathedral");
        }
        return churches;
    }

    public static HashMap<Image, String> getFoodBuilding() {
        if (foodBuilding.size() == 0) {
            foodBuilding.put(appleFarm, "apple garden");
            foodBuilding.put(bakery, "bakery");
            foodBuilding.put(brewery, "brewery");
            foodBuilding.put(dairy, "diary product");
            foodBuilding.put(foodStuck, "food stock pile");
            foodBuilding.put(hopFarm, "hop field");
            foodBuilding.put(mill, "mill");
            foodBuilding.put(wheatFarm, "wheat field");
        }
        return foodBuilding;
    }

    public static HashMap<Image, String> getSourceBuilding() {
        if (sourceBuilding.size() == 0) {
            sourceBuilding.put(ironMine, "iron mine");
            sourceBuilding.put(market, "market");
            sourceBuilding.put(oilSmelter, "oil smelter");
            sourceBuilding.put(oxTether, "ox tether");
            sourceBuilding.put(quarry, "quarry");
            sourceBuilding.put(stockPile, "stock pile");
            sourceBuilding.put(woodCutter, "wood cutter");
        }
        return sourceBuilding;
    }

    public static String getNameOfBuildingByImage(String url) {
        for (Image image1 : militaryBuilding.keySet()) {
            if (image1.getUrl().equals(url))
                return militaryBuilding.get(image1);
        }
        for (Image image1 : buildBuilding.keySet()) {
            if (image1.getUrl().equals(url))
                return buildBuilding.get(image1);
        }
        for (Image image1 : churches.keySet()) {
            if (image1.getUrl().equals(url))
                return churches.get(image1);
        }
        for (Image image1 : foodBuilding.keySet()) {
            if (image1.getUrl().equals(url))
                return foodBuilding.get(image1);
        }
        for (Image image1 : sourceBuilding.keySet()) {
            if (image1.getUrl().equals(url))
                return sourceBuilding.get(image1);
        }
        return null;
    }

    public static Image getKeep() {
        return keep;
    }

    public static Image getStockPile() {
        return stockPile;
    }

    public static Image getImageByName(String name) {
        initializeBuildingImage();
        for (Image image1 : militaryBuilding.keySet()) {
            if (militaryBuilding.get(image1).equals(name))
                return image1;
        }
        for (Image image1 : buildBuilding.keySet()) {
            if (buildBuilding.get(image1).equals(name))
                return image1;
        }
        for (Image image1 : churches.keySet()) {
            if (churches.get(image1).equals(name))
                return image1;
        }
        for (Image image1 : foodBuilding.keySet()) {
            if (foodBuilding.get(image1).equals(name))
                return image1;
        }
        for (Image image1 : sourceBuilding.keySet()) {
            if (sourceBuilding.get(image1).equals(name))
                return image1;
        }
        return null;
    }

    private static void initializeBuildingImage() {
        if (sourceBuilding.size() == 0) {
            sourceBuilding.put(ironMine, "iron mine");
            sourceBuilding.put(market, "market");
            sourceBuilding.put(oilSmelter, "oil smelter");
            sourceBuilding.put(oxTether, "ox tether");
            sourceBuilding.put(quarry, "quarry");
            sourceBuilding.put(stockPile, "stock pile");
            sourceBuilding.put(woodCutter, "wood cutter");
        }
        if (foodBuilding.size() == 0) {
            foodBuilding.put(appleFarm, "apple garden");
            foodBuilding.put(bakery, "bakery");
            foodBuilding.put(brewery, "brewery");
            foodBuilding.put(dairy, "diary product");
            foodBuilding.put(foodStuck, "food stock pile");
            foodBuilding.put(hopFarm, "hop field");
            foodBuilding.put(mill, "mill");
            foodBuilding.put(wheatFarm, "wheat field");
        }
        if (churches.size() == 0) {
            churches.put(church, "church");
            churches.put(catheral, "cathedral");
        }
        if (militaryBuilding.size() == 0) {
            militaryBuilding.put(keep, "keep");
            militaryBuilding.put(armourer, "armourer");
            militaryBuilding.put(dogCage, "caged war dog");
            militaryBuilding.put(blackSmith, "blacksmith");
            militaryBuilding.put(barrack, "barrack");
            militaryBuilding.put(killingPit, "killing pit");
            militaryBuilding.put(tent, "tent");
            militaryBuilding.put(pitchDitch, "pitch ditch");
            militaryBuilding.put(armoury, "armoury");
            militaryBuilding.put(engineerGuild, "engineer guild");
            militaryBuilding.put(fletcher, "fletcher");
            militaryBuilding.put(mercenary, "mercenary post");
            militaryBuilding.put(poleturnner, "pole turner");
            militaryBuilding.put(stable, "stable");
        }
        if (buildBuilding.size() == 0) {
            buildBuilding.put(gateHouse, "small gate house");
            buildBuilding.put(drawBridge, "draw bridge");
            buildBuilding.put(lookoutTower, "lookout tower");
            buildBuilding.put(circularTower, "circular tower");
            buildBuilding.put(defensiveTower, "defensive tower");
            buildBuilding.put(squareTower, "square tower");
            buildBuilding.put(inn, "inn");
            buildBuilding.put(hovel, "hovel");
            buildBuilding.put(perimeterTower, "perimeter tower");
            buildBuilding.put(wall, "wall");
        }
    }

    public static BuildingType getBuildingTypeByName(String name) {
        for (String buildingName : setBuildings().keySet()) {
            if (buildingName.equals(name))
                return buildings.get(name);
        }
        return null;
    }

    public static Image getBuildingImageByBuildingType(BuildingType type) {
        for (BuildingType buildingType : setBuildingImages().keySet()) {
            if (type.equals(buildingType))
                return setBuildingImages().get(type);
        }
        return null;
    }
}
