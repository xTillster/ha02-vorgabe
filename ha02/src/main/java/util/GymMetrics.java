package util;

import java.util.LinkedList;

/**
 * DO NOT MODIFY!
 *
 * Used by the tests to evaluate the runtime characteristics of your implementation.
 */
public class GymMetrics {

    public enum ActionType {
        STRETCH,
        EXERCISE,
        PICK_UP_SUCCESS,
        PICK_UP_FAILURE,
        PUT_DOWN_SUCCESS,
        PUT_DOWN_FAILURE
    }

    private static final LinkedList<AbstractAthlete> athletes = new LinkedList<>();

    private static final LinkedList<AbstractWeight> weights = new LinkedList<>();
    private static final LinkedList<AthleteAction> actions = new LinkedList<>();

    protected static void registerAthlete(AbstractAthlete athlete) {
        athletes.add(athlete);
    }

    protected static void registerWeight(AbstractWeight weight) {
        weights.add(weight);
    }

    protected static void registerWeightPickedUp(AbstractAthlete athlete, AbstractWeight weight, boolean success) {
        synchronized (actions) {
            actions.add(new AthleteAction(athlete.getAthleteId(), weight.getWeightId(), success ? ActionType.PICK_UP_SUCCESS : ActionType.PICK_UP_FAILURE));
        }
    }

    protected static void registerWeightPutDown(AbstractAthlete athlete, AbstractWeight weight, boolean success) {
        synchronized (actions) {
            actions.add(new AthleteAction(athlete.getAthleteId(), weight.getWeightId(), success ? ActionType.PUT_DOWN_SUCCESS : ActionType.PUT_DOWN_FAILURE));
        }
    }

    protected static void registerAthleteStretching(AbstractAthlete athlete) {
        actions.add(new AthleteAction(athlete.getAthleteId(), -1, ActionType.STRETCH));
    }

    protected static void registerAthleteExercising(AbstractAthlete athlete) {
        actions.add(new AthleteAction(athlete.getAthleteId(), -1, ActionType.EXERCISE));
    }

    public static LinkedList<AbstractAthlete> getAthletes() {
        return athletes;
    }

    public static LinkedList<AbstractWeight> getWeights() {
        return weights;
    }

    public static LinkedList<AthleteAction> getActions() {
        return actions;
    }

    public static void reset() {
        athletes.clear();
        weights.clear();
        actions.clear();
    }

    public static class AthleteAction {

        public final int athleteId;
        public final int weightId;
        public final GymMetrics.ActionType actionType;

        public AthleteAction(int athleteId, int weightId, GymMetrics.ActionType actionType) {
            this.athleteId = athleteId;
            this.weightId = weightId;
            this.actionType = actionType;
        }

        public int getAthleteId() {
            return athleteId;
        }

        public int getWeightId() {
            return weightId;
        }

        public ActionType getActionType() {
            return actionType;
        }
    }
}
