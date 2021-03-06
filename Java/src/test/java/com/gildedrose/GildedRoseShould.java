package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseShould {

    public static final int NOT_RELEVANT_SELL_IN = 0;
    public static final int INITIAL_LEGENDARY_QUALITY = 80;
    private static final int DEFAULT_QUALITY = 0;
    private static final int DEFAULT_QUALITY_INCREASED_AFTER_A_DAY = 1;
    private static final int DEFAULT_SELL_IN = 2;
    String goldenMaster = "OMGHAI!\n" +
            "-------- day 0 --------\n" +
            "name, sellIn, quality\n" +
            "+5 Dexterity Vest, 10, 20\n" +
            "Aged Brie, 2, 0\n" +
            "Elixir of the Mongoose, 5, 7\n" +
            "Sulfuras, Hand of Ragnaros, 0, 80\n" +
            "Sulfuras, Hand of Ragnaros, -1, 80\n" +
            "Backstage passes to a TAFKAL80ETC concert, 15, 20\n" +
            "Backstage passes to a TAFKAL80ETC concert, 10, 49\n" +
            "Backstage passes to a TAFKAL80ETC concert, 5, 49\n" +
            "Conjured Mana Cake, 3, 6\n" +
            "\n" +
            "-------- day 1 --------\n" +
            "name, sellIn, quality\n" +
            "+5 Dexterity Vest, 9, 19\n" +
            "Aged Brie, 1, 1\n" +
            "Elixir of the Mongoose, 4, 6\n" +
            "Sulfuras, Hand of Ragnaros, 0, 80\n" +
            "Sulfuras, Hand of Ragnaros, -1, 80\n" +
            "Backstage passes to a TAFKAL80ETC concert, 14, 21\n" +
            "Backstage passes to a TAFKAL80ETC concert, 9, 50\n" +
            "Backstage passes to a TAFKAL80ETC concert, 4, 50\n" +
            "Conjured Mana Cake, 2, 5\n\n";

    @Test
    void follow_golden_master_output() {
        String result = "OMGHAI!\n";

        Item[] items = new Item[]{
                new Item("+5 Dexterity Vest", 10, 20), //
                new Item("Aged Brie", 2, 0), //
                new Item("Elixir of the Mongoose", 5, 7), //
                new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                // this conjured item does not work properly yet
                new Item("Conjured Mana Cake", 3, 6)};


        GildedRose app = new GildedRose(items);

        int days = 2;
        for (int i = 0; i < days; i++) {
            result += "-------- day " + i + " --------\n";
            result += "name, sellIn, quality\n";
            for (Item item : items) {
                result += item + "\n";
            }
            result += "\n";
            app.updateQuality();
        }

        assertEquals(goldenMaster, result);

    }

    @Test
    void never_modify_quality_for_sulfuras_after_one_day() {
        Item[] items = new Item[]{
                sulfuras()
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(INITIAL_LEGENDARY_QUALITY, items[0].quality);
    }

    @Test
    void never_modify_quality_for_sulfuras_after_several_days() {
        Item[] items = new Item[]{
                sulfuras()
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        app.updateQuality();

        assertEquals(INITIAL_LEGENDARY_QUALITY, items[0].quality);
    }

    @Test
    void never_modify_sell_in_value_for_sulfuras_after_one_day() {
        Item[] items = new Item[]{
                sulfuras()
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(NOT_RELEVANT_SELL_IN, items[0].sellIn);
    }

    @Test
    void never_modify_sell_in_value_for_sulfuras_after_several_days() {
        Item[] items = new Item[]{
                sulfuras()
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        app.updateQuality();
        app.updateQuality();

        assertEquals(NOT_RELEVANT_SELL_IN, items[0].sellIn);
    }

    @Test
    void increase_quality_for_aged_brie_after_a_day() {
        Item agedBrie = new Item("Aged Brie", DEFAULT_SELL_IN, DEFAULT_QUALITY);
        Item[] items = new Item[]{
                agedBrie
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(DEFAULT_QUALITY_INCREASED_AFTER_A_DAY, agedBrie.quality);
    }

    private Item sulfuras() {
        return new Item("Sulfuras, Hand of Ragnaros", NOT_RELEVANT_SELL_IN, INITIAL_LEGENDARY_QUALITY);
    }
}
