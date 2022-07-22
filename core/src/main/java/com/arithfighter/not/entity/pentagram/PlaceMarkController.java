package com.arithfighter.not.entity.pentagram;

import java.util.List;

public class PlaceMarkController {
    private final PlaceMark[] placeMarks;
    private int selectedPlaceMarkIndex;

    public PlaceMarkController(PlaceMark[] placeMarks) {
        this.placeMarks = placeMarks;
    }

    public void setSelectedPlaceMarkIndex(int selectedPlaceMarkIndex) {
        this.selectedPlaceMarkIndex = selectedPlaceMarkIndex;
    }

    public void changeLevelOfNearbyEnchantments() {
        int[] indexes =
                new EnchantmentAssociateIndexes().getEnchantmentAssociateIndexes()[selectedPlaceMarkIndex];

        for (int index : indexes) {
            EnchantmentLevel nearbyLevel = placeMarks[index].getLevel();

            if (isNearbyLevelEqualToSelectedLevel(nearbyLevel))
                bringDownEnchantmentLevel(nearbyLevel, index);
        }
    }

    private boolean isNearbyLevelEqualToSelectedLevel(EnchantmentLevel nearbyLevel){
        return placeMarks[selectedPlaceMarkIndex].getLevel() == nearbyLevel;
    }

    private void bringDownEnchantmentLevel(EnchantmentLevel level, int index){
        EnchantmentLevel[] enchantmentLevels = EnchantmentLevel.values();

        for(int i = 0;i<enchantmentLevels.length;i++){
            if (level == enchantmentLevels[i] && i!=0)
                placeMarks[index].setLevel(enchantmentLevels[i-1]);
        }
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
        PlaceMarkLevelProducer placeMarkLevelProducer = new PlaceMarkLevelProducer(placeMarks.length);
        placeMarkLevelProducer.setMidLevelQuantity(2);

        List<EnchantmentLevel> levelList = placeMarkLevelProducer.getLevelList();

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