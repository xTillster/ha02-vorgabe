import org.junit.jupiter.api.*;

import impl.Gym;

import util.AbstractAthlete;
import util.AbstractWeight;
import util.GymMetrics;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestGymSetup {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void divertSystemOut() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @AfterEach
    void resetGymMetrics() {
        GymMetrics.reset();
    }

    @Test
    @Order(1)
    void testAthleteArray() {
        AbstractAthlete[] returnedAthletes = Gym.setup(20, 20);
        LinkedList<AbstractAthlete> athletes = GymMetrics.getAthletes();

        assertArrayEquals(athletes.toArray(), returnedAthletes);
        assertEquals(20, athletes.size());
        for(AbstractAthlete a : GymMetrics.getAthletes()) {
            assertNotNull(a);
        }
    }

    @Test
    @Order(2)
    void testWeightArray() {
        Gym.setup(20, 20);
        LinkedList<AbstractWeight> weights = GymMetrics.getWeights();

        assertEquals(20, weights.size());
        for(AbstractWeight w : weights) {
            assertNotNull(w);
        }
    }

    @Test
    @Order(3)
    void testArrayMinSizes() {
        Gym.setup(0, 1);
        LinkedList<AbstractAthlete> athletes = GymMetrics.getAthletes();
        LinkedList<AbstractWeight> weights = GymMetrics.getWeights();

        assertEquals(1, athletes.size());
        assertEquals(2, weights.size());
    }

    @Test
    @Order(4)
    void testAthleteWeights() {
        Gym.setup(20, 20);
        LinkedList<AbstractAthlete> athletes = GymMetrics.getAthletes();

        // make sure the list is populated
        assertEquals(20, athletes.size());
        for(AbstractAthlete w : athletes) {
            assertNotNull(w);
        }

        for(int i = 0; i < athletes.size(); i++) {
            AbstractWeight w1 = athletes.get(i).getRightWeight();
            AbstractWeight w2 = athletes.get((i + 1) % athletes.size()).getLeftWeight();
            assertEquals(w1, w2);
        }
    }

    @Test
    @Order(5)
    void testAthleteCycles() {
        Gym.setup(20, 20);
        LinkedList<AbstractAthlete> athletes = GymMetrics.getAthletes();

        // make sure the list is populated
        assertEquals(20, athletes.size());
        for(AbstractAthlete w : athletes) {
            assertNotNull(w);
        }

        for(AbstractAthlete a : athletes) {
            assertEquals(20, a.getCycles());
        }
    }
}
