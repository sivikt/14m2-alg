# Draws Hilbert Curve
# author Serj Sintsov <ssivikt@gmail.com>, 2014 Public Domain 

import sys
from tkinter import *


class TkDraw(object):
    def __init__(self):
         self._color  = 'black'
         self._x_coor = 0
         self._y_coor = 0
         self._width  = 1024
         self._height = 800

         master = Tk()
         self._canvas = Canvas(master,
                               width=self._width, height=self._height,
                               bg='white')
         self._canvas.pack()

    def LineTo(self, x=0, y=0):
        self._canvas.create_line(self._x_coor, self._y_coor, x, y,
                                 fill=self._color, smooth=1)
        self._x_coor = x
        self._y_coor = y

    @property    
    def Color(self):
        return self._color

    @Color.setter
    def Color(self, value):
        self._color = value

    @property    
    def PenX(self):
        return self._x_coor

    @PenX.setter    
    def PenX(self, value):
        self._x_coor = value
    
    @property
    def PenY(self):
        return self._y_coor

    @PenY.setter
    def PenY(self, value):
        self._y_coor = value

    @property
    def Width(self):
        return self._width

    @property
    def Height(self):
        return self._height

    
class HilbertCurve(object):
    def __init__(self, graphics):
        self._g = graphics

    def _Colorize(self):
        if self._g.Color == 'black':
            self._g.Color = 'red'
        else:
            self._g.Color = 'black'
        
            
    def _StartFrom(self, posX, posY):
        self._g.PenX = posX
        self._g.PenY = posY
            
    def _StepHor(self, width):
        self._g.LineTo(self._g.PenX + width, self._g.PenY)

    def _StepVer(self, width):
        self._g.LineTo(self._g.PenX, self._g.PenY + width)
                        
    ''' |_|
    '''
    def _Top(self, order, width):
        if (order > 0):
            order -= 1
            self._Right(order, width)
            self._StepVer(width)
            self._Top(order, width)
            self._StepHor(-width)
            self._Top(order, width)
            self._StepVer(-width)
            self._Left(order, width)
                        
    '''  _
        | |
    '''    
    def _Down(self, order, width):
        if (order > 0):
            order -= 1
            self._Left(order, width)
            self._StepVer(-width)
            self._Down(order, width)
            self._StepHor(width)
            self._Down(order, width)
            self._StepVer(width)
            self._Right(order, width)

    '''  _
        |_
    '''
    def _Right(self, order, width):
        if (order > 0):
            order -= 1 
            self._Top(order, width)
            self._StepHor(-width)
            self._Right(order, width)
            self._StepVer(width)
            self._Right(order, width)
            self._StepHor(width)
            self._Down(order, width)
            
    ''' _
        _|
    '''
    def _Left(self, order, width):
        if (order > 0):
            order -= 1
            self._Down(order, width)
            self._StepHor(width)
            self._Left(order, width)
            self._StepVer(-width)
            self._Left(order, width)
            self._StepHor(-width)
            self._Top(order, width)
            
    def Recur(self, order=0):
        posX = self._g.Width / 2
        posY = self._g.Height / 2
        width = posX + (posX / 2)

        for i in range(1, order+1):
            width /= 2
            posX += width / 2
            posY -= width / 2

            self._Colorize()
            self._StartFrom(posX, posY)
            
            print("Hilbert order = %d, width=%d" % (i, width))
            self._Right(i, width)

            
def main():
    order = int(sys.argv[1])
    g  = TkDraw()
    hc = HilbertCurve(g)
    hc.Recur(order=order)
    mainloop()

    
main()
