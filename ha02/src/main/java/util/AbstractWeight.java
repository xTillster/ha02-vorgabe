package util;

import java.util.Objects;

/**
 * DO NOT MODIFY!
 *
 * Abstract superclass of Weight.
 */
public abstract class AbstractWeight {

    protected final int id;

    public AbstractWeight(int id) {
        this.id = id;
        GymMetrics.registerWeight(this);
    }

    public final synchronized boolean pickUp(AbstractAthlete athlete) {
            boolean allowed = pickUpImpl(athlete);
            if(allowed) {
                System.out.println("Athlete " + athlete.id + " picked up weight " + id);
                GymMetrics.registerWeightPickedUp(athlete, this, true);
            } else {
                System.out.println("Athlete " + athlete.id + " failed to pick up weight " + id);
                GymMetrics.registerWeightPickedUp(athlete, this, false);
            }
            return allowed;
    }

    public final synchronized boolean putDown(AbstractAthlete athlete) {
            boolean allowed = putDownImpl(athlete);
            if(allowed) {
                System.out.println("Athlete " + athlete.id + " put down weight " + id);
                GymMetrics.registerWeightPutDown(athlete, this, true);
            } else {
                System.out.println("Athlete " + athlete.id + " failed to put down weight " + id);
                GymMetrics.registerWeightPutDown(athlete, this, false);
            }
            return allowed;
    }

    public abstract boolean isAvailable();

    protected abstract boolean pickUpImpl(AbstractAthlete athlete);

    protected abstract boolean putDownImpl(AbstractAthlete athlete);

    public int getWeightId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractWeight that = (AbstractWeight) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
