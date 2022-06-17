import socket
import sys
import time
import math
import pygame

MSGLEN = 128
PRINT_STUFF = True
SERV_IP = "10.0.0.12"

WIN_SIZE_X = 600
WIN_SIZE_Y = 600

WHITE = [255,255,255]

class XO_ClientGUI:

    def __init__(self, id:str):
        self.my_turn = False
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.board = [['N' for i in range(3)] for j in range(3)]

        # Change self.host to server's ip
        self.host = socket.gethostname()

        self.port = 12345
        self.s.connect( (self.host, self.port))

        self.id = id

        self.init_board()
        self.play()

    def play(self):
        print("Waiting for confirmation from server")
        msg = self.s.recv(1).decode()
        while msg != 'X' and msg != 'O':
            msg = self.s.recv(1).decode()
            time.sleep(0.2)
        print("Got confirmation")
        self.id = msg
        print(f'I am {self.id}')



        pygame.draw.line(self.screen, WHITE, [WIN_SIZE_X / 3, 0], [WIN_SIZE_X / 3,WIN_SIZE_Y])
        pygame.draw.line(self.screen, WHITE, [(2*WIN_SIZE_X) / 3, 0], [(2*WIN_SIZE_X) / 3,WIN_SIZE_Y])
        pygame.draw.line(self.screen, WHITE, [0, WIN_SIZE_Y / 3], [WIN_SIZE_X,WIN_SIZE_Y / 3])
        pygame.draw.line(self.screen, WHITE, [0, 2*WIN_SIZE_Y/3], [WIN_SIZE_X,2*WIN_SIZE_Y/3])
        pygame.display.flip()



        reply = "!NO!"
        run = True
        while run:

            while not self.my_turn:
                reply = self.s.recv(MSGLEN).decode()

                if len(reply) == 0:
                    continue

                if len(reply.strip("WAIT")) == 9:

                    current_board = []
                    for char in reply:
                        if char == 'X' or char == 'O' or char == 'N':
                            current_board.append(char)
                    temp = [['N' for i in range(3)] for j in range(3)]
                    k = 0
                    for i in range(3):
                        for j in range(3):
                            temp[i][j] = current_board[k]
                            k += 1

                    self.update_my_board(temp)

                if self.stop_indicator(reply):
                    run = False
                    break

                if "GO" in reply:
                    self.my_turn = True

            reply = "!NO!"
            while self.my_turn:

                # Get mouse pressed location
                pos = None
                while pos is None:
                    events = pygame.event.get()
                    for ev in events:
                        if ev.type == pygame.QUIT:
                            run = False
                            break
                        if ev.type == pygame.MOUSEBUTTONUP:
                            pos = pygame.mouse.get_pos()
                    self.clock.tick(30)

                # Translate it to a number from 0 to 2
                x, y = pos[0] / (WIN_SIZE_X / 3), pos[1] / (WIN_SIZE_Y / 3)
                x = min(int(x),2)
                y = min(int(y),2)
                val = str(x) + "," + str(y)

                # val = input()
                if 'q' in val.lower():
                    self.send_message('q')
                    run = False
                    break

                if not self.valid_message(val):
                    continue

                self.send_message(val)
                reply = self.s.recv(MSGLEN).decode()
                if PRINT_STUFF: print(reply)

                if "!NO!" not in reply:
                    self.my_turn = False
                    self.board[x][y] = self.id
                    a_bit = 40
                    start_x = self.x_to_window(x) + a_bit
                    start_y = self.y_to_window(y) + a_bit
                    end_x = self.y_to_window(x + 1) - a_bit
                    end_y = self.y_to_window(y + 1) - a_bit

                    if self.id == 'X':
                        self.drawX(start_x, start_y, end_x, end_y, end_x, start_y, start_x, end_y)
                    else:
                        self.drawO((start_x + end_x) / 2, (start_y + end_y) / 2, a_bit)

                if self.stop_indicator(reply):
                    run = False
                    break

        self.s.close()

    def init_board(self):
        pygame.init()
        self.clock = pygame.time.Clock()
        self.screen = pygame.display.set_mode([WIN_SIZE_X,WIN_SIZE_Y])

    def stop_indicator(self, reply):
        return reply == "DC" or reply == "W" or reply == "L" or reply == "DRAW"

    def valid_message(self, msg:str):
        msg = msg.split(",")
        if len(msg) != 2:
            return False
        try:
            i = int(msg[0])
            j = int(msg[1])

            if 0<=i<=2 and 0<=j<=2:
                return True
            return False

        except ValueError or ArithmeticError or IndexError:
            return False

    def send_message(self, message):
        print(f'Sending this message: {message}')
        message = self.id + message
        self.s.send(message.encode())

    def update_my_board(self, reply):

        diff_char = 'N'
        ind = 0
        no_diff = True
        print(reply)
        print(self.board)
        for i in range(3):
            for j in range(3):
                if self.board[i][j] != reply[i][j]:
                    diff_char = reply[i][j]
                    print(f'false, {reply[i][j]}, {self.board[i][j]}')
                    x, y = i, j
                    no_diff = False
                    break
                else:
                    ind += 1

        if no_diff:
            return
        print(f'diff : {diff_char}, pos : {x},{y}')

        self.board[x][y] = diff_char
        print(f'board now\n{self.board}')

        a_bit = 40
        start_x = self.x_to_window(x) + a_bit
        start_y = self.y_to_window(y) + a_bit
        end_x = self.y_to_window(x + 1) - a_bit
        end_y = self.y_to_window(y + 1) - a_bit

        if diff_char == 'X':
            self.drawX(start_x, start_y, end_x, end_y, end_x, start_y, start_x, end_y)
        else:
            self.drawO((start_x + end_x)/2,(start_y + end_y)/2, a_bit)

    def drawX(self, ax, ay, bx, by, cx, cy, dx, dy):
        R = 4
        D = 2

        dt = (ax - ay) / D
        x = ax
        y = ay
        while x <= bx:
            x += D
            y += D
            pygame.draw.circle(self.screen, WHITE, [x, y], R)
            pygame.display.flip()
            self.clock.tick(100)

        x = cx
        y = cy
        while x >= dx:
            x -= D
            y += D
            pygame.draw.circle(self.screen, WHITE, [x, y], R)
            pygame.display.flip()
            self.clock.tick(100)

    def drawO(self, x, y, radius):
        cenx = x
        ceny = y
        R = 2

        x += radius
        theta = math.pi / 90
        cos_theta = math.cos(theta)
        sin_theta = math.sin(theta)

        for i in range(180):
            pygame.draw.circle(self.screen, WHITE, [x, y], R)
            pygame.display.flip()

            temp_x = ((x - cenx) * cos_theta) - ((y - ceny) * sin_theta)
            temp_y = ((x - cenx) * sin_theta) + ((y - ceny) * cos_theta)

            x = temp_x + cenx
            y = temp_y + ceny

            pos = [x, y]
            self.clock.tick(100)

    def x_to_window(self, x):
        return x * (WIN_SIZE_X / 3)

    def y_to_window(self, y):
        return y * (WIN_SIZE_Y / 3)
if __name__ == '__main__':
    p = XO_ClientGUI(id='A')
