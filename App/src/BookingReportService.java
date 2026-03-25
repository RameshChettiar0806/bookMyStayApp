/**
 * CLASS - BookingReportService
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * Generates reports from booking history.
 *
 * @version 8.8
 */
public class BookingReportService {

    // Generate and print report
    public void generateReport(BookingHistory history) {

        System.out.println("\nBooking History Report");

        for (Reservation reservation : history.getConfirmedReservations()) {

            System.out.println("Guest: "
                    + reservation.getGuestName()
                    + ", Room Type: "
                    + reservation.getRoomType());
        }
    }
}