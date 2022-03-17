from Elevator import Elevator


class Algorithm:
    """"This class is going to handle all the heavy lifting, allocating
        each call to an elevator and ordering the elevators around."""


def allocate(call, elev_list):
    ans = 0
    # allocate it to the elevator with minimal cost
    if len(elev_list) > 1:
        min_idx = 0
        for i in range(1, len(elev_list)):
            if elev_list[i].cost_with_new_call(call) < elev_list[min_idx].cost_with_new_call(call):
                min_idx = i
        # got answer
    ans = min_idx
    elev_list[ans].calls_got += 1
    elev_list[ans].add(call)


def abs(x, y):
    dt = float(x - y)
    if dt < 0: return -dt
    return dt

# for each elev checks status of call, and if elevator can reach call source/destination within 1 clock tick
# it goes there and updates call status, if completes the call adds it to done call list


def update(elev=Elevator, curr_time=int):
    if len(elev.calls) == 0:
        return 0
    complete_calls = 0
    for call in elev.calls:
        # if Elevator can reach source in one iteration:
        if abs(elev.position + elev.speed , elev.stopTime) >= call.source and call.status == 1:
            call.status = 2
            elev.cooldown += (abs(elev.position, call.source) / elev.speed)
            if elev.position != call.source:
                elev.cooldown += elev.stop_start_time()
            elev.position = call.source
            break
            # else --> call still needs to be picked up
        elif call.status == 0:
            call.status = 1
            # can reach destination in one iteration
        if abs(elev.position + elev.speed , elev.stopTime) >= call.destination and call.status == 2:
            elev.cooldown += (abs(elev.position, call.destination) / elev.speed)
            if elev.position != call.destination:
                elev.cooldown += elev.stop_start_time()
            elev.position = call.destination
            call.status = 3
            call.status_str = 'Done'
            call.time_finished = curr_time - call.time_called
            elev.calls.remove(call)
            complete_calls += 1
            break
        # end for
    elev.update_elev()
    return complete_calls
