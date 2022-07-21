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

    public void bringDownLevelOfNearbyEnchantments(){
        int[] indexes = getAssociateEnchantmentIndexes(selectedPlaceMarkIndex);
        for (int index : indexes) {
            EnchantmentLevel level = placeMarks[index].getLevel();

            if (level == EnchantmentLevel.HIGH)
                placeMarks[index].setLevel(EnchantmentLevel.MID);
            if (level == EnchantmentLevel.MID)
                placeMarks[index].setLevel(EnchantmentLevel.LOW);
        }
    }

    private int[] getAssociateEnchantmentIndexes(int placeMarkIndex){
        int[][] pentagramAssociateIndexes = new int[][]{
                new int[]{1,3},
                new int[]{0,4},
                new int[]{0,1,3,4,5},
                new int[]{0,5},
                new int[]{1,5},
                new int[]{3,4},
        };
        return pentagramAssociateIndexes[placeMarkIndex];
    }

    public boolean allPlaceMarksAreLevelNone() {
        int cursor = 0;

        for (PlaceMark placeMark : placeMarks) {
            if (placeMark.getLevel() == EnchantmentLevel.NONE)
                cursor++;
        }
        return cursor == placeMarks.length;
    }

    public void setPlaceMarks() {
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
