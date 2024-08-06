##########
#   Michael Poe
#   4-19-24
#   Program 4
############
import pygame
from Constants import *
from sprite import MySprites
import random
import os




#make a wizard class (is a sprite)
class Player(pygame.sprite.Sprite,MySprites):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        MySprites.__init__(self,WIDTH/2,HEIGHT-70,50,70)
        self.surf = pygame.image.load(os.path.join("Images","wizard.png")).convert()
        self.surf = pygame.transform.scale(self.surf,(self.sizeX,self.sizeY))
        self.surf.set_colorkey((0, 0, 0), RLEACCEL)
        self.rect = self.surf.get_rect()
        self.rect.center = self.getCenter()
        self.lastShot = pygame.time.get_ticks()
        self.shot_cooldown = 450
        self.score = 0
        self.lives = 5

        
    
    def update(self,pressedKeys):
        if pressedKeys[K_LEFT]:
            self.x -= 3
            self.rect.x -= 3
        if pressedKeys[K_RIGHT]:
              self.x += 3
              self.rect.x += 3
        #mask for collisions
        self.mask = pygame.mask.from_surface(self.surf)
        
        #timer and functions for making bullets
        time = pygame.time.get_ticks()
        if pressedKeys[K_SPACE] and ((time - self.lastShot)>self.shot_cooldown) and can_update:
            bullet = Bullet((self.x+25),self.y)
            bullets_group.add(bullet)
            self.lastShot = time

    
    
#make an enemy class (is a sprite)
class Enemy(pygame.sprite.Sprite,MySprites):
    
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        MySprites.__init__(self,randint(0,WIDTH-40),randint(0,ENEMYLIMIT),50,40)
        self.surf = pygame.image.load(os.path.join("Images","spider.png")).convert()
        self.surf = pygame.transform.scale(self.surf,(self.sizeX,self.sizeY))
        self.surf.set_colorkey((0, 0, 0), RLEACCEL)
        self.rect = self.surf.get_rect()
        self.rect.center = self.getCenter()
        self.move_counter = 0
        self.move_direction = 1
        
        

    def update(self):
        self.x += self.move_direction
        self.rect.x += self.move_direction
        self.move_counter += 1
        #makes the enemies move left and right 
        if abs(self.move_counter) > 100:
            self.move_direction *= -1
            self.move_counter *= self.move_direction
        
        #mask for collision and a kill if game over
        self.mask = pygame.mask.from_surface(self.surf)
        if gameOver == -1:
            self.kill()


        



#make a enemy bullet class
class EnemyBullet(pygame.sprite.Sprite,MySprites):
    def __init__(self,x,y):
        pygame.sprite.Sprite.__init__(self)
        MySprites.__init__(self,x,y,15,15)
        self.surf = pygame.image.load(os.path.join("Images","SpiderWeb.jpeg")).convert()
        self.surf = pygame.transform.scale(self.surf,(self.sizeX,self.sizeY))
        self.rect = self.surf.get_rect()
        self.rect.center = self.getCenter()
    

    def update(self):
        #fast enemy bullets if not in boss fight
        if boss_fight == False:
            self.y += 5
            self.rect.y +=5
        #slow enemy bullets if boss fight
        if boss_fight == True:
            self.y +=3
            self.rect.y +=3
       
        #kills bullets when they hit bottom of screen
        if self.y >= (HEIGHT-10):
            self.kill()
        
        #mask for collisions and removing lives counter
        self.mask = pygame.mask.from_surface(self.surf)
        if pygame.sprite.spritecollide(self,wizardGroup,False,pygame.sprite.collide_mask):
            self.kill()
            wiz.lives -= 1
        if gameOver == -1:
            self.kill()
        
        
        

