#!/usr/bin/env python

from matplotlib import pyplot as plt

def ivp_chaos():
    f0 = 0.8323
    f = lambda xt: 0.5 + 1.5*xt - 2.0*xt*xt*xt
    time = range(100)

    values = [f0]
    for _ in time:
        values.append(f(values[-1]))

    time.append(101)

    print(values)

    fig = plt.figure()
    fig.canvas.set_window_title('ivp chaos')
    ax = fig.add_subplot(111)

    ax.plot(time, values)

    ax.set_xlabel('time', labelpad=5)
    ax.set_ylabel('value')
    ax.yaxis.set_ticks_position('left')
    ax.xaxis.set_ticks_position('bottom')

    plt.show()

if __name__ == '__main__':
    ivp_chaos()
