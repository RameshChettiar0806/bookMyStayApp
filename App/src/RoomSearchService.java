import java.util.Map;

/**
 * CLASS - RoomSearchService
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * This class provides search functionality
 * for guests to view available rooms.
 *
 * It reads room availability from inventory
 * and room details from Room objects.
 *
 * No inventory mutation or booking logic
 * is performed in this class.
 *
 * @version 4.0
 */
public class RoomSearchService {

    /**
     * Displays available rooms along with
     * their details and pricing.
     *
     * This method performs read-only access.
     *
     * @param inventory centralized room inventory
     * @param singleRoom single room definition
     * @param doubleRoom double room definition
     * @param suiteRoom suite room definition
     */
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        System.out.println("Room Search\n");

        // Single Room
        if (inventory.getAvailability("Single Room") > 0) {
            System.out.println("Single Room:");
            singleRoom.displayRoomDetails();
            System.out.println("Available: " +
                    inventory.getAvailability("Single Room"));
            System.out.println();
        }

        // Double Room
        if (inventory.getAvailability("Double Room") > 0) {
            System.out.println("Double Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " +
                    inventory.getAvailability("Double Room"));
            System.out.println();
        }

        // Suite Room
        if (inventory.getAvailability("Suite Room") > 0) {
            System.out.println("Suite Room:");
            suiteRoom.displayRoomDetails();
            System.out.println("Available: " +
                    inventory.getAvailability("Suite Room"));
            System.out.println();
        }
    }
}