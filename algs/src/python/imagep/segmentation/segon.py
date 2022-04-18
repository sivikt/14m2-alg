#
# inspired by http://cs.brown.edu/~pff/segment/
#

import sys
import math
import random
from time import gmtime, strftime

from PIL import Image

from ..ktransforms.filter import applyFilter
from ..ktransforms.kernels import GAUSSIAN_BLUR

# disjoint-set forests using union-by-rank and path compression (sort of)
class UnionElem:
    def __init__( self, rank, p, size ):
        self.rank, self.p, self.size = rank, p, size
 
    def __str__( self ):
        return "rank = " + self.rank + "; size = " + self.size + "; p = " + self.p


class UnionFindSet:
    def __init__( self, elemsCount ):
        self.segmentsCount = elemsCount
        self.elems = []
        
        for i in range(elemsCount - 1):
            self.elems.append( UnionElem( 0, i, 1 ) )
 
    def __str__( self):
        return "elems = " + self.elems + "; segmentsCount = " + self.segmentsCount
    
    def find( self, x):
        y = x
        while y != self.elems[ y ].p:
            y = self.elems[ y ].p
            self.elems[ x ].p = y
    
        return y
  
    def join( self, x, y ):
        if self.elems[ x ].rank > self.elems[ y ].rank:
            self.elems[ y ].p = x
            self.elems[ x ].size += self.elems[ y ].size
        else:
            self.elems[ x ].p = y
            self.elems[ y ].size += self.elems[ x ].size
            
            if self.elems[ x ].rank == self.elems[ y ].rank:
                self.elems[ y ].rank += 1
        
        self.segmentsCount -= 1
  
    def size( self, x ):
        return self.elems[ x ].size
    
    def segmentsCount( self ):
        return self.segmentsCount


class Edge:
    def __init__(self, a, b, w):
        self.a, self.b, self.w = a, b, w
 
    def __str__(self):
        return "a = " + self.a + "; b = " + self.b + "; w = " + self.w


# dissimilarity measure between pixels
def simplePixelDist( rgb1, rgb2 ):
    return math.sqrt( math.pow( rgb1[0] - rgb2[0], 2 ) + 
                      math.pow( rgb1[1] - rgb2[1], 2 ) + 
                      math.pow( rgb1[2] - rgb2[2], 2 ) )


def findSegments( img, c, minSize, colorDistFunc ):
    # smooth input image
    inData = applyFilter( img, GAUSSIAN_BLUR )
    
    outData = [0 for x in range( len(inData) )]

    width = img.size[0]
    height = img.size[1]
    pixelsCount = width*height
      
    # build graph
    edges = []
    for y in range(height - 1):
        for x in range(width - 1):
            offset = y * width + x
          
            if x < width - 2:
                w = colorDistFunc( inData[ offset ], inData[ offset + 1 ] )
                edges.append( Edge( offset, offset + 1, w ) )
    
            if y < height - 2:
                w = colorDistFunc( inData[ offset ], inData[ offset + width] )
                edges.append( Edge( offset, offset + width, w ) )
            
            if ( x < width - 2 ) and ( y < height - 2 ):
                w = colorDistFunc( inData[ offset ], inData[ offset + width + 1 ] )
                edges.append( Edge( offset, offset + width + 1, w ) )
            
            if ( x < width - 2 ) and ( y > 0 ):
                w = colorDistFunc( inData[ offset ], inData[ offset - width + 1 ] )
                edges.append( Edge( offset, offset - width + 1, w ) )
    
    print('graph building was finished! edges count ', len(edges))
        
    edges.sort(key=lambda edge: edge.w, reverse=False)        
    print('edges sorting was finished!')
     
    # find unions 
    # make a disjoint-set forest
    unionFindSet = UnionFindSet( pixelsCount )

    thresholdFunc = lambda size, c: c / size
        
    # init thresholds
    thresholds = []
    for i in range(pixelsCount):
        thresholds.append( thresholdFunc( 1, c ) )

    for i in (0, len(edges) - 1):
        edge = edges[ i ]
    
        # components conected by this edge
        a = unionFindSet.find( edge.a )
        b = unionFindSet.find( edge.b )
        
        if a != b:
            if ( edge.w <= thresholds[ a ] ) and ( edge.w <= thresholds[ b ] ):
                unionFindSet.join( a, b )
                a = unionFindSet.find( a )
                thresholds[ a ] = edge.w + thresholdFunc( unionFindSet.size( a ), c )
    
    print('segmentation was finished! segments count ', unionFindSet.segmentsCount)
         
    for i in range(len(edges) - 1):
        a = unionFindSet.find( edges[ i ].a )
        b = unionFindSet.find( edges[ i ].b )
        
        if (a != b) and ( 
                (unionFindSet.size( a ) < minSize) or (unionFindSet.size( b ) < minSize) ):
            
            unionFindSet.join( a, b )
            
    print('post process small components! final segments count ', unionFindSet.segmentsCount)
     
    # pick random colors for each component
    colors = []
    for i in range(pixelsCount - 1):
        colors.append( (random.randint(0, 255), random.randint(0, 255), random.randint(0, 255)) )
  
    for y in range(height - 1):
        for x in range(width - 1):
            offset = y * width + x
            comp = unionFindSet.find( offset )
            outData[ offset ] = colors[ comp ]
    
    print('segments colorized finished!')
             
    return outData
    
    
def main():
    if len( sys.argv ) != 4:
        print('use options: c min_size source_image')
        exit()
 
    try:
        sourceImg = Image.open( sys.argv[3] )  
        print('image: ', sourceImg.format, "%dx%d" % sourceImg.size, sourceImg.mode, sourceImg.getbands())
 
        outputData = findSegments( sourceImg, 
                                   int( sys.argv[1], 10 ), 
                                   int( sys.argv[2], 10 ), 
                                   simplePixelDist )
        outputImg = Image.new( 'RGB', sourceImg.size )
     
        outputImg.putdata( outputData )
            
        outputImg.save( strftime("%a_%d_%b_%Y_%H:%M:%S", gmtime()) + sys.argv[3], 
                        quality = 100 )
        
        print('finish!')
    except IOError:
        print('no such file ' + sys.argv[3])
        exit()
    except Exception:
        print('use options: source_image')
        exit()
 
 
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