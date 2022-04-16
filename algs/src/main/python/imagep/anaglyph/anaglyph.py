"""Simple implementation of Anaglyph Filter.
   Anaglyph is a type of stereo 3D image created from two photographs taken apart.
   The center distance typically between human eyes. The Red color field of the left
   photo is combined with that of the right photo in such a way as to create the illusion of depth.
"""
import sys
import Image
import numpy
import anaglyph_methods
from time import gmtime, strftime


def anaglyph( leftImg, rightImg, methodMxs ):
    leftData = leftImg.getdata()
    rightData = rightImg.getdata()  
    
    outData = []
    
    m1 = numpy.matrix( methodMxs[0] )
    m2 = numpy.matrix( methodMxs[1] )
        
    for i in xrange(0, len(leftData) - 1):
        resRGB = ( m1 * numpy.matrix(leftData[ i ]).T ) + ( m2 * numpy.matrix(rightData[ i ]).T )
        
        resRGB = map( round, resRGB )
        resRGB = map( int, resRGB )
        
        outData.append( tuple( resRGB ) )
             
    return outData
    
    
def main():
    if len( sys.argv ) != 4:
        print 'use options: a_type src_img_left src_img_right'
        exit()
 
    try:
        srcImgLeft = Image.open( sys.argv[2] ) 
        srcImgRight = Image.open( sys.argv[3] ) 
        print 'left image: ', srcImgLeft.format, "%dx%d" % srcImgLeft.size, srcImgLeft.mode, srcImgLeft.getbands()
        print 'right image: ', srcImgRight.format, "%dx%d" % srcImgRight.size, srcImgRight.mode, srcImgRight.getbands()
 
        outputData = anaglyph( srcImgLeft, srcImgRight, anaglyph_methods.methodsMap[ sys.argv[1] ] )
        outputImg = Image.new( 'RGB', srcImgLeft.size )
     
        outputImg.putdata( outputData )
            
        outputImg.save( strftime("%a_%d_%b_%Y_%H:%M:%S", gmtime()) + sys.argv[2], 
                        quality = 100 );
        
        print 'finish!'
    except IOError:
        print 'no such file ' + sys.argv[2] + ' or ' + sys.argv[3]
        exit()
    except Exception:
        print 'use options: src_img_left src_img_right'
        exit()
 
 
if __name__ == "__main__":
 
    try:
        import psyco
        print 'on psyco'
        psyco.full()
    except ImportError:
        pass
 
    main()    