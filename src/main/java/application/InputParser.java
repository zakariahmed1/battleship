package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputParser
{

    /**
     * Parses a ship selection index from the given input string.
     *
     * @param input the string representation of an index corresponding to a supported ship
     * @return the ship instance corresponding to the given index
     * @throws IllegalArgumentException if the input is not valid or the given index does not correspond to a ship.
     * @throws CommandException if the input string is a supported game command
     *
     */
    public static Ship parseShip(String input) throws IllegalArgumentException, CommandException {
        try
        {
            throwIfInputIsCommand(input); //throws CommandException

            int shipId = Integer.parseInt(input);
            Ship ship = ShipBuilder.create(shipId-1);
            return ship;
        }
        catch (NumberFormatException | NullPointerException e) {
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
     * @return a List (vector) of cells corresponding to the range of the given coordinates
     * @throws IllegalArgumentException if:
     * - the input format is not correct
     * - the input contains negative numbers
     * - the input is not a perfect 45° diagonal
     * @throws CommandException if the input string is a supported game command
     */
    public static List<Cell> parseCoordinatesRange(String input) throws IllegalArgumentException, CommandException
    {
        String error = "Please enter valid coordinates in the format x1,y1 or x1,y1-x2,y2 for a range!";

        throwIfInputIsCommand(input);

        String[] fromToSplit = input.split("-");

        if (fromToSplit == null || fromToSplit.length == 0 || fromToSplit.length > 2)
            throw new IllegalArgumentException(error);

        try {
            int x1 = parseXCoordinate(fromToSplit[0]);
            int y1 = parseYCoordinate(fromToSplit[0]);
            int x2 = x1;
            int y2 = y1;

            if (fromToSplit.length == 2) {
                x2 = parseXCoordinate(fromToSplit[1]);
                y2 = parseYCoordinate(fromToSplit[1]);
            }
            return getCellsFromRange(x1,y1,x2,y2);
        }
        catch(NumberFormatException | NullPointerException e) {throw new IllegalArgumentException(error);}

        }

    /**
     * Creates a Cell object out of String coordinates.
     * @param input the input String representing the coordinates
     * @return The cell that corresponds to the input coordinates.
     * @throws IllegalArgumentException If the input is not valid
     * @throws CommandException if the input string is a supported game command
     */
    public static Cell parseCoordinates(String input) throws IllegalArgumentException, CommandException{
        try {
            List<Cell> a = parseCoordinatesRange(input);
            if (a == null || a.size() != 1)
                throw new IllegalArgumentException("Invalid Coordinates");
            return a.get(0);
        }
        catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid coordinates!");
        }
    }


    /**
     * Creates a List (vector) of cells out of the given coordinate range
     *
     * @param x1 the starting x-coordinate
     * @param y1 the starting y-coordinate
     * @param x2 the ending x-coordinate
     * @param y2 the ending y-coordinate
     * @return a List (vector) of cells created from the range.
     //@throws IllegalArgumentException if:
        - the range contains negative numbers
        - the range is not a perfect diagonal
     */
    public static List<Cell> getCellsFromRange(int x1, int y1, int x2, int y2) {

        if (containsNegativeValue(x1,y1,x2,y2))
            throw new IllegalArgumentException("Negative coordinates not supported!");

        //todo maybe dont allow diagonals?
        if (!isValidLine(x1,y1,x2,y2)) // no straight line
            throw new IllegalArgumentException("Invalid coordinates!");

        int deltaX = Integer.compare(x2,x1);
        int deltaY = Integer.compare(y2,y1);

        List<Cell> range = new ArrayList<>();

        while (x1 != x2 || y1 != y2) {
            range.add(new Cell(x1,y1));
            x1 += deltaX;
            y1 += deltaY;
        }
        range.add(new Cell(x1,y1));
        return range;
    }


    /**
     * Throws a CommandException if the input is a supported command.
     * @param input an input string
     * @throws CommandException if the input string equals a supported game command
     */
    public static void throwIfInputIsCommand(String input) throws CommandException{
        if (input != null && input.length() > 0)
        {
            try
            {
                Command command = Command.valueOf(input.toString().toUpperCase());
                throw new CommandException(command);
            }
            catch (IllegalArgumentException e) {
                //dont do anything if the input is not a command
            }
        }
    }

    /*
     * returns true, if the parameters contain a negative value
     */
    private static boolean containsNegativeValue(int ... num) {
        return Arrays.stream(num).anyMatch(n -> n < 0);
    }

    /*
     * returns true, if the given range is valid.
     * the range is valid if:
     *    - it is a horizontal line
     *    - it is a diagonal
     */
    private static boolean isValidLine(int x1, int y1, int x2, int y2) {
        if (x1 != x2 && y1 != y2) // no straight line
            if (x1 != y1 || x2 != y2)  // and no perfect diagonal (45°)
                return false;
        return true;
    }

    /*
     * parses the x coordinate out of the following format: x,y
     */
    private static int parseXCoordinate(String coord) throws NumberFormatException {
        return getCoordinateComponent(coord, 0);
    }

    /*
     * parses the y coordinate out of the following format: x,y
     */
    private static int parseYCoordinate(String coord) throws NumberFormatException {
        return getCoordinateComponent(coord, 1);
    }

    /*
     * helper method to extract a value out of coordinates, where index = 0 is the x value, and index = 1 the y value
     */
    private static int getCoordinateComponent(String input, int index) throws NumberFormatException {
        String[] split = input.split(",");
        if (split.length != 2)
            throw new NumberFormatException("No valid coordinates");
        return Integer.parseInt(split[index]);
    }


}
