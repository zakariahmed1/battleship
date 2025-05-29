package application;

import java.util.ArrayList;
import java.util.List;

public class InputParser
{

    /**
     * Parses a ship selection index from the given input string.
     *
     * @param input the string representation of an index corresponding to a supported ship
     * @return the ship instance corresponding to the given index
     * @throws IllegalArgumentException if the input is not valid or the given index does not correspond to a ship.
     */
    public static Ship parseShip(String input) throws IllegalArgumentException {
        try
        {
            int shipId = Integer.parseInt(input);
            Ship ship = ShipBuilder.create(shipId-1);
            return ship;
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid ship selection!");
        }
    }

    /**
     * Parses a String of coordinates to a List of cells.
     * The input coordinates must have the following format:
     *
     * single cell: x,y
     * range of cells: x1,y1-x2,y2
     *
     * @param input the input string representing the coordinates
     * @return a List of cells corresponding to the coordinates or range
     * @throws IllegalArgumentException if:
     * - the input format is not correct
     * - the input coordinates (range) does not corrispond to the ship size
     */
    public static List<Cell> parseCoordinates(String input)
    {
        String[] fromTo = input.split("-");
        if (fromTo.length <= 2)
        {
            int x1, y1, x2, y2;
            try
            {
                x1 = parseXCoordinate(fromTo[0]);
                y1 = parseYCoordinate(fromTo[0]);
                if (fromTo.length == 2)
                {
                    x2 = parseXCoordinate(fromTo[1]);
                    y2 = parseYCoordinate(fromTo[1]);
                }
                else
                {
                    x2 = x1;
                    y2 = y1;
                }
                return getCellsFromRange(x1, y1, x2, y2);
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Please enter valid coordinates in the format x1,y1 or x1,y1-x2,y2 for a range!");
            }
        }
        return null;
    }


    /**
     * Creates a List of cells out of the given coordinate range
     *
     * @param x1 and y1 are the starting point, x2 and y2 the end of the range
     * @return a List of Cells created from the range.
     * @throws IllegalArgumentException if the range does not correspond to the chosen ship size
     */
    public static List<Cell> getCellsFromRange(int x1, int y1, int x2, int y2) {
        int deltaX = Integer.compare(x2,x1);//x2==x1 ? 0 : ((x2 < x1) ? -1 : 1) ;
        int deltaY = Integer.compare(y2,y1);// y2==y1 ? 0 : ((y2 < y1) ? -1 : 1);

        if (Integer.max(Math.abs(x2-x1),Math.abs(y2-y1)) > ShipBuilder.getMaxSupportedShipSize())
            throw new IllegalArgumentException("Range too high!");

        List<Cell> range = new ArrayList<>();

        while (x1 != x2 || y1 != y2) {
            range.add(new Cell(x1,y1));
            x1 += deltaX;
            y1 += deltaY;
        }
        range.add(new Cell(x1,y1));
        return range;
    }


    /*
     * parses the x coordinate out of the following format: x,y
     */
    private static int parseXCoordinate(String coord) throws NumberFormatException {
        return Integer.parseInt(coord.split(",")[0]);
    }

    /*
     * parses the y coordinate out of the following format: x,y
     */

    private static int parseYCoordinate(String coord) {
        return Integer.parseInt(coord.split(",")[1]);
    }


}
