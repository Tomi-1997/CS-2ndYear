import socket
import sys
import time

MSGLEN = 64
class XO_Client:
    def __init__(self, id:str):
        self.my_turn = False
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        # Change self.host to server's ip
        self.host = socket.gethostname()

        self.port = 12345
        self.s.connect( (self.host, self.port))

        self.id = id
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

        reply = "NO"
        run = True
        while run:

            while not self.my_turn:
                reply = self.s.recv(MSGLEN).decode()

                if len(reply) == 0:
                    continue
                print(reply)

                if self.stop_indicator(reply):
                    run = False
                    break

                if "GO" in reply:
                    self.my_turn = True


            reply = "NO"
            while self.my_turn:
                val = input()
                if 'q' in val.lower():
                    self.send_message('q')
                    run = False
                    break

                if not self.valid_message(val):
                    continue

                self.send_message(val)
                reply = self.s.recv(MSGLEN).decode()
                print(reply)

                if "NO" not in reply:
                    self.my_turn = False

                if self.stop_indicator(reply):
                    run = False
                    break


        self.s.close()

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

if __name__ == '__main__':
    p = XO_Client(id='A')
