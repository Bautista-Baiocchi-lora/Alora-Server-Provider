package org.alora.api.data;

/**
 * Created by Ethan on 3/1/2018.
 */
public enum ItemOptions {
    WEAR(5, "wear"), WEILD(5, "weild"), DROP(58, "drop"), EAT(47, "eat"), USE(22, "use"),
    EXAMINE(1006, "examine"), STORE_1(25, "store 1"), STORE_5(23, "store 5"),
    STORE_10(48, "store 10"), STORE_ALL(7, "store all"), STORE_X(13, "store x"),
    WITHDRAW_X(9, "withdraw-x"), WITHDRAW_ALL(1003, "withdraw-all"),
    SELL_50(13, "sell-50"), SELL_1(23, "sell-1");

    private int index;
    private String name;

    ItemOptions(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
