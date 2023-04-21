import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class SandwichManager {
 /**************************TABLE OF CONTENTS***********************************
 *                          GLOBAL VARIABLES
 *                          HELPER METHODS
 *                          MAIN METHOD
 *                          FOOD
 *                          PRODUCERS
 *                          BUFFERS
 ******************************************************************************/

 /******************************************************************************
 * GLOBAL VARIABLES
 ******************************************************************************/
    static Boolean allSandwichesMade = false; // stop the makers from making when all sandwiches have been made
    static ArrayList<Runnable> makers; // list of all food makers, for storing no. food items

 /******************************************************************************
 * HELPER METHODS
 ******************************************************************************/
    // Direct System.out to the log.txt file
    static void changeOutputStream() {
        // Creating the log file
        File logFile = null;
        try {
            logFile = new File("log.txt");
            if (logFile.createNewFile()) {
                System.out.println("File created: " + logFile.getName());
            } else {
                System.out.println("The file already exists.");
            }
            } catch (IOException e) {
                System.out.println("An IOException occurred.");
                e.printStackTrace(); // print the stack trace
            }

        // Set up the printstream
        PrintStream o = null;
        try {
            o = new PrintStream("log.txt");
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
        }
 
        // Assign o to output stream
        System.setOut(o);
    }

    // Print out the user's input
    static void printInput(String[] args) {
        // Convert the String arguments to integer values
        int[] input = new int[9];
        for (int i = 0 ; i < 9 ; i++) {
            input[i] = Integer.parseInt(args[i]);
        }

        // Print out a summary of what the user entered
        System.out.printf(
            "sandwiches:%d\n" + // target number of sandwiches
            "bread capacity:%d\n" + // bread pool buffer size
            "egg capacity:%d\n" + // egg pool buffer size
            "bread makers:%d\n" + // number of bread makers
            "egg makers:%d\n" + // number of egg makers
            "sandwich packers:%d\n" + // number of sandwich packers
            "bread rate:%d\n" + // rate of bread production
            "egg rate:%d\n" + // rate of egg production
            "packing rate:%d\n\n", // packing rate
            input[0], input[1], input[2], input[3], input[4], input[5], input[6], input[7], input[8] // user's arguments
        );
    }

    // Simulate work
    static void gowork(int n) {
        for (int i = 0 ; i < n ; i++) {
            long m = 300000000; // arbitrary constant
            while (m > 0){
                m--;
            }
        }
    }

    // Sleep for a random duration
    static void random_sleep() {
        try {
            int n = (int)(Math.random() * 10);
            Thread.sleep(n);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    // Return the results of production
    static String getSummary() { 
        // B: Breadmaker
        // E: EggMaker
        // S: SandwichMaker
        // index represents the number of food items made per machine
        String s = "summary:\n";
        for (Runnable r : makers) {
            if (r instanceof BreadMaker) {
                BreadMaker b = (BreadMaker)r;
                s += "B" + b.id + " makes " + b.index + "\n";
            } else if (r instanceof EggMaker) {
                EggMaker b = (EggMaker)r;
                s += "E" + b.id + " makes " + b.index + "\n";
            } else if (r instanceof SandwichMaker) {
                SandwichMaker b = (SandwichMaker)r;
                s += "S" + b.id + " packs " + b.index + "\n";
            }
        }
        return s;
    }

 /******************************************************************************
 * MAIN METHOD
 ******************************************************************************/
    public static void main(String[] args) {
        // Direct System.out output to log.txt
        changeOutputStream();

        // Print output
        printInput(args);

        // Store a list of all threads created, to collectively start/join them
        ArrayList<Thread> threads = new ArrayList<>();

        // List of makers
        makers = new ArrayList<>();

        // Get input variables
        int n_sandwiches = Integer.parseInt(args[0]); // number of sandwiches to pack
        int bread_capacity = Integer.parseInt(args[1]); //number of slots in the bread pool
        int egg_capacity = Integer.parseInt(args[2]); // number of slots in the egg pool
        int n_bread_makers = Integer.parseInt(args[3]); // number of bread makers
        int n_egg_makers = Integer.parseInt(args[4]); // number of scrambled egg makers
        int n_sandwich_packers = Integer.parseInt(args[5]); // number of sandwich packers
        int bread_rate = Integer.parseInt(args[6]); // number of minutes to toast a slice of bread
        int egg_rate = Integer.parseInt(args[7]); // number of minutes to make single portion of scrambled egg
        int packing_rate = Integer.parseInt(args[8]); // number of minutes to pack a sandwich

        // Create tge Bread and Egg pool buffers with the specified capacities
        BreadBuffer breadBuffer = new BreadBuffer(bread_capacity);
        EggBuffer eggBuffer = new EggBuffer(egg_capacity);

        // Create the bread maker threads
        for (int i = 0 ; i < n_bread_makers ; i++) {
            BreadMaker breadMaker = new BreadMaker(breadBuffer, bread_rate);
            makers.add(breadMaker); // store the breadmaker in the global list
            threads.add(new Thread(breadMaker));
        }

        // Create the egg maker threads
        for (int i = 0 ; i < n_egg_makers ; i++) {
            EggMaker eggMaker = new EggMaker(eggBuffer, egg_rate);
            makers.add(eggMaker); // store the eggmaker in the global list
            threads.add(new Thread(eggMaker));
        }

        // Create the sandwich maker threads
        for (int i = 0 ; i < n_sandwich_packers ; i++) {
            SandwichMaker sandwichMaker = new SandwichMaker(eggBuffer, breadBuffer, packing_rate, n_sandwiches);
            makers.add(sandwichMaker); // store the sandwichmaker in the global list
            threads.add(new Thread(sandwichMaker));
        }
        
        // Start all threads
            for (Thread t : threads) {
                t.start();
            }

        // Join all threads
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}

 /******************************************************************************
 * FOOD
 ******************************************************************************/
class Bread {
    int id; // stores the ID of the bread
    int breadMakerID; // stores the ID of the breadmaker
    public Bread(int id, int breadMakerID) {
        this.id = id;
        this.breadMakerID = breadMakerID;
    }
}

class Egg {
    int id; // stores the ID of the egg
    int eggMakerID; // stores the ID of the eggmaker
    public Egg(int id, int eggMakerID) {
        this.id = id;
        this.eggMakerID = eggMakerID;
    }
}

class Sandwich {
    int id; // stores the id of the sandwich
    Egg egg; // stores a reference to the egg used to make it
    Bread bread1; // stores a reference to a slice of bread it contains
    Bread bread2; // stores a reference to the other slice of bread it contains

    public Sandwich(int id, Egg egg, Bread bread1, Bread bread2) {
        this.id = id;
        this.egg = egg;
        this.bread1 = bread1;
        this.bread2 = bread2;
    }
}

 /******************************************************************************
 * PRODUCERS
 ******************************************************************************/
class BreadMaker implements Runnable {
    int id; // breadmaker id
    static int latestID = 0; // latest breadmaker id created, represents the number of breadmakers
    int index = 0; // amount of bread made so far
    BreadBuffer breadBuffer; // reference to the bread pool buffer
    int n; // rate of production

    BreadMaker(BreadBuffer breadBuffer, int n) {
        // Set up ID
        this.id = latestID;
        latestID++;
        // Store the bread pool and rate of production
        this.breadBuffer = breadBuffer;
        this.n = n;
    }

    @Override
    public void run() {
        while(!SandwichManager.allSandwichesMade) { // continue if there are not enough sandwiches
            SandwichManager.gowork(n); // simulate work
            Bread bread = new Bread(index++, id); // toast a slice of bread
            breadBuffer.put(bread, id); // add the bread to the bread pool
        }
    }
}

class EggMaker implements Runnable {
    int id; // eggmaker id
    static int latestID = 0; // latest eggmaker id, represents the number of eggmakers made
    int index = 0; // represents the number of eggs made
    EggBuffer eggBuffer; // reference to the egg pool buffer
    int n; // This records the rate of production

    EggMaker(EggBuffer eggBuffer, int n) {
        // Get ID
        this.id = latestID;
        latestID++;
        // Store the egg pool and rate of production
        this.eggBuffer = eggBuffer;
        this.n = n;
    }

    @Override
    public void run() {
        while(!SandwichManager.allSandwichesMade) { // continue if there are not enough sandwiches
            SandwichManager.gowork(n); // simulate work
            Egg egg = new Egg(index++, id); // cook an egg
            eggBuffer.put(egg, id); // add the egg to the egg pool buffer
        }
    }
}

class SandwichMaker implements Runnable {
    // ID
    int id; // Identifier for sandwich maker
    static int num_sandwich_makers = 0; // number of sandwich makers so far

    // Buffers
    EggBuffer eggBuffer;
    BreadBuffer breadBuffer;

    // Miscellaneous
    int n; // rate of production
    int index = 0; // sandwiches made so far for this machine
    int n_sandwiches; // target number of sandwiches to make
    static int num_sandwiches = 0; // total number of sandwiches made by all machines

    SandwichMaker(EggBuffer eggBuffer, BreadBuffer breadBuffer, int n, int n_sandwiches) {
        // Set up the ID
        this.id = num_sandwich_makers;
        num_sandwich_makers++;
        // Store a reference to the Bread and Egg buffers
        this.eggBuffer = eggBuffer;
        this.breadBuffer = breadBuffer;
        // Store the rate of production and the target number of sandwiches
        this.n = n;
        this.n_sandwiches = n_sandwiches;
    }

    @Override
    public void run() {
        while(true) { // keep on running indefinitely
            Bread bread1 = breadBuffer.get(id); // take a slice of bread from the bread buffer
            Egg egg = eggBuffer.get(id); // take an egg from the egg buffer
            Bread bread2 = breadBuffer.get(id); // take a slice of bread from the breaad buffer
            SandwichManager.gowork(n); // simulate work
                if (!SandwichManager.allSandwichesMade) { // continue if there are not enough sandwiches
                    Sandwich sandwich = new Sandwich(index, egg, bread1, bread2); // create a new sandwich and store a reference to its ingredients
                    index++; // increase the number of sandwiches made for this machine
                    System.out.printf("S%d packs sandwich %d with bread %d from B%d and egg %d from E%d and bread %d from B%d\n", id, sandwich.id, bread1.id, bread1.breadMakerID, egg.id, egg.eggMakerID, bread2.id, bread2.breadMakerID);
                    if(++num_sandwiches == n_sandwiches) { // when the target number of sandwiches has been made
                        SandwichManager.allSandwichesMade = true; // flag to all threads that the target number of sandwiches has been made
                        System.out.print("\n" + SandwichManager.getSummary()); // print production results
                        System.exit(0); // stop all threads
                    }
            }
        }
    }
}

 /******************************************************************************
 * BUFFERS
 ******************************************************************************/
class BreadBuffer {
    static volatile Bread[] breadBuffer; // array of bread
    static volatile int front = 0, back = 0, item_count = 0; // store the front and back of the buffer, and the number of items
    int size; // capacity of the buffer

    BreadBuffer(int size) {
        breadBuffer = new Bread[size]; // create a Bread array with the specified capacity
        this.size = size; // store this capacity
    }

    public synchronized void put(Bread bread, int id) { // add a slice of bread to the bread buffer
        while (item_count == breadBuffer.length){
            try { this.wait(); } catch (InterruptedException e) {}
        }
        breadBuffer[back] = bread;
        back = (back + 1) % breadBuffer.length; // maintain the circular queue
        System.out.println("B" + id + " puts bread " + bread.id); // print a record of the breadmaker producing the bread
        item_count++; // increase the number of items in the buffer
        this.notifyAll(); // wake up all threads waitng on the monitor
    }

    public synchronized Bread get(int id) { // get a slice of bread from the bread buffer
        while (item_count == 0){
            try { this.wait(); } catch (InterruptedException e) {}
        }
        Bread bread = breadBuffer[front];
        front = (front + 1) % breadBuffer.length; // maintain the circular queue
        item_count--; // reduce the number of items in the buffer
        this.notifyAll(); // wake up all threads waitng on the monitor
        return bread; // return the bread taken from the buffer
    }
}

class EggBuffer {
    static volatile Egg[] eggBuffer; // array of eggs
    static volatile int front = 0, back = 0, item_count = 0; // store the front and back of the buffer, and the number of items
    int size; // store this capacity

    EggBuffer(int size) {
        eggBuffer = new Egg[size]; // create an Egg array with the specified capacity
        this.size = size; // store this capacity
    }

    public synchronized void put(Egg egg, int id) { // add an egg to the buffer
        while (item_count == eggBuffer.length){
            try { this.wait(); } catch (InterruptedException e) {}
        }
        eggBuffer[back] = egg; // add the egg to the back of the queue
        back = (back + 1) % eggBuffer.length; // maintain a circular queue
        System.out.println("E" + id + " puts egg " + egg.id); // print a record of the egg maker making an egg
        item_count++; // increase the number of items in the buffer
        this.notifyAll(); // wake up all threads waiting on the monitor
    }

    public synchronized Egg get(int id) { // take an egg from the egg buffer
        while (item_count == 0) {
            try { this.wait(); } catch (InterruptedException e) {}
        }
        Egg egg = eggBuffer[front]; // get the egg at the front of the queue
        front = (front + 1) % eggBuffer.length; // maintain the circular queue
        item_count--; // reduce the number of items in the buffer
        this.notifyAll(); // wake up all threads waitng on the monitor
        return egg; // return the egg that was taken from the egg buffer
    }
}