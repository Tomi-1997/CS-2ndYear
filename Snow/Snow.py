import sys
import pygame
import random


class Flake:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.sway = 0.1
        self.dir = 1
        if random.randint(0,1) == 1:
            self.sway = -0.1
            self.dir = -1
        # change represents how frequently the flake will change direction
        self.change = random.randint(30,100)
        self.counter = 0


if __name__ == "__main__":
    pygame.init()
    FLAKES = 10
    X_SIZE = 600
    Y_SIZE = 400
    size = [X_SIZE , Y_SIZE]
    screen = pygame.display.set_mode(size)

    flakes = []
    for i in range(FLAKES):
        flakes.append(Flake(random.randint(0,X_SIZE) , random.randint(0 , Y_SIZE)))

    clock = pygame.time.Clock()
    running = True

    while running:

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False

        # BLACK SCREEN
        screen.fill([0,0,0])

        # FILL SNOW
        for i in flakes:
            pygame.draw.circle(screen, [255,255,255], [i.x, i.y], 1)
            i.y = i.y + 1
            i.x = i.x + i.sway

            if i.counter == i.change:
                # Change direction once in a while, different for each flake
                i.dir = i.dir * -1
                i.sway = i.sway * 0.5
                i.counter = 0
            else:
                i.counter += 1

                # keep sway amount within a certain bound
            if i.sway >= -2 or i.sway <= 2:
                i.sway +=  0.01 * i.dir

            if i.y == Y_SIZE:
                i.y = 0

        pygame.display.flip()
        clock.tick(20)

    pygame.quit()
    sys.exit()
