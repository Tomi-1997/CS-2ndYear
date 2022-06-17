import socket
import sys

MSGLEN = 128
DEBUG = True

class XO_Server:

    def __init__(self):
        self.board = [['N' for i in range(3)] for j in range(3)]
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.host = '0.0.0.0'
        self.port = 12345

        self.s.bind((self.host, self.port))
        self.s.listen(2)
        self.clients = []

        print('waiting for 2 players...')
        while len(self.clients) < 2:
            c,  addr = self.s.accept()
            self.clients.append((c, addr))

        print("2 players connected, starting...")
        self.run()

    def run(self):
        player1 = self.clients[0][0]
        player2 = self.clients[1][0]

        player1.send('X'.encode())
        player1_id = 'X'
        print(f'Player 1 is X')
        player2.send('O'.encode())
        player2_id = 'O'
        print(f'Player 2 is O')

        turn = player1_id
        send_GO_msg = True

        # While connection is alive, and nobody sent 'q', keep playing.
        while True:
            print(f'{turn}\'s turn!')
            try:
                player1.send(self.__repr__().encode())
                player2.send(self.__repr__().encode())
                current = player1
                if turn == player1_id:
                    if send_GO_msg:
                        player1.send("GO".encode())
                        player2.send("WAIT".encode())
                    buff = player1.recv(MSGLEN).decode()
                else:
                    current = player2
                    if send_GO_msg:
                        player2.send("GO".encode())
                        player1.send("WAIT".encode())
                    buff = player2.recv(MSGLEN).decode()

            except ConnectionError:
                self.dc()
                break

            if len(buff) == 0:
                continue

            sender = buff[0]
            msg = buff[1:].split(",")

            if msg == ['q']:
                print("Got quit message.")
                self.dc()
                break

            if sender == turn:
                if DEBUG: print(f'from {sender}, msg: {msg}')

                # If valid, send the two users the current board, else send the current client error message.
                if self.valid_message(msg):
                    send_GO_msg = True
                    self.board[int(msg[0])][int(msg[1])] = turn

                    # Update both players with the new board.
                    # player1.send(self.__repr__().encode())
                    # player2.send(self.__repr__().encode())

                    # Check if game over, send the winner W and loser L or draw.
                    game_status = self.is_game_over()
                    if game_status == 'W':
                        if turn == player1_id:
                            player1.send("W".encode())
                            player2.send("L".encode())
                        else:
                            player2.send("W".encode())
                            player1.send("L".encode())
                        print(f'{turn} won!')
                        break

                    if game_status == 'D':
                        player2.send("DRAW".encode())
                        player1.send("DRAW".encode())
                        print(f'Draw!')
                        break

                    # Switch turns
                    if turn == player1_id:
                        turn = player2_id
                    else:
                        turn = player1_id
                    if DEBUG: print("Valid input")

                # Invalid message, don't switch turns.
                else:
                    send_GO_msg = False
                    current.send(("!NO!".encode()))
                    if DEBUG: print("Invalid input")
            else:
                current.send(("!NO!".encode()))

    def __repr__(self):
        str = ""
        for i in range(3):
            for j in range(3):
                str = str + self.board[i][j]
        return str

    def dc(self):
        print("Connection error, self destructing in 3..2..1..")
        try:
            self.clients[0][0].send("DC".encode())
        except ConnectionError:
            pass
        try:
            self.clients[1][0].send("DC".encode())
        except ConnectionError:
            pass
        self.s.close()

    def is_game_over(self):

        # W - win
        # F - False
        # D - draw

        # Vertical check
        for i in range(2):
            if self.board[i][0] != 'N' and \
                    self.board[i][0] == self.board[i][1] and \
                    self.board[i][1] == self.board[i][2]:
                return 'W'

        # Horizontal check
        for i in range(2):
            if self.board[0][i] != 'N' and \
                    self.board[0][i] == self.board[1][i] and \
                    self.board[1][i] == self.board[2][i]:
                return 'W'

        # Diagonal check
        if self.board[0][0] != 'N' and \
            self.board[0][0] == self.board[1][1] and \
            self.board[1][1] == self.board[2][2]:
            return 'W'

        if self.board[2][0] != 'N' and \
            self.board[2][0] == self.board[1][1] and \
            self.board[1][1] == self.board[0][2]:
            return 'W'

        # returns D if every line is filled but no one won.
        if not 'N' in self.board[0] and not 'N' in self.board[1] and not 'N' in self.board[2]:
            return 'D'
        return 'F'

    def valid_message(self, msg:str):
        try:
            i = int(msg[0])
            j = int(msg[1])

            if 0<=i<=2 and 0<=j<=2 and self.board[i][j] == 'N':
                return True
            return False

        except ValueError or ArithmeticError:
            return False

if __name__ == '__main__':
    b = XO_Server()
    b.s.close()