package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;


public class myAlgo implements ElevatorAlgo {
    private boolean print = false;
    public static final int UP=1, DOWN=-1;
    private int _direction;
    private Building _building;
    private ArrayList<Stack<CallForElevator>> elevCalls = new ArrayList<Stack<CallForElevator>>();
    private int[] elevPos = new int[100];
    private int[] elevStat = new int[100];

    public myAlgo (Building b) {
        this._building = b;
        this._direction = DOWN;
        initStacks();
        initElevPos();
    }

    // initialize stacks of calls.
    private void initStacks() {
        for (int i = 0; i< _building.numberOfElevetors();i++) {
            Stack<CallForElevator> e = new Stack<CallForElevator>();
            elevCalls.add(e);
        }
    }
    private void initElevPos(){     //initialize array of the elevators positions & states at floor & LEVEL
       for(int i = 0; i < _building.numberOfElevetors(); i++){
           elevPos[i] = 0;
           elevStat[i] = 0;
        }
    }

    @Override
    public Building getBuilding() {
        return this._building;
    }

    @Override
    public String algoName() {
        return "my implementation of elevator algo";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        int answer = 0;

        if (print)
        {
            System.out.println("From:"+c.getSrc()+", to:"+c.getDest()+","+c.getType());
            System.out.println(elevCalls.get(0).toString());
        }

        Elevator e = _building.getElevetor(answer);
        Stack<CallForElevator> ce = elevCalls.get(answer); // current elevator's stack of calls pointer

        
        // Find best elevator for the call here
        // **
        // **
        int rest_elev_p = 0;
        int potential_elev = 0;
        double rest_time = calcElevTimeForC(rest_elev_p,c);
        double potential_time = calcElevTimeForC(potential_elev,c);
            for (int i = 0; i < _building.numberOfElevetors(); i++) {

                if (elevStat[i] == 0) {                                //elevator in REST
                    if (c.getSrc() == elevPos[i]) {                    //if it pos in src
                        answer = i;

                    } else {                                           //if not is pos, calculate the fastest of the resting elevator.
                        if (rest_time > calcElevTimeForC(i, c)) {
                            rest_elev_p = i;
                            rest_time = calcElevTimeForC(rest_elev_p, c);
                        } else continue;
                    }


                }
                if (elevStat[i] == 1 && goingUP(ce)) {                               //elevator going UP
                    if (c.getSrc() < _building.getElevetor(i).getPos()) continue;   //call in opposite direction
                    if (c.getSrc() > _building.getElevetor(i).getPos()) {
                        if (potential_time > calcElevTimeForC(i, c)) {
                            potential_elev = i;
                            potential_time = calcElevTimeForC(potential_elev, c);
                        }
                    }


                }
                if (elevStat[i] == -1 && goingDOWN(ce)) {                              //elevator going DOWN
                    if (c.getSrc() > _building.getElevetor(i).getPos()) continue;    //call in opposite direction
                    if (c.getSrc() < _building.getElevetor(i).getPos()) {
                        if (potential_time > calcElevTimeForC(i, c)) {
                            potential_elev = i;
                            potential_time = calcElevTimeForC(potential_elev, c);
                        }
                    }
                }
                if (potential_time > rest_time) {
                    answer = rest_elev_p;

                } else if (potential_time < rest_time) {
                    answer = potential_elev;
                    ;
                } else if (potential_time == rest_time) {
                    answer = rest_elev_p;
                }
            }
        
        // After finding best elevator, check if new callForElevator has higher priority than old callForElevator 
        if (ce.size()>0) {
            int i = 0;
            if (print) System.out.println("going into switch case");
            switch (e.getState())
            {
                case 1: // going UP
                {
                    // if the new call source is above current position AND destination is closer than previous call,
                    // then it would be better if this call has higher priority
                    if (c.getSrc() > e.getPos()) {
                        if (c.getDest() < ce.peek().getDest())
                        {
                            ce.push(c);
                            if (print) System.out.println("call with higher priority inserted");
                        }
                        else
                        {
                            ce.insertElementAt(c, ce.size()-1);
                        }
                }
                }
                case 0: // going nowhere
                {
                    elevCalls.get(answer).push(c);
                }
                case -1: // going DOWN
                {
                    if (c.getSrc() < e.getPos()) {
                        if (c.getDest() > ce.peek().getDest()) {
                            ce.push(c);
                            if (print) System.out.println("call with higher priority inserted");
                        } else {
                            ce.insertElementAt(c, ce.size() - 1);
                        }
                    }
                }
                default: // **case = -2 ** ERROR boaz help
                {}
            }
        }
        // this else will be done if the best elevator for the call has an empty stack of calls.
        else
            elevCalls.get(answer).push(c);
        return answer;
    }

