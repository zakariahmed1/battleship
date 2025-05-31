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
     * @return a List of cells corresponding to the coordinates or range
     * @throws IllegalArgumentException if:
     * - the input format is not correct
     * - the input coordinates (range) does not corrispond to the ship size
     * @throws CommandException if the input string is a supported game command
     */
    public static List<Cell> parseCoordinatesRange(String input) throws IllegalArgumentException, CommandException
    {
        String error = "Please enter valid coordinates in the format x1,y1 or x1,y1-x2,y2 for a range!";

        try
        {
            throwIfInputIsCommand(input); //throws CommandException
            String[] fromTo = input.split("-");

            if (fromTo.length == 1) {
                return new ArrayList<>(List.of(parseCoordinates(fromTo[0])));
            }
            else if (fromTo.length == 2) {
                int x1, y1, x2, y2;
                x1 = parseXCoordinate(fromTo[0]);
                y1 = parseYCoordinate(fromTo[0]);
                x2 = parseXCoordinate(fromTo[1]);
                y2 = parseYCoordinate(fromTo[1]);
                return getCellsFromRange(x1, y1, x2, y2);
            }
            else {
                throw new IllegalArgumentException(error);
            }
        }
        catch (NumberFormatException | NullPointerException e)
        {
            throw new IllegalArgumentException(error);
        }
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
            throwIfInputIsCommand(input); //throws CommandException
            int x = parseXCoordinate(input);
            int y = parseYCoordinate(input);
            return new Cell(x,y);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordintes!");
        }
    }


    /**
     * Creates a List of cells out of the given coordinate range
     *
     * @param x1 the starting x-coordinate
     * @param y1 the starting y-coordinate
     * @param x2 the ending x-coordinate
     * @param y2 the ending y-coordinate
     * @return a List of Cells created from the range.
     * @throws IllegalArgumentException if the range does not correspond to the chosen ship size
     */
    public static List<Cell> getCellsFromRange(int x1, int y1, int x2, int y2) {
        int deltaX = Integer.compare(x2,x1);
        int deltaY = Integer.compare(y2,y1);

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
     * parses the x coordinate out of the following format: x,y
     */
    private static int parseXCoordinate(String coord) throws NumberFormatException {
        return Integer.parseInt(coord.split(",")[0]);
    }

    /*
     * parses the y coordinate out of the following format: x,y
     */

    private static int parseYCoordinate(String coord) throws NumberFormatException {
        return Integer.parseInt(coord.split(",")[1]);
    }


}
