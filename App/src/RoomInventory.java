/**
 * CLASS - RoomInventory
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This class acts as the single source of truth
 * for room availability in the hotel.
 *
 * @version 3.0
 */
import java.util.HashMap;

public class RoomInventory {

    // Stores available room count for each room type
    private HashMap<String, Integer> inventory;

    // Constructor initializes the inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Returns current availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Updates availability for a specific room type
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }
}