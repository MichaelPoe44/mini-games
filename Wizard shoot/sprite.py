# a class for sprites
from Constants import *

class MySprites:
    def __init__(self,x,y,sizeX,sizeY):
        """x and y is the x and y position of the top left of a 
        sprite. SizeX and sizeY are the size of the image for x and y 
        that you want it to be"""
        self.x = x
        self.y = y
        self.sizeX = sizeX
        self.sizeY = sizeY

    @property
    def x(self):
        return self._x
    @x.setter
    def x(self,x):
        if 0<=x<=(WIDTH-40):
            self._x = x
        else:
            pass
    @property
    def y(self):
        return self._y
    @y.setter
    def y(self,y):
        if 0<=y<=HEIGHT:
            self._y = y
        else:
            pass

    def getPosition(self):
        return(self.x,self.y)
    
    def getCenter(self):
        self.centerX = self.x+(self.sizeX/2)
        self.centerY = self.y+(self.sizeY/2)
        return (self.centerX,self.centerY)

    
      