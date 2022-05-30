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

            //funktioniert für 4 tests
            /*if(this.id == maxID){
                do{
                } while (!rightWeight.pickUp(this));
                do{
                } while (!leftWeight.pickUp(this));
            } else {
                do{
                } while (!leftWeight.pickUp(this));
                do{
                } while (!rightWeight.pickUp(this));
            }*/

            if(this.id == maxID){
                while(!rightWeight.isAvailable()){
                }
                rightWeight.pickUp(this);
                while(!leftWeight.isAvailable()){
                }
                leftWeight.pickUp(this);
            } else {
                while(!leftWeight.isAvailable()){
                }
                leftWeight.pickUp(this);
                while(!rightWeight.isAvailable()){
                }
                rightWeight.pickUp(this);
            }

            exercise();

            if(this.id == maxID){
                rightWeight.putDown(this);
                leftWeight.putDown(this);
            } else {
                leftWeight.putDown(this);
                rightWeight.putDown(this);
            }
        }
        System.out.println("Athlete " + id + " is done");
    }

    public static void main(String[] args) {
        Athlete[] athletes = Gym.setup(5,3);
        for(Athlete athlete : athletes){
            athlete.start();
        }
    }
}
