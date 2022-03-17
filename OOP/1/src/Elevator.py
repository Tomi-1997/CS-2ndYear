class Elevator:
    """"This class is intended to create the elevators and it's properties.
    properties include: speed, floor range, time to close or open doors."""

    def __init__(self, id, speed, minFloor, maxFloor, closeTime, openTime, startTime, stopTime):
        self._id = id
        self.speed = speed
        self._minFloor = minFloor
        self._maxFloor = maxFloor
        self._closeTime = closeTime
        self._openTime = openTime
        self._startTime = startTime
        self.stopTime = stopTime
        self.calls = []
        self.position = 0
        self.direction = 1  # 1, 0 or -1. 1 is up, 0 is rest, -1 is down
        # this variable will be the sum of stop \ start \ open doors \ close doors and each second will go down by 1
        self.cooldown = 0
        self.calls_got = 0

    def __repr__(self):
        return f'(_id:{self._id},_speed:{self.speed},_minFloor:{self._minFloor},' \
               f'_maxFloor:{self._maxFloor},_closeTime:{self._closeTime},' \
               f'_openTime:{self._openTime},_startTime:{self._startTime},' \
               f'_closeTime:{self._closeTime})'

    def stop_start_time(self):
        return self.stopTime + self._openTime + self._closeTime + self._startTime

    # time cost of current calls with no additional call
    def cost(self):
        sum = 0
        if len(self.calls) == 0: return sum
        for i in range(len(self.calls)):
            # assuming we have N calls, first call has to wait N-1 stop and go time,
            # second call has to wait N -2 stop and go time, and last call has to wait
            # N - (N - 1) stop and go times (which is one).
            sum += (len(self.calls) - i) * self.stop_start_time()
            sum += self.calls[i].dist() / self.speed

        # no need to stop at first call, and no need to start again after last call
        sum = sum - self._startTime - self.stop_start_time()
        return sum

    # time cost if we insert this given call
    def cost_with_new_call(self, new_call):
        where_to = self.optimal_place(new_call)
        self.calls.insert(where_to, new_call)
        ans = self.cost()
        self.calls.remove(new_call)
        return ans

    # given a new call, find the correct order among other calls
    def optimal_place(self, c):
        ans = 0
        if len(self.calls) == 0: return ans
        for i in range(len(self.calls)):
            if c.direction == 1 and self.calls[i].direction == 1:
                if c.source > self.calls[i].source:
                    ans += 1

            if c.direction == -1 and self.calls[i].direction == -1:
                if c.source < self.calls[i].destination:
                    ans += 1
        return ans

    def add(self, c):
        self.calls.insert(self.optimal_place(c), c)
        c.allocated_to_elev = self._id

    # if cooldown=0 elev moves depends on call, if cooldown>0 elevator to wait due to technical limitation(open door,..)
    # if cooldown<0 then cooldown=max(0, cooldown)

    def update_elev(self):

        if len(self.calls) > 0 and self.cooldown == 0:
            if self.calls[0].status == 0 or 1:
                if self.position < self.calls[0].source:
                    self.position += self.speed
                    self.direction = 1
                else:
                    self.position -= self.speed
                    self.direction = -1

            elif self.calls[0].status == 2:
                if self.position < self.calls[0].destination:
                    self.position += self.speed
                    self.direction = 1

                else:
                    self.position -= self.speed
                    self.direction = -1

        else:
            self.cooldown = self.cooldown - 1
            # in case the stop\start times are not whole numbers
            if self.cooldown < 0:
                self.cooldown = 0
