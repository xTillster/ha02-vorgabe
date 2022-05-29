package impl;

import util.AbstractAthlete;
import util.AbstractWeight;

import java.util.ArrayList;

/**
 * DO NOT CHANGE THE SIGNATURE OF THIS CLASS!
 *
 * Represents an athlete in our gym. Extends AbstractAthlete, which in turn extends Thread.
 *
 * An athlete performs a number of training cycles. In each cycle, the following sequence is performed:
 *
 * - the athlete stretches,
 * - picks up both weights
 * - exercises,
 * - and puts down both weights.
 */
public class Athlete extends AbstractAthlete {
    int cycles;
    int id;
    AbstractWeight leftWeight;
    AbstractWeight rightWeight;
    static ArrayList<Athlete> athletes = new ArrayList<>();

    static int maxID = 0;

    /**
     * DO NOT CHANGE THE SIGNATURE OF THE CONSTRUCTOR!
     *
     * @param id The identifier of this athlete. Must be unique among all athletes.
     * @param cycles Specifies how many times the athlete should perform the training sequence.
     * @param leftWeight The weight to the left of the athlete.
     * @param rightWeight The weight to the right of the athlete.
     */
    public Athlete(int id, int cycles, AbstractWeight leftWeight, AbstractWeight rightWeight) {
        super(id, cycles, leftWeight, rightWeight);
        this.cycles = cycles;
        this.leftWeight = leftWeight;
        this.rightWeight = rightWeight;
        this.id = id;
        if(maxID < id){
            maxID = id;
        }
        System.out.println(maxID);
    }

    /**
     * Implements the athlete's training cycle. The following sequence is performed @cycle times:
     * 1. stretch - call super.stretch()
     * 2. pick up both weights - call pickUp() on both
     * 3. exercise - call super.exercise()
     * 4. put down both weights - call putDown() on both
     *
     * Ensure the following:
     * - no starvation: every athlete gets perform their full training cycle
     * - no deadlocks: the program always completes successfully
     * - no concurrent access: a weight is only ever held by up to one athlete at any given time
     *
     * For maximum points:
     * - on step 2 of the sequence, the athlete calls pickUp() exactly once on each weight
     * - on step 4 of the sequence, the athlete calls putDown() exactly once on each weight
     */
    @Override
    public void run() {
        for(int i = 0; i < cycles; i++){
            stretch();

            /*if(this.id == maxID){
                synchronized (rightWeight){
                    do {
                    } while(!rightWeight.isAvailable());
                    rightWeight.pickUp(this);
                    synchronized (leftWeight){
                        do {
                        } while(!leftWeight.isAvailable());
                        leftWeight.pickUp(this);
                    }
                }
            } else {
                synchronized (leftWeight){
                    do {
                    } while(!leftWeight.isAvailable());
                    leftWeight.pickUp(this);
                    synchronized (rightWeight){
                        do {
                        } while(!rightWeight.isAvailable());
                        rightWeight.pickUp(this);
                    }
                }
            }*/

            /*if(this.id == maxID){
                rightWeight.pickUp(this);
                leftWeight.pickUp(this);
            } else {
                leftWeight.pickUp(this);
                rightWeight.pickUp(this);
            }*/

            if(this.id == maxID){
                do{
                    //System.out.println("try to pick up right Weight");
                } while (!rightWeight.pickUp(this));
                do{
                    //System.out.println("try to pick up left Weight");
                } while (!leftWeight.pickUp(this));
            } else {
                do{
                    //System.out.println("try to pick up left Weight");
                } while (!leftWeight.pickUp(this));
                do{
                    //System.out.println("try to pick up right Weight");
                } while (!rightWeight.pickUp(this));
            }

            /*if(this.id == maxID){
                while(!rightWeight.pickUp(this)){
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while(!leftWeight.pickUp(this)){
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                while(!leftWeight.pickUp(this)){
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while(!rightWeight.pickUp(this)){
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }*/

            /*if(this.id == maxID) {
                synchronized (rightWeight) {
                    while (!rightWeight.isAvailable()) {
                        try {
                            athletes.add(this);
                            System.out.println("hier Athlete" + id);
                            wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                    rightWeight.pickUp(this);

                    synchronized (leftWeight) {
                        while (!leftWeight.isAvailable()) {
                            try {
                                athletes.add(this);
                                System.out.println("hier Athlete" + id);
                                wait();
                            } catch (InterruptedException ignored) {
                            }
                        }
                        leftWeight.pickUp(this);
                    }
                }
            } else {
                synchronized (leftWeight) {
                    while (!leftWeight.isAvailable()) {
                        try {
                            athletes.add(this);
                            System.out.println("hier Athlete" + id);
                            wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                    leftWeight.pickUp(this);

                    synchronized (rightWeight) {
                        while (!rightWeight.isAvailable()) {
                            try {
                                athletes.add(this);
                                System.out.println("hier Athlete" + id);

                                wait();
                            } catch (InterruptedException ignored) {
                            }
                        }
                        rightWeight.pickUp(this);
                    }
                }
            }*/

            exercise();

            if(this.id == maxID){
                rightWeight.putDown(this);
                leftWeight.putDown(this);
                //notifyAllAthletes();

            } else {
                leftWeight.putDown(this);
                rightWeight.putDown(this);
                //notifyAllAthletes();
            }
        }
    }

    public static void main(String[] args) {
        Athlete[] athletes = Gym.setup(5,4);
        for(Athlete athlete : athletes){
            athlete.start();
        }
    }

    private synchronized void notifyAllAthletes(){
        System.out.println(athletes);
        int size = athletes.size();
        for(int i = 0; i < size; i++){
            athletes.get(0).notify();
            athletes.remove(0);
        }
    }
}
