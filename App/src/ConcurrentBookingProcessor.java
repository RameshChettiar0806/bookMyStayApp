public class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;
    private CancellationService cancellationService;
    private AddOnServiceManager addOnServiceManager;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService,
            CancellationService cancellationService,
            AddOnServiceManager addOnServiceManager) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
        this.cancellationService = cancellationService;
        this.addOnServiceManager = addOnServiceManager;
    }

    @Override
    public void run() {

        while (true) {
            Reservation reservation;

            synchronized (bookingQueue) {
                if (!bookingQueue.hasPendingRequests()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            synchronized (inventory) {

                allocationService.allocateRoom(reservation, inventory);

                String roomId = allocationService
                        .getRoomIdForGuest(reservation.getGuestName());

                if (roomId != null) {

                    // UC10 Integration
                    cancellationService.registerBooking(
                            roomId, reservation.getRoomType());

                    // UC9 Integration
                    addOnServiceManager.addService(
                            roomId, new AddOnService("Breakfast", 500));

                    addOnServiceManager.addService(
                            roomId, new AddOnService("Spa", 800));

                    double total = addOnServiceManager
                            .calculateTotalServiceCost(roomId);

                    System.out.println("Add-On Cost for "
                            + roomId + ": " + total);
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
    }
}