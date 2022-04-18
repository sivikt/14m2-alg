"""Set of convolution matrix (kernels, masks)
"""
UNSHARPEN = [
    [ -1, -1, -1 ], 
    [ -1, 9, -1 ], 
    [ -1, -1, -1 ]
]
    
SHARPEN = [ 
    [ -1, -1, -1 ], 
    [ -1, 16, -1 ], 
    [ -1, -1, -1 ]
]

EDGE_DETECT = [
    [ -1, -1, -1 ], 
    [ 0, 0, 0 ], 
    [ 1, 1, 1 ] 
]

BOX_BLUR = [
    [ 1, 1, 1 ], 
    [ 1, 1, 1 ], 
    [ 1, 1, 1 ] 
]

LIGHT_BLUR = [
    [ 1, 1, 0 ], 
    [ 1, 1, 0 ], 
    [ 0, 0, 0 ] 
]

# Gaussian filter
GAUSSIAN_BLUR = [
    [ 1, 2, 3, 2, 1 ],
    [ 2, 4, 5, 4, 2 ],
    [ 3, 5, 6, 5, 3 ],
    [ 2, 4, 5, 4, 2 ],
    [ 1, 2, 3, 2, 1 ]
]

STRENGTH_EDGE = [
    [ 0, 0, 0 ],
    [ -1, 1, 0 ],
    [ 0, 0, 0 ]
]

SELECT_EDGE = [
    [ 0, -1, 0 ],
    [ -1, 4, -1 ],
    [ 0, -1, 0 ]
]

GIVE_RELIEF = [
    [ -3, -2, 1 ],
    [ -2, 7, 2 ],
    [ 1, 2, 3 ]
]

SOBEL = [
    [ -2, -1, 0 ],
    [ -1, 1, 1 ],
    [ 0, 1, 2 ]
]

kernelsMap = {
    'us' : UNSHARPEN, 
    's'  : SHARPEN,
    'gr' : GIVE_RELIEF,
    'so' : SOBEL,
    'ed' : EDGE_DETECT,
    'se' : STRENGTH_EDGE,
    'sle': SELECT_EDGE,
    'lb' : LIGHT_BLUR,
    'bb' : BOX_BLUR,
    'gb' : GAUSSIAN_BLUR
}