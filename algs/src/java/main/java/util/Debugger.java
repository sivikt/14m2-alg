package java.util;

import algs.adt.Pair;

import java.util.Stack;

/**
 * Simple debugger which provides possibility to measure execution time
 * and memory usage.
 *
 * @author Serj Sintsov
 */
public final class Debugger {

    private Stack<Long> memoryMarkers = new Stack<>();
    private Stack<Pair<String, Long>> timeMarkers = new Stack<>();

    public void startTimer(String msg) {
        timeMarkers.push(Pair.of(msg, System.nanoTime()));
        log(prefix() + "start timer: " + msg);
    }

    public void startTimer() {
        startTimer("");
    }

    public void stopTimer() {
        long end = System.nanoTime();
        Pair<String, Long> currTimeMarker = timeMarkers.pop();
        log("%s stop timer in %s sec: %s", prefix(), toSec(end - currTimeMarker.getSecond()), currTimeMarker.getFirst());
    }

    public void markFreeMemory() {
        long memoryMarker = calcFreeMemory();
        memoryMarkers.push(memoryMarker);
        log("%s mark free memory on %s bytes (%s MB)", prefix(), memoryMarker, toMb(memoryMarker));
    }

    public void checkMemoryUsage() {
        long bytes = memoryMarkers.pop() - calcFreeMemory();
        log("%s memory usage $s bytes (%s MB)", prefix(), bytes, toMb(bytes));
    }

    private double toMb(long bytes) {
        return (double) (bytes / 1024) / 1024;
    }

    private void log(String str, Object... args) {
        System.out.println(String.format(str, args));
    }

    private double toSec(long nSec) {
        return (double) nSec / 1_000_000_000;
    }

    private String prefix() {
        return "dbg: ";
    }

    private long calcFreeMemory() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory  = runtime.freeMemory();
        long maxMemory   = runtime.maxMemory();
        long usedMemory  = totalMemory - freeMemory;
        long availableMemory = maxMemory - usedMemory;
        return availableMemory;
    }

}