package ex0.algo;

import ex0.Building;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class myAlgoTest {
    public static void main(String[] args) {

        // Init
        Simulator_A.initData(1, null);
        Building b = Simulator_A.getBuilding();
        ElevatorAlgo myAlg = new myAlgo(Simulator_A.getBuilding());
        Simulator_A.initAlgo(myAlg);
        System.out.println("***");

        // Checking if the simulator does not run for too long
        timeout();

        // Checking boundaries of
        validFloors();
    }
    @Test
    static void timeout() {
        assertTimeout(ofMillis(100), () -> {
            Simulator_A.runSim();
        });
        //System.out.println("success");
    }


    // this does not work because i can not access the simulator while it is running boaz
    @Test
      static void validFloors() {
        boolean flag = false;
        try {
            Simulator_A.getBuilding().getElevetor(0).goTo(100);
        }
        catch (Exception e)
        {
            flag = true;
        }
        assert(flag);
    }

}
