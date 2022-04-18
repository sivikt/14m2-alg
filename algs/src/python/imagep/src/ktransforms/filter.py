"""Simple implementation of "kernel" based image transformation algorithms such
   as blur, sharpening, etc.
"""
import sys
from time import gmtime, strftime

from PIL import Image

from . import kernels


def applyFilter( img, kernel ):
    inData = img.getdata()

    outData = [0 for x in range( len(inData) )]

    width = img.size[0]
    height = img.size[1]

    kernelWidth = len( kernel )
    kernelHeight = len( kernel )
    kernelHWidth = kernelWidth / 2 
    kernelHHeight = kernelHeight / 2
    
    for x in range(width - 1):
        for y in range(height - 1):
            rSum, gSum, bSum, kSum = 0, 0, 0, 0
            
            for i in range(kernelWidth - 1):
                for j in range(kernelHeight - 1):
                    pixelPosX = x + i - kernelHWidth
                    pixelPosY = y + j - kernelHHeight
                    
                    if ((pixelPosX < 0) or (pixelPosX >= width) or 
                        (pixelPosY < 0) or (pixelPosY >= height)): 
                        continue

                    inPix = int(width * pixelPosY + pixelPosX)
                    
                    r = inData[ inPix ][0]
                    g = inData[ inPix ][1]
                    b = inData[ inPix ][2]

                    kernelVal = kernel[i][j]

                    rSum += r * kernelVal
                    gSum += g * kernelVal
                    bSum += b * kernelVal

                    kSum += kernelVal

            if kSum <= 0: 
                kSum = 1

            rSum /= kSum
            if rSum < 0: 
                rSum = 0
            if rSum > 255:
                rSum = 255

            gSum /= kSum
            if gSum < 0: 
                gSum = 0
            if gSum > 255: 
                gSum = 255

            bSum /= kSum
            if bSum < 0: 
                bSum = 0
            if bSum > 255: 
                bSum = 255

            outPix = width * y + x 
            outData[ outPix ] = (rSum, gSum, bSum)
             
    return outData
    
    
def main():
    if len( sys.argv ) != 3:
        print('use options: [us | s | gr | so | ed | se | sle | lb | bb | gb] source_image')
        exit(1)
 
    try:
        sourceImg = Image.open( sys.argv[2] )  
        print('image: ', sourceImg.format, "%dx%d" % sourceImg.size, sourceImg.mode, sourceImg.getbands())
        
        kernel = kernels.kernelsMap[ sys.argv[1] ]
        if kernel == None:    
            raise Exception
 
        outputData = applyFilter( sourceImg, kernel )
        outputImg = Image.new( 'RGB', sourceImg.size )
     
        outputImg.putdata( outputData )
            
        outputImg.save( strftime("%a_%d_%b_%Y_%H:%M:%S", gmtime()) + sys.argv[2], 
                        quality = 100 )
        
        print('finish!')
    except IOError:
        print('no such file ' + sys.argv[2])
        exit(1)
    except Exception:
        print('use options: [us | s | ed | bb | gb] source_image')
        exit(1)
 
 
if __name__ == "__main__":
 
    try:
        """Psyco is a historical project and only works on Python <= 2.6 on 32-bit x86 Intel CPUs. See also PyPy.
           Psyco shows that it is possible to execute Python code at speeds approaching that of fully compiled languages,
           by “specialization”. This extension module for the unmodified interpreter accelerates user programs with
           little or not change in their sources, by a factor that can be very interesting (2-10 times is common).
        """
        import psyco
        print('on psyco')
        psyco.full()
    except ImportError:
        pass
 
    main()    