package com.arithfighter.not.entity.pentagram;

import java.util.List;

public class PlaceMarkController {
    private final PlaceMark[] placeMarks;
    private EnchantmentAssociateIndexes enchantmentAssociateIndexes;
    private int selectedPlaceMarkIndex;

    public PlaceMarkController(PlaceMark[] placeMarks) {
        this.placeMarks = placeMarks;
    }

    public void setSelectedPlaceMarkIndex(int selectedPlaceMarkIndex) {
        this.selectedPlaceMarkIndex = selectedPlaceMarkIndex;

        enchantmentAssociateIndexes = new EnchantmentAssociateIndexes();
    }

    public void setLevelOfNearbyEnchantments() {
        int[] indexes = getAssociateEnchantmentIndexes(selectedPlaceMarkIndex);

        for (int index : indexes) {
            EnchantmentLevel nearbyLevel = placeMarks[index].getLevel();

            if (isNearbyLevelEqualToSelectedLevel(nearbyLevel))
                bringDownLevelOfEnchantment(nearbyLevel, index);
        }
    }

    private boolean isNearbyLevelEqualToSelectedLevel(EnchantmentLevel nearbyLevel){
        return placeMarks[selectedPlaceMarkIndex].getLevel() == nearbyLevel;
    }

    private void bringDownLevelOfEnchantment(EnchantmentLevel level, int index){
        switch (level){
            case HIGH:
                placeMarks[index].setLevel(EnchantmentLevel.MID);
                break;
            case MID:
                placeMarks[index].setLevel(EnchantmentLevel.LOW);
                break;
            case LOW:
                placeMarks[index].setLevel(EnchantmentLevel.NONE);
                break;
        }
    }

    private int[] getAssociateEnchantmentIndexes(int placeMarkIndex) {
        return enchantmentAssociateIndexes.getEnchantmentAssociateIndexes()[placeMarkIndex];
    }

    public boolean allPlaceMarksAreLevelNone() {
        int cursor = 0;

        for (PlaceMark placeMark : placeMarks) {
            if (placeMark.getLevel() == EnchantmentLevel.NONE)
                cursor++;
        }
        return cursor == placeMarks.length;
    }

    public void setLevelForPlaceMarks() {
        List<EnchantmentLevel> levelList =
                new PlaceMarkLevelProducer(placeMarks.length).getLevelList();

        for (int i = 0; i < placeMarks.length; i++)
            placeMarks[i].setLevel(levelList.get(i));

        setCenterOfPentagramToHighLevel();
    }

    private void setCenterOfPentagramToHighLevel() {
        int midIndex = placeMarks.length / 2 - 1;

        placeMarks[midIndex].setLevel(EnchantmentLevel.HIGH);
    }
}

class EnchantmentAssociateIndexes {
    private final int[][] enchantmentAssociateIndexes;

    public EnchantmentAssociateIndexes() {
        enchantmentAssociateIndexes = new int[][]{
                new int[]{1, 3},
                new int[]{0, 4},
                new int[]{0, 1, 3, 4, 5},
                new int[]{0, 5},
                new int[]{1, 5},
                new int[]{3, 4},
        };
    }

    public int[][] getEnchantmentAssociateIndexes() {
        return enchantmentAssociateIndexes;
    }
}