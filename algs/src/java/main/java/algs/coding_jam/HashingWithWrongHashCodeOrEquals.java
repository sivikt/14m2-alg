package algs.coding_jam;

import java.util.HashMap;

/**
 * @author Serj Sintsov
 */
public class HashingWithWrongHashCodeOrEquals {

    private static class OlympicAthlete {
        String name;

        public OlympicAthlete(String name) {
            this.name = name;
        }
    }

    private static class OlympicAthleteEqualsOverride extends OlympicAthlete {
        String name;

        public OlympicAthleteEqualsOverride(String name) {
            super(name);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof OlympicAthleteEqualsOverride))
                return false;

            if (obj == this)
                return true;

            OlympicAthleteEqualsOverride that = (OlympicAthleteEqualsOverride) obj;
            return name != null && name.equals(that.name);
        }
    }

    private static class OlympicAthleteHashcodeOverride extends OlympicAthlete {
        String name;

        public OlympicAthleteHashcodeOverride(String name) {
            super(name);
        }

        @Override
        public int hashCode() {
            return name == null ? 0 : name.hashCode();
        }
    }

    private static class OlympicAthleteIncorrectEquals extends OlympicAthlete {
        String name;

        public OlympicAthleteIncorrectEquals(String name) {
            super(name);
        }

        @Override
        public int hashCode() {
            return name == null ? 0 : name.hashCode();
        }

        public boolean equals(OlympicAthleteIncorrectEquals that) {
            if (that == null)
                return false;

            if (that == this)
                return true;

            return name != null && name.equals(that.name);
        }
    }


    // test it
    public static void main(String[] args) {
        checkWhenEqualsOverride();
        checkWhenHashOverride();
        checkWhenEqualsOverloaded();
    }

    private static void checkWhenEqualsOverride() {
        HashMap<OlympicAthleteEqualsOverride, String> map = new HashMap<>();
        map.put(new OlympicAthleteEqualsOverride("Serj"), "Serj");
        OlympicAthleteEqualsOverride vlad1 = new OlympicAthleteEqualsOverride("Vlad");
        map.put(vlad1, "Vlad1");
        map.put(new OlympicAthleteEqualsOverride("Vlad"), "Vlad2");
        map.put(new OlympicAthleteEqualsOverride("Nika"), "Nika");

        System.out.println(map.get(new OlympicAthleteEqualsOverride("Serj")));
        System.out.println(map.get(new OlympicAthleteEqualsOverride("Vlad")));
        System.out.println(map.get(vlad1));
    }

    private static void checkWhenHashOverride() {
        HashMap<OlympicAthleteHashcodeOverride, String> map = new HashMap<>();
        map.put(new OlympicAthleteHashcodeOverride("Serj"), "Serj");
        OlympicAthleteHashcodeOverride vlad1 = new OlympicAthleteHashcodeOverride("Vlad");
        map.put(vlad1, "Vlad1");
        map.put(new OlympicAthleteHashcodeOverride("Vlad"), "Vlad2");
        map.put(new OlympicAthleteHashcodeOverride("Nika"), "Nika");

        System.out.println(map.get(new OlympicAthleteHashcodeOverride("Serj")));
        System.out.println(map.get(new OlympicAthleteHashcodeOverride("Vlad")));
        System.out.println(map.get(vlad1));
    }

    private static void checkWhenEqualsOverloaded() {
        HashMap<OlympicAthleteIncorrectEquals, String> map = new HashMap<>();
        map.put(new OlympicAthleteIncorrectEquals("Serj"), "Serj");
        OlympicAthleteIncorrectEquals vlad1 = new OlympicAthleteIncorrectEquals("Vlad");
        map.put(vlad1, "Vlad1");
        map.put(new OlympicAthleteIncorrectEquals("Vlad"), "Vlad2");
        map.put(new OlympicAthleteIncorrectEquals("Nika"), "Nika");

        System.out.println(map.get(new OlympicAthleteIncorrectEquals("Serj")));
        System.out.println(map.get(new OlympicAthleteIncorrectEquals("Vlad")));
        System.out.println(map.get(vlad1));
    }

}
