TRUE = [
    [[ 0.299, 0.587, 0.114 ], 
     [ 0, 0, 0 ], 
     [ 0, 0, 0 ]],
     
    [[ 0, 0, 0 ], 
     [ 0, 0, 0 ], 
     [ 0.299, 0.587, 0.114 ]] 
]  
    
GRAY = [
    [[ 0.299, 0.587, 0.114 ], 
     [ 0, 0, 0 ], 
     [ 0, 0, 0 ]],
     
    [[ 0, 0, 0 ], 
     [ 0.299, 0.587, 0.114 ], 
     [ 0.299, 0.587, 0.114 ]] 
] 

COLOR = [
    [[ 1, 0, 0 ], 
     [ 0, 0, 0 ], 
     [ 0, 0, 0 ]],
     
    [[ 0, 0, 0 ], 
     [ 0, 1, 0 ], 
     [ 0, 0, 1 ]] 
] 

HALF_COLOR = [
    [[ 0.299, 0.587, 0.114 ], 
     [ 0, 0, 0 ], 
     [ 0, 0, 0 ]],
     
    [[ 0, 0, 0 ], 
     [ 0, 1, 0 ], 
     [ 0, 0, 1 ]] 
] 

OPTIMIZED = [
    [[ 0, 0.7, 0.3 ], 
     [ 0, 0, 0 ], 
     [ 0, 0, 0 ]],
     
    [[ 0, 0, 0 ], 
     [ 0, 1, 0 ], 
     [ 0, 0, 1 ]] 
]

methodsMap = {
    't' : TRUE,
    'g' : GRAY,
    'c' : COLOR,
    'h' : HALF_COLOR,
    'o' : OPTIMIZED
}