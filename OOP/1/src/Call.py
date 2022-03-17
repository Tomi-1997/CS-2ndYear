class Call:
    """"This class is intended to create a call for elevator with these different properties:
    the time the call was received, starting floor --> ending floor and status."""

    # class constants
    # _just_initialized = 0
    # _going_source = 1
    # _going_destination = 2
    # _done = 3
    # _UP = 1
    # _DOWN = -1

    # class constructor:
    def __init__(self, time_called: float, source, destination):
        self.time_called = time_called
        self.source = source
        self.destination = destination
        self.status = 0
        self.status_str = 'NotDone'
        self.is_done = 0
        self.dt_received_to_finished = -1
        self.direction = 1
        if self.source > self.destination:
            self.direction = -1
        self.allocated_to_elev = -1
        self.time_finished = -1

    # class methods:
    """Prints description of call: source, destination, status, time, direction"""

    def __str__(self):
        return f'Elevator call,{self.time_called}' \
               f',{self.source},{self.destination},' \
               f'{self.status},{self.allocated_to_elev},' \
               f'{self.status_str},dt,' \
               f'{self.time_finished}\n'

    def __repr__(self):
        return f'Elevator call,{self.time_called}' \
               f',{self.source},{self.destination},' \
               f'{self.status},{self.allocated_to_elev},' \
               f'{self.status_str},dt,' \
               f'{self.time_finished}'

    """call1 > call2 if call1 has more floors between source and destination."""

    def __gt__(self, other: "Call") -> bool:
        return self.dist() > other.dist()

    """returns how many floors this call needs to pass"""

    def dist(self):
        dt = int(self.source - self.destination)
        if dt < 0:
            dt = -dt
        return dt
# end of Call class
