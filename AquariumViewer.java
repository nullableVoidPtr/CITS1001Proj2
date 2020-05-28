/**
 * AquariumViewer represents an interface for playing a game of Aquarium.
 *
 * @author Lyndon While
 * @author Jesse Francis (21829656)
 * @author Aria Warddhana (22984998)
 * @version 2020
 */
import java.awt.*;
import java.awt.event.*; 
import javax.swing.SwingUtilities;

public class AquariumViewer implements MouseListener
{
    private final int BOXSIZE = 40;          // the size of each square
    private final int OFFSET  = BOXSIZE * 2; // the gap around the board
    private       int WINDOWSIZE;            // set this in the constructor 
    private       int BUTTONTOP;
    private       int BUTTONBOTTOM;
    private       int SOLVEBUTTONLEFT;
    private       int SOLVEBUTTONRIGHT;
    private       int CLEARBUTTONLEFT;
    private       int CLEARBUTTONRIGHT;
    
    private Aquarium puzzle;                 // the internal representation of the puzzle
    private int        size;                 // the puzzle is size x size
    private SimpleCanvas sc;                 // the display window
    
    private final Color waterClr    = Color.cyan;
    private final Color airClr      = Color.pink;
    private final Color gridLine    = Color.black;
    private final Color aqumLine    = Color.red;
    private final Color btnClr      = Color.cyan;
    private final Color textClr     = Color.black;
    private final Color bgColor     = Color.white;

    private final int aqumLineWidth = 2;
    /**
     * Main constructor for objects of class AquariumViewer.
     * Sets all fields, and displays the initial puzzle.
     */
    public AquariumViewer(Aquarium puzzle)
    {
        this.puzzle = puzzle;
        size = puzzle.getSize();
        WINDOWSIZE = size * BOXSIZE + 2 * OFFSET;
        sc = new SimpleCanvas(size + " x " + size + " Aquarium Puzzle", WINDOWSIZE, WINDOWSIZE, bgColor);
        sc.addMouseListener(this);
        sc.setFont(new Font("Times", 20, BOXSIZE / 2));
        displayPuzzle();
    }
    
