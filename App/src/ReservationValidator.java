import java.util.*;

/**
 * CLASS - ReservationValidator
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * Centralized validation for booking inputs.
 *
 * @version 9.0
 */
public class ReservationValidator {

    private static final Set<String> VALID_ROOM_TYPES =
            new HashSet<>(Arrays.asList(
                    "Single Room",
                    "Double Room",
                    "Suite Room"
            ));

    /**
     * Validates booking input
     */
    public void validate(
            String guestName,
            String roomType,
            RoomInventory inventory
    ) throws InvalidBookingException {

        // Guest name validation
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Normalize input (important for viva)
        roomType = roomType.trim();

        // Room type validation
        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        // Availability validation
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Selected room type is not available.");
        }
    }
}