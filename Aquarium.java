/**
 * Aquarium represents a single problem in the game Aquarium.
 *
 * @author Lyndon While
 * @author Aria Warddhana
 * @version 2020
 */

import java.util.ArrayList;

public class Aquarium
{
    private int   size;         // the board is size x size
    private int[] columnTotals; // the totals at the top of the columns, left to right
    private int[] rowTotals;    // the totals at the left of the rows, top to bottom 
    
    // the board divided into aquariums, numbered from 1,2,3,...
    // spaces with the same number are part of the same aquarium
    private int[][] aquariums;
    // the board divided into spaces, each empty, water, or air
    private Space[][] spaces;

    /**
     * Constructor for objects of class Aquarium. 
     * Creates, initialises, and populates all of the fields.
     */
    public Aquarium(String filename)
    {
        ArrayList<String> lines = new FileIO(filename).getLines();

        columnTotals = parseLine(lines.get(0));
        rowTotals = parseLine(lines.get(1));
        size = columnTotals.length;

        aquariums = new int[size][];
        for (int i = 3; i < lines.size(); i++) {
            aquariums[i - 3] = parseLine(lines.get(i));
        }

        spaces = new Space[size][size];
        clear();
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public Aquarium()
    {
        this("Examples/a6_1.txt");
    }

    /**
     * Returns an array containing the ints in s, 
     * each of which is separated by one space. 
     * e.g. if s = "1 299 34 5", it will return {1,299,34,5} 
     */
    public static int[] parseLine(String s)
    {
        String tokens[] = s.split(" ");
        int result[] = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            result[i] = Integer.parseInt(tokens[i]);
        }

        return result;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        return size;
    }
    
    /**
     * Returns the column totals.
     */
    public int[] getColumnTotals()
    {
        return columnTotals;
    }
    
    /**
     * Returns the row totals.
     */
    public int[] getRowTotals()
    {
        return rowTotals;
    }
    
    /**
     * Returns the board in aquariums.
     */
    public int[][] getAquariums()
    {
        return aquariums;
    }
    
    /**
     * Returns the board in spaces.
     */
    public Space[][] getSpaces()
    {
        return spaces;
    }

    /**
     * Iff both r and c are legal indices, toggle a square. If the space is already
     * the target, it becomes empty; otherwise, it becomes the target.
     */
    public void toggleSpace(int r, int c, Space targetState) {
        if (r < 0 || r >= size) return;
        if (c < 0 || c >= size) return;

        if (spaces[r][c] == targetState) {
            spaces[r][c] = Space.EMPTY;
        } else {
            spaces[r][c] = targetState;
        }
    }

    /**
     * Performs a left click on Square r,c if the indices are legal, o/w does nothing. 
     * A water space becomes empty; other spaces become water. 
     */
    public void leftClick(int r, int c)
    {
        toggleSpace(r, c, Space.WATER);
    }
    
    /**
     * Performs a right click on Square r,c if the indices are legal, o/w does nothing. 
     * An air space becomes empty; other spaces become air. 
     */
    public void rightClick(int r, int c)
    {
        toggleSpace(r, c, Space.AIR);
    }
    
    /**
     * Empties all of the spaces.
     */
    public void clear()
    {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                spaces[r][c] = Space.EMPTY;
            }
        }
    }
}
