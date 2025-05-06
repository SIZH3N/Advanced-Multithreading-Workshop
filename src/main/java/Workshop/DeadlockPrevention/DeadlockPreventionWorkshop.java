package Workshop.DeadlockPrevention;

public class DeadlockPreventionWorkshop {
    static class Resource {
        private final int id;
        private final String name;

        public Resource(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
    public static void useResources(Resource r1, Resource r2) {
        //TODO: prevent deadlock
        Resource first, second;
        if(r1.getId() > r2.getId()) {
            first = r1;
            second = r2;
        } else {
            first = r2;
            second = r1;
        }


        //HINT: if you make sure that all locks are always taken in the same order by any thread, deadlocks cannot occur
        System.out.println(Thread.currentThread().getName() + " is attempting to lock " + r1.getName());
        synchronized (first) {
            System.out.println("+ " +Thread.currentThread().getName() + " locked " + r1.getName());
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            System.out.println(Thread.currentThread().getName() + " is attempting to lock " + r2.getName());
            synchronized (second) {
                System.out.println("+ " +Thread.currentThread().getName() + " locked " + r2.getName());
                System.out.println(Thread.currentThread().getName() + " using " + r1.getName() + " and " + r2.getName());
            }
        }
    }

    public static void main(String[] args) {
        Resource resA = new Resource(1, "ResourceA");
        Resource resB = new Resource(2, "ResourceB");

        Thread t1 = new Thread(() -> useResources(resA, resB), "Thread 1");
        Thread t2 = new Thread(() -> useResources(resB, resA), "Thread 2");

        t1.start();
        t2.start();
    }
}