    @Override
    public void cmdElevator(int elev) {
        updateArrPosStat();
        Elevator e = this._building.getElevetor(elev); // current elevator pointer
        Stack<CallForElevator> ce = elevCalls.get(elev); // current elevator's stack of calls pointer

        // remove DONE calls
        for (int i=0;i<ce.size();i++)
            if(ce.get(i).getState()==CallForElevator.DONE)
                ce.remove(i);

        if (elevCalls.get(elev).isEmpty())
        {
            // no calls for elevator
        }
        else {
            if (e.getPos() < ce.peek().getSrc())
            e.goTo(ce.peek().getSrc());
            else e.goTo(ce.peek().getDest());
        }
    }

    private boolean goingUP(CallForElevator ce) {
        return ce.getDest() > ce.getSrc();
    }

    private boolean goingDOWN(CallForElevator ce) {
        return ce.getDest() < ce.getSrc();
    }
    
    public double Abs(double x) {
        if (x < 0) return -x;
        return x;
    }
    public void updateArrPosStat(){
        for(int i = 0; i < _building.numberOfElevetors(); i++){
            elevPos[i] = _building.getElevetor(i).getPos();
            elevStat[i] = _building.getElevetor(i).getState();
        }

    }



    public double doorTime(Elevator elev){
        return elev.getTimeForClose() + elev.getTimeForOpen();
    }
    public double extraSpeedTime(Elevator elev){
        return elev.getStartTime() + elev.getStopTime();
    }
    public double CallTime(Elevator elev, CallForElevator c){
        return elev.getSpeed()*abs((c.getSrc()-c.getDest()));
     }

    public double calcElevTimeForC(int elev, CallForElevator c){            //include (for now) just the case that elevator finishing the call and then take new one (not stopping in the middle to take more passengers)
        double total_time = 0;
        total_time = CallTime(_building.getElevetor(elev),c) + extraSpeedTime(_building.getElevetor(elev)) + doorTime(_building.getElevetor(elev)); //adding the elevator features and the call itself
        if(!elevCalls.get(elev).isEmpty()){
            if(elevCalls.get(elev).peek().getDest() > c.getSrc() && elevStat[elev] == 1&& goingUP(ce)){                                                           // if there's a src from new call on the way & going UP
                total_time = total_time + (abs((_building.getElevetor(elev).getPos()-c.getSrc()))*_building.getElevetor(elev).getSpeed());          //time from current position to pickup
                if(elevCalls.get(elev).peek().getDest() < c.getDest()){
                    total_time = total_time +doorTime(_building.getElevetor(elev))+extraSpeedTime(_building.getElevetor(elev));                           //time for dropping passenger from previous call on the way
                }
            }
            if(elevCalls.get(elev).peek().getDest() < c.getSrc() && elevStat[elev] == -1 && goingDOWN(ce)){                                                          //the same conditions but in elevator going DOWN
                total_time = total_time + (abs((_building.getElevetor(elev).getPos()-c.getSrc()))*_building.getElevetor(elev).getSpeed());
                if(elevCalls.get(elev).peek().getDest() > c.getDest()){
                    total_time = total_time +doorTime(_building.getElevetor(elev))+extraSpeedTime(_building.getElevetor(elev));
                }
            }
            else {
                total_time = total_time + _building.getElevetor(elev).getSpeed()*abs((elevCalls.get(elev).peek().getDest() - c.getSrc()));          //time to finish previous call
            }
        }
        return total_time;
    }
}