#make a bullet class
class Bullet(pygame.sprite.Sprite,MySprites):
    def __init__(self,x,y):
        pygame.sprite.Sprite.__init__(self)
        MySprites.__init__(self,x,y,15,15)
        self.surf = pygame.image.load(os.path.join("Images","Fireball.jpeg")).convert()
        self.surf = pygame.transform.scale(self.surf,(self.sizeX,self.sizeY))        
        self.rect = self.surf.get_rect()
        self.rect.center = self.getCenter()
    
    #special x getters and setters so bullets can be shot near edges of screen
    @property
    def x(self):
        return self._x
    @x.setter
    def x(self,x):
        if 0<x<(WIDTH-5):
            self._x = x
        else:
            pass

    def update(self):
        #moves the bullet 
        self.y -= 5
        self.rect.y -= 5 

        #kills bullet at top of screen
        if self.y == 0:
            self.kill()

        #mask for collisions with normal enemies then boss 
        self.mask = pygame.mask.from_surface(self.surf)
        if pygame.sprite.spritecollide(self,enemies_group,True,pygame.sprite.collide_mask):
            self.kill()
            wiz.score += 1
        if pygame.sprite.spritecollide(self,boss_group,False,pygame.sprite.collide_mask):
            self.kill()
            boss.health_remaining -= 1
        
        





#make a boss class
class Boss(pygame.sprite.Sprite,MySprites):
    
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        MySprites.__init__(self,125,20,400,400)
        self.surf = pygame.image.load(os.path.join("Images","spider.png")).convert()
        self.surf = pygame.transform.scale(self.surf,(self.sizeX,self.sizeY))
        self.surf.set_colorkey((0, 0, 0), RLEACCEL)
        self.rect = self.surf.get_rect()
        self.rect.center = self.getCenter()
        self.tot_health = 20
        self.health_remaining = 20 #needs to match total health to start
        #green and red healthbar surfaces
        self.healthbar_red_surf = pygame.Surface((400,30))
        self.healthbar_red_surf.fill((255,0,0))
        self.healthbar_green_surf = pygame.Surface((400,30))
        self.healthbar_green_surf.fill((0,255,0))
        

    def update(self): 
        #mask for collisions
        self.mask = pygame.mask.from_surface(self.surf)
        #scales the green healthbar to lower based on a ratio of health remain and total health
        if self.health_remaining > 0: 
            self.healthbar_green_surf = pygame.transform.scale(
                    self.healthbar_green_surf, ((400*(self.health_remaining/self.tot_health)),30)
                    )
        #ends game if boss is killed
        if self.health_remaining == 0:
            global gameOver 
            gameOver = 1
        






def makeText(text,font,x,y):
    #just a funciton that writes text 
    text_surface = font.render(text,True,(0,0,0))
    screen.blit(text_surface,(x,y))
    


#makes screen and creates a clock to track ticks 
screen = pygame.display.set_mode((WIDTH, HEIGHT))
clock = pygame.time.Clock()

#game variables
gameOver = 0
boss_fight = False
boss_created = False
    #for enemy bullets
last_shot = pygame.time.get_ticks()
enemy_cooldown = 500 #miliseconds
boss_cooldown = 1150
counter = 0
boss_countdown = 4
last_count = pygame.time.get_ticks()
can_update = True



#fonts for text
pygame.font.init()
Playing_font = pygame.font.SysFont('Constantia', 15)
End_font = pygame.font.SysFont('Constantia',30)



#Groups
bullets_group = pygame.sprite.Group()
enemyB_group = pygame.sprite.Group()
enemies_group = pygame.sprite.Group()
wizardGroup = pygame.sprite.Group()
boss_group = pygame.sprite.Group()
bossB_group = pygame.sprite.Group()

#making player sprite
wiz = Player()
wizardGroup.add(wiz)
 



