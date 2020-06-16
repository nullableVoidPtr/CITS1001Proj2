
/**
 * CheckSolution is a utility class which can check if
 * a board position in an Aquarium puzzle is a solution.
 *
 * @author Lyndon While
 * @author Jesse Francis (21829656)
 * @author Aria Warddhana (22984998)
 * @version 2020
 */

import java.util.Arrays;

public class CheckSolution
{
    /**
     * Non-constructor for objects of class CheckSolution
     */
    private CheckSolution(){}
    
    /**
     * Returns the number of water squares in each row of Aquarium puzzle p, top down.
     */
    public static int[] rowCounts(Aquarium p)
    {
        int size = p.getSize();
        int[] counts = new int[size];
        Space[][] spaces = p.getSpaces();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (spaces[r][c] == Space.WATER) {
                    counts[r] += 1;
                }
            }
        }

        return counts;
    }
    
    /**
     * Returns the number of water squares in each column of Aquarium puzzle p, left to right.
     */
    public static int[] columnCounts(Aquarium p)
    {
        int size = p.getSize();
        int[] counts = new int[size];
        Space[][] spaces = p.getSpaces();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (spaces[r][c] == Space.WATER) {
                    counts[c] += 1;
                }
            }
        }

        return counts;
    }
    
    /**
     * Returns a 2-int array denoting the collective status of the spaces 
     * in the aquarium numbered t on Row r of Aquarium puzzle p. 
     * The second element will be the column index c of any space r,c which is in t, or -1 if there is none. 
     * The first element will be: 
     * 0 if there are no spaces in t on Row r; 
     * 1 if they're all water; 
     * 2 if they're all not-water; or 
     * 3 if they're a mixture of water and not-water. 
     */
	// CLARITY: return error/status when found
    public static int[] rowStatus(Aquarium p, int t, int r)
    {
        int size = p.getSize();
        Space[] spaces = p.getSpaces()[r];
        int[] aquariums = p.getAquariums()[r];
        int firstSpaceIdx = -1;
        boolean foundWater = false;
        boolean foundOther = false;
        for (int c = 0; c < size; c++) {
            if (aquariums[c] == t) {
                if (firstSpaceIdx == -1) {
                    firstSpaceIdx = c;
                }

                if (spaces[c] == Space.WATER) {
                    foundWater = true;
                } else {
                    foundOther = true;
                }
            }
        }

        int status = 0;
        if (firstSpaceIdx != -1) {
            if (foundWater) {
                status += 1;
            }
            if (foundOther) {
                status += 2;
            }
        }

        return new int[]{status, firstSpaceIdx};
    }
    
    /**
     * Returns a statement on whether the aquarium numbered t in Aquarium puzzle p is OK. 
     * Every row must be either all water or all not-water, 
     * and all water must be below all not-water. 
     * Returns "" if the aquarium is ok; otherwise 
     * returns the indices of any square in the aquarium, in the format "r,c". 
     */
    public static String isAquariumOK(Aquarium p, int t)
    {
        int size = p.getSize();
        boolean underwater = false;
        for (int r = 0; r < size; r++) {
            int[] result = rowStatus(p, t, r);
            int status = result[0];
            int column = result[1];
            if (status != 0) {
                if (status == 1 && !underwater) {
                    underwater = true;
                } else if ((status == 2 && underwater) || status == 3) {
                    return r + "," + column;
                }
            }
        }

        return "";
    }
    
    /**
     * Returns a statement on whether we have a correct solution to Aquarium puzzle p. 
     * Every row and column must have the correct number of water squares, 
     * and all aquariums must be OK. 
     * Returns three ticks if the solution is correct; 
     * otherwise see the LMS page for the expected results. 
     */
	// CORRECTNESS: 2/3, doesn't pass one of 3 tests
    public static String isSolution(Aquarium p)
    {
        int mismatchIdx = Arrays.mismatch(rowCounts(p), p.getRowTotals());
        if (mismatchIdx != -1) {
            return "Row " + mismatchIdx + " is wrong";
        }
        mismatchIdx = Arrays.mismatch(columnCounts(p), p.getColumnTotals());
        if (mismatchIdx != -1) {
            return "Column " + mismatchIdx + " is wrong";
        }

        int size = p.getSize();
        int aquariums[][] = p.getAquariums();
        for (int a = 0; a < aquariums[size - 1][size - 1]; a++) {
            String aquariumSpace = isAquariumOK(p, a);
            if (!aquariumSpace.isEmpty()) {
                return "The aquarium at " + aquariumSpace + " is wrong";
            }
        }

        return "\u2713\u2713\u2713";
    }
}
