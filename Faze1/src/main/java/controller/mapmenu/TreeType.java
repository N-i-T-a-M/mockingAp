package controller.mapmenu;

import model.map.Tree;

import java.util.regex.Pattern;

public enum TreeType {
    DESERT_SHRUB("^desertShrub$", Tree.DESERT_SHRUB),
    CHERRY("^cherry$", Tree.CHERRY),
    OLIVE("^olive$", Tree.OLIVE),
    COCONUT("^coconut$", Tree.COCONUT),
    DATE("^date$", Tree.DATE);

    private final String regex;

    private final Tree tree;

    TreeType(String regex, Tree tree) {
        this.regex = regex;
        this.tree = tree;
    }

    public static boolean hasMatcher(String string, TreeType treeType) {
        return Pattern.compile(treeType.regex).matcher(string).find();
    }

    public Tree getTree() {
        return tree;
    }
}