RUNNING = True # A variable to determine whether to get out of the
# infinite game loop
while (RUNNING):


    #making enemy sprites and makes sure always ten 
    while len(enemies_group)<10 and gameOver == 0 and not boss_fight:
        spid = Enemy()
        enemies_group.add(spid)

    #enemy bullets
    time_now = pygame.time.get_ticks()
    if (time_now-last_shot)>enemy_cooldown and len(enemies_group)>0:
        shooter = random.choice(enemies_group.sprites())
        eBullet = EnemyBullet(shooter.x,shooter.y)
        enemyB_group.add(eBullet)
        last_shot = pygame.time.get_ticks()




    # kills game if quit or escape
    for event in pygame.event.get():
        if (event.type == KEYDOWN and event.key == K_ESCAPE):
            RUNNING = False
        elif (event.type == QUIT):
            RUNNING = False
        elif (event.type == KEYDOWN and event.key == K_SPACE):
            pass
            
    #pressed key tracker
    pressedKeys = pygame.key.get_pressed()
    

    #updates
    #top one is false during boss countdown so 
    # nothing gets created and turns on when countdown ends
    if boss_countdown == 0:
        can_update = True
    wiz.update(pressedKeys)
    enemies_group.update()
    enemyB_group.update()
    if boss_fight and boss_countdown == 0:
        boss.update()
        bossB_group.update()
    bullets_group.update()

    
    

    # fill the screen with a color
    screen.fill(WHITE)


    # put sprites on screen
    if gameOver == 0:
        #wizard
        screen.blit(wiz.surf,wiz.getPosition())
        #enemies
        for enemy in enemies_group :
            screen.blit(enemy.surf,enemy.getPosition())
        #bullets
        for bullet in bullets_group:
            screen.blit(bullet.surf,bullet.getPosition())
        #enemy bullets
        for bullet in enemyB_group:
            screen.blit(bullet.surf,bullet.getPosition())
        #boss, healthbar, and bullets
        if boss_fight and boss_countdown == 0:
            screen.blit(boss.surf,boss.getPosition())
            screen.blit(boss.healthbar_red_surf,(boss.x+25,boss.y))
            for bullet in bossB_group:
                screen.blit(bullet.surf,bullet.getPosition())
            if boss_created and boss.health_remaining > 0:
                    screen.blit(boss.healthbar_green_surf,(boss.x+25,boss.y))
        #lives and score counter
        makeText("Lives: "+str(wiz.lives)+" Score: "+str(wiz.score),Playing_font,5,HEIGHT-25)

    #If wizard runs out of lives game ends on loss
    if wiz.lives == 0:
        wiz.kill()
        makeText("Game Over!",End_font,(WIDTH/2)-50,(HEIGHT/2)-30)
        gameOver = -1

    #if player kills boss 
    #kills everything and displays victior
    if gameOver == 1:
        wiz.kill()
        boss.kill()
        for bullet in enemyB_group:
            bullet.kill()
        for bullet in bullets_group:
            bullet.kill()
        makeText("Victory!",End_font,(WIDTH/2)-50,(HEIGHT/2)-30)
   

    #Boss fight sequence
    if wiz.score == 15:
        #makes a countdown screen sequence
        if boss_countdown > 0:
            can_update = False
            makeText("Boss Spawning in",End_font,(WIDTH/2)-100,(HEIGHT/2)-30)
            makeText(str(boss_countdown),End_font,(WIDTH/2),(HEIGHT/2)+10)
            boss_count_time = pygame.time.get_ticks()
            if (boss_count_time - last_count) > 1000:
                boss_countdown -= 1
                last_count = boss_count_time
        
        #boss fight started variable
        boss_fight = True

        #runs one time to create boss and kill any remaining sprites
        if boss_created == False:
            for bullets in bullets_group:
                bullets.kill()
            for enemy in enemies_group:
                enemy.kill()
            for bullets in enemyB_group:
                bullets.kill()
            boss = Boss()
            boss_group.add(boss)
            boss_created = True

        #creates boss bullets that change postions on intervals
        boss_shot_time = pygame.time.get_ticks()
        if (boss_shot_time-last_shot)>boss_cooldown and can_update:
            if counter % 2 == 0:
                for i in range(8):
                    eBullet = EnemyBullet((1+(i*90)),300)
                    bossB_group.add(eBullet)
            else:
                for i in range(7):
                    eBullet = EnemyBullet((45+(i*90)),300)
                    bossB_group.add(eBullet)
            counter += 1
            last_shot = pygame.time.get_ticks()




        
        



         
    

        


    #displays and chooses fps
    pygame.display.flip()
    clock.tick(60)