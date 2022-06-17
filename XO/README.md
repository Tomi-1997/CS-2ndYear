# Tic-tac-toe
A match of tic tac toe using tcp to establish a connection between a server and two clients.
### How to play
Need to have: <br>
- From one to three computers in the **same network.** <br>
- Python and the pygame module. (pip install pygame to install it)
1) Host a server (run XO_Server.py), and let whoever plays know your internal IP.  <br>
```
python3 XO_Server.py
```
2) In XO_ClientGUI, at line 9 change SERV_IP to be the server's internal ip. <br>
Connect with two clients from one or two computers. (could be same computer that ran the server) <br>
```
python3 XO_Client.py
```
### Demo
![demo](https://github.com/Tomi-1997/CS-2ndYear/blob/main/XO/demo.gif)
