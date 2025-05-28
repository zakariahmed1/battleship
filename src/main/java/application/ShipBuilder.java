package application;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * A class that holds all the possible ships a player can choose.
 */
public class ShipBuilder
{
    //Supplier for supported ship instances
    private static final Supplier<Ship>[] possibleShips = new Supplier[]{
            Destroyer::new,
            Submarine::new,
            Cruiser::new
    };

    /**
     * Creates a ship instance out of an id
     * @param id the id of the ship, corresponding to our Supplier-index, with an offset of +1
     * @return the ship instance where the (id-1) corresponds to our supplier index
     */
    public static Ship create(int id) {
        if (id < 1 || id > possibleShips.length)
            throw new IllegalArgumentException("invalid ship id selection!");
        return possibleShips[id-1].get();
    }

    /**
     * @return a String description of the possible ships a player can choose with the possible size.
     */
    public static String getShipsDescription() {
        StringBuilder descr = new StringBuilder();
        for (int i = 0; i < possibleShips.length; i++) {
            Ship ship = possibleShips[i].get();
            String shipName = ship.getClass().getSimpleName();
            descr.append(i+1)
                    .append(") ")
                    .append(shipName)
                    .append(" (size: ")
                    .append(ship.getSize())
                    .append(")")
                    .append(System.lineSeparator());
        }
        return descr.toString();
    }

    /**
     * @return the size of the largest ship
     */
    public static int getMaxSupportedShipSize() {
        return Arrays.stream(possibleShips).mapToInt(supplier -> supplier.get().getSize()).max().getAsInt();
    }


}
