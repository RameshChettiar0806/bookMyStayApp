import java.util.*;

/**
 * CLASS - BookingHistory
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * Stores confirmed reservations in order.
 *
 * @version 8.0
 */
public class BookingHistory {

    // List that stores confirmed reservations
    private List<Reservation> confirmedReservations;

    // Initializes empty history
    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    // Return all reservations
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}