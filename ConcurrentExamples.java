import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class ConcurrentExamples {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Executor and ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Future
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            return "Task Completed";
        });

        // ScheduledExecutorService
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
        Runnable scheduledTask = () -> System.out.println("Executing scheduled task...");
        scheduledExecutor.scheduleAtFixedRate(scheduledTask, 0, 1, TimeUnit.SECONDS);

        // CountDownLatch
        CountDownLatch latch = new CountDownLatch(3);
        Runnable latchTask = () -> {
            System.out.println("Executing latch task...");
            latch.countDown();
        };

        // CyclicBarrier
        CyclicBarrier barrier = new CyclicBarrier(3);
        Runnable barrierTask = () -> {
            try {
                System.out.println("Waiting at barrier...");
                barrier.await();
                System.out.println("Barrier passed");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        // Semaphore
        Semaphore semaphore = new Semaphore(3);
        Runnable semaphoreTask = () -> {
            try {
                semaphore.acquire();
                System.out.println("Semaphore acquired");
                Thread.sleep(2000);
                semaphore.release();
                System.out.println("Semaphore released");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // ThreadFactory
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        // BlockingQueue
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        Runnable producer = () -> {
            try {
                queue.put("Message");
                System.out.println("Produced message");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable consumer = () -> {
            try {
                String message = queue.take();
                System.out.println("Consumed message: " + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // DelayQueue
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();
        delayQueue.put(new DelayedTask("Task 1", 2000));
        delayQueue.put(new DelayedTask("Task 2", 4000));

        // Lock
        Lock lock = new ReentrantLock();
        Runnable lockTask = () -> {
            lock.lock();
            try {
                System.out.println("Lock acquired by " + Thread.currentThread().getName());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("Lock released by " + Thread.currentThread().getName());
            }
        };

        // Phaser
        Phaser phaser = new Phaser(1);
        Runnable phaserTask = () -> {
            System.out.println("Arrived at phaser point 1");
            phaser.arriveAndAwaitAdvance();
            System.out.println("Arrived at phaser point 2");
            phaser.arriveAndDeregister();
        };

        // Execute tasks    
        executor.execute(latchTask);
        // executor.execute(latchTask);
        // executor.execute(latchTask);

        executor.execute(barrierTask);
        // executor.execute(barrierTask);
        // executor.execute(barrierTask);

        executor.execute(semaphoreTask);
        // executor.execute(semaphoreTask);
        // executor.execute(semaphoreTask);

        executor.execute(lockTask);
        executor.execute(lockTask);

        executor.execute(phaserTask);
        executor.execute(phaserTask);

        // Wait for latch
        latch.await();
        
        // Wait for future result
        String result = future.get();
        System.out.println("Future Result: " + result);

        // Shutdown executors
        executor.shutdown();
        scheduledExecutor.shutdown();
    }
}

// DelayedTask class for DelayQueue
class DelayedTask implements Delayed {
    private String name;
    private long delay;

    public DelayedTask(String name, long delay) {
        this.name = name;
        this.delay = delay + System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = delay - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.delay - ((DelayedTask) o).delay);
    }

    @Override
    public String toString() {
        return name;
    }
}
