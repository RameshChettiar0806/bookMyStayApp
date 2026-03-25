public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Abhi", "Single Room"));
        bookingQueue.addRequest(new Reservation("Subha", "Single Room"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite Room"));

        RoomAllocationService allocationService = new RoomAllocationService();

        // UC7: Add-On Service Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        while (bookingQueue.hasPendingRequests()) {

            Reservation request = bookingQueue.getNextRequest();

            allocationService.allocateRoom(request, inventory);

            // Fetch generated Room ID
            String roomId = allocationService.getRoomIdForGuest(request.getGuestName());

            if (roomId != null) {

                // Attach services AFTER confirmation
                serviceManager.addService(roomId, new AddOnService("Breakfast", 500));
                serviceManager.addService(roomId, new AddOnService("Spa", 800));

                double totalCost = serviceManager.calculateTotalServiceCost(roomId);

                System.out.println("Add-On Cost for " + roomId + ": " + totalCost);
            }
        }
    }
}