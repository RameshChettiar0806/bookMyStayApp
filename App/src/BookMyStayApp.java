// Instead of while loop → use threads

Thread t1 = new Thread(
        new ConcurrentBookingProcessor(var4, var3, var7, var9, var8));

Thread t2 = new Thread(
        new ConcurrentBookingProcessor(var4, var3, var7, var9, var8));

t1.start();
t2.start();

try {
        t1.join();
    t2.join();
} catch (InterruptedException e) {
        System.out.println("Thread execution interrupted.");
}