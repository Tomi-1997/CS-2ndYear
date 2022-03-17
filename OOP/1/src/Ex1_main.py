from Call import Call
from Simulator import Simulator
from Calls import Calls
from Elevator import Elevator
from Building import Building
import sys
import json
import csv
import numpy as np


#
def init_elev_list():
    elev_l = []
    # initialize elevator list
    for current in elev_dict:
        e = Elevator(int(current['_id']), float(current['_speed']), int(current['_minFloor']),
                     int(current['_maxFloor']), float(current['_closeTime']), float(current['_openTime']),
                     float(current['_startTime']), float(current['_stopTime']))
        elev_l.append(e)
    return elev_l


if __name__ == "__main__":
    run = True
    args = sys.argv
    if len(args) == 3 or run:
        b_name = args[0]
        c_name = args[1]
        out_name = args[2]

        # Building.JSON
        b_string = open(b_name, )
        b_data = json.load(b_string)  # get data from json
        elev_dict = b_data['_elevators']  # convert elev dict to an elev list
        elev_list = init_elev_list()
        b = Building(b_data['_minFloor'], b_data['_maxFloor'], elev_list)  # init building

        # Calls.CSV
        with open(c_name, newline='') as f:
            reader = csv.reader(f)
            calls_data = list(reader)
        # get list of calls from CSV
        calls_list = []
        for i in range(len(calls_data)):
            # define each new call (start time, src, destination)
            c = Call(float(calls_data[i][1]), int(calls_data[i][2]), int(calls_data[i][3]))
            calls_list.append(c)

        # let end time = time the last call received *2
        last = calls_list.pop()
        end_time = last.time_called * 2
        calls_list.append(last)

        # initialize simulator and run
        sim = Simulator(building=b, elevators=elev_list, calls=calls_list, end_time=end_time)
        sim.run()
        # updated list of calls

        # # get output.CSV
        # with open('out.csv', 'w', encoding='UTF8', newline='') as csvfile:
        #     writer = csv.writer(csvfile)
        #     writer.writerow(sim.calls)

        np.savetxt('out.csv', [p for p in zip(sim.calls)], delimiter=',', fmt='%r')
