package impl;

public class Gym {

    /**
     * DO NOT CHANGE THE SIGNATURE OF THIS METHOD!
     *
     * Sets up the gym with the specified number of athletes and prepares them for specified number of training cycles.
     * If the specified number of athletes is smaller than 1, one athlete and two weights are created.
     * Otherwise, the number of weights is equal to the number of athletes. A weight is always shared by two athletes:
     * for the first athlete it's the left-hand weight, for the second athlete it's the right-hand weight.
     * @param numAthletes Number of athletes to be created.
     * @param cycles Number of training cycles, i.e. how many times each athlete should perform the training sequence.
     * @return Array of prepared athletes.
     */
    public static Athlete[] setup(int numAthletes, int cycles) {
        if(numAthletes <= 0){
            return new Athlete[]{new Athlete(1,Math.max(1, cycles), new Weight(1), new Weight(2))};
        } else if (numAthletes == 1) {
            return new Athlete[]{new Athlete(1,Math.max(1, cycles), new Weight(1), new Weight(2))};
        }

        Athlete[] athletes = new Athlete[numAthletes];
        Weight[] weights = new Weight[numAthletes];

        for(int i = 0; i < numAthletes; i++){
            weights[i] = new Weight(i+1);
        }

        for(int i = 0; i < numAthletes; i++){
            athletes[i] = new Athlete(i+1, Math.max(1, cycles), weights[(((i-1) % numAthletes+ numAthletes) % numAthletes)], weights[i]);
        }

        return athletes;
    }
}
