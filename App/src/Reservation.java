/**
 * CLASS - Reservation
 *
 * Use Case 5: Booking Request (FIFO)
 *
 * Description:
 * This class represents a booking request
 * made by a guest.
 *
 * @version 5.0
 */
public class Reservation {

    // Name of the guest
    private String guestName;

    // Requested room type
    private String roomType;

    /**
     * Creates a new booking request.
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}