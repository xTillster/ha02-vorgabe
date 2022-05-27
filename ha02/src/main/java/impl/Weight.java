package impl;

import util.AbstractAthlete;
import util.AbstractWeight;

public class Weight extends AbstractWeight {
    public AbstractAthlete heldByAthlete = null;

    /**
     * DO NOT CHANGE THE SIGNATURE OF THE CONSTRUCTOR!
     *
     * @param id The identifier of this weight. Must be unique among all weights.
     */
    public Weight(int id) {
        super(id);
    }

    /**
     * Determines whether the weight is available to be picked up by an athlete.
     * This is the case if the weight is not currently held by any athlete.
     * @return false if the weight is held by an athlete, true otherwise.
     */
    @Override
    public boolean isAvailable() {
        synchronized (heldByAthlete){
            return heldByAthlete == null;
        }
    }

    /**
     * Called by super.pickUp(). Implements the logic of checking whether the weight is available for pick-up
     * by an athlete and marking it as held by the athlete if it was available.
     * @param athlete The athlete who is attempting to pick up the weight.
     * @return true if the weight was successfully picked up, false otherwise.
     */
    @Override
    protected boolean pickUpImpl(AbstractAthlete athlete) {
        if(heldByAthlete == null) {
            heldByAthlete = athlete;
            return true;
        }
        return false;
    }

    /**
     * Called by super.putDown(). Implements the logic of checking whether the weight may be put down by the
     * calling athlete (this is the case only if the weight is currently being held by this athlete) and releasing
     * the weight if applicable.
     * @param athlete The athlete who is attempting to put down the weight.
     * @return true if the weight was successfully put down, false otherwise.
     */
    @Override
    protected boolean putDownImpl(AbstractAthlete athlete) {
        if(heldByAthlete == null) return false;
        if(heldByAthlete.equals(athlete)){
            heldByAthlete = null;
            return true;
        }
        return false;
    }
}