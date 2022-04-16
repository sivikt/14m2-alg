# author Serj Sintsov <ssivikt@gmail.com>, Public Domain 

import sys
import time
import math

"""Output example: [######....] 75%
"""
def progress(start=0, bar_width=30):
    if start > 100:
        start = 100

    if start < 0:
        start = 0

    for percent in xrange(start, 101):
        marks = math.floor(bar_width * (percent / 100.0))
        spaces = math.floor(bar_width - marks)

        marks = int(marks)
        spaces = int(spaces)

        loader = '[' + ('#' * marks) + ('.' * spaces) + ']'

        yield " %s %d%%\r" % (loader, percent)

        if percent == 100:
            yield "\n"

# usage
bars = progress()
for i in bars:
   sys.stdout.write(i)
   sys.stdout.flush()
   time.sleep(0.05)
