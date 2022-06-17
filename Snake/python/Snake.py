import random
import sys

import pygame

class Snake:
    def __init__(self, x:int = 1, y:int = 1, prev = None):
        self.x = x
        self.y = y
        self.direction = 'D'
        self.next = None
        self.prev = prev

    def move(self):
        if self.direction == 'D':
            self.y += 1
        elif self.direction == 'U':
            self.y -= 1
        elif self.direction == 'L':
            self.x -= 1
        elif self.direction == 'R':
            self.x += 1


def play():

    # init pygame
    WIDTH = HEIGHT = 800
    RATIO = WIDTH / tiles

    run = True
    pygame.init()
    clock = pygame.time.Clock()
    font = pygame.font.Font('freesansbold.ttf', 10)

    screen = pygame.display.set_mode((WIDTH,HEIGHT))
    pygame.display.set_caption("snake")

    # init snake
    player = Snake(1,1)
    temp = player
    # add tail
    for i in range(5):
        s = Snake(temp.x + 1, temp.y, prev = temp)
        temp.next, temp = s, s

    # draw borders
    start = WIDTH * 0.01
    end = WIDTH  - start
    pygame.draw.line(screen, [255,255,255], [start,start], [start,end])
    pygame.draw.line(screen, [255,255,255], [start,start], [end,start])
    pygame.draw.line(screen, [255,255,255], [end,end], [start,end])
    pygame.draw.line(screen, [255,255,255], [end,end], [end,start])

    # play
    while run:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False

            # control player with arrow keys and press Q to quit.
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_UP and (player.next == None or player.y - 1 != player.next.y):
                    player.direction = 'U'
                elif event.key == pygame.K_RIGHT and (player.next == None or player.x + 1 != player.next.x):
                    player.direction = 'R'
                elif event.key == pygame.K_DOWN and (player.next == None or player.y + 1 != player.next.y):
                    player.direction = 'D'
                elif event.key == pygame.K_LEFT and (player.next == None or player.x - 1 != player.next.x):
                    player.direction = 'L'
                if event.key == pygame.K_q:
                    run = False
                    break

        # delete previous head and tail. (no need to delete everything between)
        pygame.draw.circle(screen, [0, 0,0], [player.x * RATIO, player.y * RATIO], WIDTH / 20)
        temp = player
        while temp.next != None:
            temp = temp.next
        pygame.draw.circle(screen, [0, 0,0], [temp.x * RATIO, temp.y * RATIO], WIDTH / 20)

        ## move current player and his tail and draw new circles
        temp = player.next
        while temp.next != None:
            temp = temp.next

        # except head, move each body part to the location of previous body part
        while temp != player:
            temp.x = temp.prev.x
            temp.y = temp.prev.y
            pygame.draw.circle(screen, [200, 200, 200], [temp.x * RATIO, temp.y * RATIO], WIDTH / 20)
            temp = temp.prev

        # draw head
        player.move()
        pygame.draw.circle(screen, [200, 200,200], [player.x * RATIO, player.y * RATIO], WIDTH / 20)

        # draw position on top of head
        text = font.render(f'{player.x},{player.y}', True, [0,0,0] , [200,200,200])
        text_rec = text.get_rect()
        text_rec.center = [player.x * RATIO, player.y * RATIO]
        screen.blit(text, text_rec)

        ## refresh rate
        pygame.display.flip()
        clock.tick(3)

    pygame.quit()
    sys.exit()

if __name__ == '__main__':
    tiles = 10
    play()