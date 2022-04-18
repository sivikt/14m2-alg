def is_prime(n):
    if n == 2 or n == 3:
        return True
 
    if n % 2 == 0 or n % 3 == 0:
        return False
 
    i = 5
 
    while i*i <= n:
        if n % i == 0 or n % (i + 2) == 0:
            return False
        else:
            i += 6
 
    return True
 
 
def manipulate_generator(g,n):
    n += 1
    while (True):
        if is_prime(n):
            next(g)
            n += 1
        else:
            break
   
 
def positive_integers_generator():
    n = 1
    while True:
        x = yield n
        if x is not None:
            n = x
        else:
            n += 1
 

k = int(raw_input())
g = positive_integers_generator()

for _ in range(k):
    n = next(g)
    print n
    manipulate_generator(g, n)