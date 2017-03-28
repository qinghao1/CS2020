package sg.edu.nus.cs2020;

import java.util.*;

public class CoverageCalculator {
    private ArrayList<Tower> towers = new ArrayList<>();
    private int length = 0;

    CoverageCalculator(int highwaylength) {
        length = highwaylength;
    }
    /*Adds tower to internal towers arraylist.
        @param location location of tower
        @param range range of tower
    */
    void addTower(int location, int range) {
        if (location > length || location < 0) throw new IllegalArgumentException("Tower range is wrong");
        Tower newTower = new Tower(location, range);
        towers.add(newTower);
    }
    /*
    First sorts towers in O(n lg n), then does O(n) sweep
    over the array to get the coverage
    @return total coverage
    */
    int getCoverage() {
        Collections.sort(towers);
        int currentCoverage = 0;
        int currentLength = towers.isEmpty() ?  0 : Math.max(0, towers.get(0).start);
        for (int i = 0; i < towers.size(); i++) {
            Tower currentTower = towers.get(i);
            //Skips current iteration if current tower is already totally covered.
            if (currentTower.end <= currentLength) continue;
            //Breaks the loop if end of highway is already covered
            if (currentTower.end >= length) {
                currentCoverage += (length - Math.max(currentTower.start, currentLength));
                break;
            } else {
                currentCoverage += (currentTower.end - Math.max(currentTower.start, currentLength));
                currentLength = currentTower.end;
            }
        }
        return currentCoverage;
    }
}
