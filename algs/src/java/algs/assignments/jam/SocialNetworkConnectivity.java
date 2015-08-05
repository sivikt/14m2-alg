package algs.assignments.jam;

import algs.adt.unionfind.impl.WeightedQuickUnionWithPcUF;
import edu.princeton.cs.introcs.In;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.*;

/**
 * """Given a social network containing N members and a log file containing M timestamps
 *    at which times pairs of members formed friendships, design an algorithm to determine
 *    the earliest time at which all members are connected (i.e., every member is a friend
 *    of a friend of a friend ... of a friend). Assume that the log file is sorted by
 *    timestamp and that friendship is an equivalence relation. The running time of your
 *    algorithm should be MlogN or better and use extra space proportional to N.
 *
 * """ (from materials by Robert Sedgewick)
 */
public class SocialNetworkConnectivity {

    private static class Connection {
        private final int friend1;
        private final int friend2;
        private final LocalDateTime time;

        public Connection(int friend1, int friend2, LocalDateTime time) {
            this.friend1 = friend1;
            this.friend2 = friend2;
            this.time = time;
        }

        public int getFriend1() {
            return friend1;
        }

        public int getFriend2() {
            return friend2;
        }

        public LocalDateTime getTime() {
            return time;
        }
    }

    public static LocalDateTime whenFullyConnected(String logPath) {
        In in = new In(logPath);
        int totalMembers = in.readInt();
        in.readLine();
        WeightedQuickUnionWithPcUF friendshipCircles = new WeightedQuickUnionWithPcUF(totalMembers);

        while (in.hasNextLine()) {
            Connection conn = parseConnection(in.readLine());
            friendshipCircles.union(conn.getFriend1(), conn.getFriend2());
            if (friendshipCircles.count() == 1) {
                return conn.getTime();
            }
        }

        return null;
    }

    private static Connection parseConnection(String connStr) {
        String[] parts = connStr.split(" ");
        int friend1 = Integer.parseInt(parts[0]);
        int friend2 = Integer.parseInt(parts[1]);
        LocalDateTime time = LocalDateTime.parse(parts[2], ISO_LOCAL_DATE_TIME);

        return new Connection(friend1, friend2, time);
    }

    public static void main(String[] args) {
        LocalDateTime expected = LocalDateTime.parse("2015-12-09T10:05:30", ISO_LOCAL_DATE_TIME);
        assert expected.equals(whenFullyConnected("algs/src/java/algs/assignments/jam/fixtures/SocialNetworkConnectivity.log"));
    }

}