    /**
     * Selects from among the provided files in folder Examples. 
     * xyz selects axy_z.txt. 
     */
    public AquariumViewer(int n)
    {
        this(new Aquarium("Examples/a" + n / 10 + "_" + n % 10 + ".txt"));
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public AquariumViewer()
    {
        this(61);
    }
    
    /**
     * Returns the current state of the puzzle.
     */
    public Aquarium getPuzzle()
    {
        return puzzle;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        return sc;
    }
    
    /**
     * Displays the initial puzzle; see the LMS page for the format.
     */
    private void displayPuzzle()
    {
        displayGrid();
        displayNumbers();
        displayAquariums();
        displayButtons();
    }
    
    /**
     * Displays the grid in the middle of the window.
     */
    public void displayGrid()
    {
        for (int i = 0; i <= size; i++) {
            sc.drawLine(OFFSET, OFFSET + i * BOXSIZE, OFFSET + size * BOXSIZE, OFFSET + i * BOXSIZE, gridLine);
            sc.drawLine(OFFSET + i * BOXSIZE, OFFSET, OFFSET + i * BOXSIZE, OFFSET + size * BOXSIZE, gridLine);
        }
    }
    
    /**
     * Displays the numbers around the grid.
     */
    public void displayNumbers()
    {
        for (int i = 0; i < size; i++) {
            sc.drawString(puzzle.getColumnTotals()[i],
                          OFFSET / 2 + (i + 1) * BOXSIZE + BOXSIZE / 2,
                          OFFSET * 3/4, textClr);
            sc.drawString(puzzle.getRowTotals()[i],
                          OFFSET * 3 / 5,
                          OFFSET * 2/3 + (i + 1) * BOXSIZE + BOXSIZE / 2, textClr);
        }
    }

    /**
     * Display a 'line' of aqumLine Color and aqumLineWidth
     */
    public void displayBoundary(int x1, int y1, int x2, int y2)
    {
        sc.drawRectangle(x1 - aqumLineWidth,
                         y1 - aqumLineWidth,
                         x2 + aqumLineWidth,
                         y2 + aqumLineWidth,
                         aqumLine);
    }
    
    /**
     * Displays the aquariums.
     */
    public void displayAquariums()
    {
        int gridSize = size * BOXSIZE;
        displayBoundary(OFFSET, OFFSET, OFFSET + gridSize, OFFSET);
        displayBoundary(OFFSET, OFFSET, OFFSET, OFFSET + gridSize);

        int aquariums[][] = puzzle.getAquariums();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int bottomRightX = OFFSET + ((c + 1) * BOXSIZE);
                int bottomRightY = OFFSET + ((r + 1) * BOXSIZE);

                if (c == size - 1 || aquariums[r][c] != aquariums[r][c + 1]) {
                    displayBoundary(bottomRightX,
                                    bottomRightY - BOXSIZE,
                                    bottomRightX,
                                    bottomRightY);
                }
                if (r == size - 1 || aquariums[r + 1][c] != aquariums[r][c]) {
                    displayBoundary(bottomRightX - BOXSIZE,
                                    bottomRightY,
                                    bottomRightX,
                                    bottomRightY);
                }
            }
        }
    }

    
    /**
     * Displays the buttons below the grid.
     */
    public void displayButtons()
    {
        int textOffset = BOXSIZE / 8;
        BUTTONTOP = WINDOWSIZE - OFFSET * 2 / 3;
        BUTTONBOTTOM = WINDOWSIZE - OFFSET / 3;
        SOLVEBUTTONLEFT = WINDOWSIZE / 5;
        SOLVEBUTTONRIGHT = WINDOWSIZE * 2 / 5;
        CLEARBUTTONLEFT = WINDOWSIZE * 3 / 5;
        CLEARBUTTONRIGHT = WINDOWSIZE * 4 / 5;

        sc.drawRectangle(SOLVEBUTTONLEFT,  // solve button
                         BUTTONTOP,
                         SOLVEBUTTONRIGHT,
                         BUTTONBOTTOM,
                         btnClr);
        sc.drawRectangle(CLEARBUTTONLEFT,  // clear button
                         BUTTONTOP,
                         CLEARBUTTONRIGHT,
                         BUTTONBOTTOM,
                         btnClr);
        sc.drawString("Solve?",
                      SOLVEBUTTONLEFT + textOffset,
                      BUTTONBOTTOM - textOffset,
                      textClr);
        sc.drawString("Clear?",
                      CLEARBUTTONLEFT + textOffset,
                      BUTTONBOTTOM - textOffset,
                      textClr);
    }
    
    /**
     * Updates the display of Square r,c. 
     * Sets the display of this square to whatever is in the squares array. 
     */
    public void updateSquare(int r, int c)
    {
        if (r < 0 || r >= size || c < 0 || c >= size) return;

        sc.drawRectangle(OFFSET + c * BOXSIZE,
                         OFFSET + r * BOXSIZE,
                         OFFSET + (c + 1) * BOXSIZE,
                         OFFSET + (r + 1) * BOXSIZE, bgColor);

        Space square = puzzle.getSpaces()[r][c];
        if (square == Space.AIR) {
            sc.drawCircle(OFFSET + c * BOXSIZE + BOXSIZE / 2,
                          OFFSET + r * BOXSIZE + BOXSIZE / 2,
                          BOXSIZE / 4,
                          airClr);
        } else if (square == Space.WATER) {
            sc.drawRectangle (OFFSET + c * BOXSIZE,
                              OFFSET + r * BOXSIZE,
                              OFFSET + (c + 1) * BOXSIZE,
                              OFFSET + (r + 1) * BOXSIZE,
                              waterClr);
        }
        displayGrid();
        displayAquariums();
    }
    
    /**
     * Responds to a mouse click. 
     * If it's on the board, make the appropriate move and update the screen display. 
     * If it's on SOLVED?,   check the solution and display the result. 
     * If it's on CLEAR,     clear the puzzle and update the screen display. 
     */
    public void mousePressed(MouseEvent e) 
    {
        int clickX = e.getX();
        int clickY = e.getY();
        
        if (clickX > OFFSET &&
            clickX < WINDOWSIZE - OFFSET &&
            clickY > OFFSET &&
            clickY < WINDOWSIZE - OFFSET) {
            int selTileX = (clickX - OFFSET) / BOXSIZE;
            int selTileY = (clickY - OFFSET) / BOXSIZE;

            if (SwingUtilities.isLeftMouseButton(e)) {
                puzzle.leftClick(selTileY, selTileX);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                puzzle.rightClick(selTileY, selTileX);
            }

            updateSquare(selTileY,selTileX);
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            if (clickY > BUTTONTOP &&
                clickY < BUTTONBOTTOM) {
                sc.drawRectangle(0,0,
                                 WINDOWSIZE,
                                 OFFSET - aqumLineWidth,
                                 bgColor);
                if (clickX > SOLVEBUTTONLEFT &&
                    clickX < SOLVEBUTTONRIGHT) {
                    sc.drawString(CheckSolution.isSolution(puzzle),
                                  WINDOWSIZE / 4,
                                  BOXSIZE,
                                  textClr);
                } else if (clickX > CLEARBUTTONLEFT &&
                    clickX < CLEARBUTTONRIGHT) {
                    this.puzzle.clear();
                    for (int r = 0; r < size ; r++) {
                        for (int c = 0; c < size; c++){
                            this.updateSquare(r, c);
                        }
                    }
                }
                displayNumbers();
            }
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
