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
    
    private Aquarium puzzle;                 // the internal representation of the puzzle
    private int        size;                 // the puzzle is size x size
    private SimpleCanvas sc;                 // the display window
    
    private final Color waterClr    = Color.blue;
    private final Color airClr      = Color.green;
    private final Color gridLine    = Color.black;
    private final Color aqumLine    = Color.red;
    private final Color btnClr      = Color.cyan;
    private final Color textClr     = Color.black;
    private final Color bgColor     = Color.white;
    /**
     * Main constructor for objects of class AquariumViewer.
     * Sets all fields, and displays the initial puzzle.
     */
    public AquariumViewer(Aquarium puzzle)
    {
        // TODO 8
        this.puzzle = puzzle;
        size = puzzle.getSize();
        this.WINDOWSIZE = size * BOXSIZE + 2 * OFFSET;
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
        // TODO 7a
        return puzzle;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        // TODO 7b
        return size;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        // TODO 7c
        return sc;
    }
    
    /**
     * Displays the initial puzzle; see the LMS page for the format.
     */
    private void displayPuzzle()
    {
        // TODO 13
        displayGrid();
        displayAquariums();
        displayNumbers();
        displayButtons();
    }
    
    /**
     * Displays the grid in the middle of the window.
     */
    public void displayGrid()
    {
        // TODO 9
        for (int i = 0 ; i <= size; i++)
            {
            sc.drawLine(OFFSET, OFFSET + i * BOXSIZE, OFFSET + size * BOXSIZE, OFFSET + i * BOXSIZE, gridLine);
            sc.drawLine(OFFSET + i * BOXSIZE, OFFSET, OFFSET + i * BOXSIZE, OFFSET + size * BOXSIZE, gridLine);
            }
    }
    
    /**
     * Displays the numbers around the grid.
     */
    public void displayNumbers()
    {
        // TODO 10
        for (int i = 1 ; i <= size; i++)
            {
            sc.drawString(puzzle.getColumnTotals()[i-1],
                          OFFSET / 2 + i * BOXSIZE + BOXSIZE / 2,
                          OFFSET * 3/4, textClr);
            sc.drawString(puzzle.getRowTotals()[i-1],
                          OFFSET * 3 / 5,
                          OFFSET * 2/3 + i * BOXSIZE + BOXSIZE / 2, textClr);
            }
    }
    
    /**
     * Displays the aquariums.
     */
    public void displayAquariums()
    {
        // TODO 11
        for (int k = -1; k<=1; k++)     // 3 iterations to draw wider lines
            {
            sc.drawLine(OFFSET,                         // draw top border
                        OFFSET + k,
                        OFFSET + size * BOXSIZE,
                        OFFSET + k,
                        aqumLine); 
            sc.drawLine(OFFSET + k,                     // draw LHS border
                        OFFSET,
                        OFFSET + k,
                        OFFSET + size * BOXSIZE,
                        aqumLine);
            sc.drawLine(OFFSET,                         // draw btm border
                        OFFSET + size * BOXSIZE + k,
                        OFFSET + size * BOXSIZE,
                        OFFSET + size * BOXSIZE + k,
                        aqumLine);
            sc.drawLine(OFFSET + size * BOXSIZE + k,    // draw RHS border
                        OFFSET,
                        OFFSET + size * BOXSIZE + k,
                        OFFSET + size * BOXSIZE,
                        aqumLine);
            
             for (int i = 0; i < size - 1; i++)
                for (int j = 0; j < size; j++)          // draw bot box wall
                {   
                    if (puzzle.getAquariums()[i][j] !=
                        puzzle.getAquariums()[i+1][j]) 
                        sc.drawLine(OFFSET + j*BOXSIZE,
                                    OFFSET + i*BOXSIZE + BOXSIZE + k,
                                    OFFSET + j*BOXSIZE + BOXSIZE,
                                    OFFSET + i*BOXSIZE + BOXSIZE + k,
                                    aqumLine);
                }
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size -1; j++)       // draw RHS box wall
                {   
                    if (puzzle.getAquariums()[i][j] !=
                        puzzle.getAquariums()[i][j+1])
                        sc.drawLine(OFFSET + j*BOXSIZE + BOXSIZE + k,
                                    OFFSET + i*BOXSIZE,
                                    OFFSET + j*BOXSIZE + BOXSIZE + k,
                                    OFFSET + i*BOXSIZE + BOXSIZE,
                                    aqumLine);
                } 
            }
    }
    
    /**
     * Displays the buttons below the grid.
     */
    public void displayButtons()
    {
        // TODO 12
        int textOffset = BOXSIZE / 8;
        sc.drawRectangle(WINDOWSIZE * 1/5,  // solve button
                         WINDOWSIZE - OFFSET * 2/3,
                         WINDOWSIZE * 2/5,
                         WINDOWSIZE - OFFSET * 1/3,
                         btnClr);
        sc.drawRectangle(WINDOWSIZE * 3/5,  // clear button
                         WINDOWSIZE - OFFSET * 2/3,
                         WINDOWSIZE * 4/5,
                         WINDOWSIZE - OFFSET * 1/3,
                         btnClr);
        sc.drawString   ("Solve?",             // solve button text
                         WINDOWSIZE * 1/5 + textOffset,  
                         WINDOWSIZE - OFFSET * 1/3 - textOffset,
                         textClr);
        sc.drawString   ("Clear?",             // clear button text
                         WINDOWSIZE * 3/5 + textOffset,  
                         WINDOWSIZE - OFFSET * 1/3 - textOffset,
                         textClr);
    }
    
    /**
     * Updates the display of Square r,c. 
     * Sets the display of this square to whatever is in the squares array. 
     */
    public void updateSquare(int r, int c)
    {
        // TODO 14
        if (puzzle.getSpaces()[r][c] == Space.EMPTY)
            sc.drawRectangle (OFFSET + c*BOXSIZE,
                              OFFSET + r*BOXSIZE,
                              OFFSET + c*BOXSIZE + BOXSIZE,
                              OFFSET + r*BOXSIZE + BOXSIZE, bgColor);
        if (puzzle.getSpaces()[r][c] == Space.AIR)
        {
            sc.drawRectangle (OFFSET + c*BOXSIZE,
                              OFFSET + r*BOXSIZE,
                              OFFSET + c*BOXSIZE + BOXSIZE,
                              OFFSET + r*BOXSIZE + BOXSIZE, bgColor);
            sc.drawCircle(    OFFSET + c*BOXSIZE + BOXSIZE / 2,
                              OFFSET + r*BOXSIZE + BOXSIZE / 2,
                              BOXSIZE / 3, airClr);
        }                 
        if (puzzle.getSpaces()[r][c] == Space.WATER)
            sc.drawRectangle (OFFSET + c*BOXSIZE,
                              OFFSET + r*BOXSIZE,
                              OFFSET + c*BOXSIZE + BOXSIZE,
                              OFFSET + r*BOXSIZE + BOXSIZE, waterClr);
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
        // TODO 15
        boolean onBoard = false;
        boolean onSolve = false;
        boolean onClear = false;
        
        if (e.getX() > OFFSET)
            if(e.getX() < WINDOWSIZE - OFFSET)
                if (e.getY() > OFFSET)
                    if (e.getY() < WINDOWSIZE - OFFSET)
                onBoard = true;
        if (onBoard != true)
            if (e.getX() > WINDOWSIZE * 1/5 &&
                e.getX() < WINDOWSIZE * 2/5)
                if (e.getY() > WINDOWSIZE - OFFSET * 2/3 &&
                    e.getY() < WINDOWSIZE - OFFSET * 1/3)
                    onSolve = true;
        if (onBoard != true)    
            if (e.getX() > WINDOWSIZE * 3/5 &&
                e.getX() < WINDOWSIZE * 4/5)
                if (e.getY() > WINDOWSIZE - OFFSET * 2/3 &&
                    e.getY() < WINDOWSIZE - OFFSET * 1/3)
                    onClear = true;
        if (SwingUtilities.isLeftMouseButton(e))
           if (onBoard)
           {
            int selTileX = 0;
            for (int i = 0; i < size; i++)
                if (e.getX() > OFFSET + i * BOXSIZE &&
                    e.getX() < OFFSET + i * BOXSIZE + BOXSIZE)
                selTileX = i;
        
                int selTileY = 0;
                for (int j = 0; j < size; j++)
            if (e.getY() > OFFSET + j * BOXSIZE &&
                e.getY() < OFFSET + j * BOXSIZE + BOXSIZE)
                selTileY = j;
                       puzzle.leftClick(selTileY, selTileX);
                       updateSquare(selTileY,selTileX);
           }               
           if (onSolve)
           {
              sc.drawRectangle (0,0,
                                WINDOWSIZE,
                                OFFSET - 1,
                                bgColor);
                  sc.drawString(CheckSolution.isSolution(puzzle),
                                WINDOWSIZE * 1/4,  
                                OFFSET * 1/3,
                                textClr);
              displayNumbers();
           }           
           if (onClear)
           {
              this.puzzle.clear();
              for (int i = 0; i < size ; i++)
                    for (int j = 0; j < size; j++)
                    this.updateSquare(i, j);
              sc.drawRectangle (0,0,
                                WINDOWSIZE,
                                OFFSET - 1,
                                bgColor);
              displayNumbers();
           }  
        if (SwingUtilities.isRightMouseButton(e))
        {
           if (onBoard)
           {
            int selTileX = 0;
            for (int i = 0; i < size; i++)
                if (e.getX() > OFFSET + i * BOXSIZE &&
                    e.getX() < OFFSET + i * BOXSIZE + BOXSIZE)
                selTileX = i;
        
                int selTileY = 0;
                for (int j = 0; j < size; j++)
            if (e.getY() > OFFSET + j * BOXSIZE &&
                e.getY() < OFFSET + j * BOXSIZE + BOXSIZE)
                selTileY = j;
                       puzzle.rightClick(selTileY, selTileX);
                       updateSquare(selTileY,selTileX);
           }           
                       
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
