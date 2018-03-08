package org.alora.api.data;

/**
 * Created by Ethan on 3/1/2018.
 */
public enum ItemOptions {
    WEAR(5, "wear"), WIELD(5, "wield"), DROP(58, "drop"), EAT(47, "eat"), USE(22, "use"),
    EXAMINE(1006, "examine"), STORE_1(25, "store 1"), STORE_5(23, "store 5"),
    STORE_10(48, "store 10"), STORE_ALL(7, "store all"), STORE_X(13, "store x"),
    WITHDRAW_1(1, "withdraw-1"), WITHDRAW_5(5, "withdraw-5"),
    WITHDRAW_X(9, "withdraw-x"), WITHDRAW_ALL(1003, "withdraw-all"), WITHDRAW_10(10, "withdraw-10"),
    SELL_50(13, "sell-50"), SELL_1(23, "sell-1"), OPEN(47, "open"), CAST(3, "cast");

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
