from src import Algorithm


class Simulator:
    """This class is intended to :
    ---receive an input file containing calls and the building.
    ---initialize the building, elevators and the calls.
    ---simulate start time-->allocate elevators to calls-->end time-->print action log."""

    # expecting: building, list of calls
    def __init__(self, building, elevators: list, calls: list, end_time: int):
        self.building = building
        self.calls = calls
        self.elevators = elevators
        self.start_time = 0
        self.end_time = end_time
        self.done_calls = 0

    def run(self):
        self.start_time = 0
        i = 0
        illegal_calls = 0
        # run x seconds with jumps of 1 second each loop.
        while self.start_time < self.end_time:
            # if call shouldn't be assigned yet
            if i >= len(self.calls) or self.calls[i].time_called > self.start_time:
                self.start_time = self.start_time + 1
                pass
            # call should be assigned now
            else:
                # if call source and destination within building's min and max floors.
                if not (self.calls[i].source < self.building.minFloor or self.calls[
                    i].destination < self.building.minFloor
                        or self.calls[i].source > self.building.maxFloor or self.calls[
                            i].destination > self.building.maxFloor):
                    Algorithm.allocate(self.calls[i], self.elevators)
                else:
                    illegal_calls += 1  # i is the number of illegal calls
                i += 1

            # command elevators and get value of current done calls
            for e in self.elevators:
                self.done_calls += Algorithm.update(e, curr_time=self.start_time)

            # number of legal calls is complete
            if self.done_calls == len(self.calls) - illegal_calls:
                break
            else:
                self.start_time = self.start_time + 1

