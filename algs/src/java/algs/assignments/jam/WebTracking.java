package algs.assignments.jam;

import algs.adt.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * """Web tracking. Suppose that you are tracking N web sites and M users and you want to support the following API:
 *      - User visits a website.
 *      - How many times has a given user visited a given site?
 *    What data structure or data structures would you use?
 *
 *    Hint: maintain a symbol table of symbol tables.
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class WebTracking<User, Site> {

    private Map< User, Pair<Map<Site, Boolean>, Map<Site, Integer>> > visitsHistory;

    public WebTracking() {
        visitsHistory = new HashMap<>();
    }

    public void visit(User user, Site site) {
        Pair<Map<Site, Boolean>, Map<Site, Integer>> history = history(user);
        incVisits(site, history);
        visitSite(site, history);
    }

    public void leave(User user, Site site) {
        leaveSite(site, history(user));
    }

    public Iterable<Site> visits(User user) {
        return new LinkedList<>(history(user).getFirst().keySet());
    }

    public int visitsCount(User user, Site site) {
        Integer count = history(user).getSecond().get(site);
        return count == null ? 0 : count;
    }

    private Pair<Map<Site, Boolean>, Map<Site, Integer>> history(User user) {
        Pair<Map<Site, Boolean>, Map<Site, Integer>> history = visitsHistory.get(user);
        if (history == null) {
            history = Pair.<Map<Site, Boolean>, Map<Site, Integer>>of(new HashMap<>(), new HashMap<>());
            visitsHistory.put(user, history);
        }
        return history;
    }

    private void visitSite(Site site, Pair<Map<Site, Boolean>, Map<Site, Integer>> history) {
        history.getFirst().put(site, Boolean.TRUE);
    }

    private void leaveSite(Site site, Pair<Map<Site, Boolean>, Map<Site, Integer>> history) {
        history.getFirst().remove(site);
    }

    private void incVisits(Site site, Pair<Map<Site, Boolean>, Map<Site, Integer>> history) {
        Integer count = history.getSecond().get(site);
        if (count == null) count = 1;
        else               count += 1;
        history.getSecond().put(site, count);
    }

}
