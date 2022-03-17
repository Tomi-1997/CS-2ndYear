
class Building:
    """This class is intended to construct a building with a certain number of
     elevators and floors read from a JSON file."""

    def __init__(self, minFloor, maxFloor, elev_list):
        self.minFloor = minFloor
        self.maxFloor = maxFloor
        self.elev_list = elev_list

    def __str__(self):
        return f'minFloor: {self.minFloor}, maxFloor: {self.maxFloor}, \n' \
               f'elev list: {self.elev_list}'
