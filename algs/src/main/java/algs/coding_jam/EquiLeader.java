package algs.coding_jam;

class EquiLeader {
    public int find_leader(int[] A) {
        if (A.length == 0)
            return -1;
        if (A.length == 1)
            return 0;

        int leaders = 0;
        int leader = 0;

        for (int i = 0; i < A.length; i++) {
            if (leaders == 0) {
                leaders = 1;
                leader = A[i];
            }
            else if (A[i] != leader)
                leaders--;
            else
                leaders++;
        }

        int result = -1;

        if (leaders > 0) {
            int count = 0;
            int l = 0;

            for (int i = 0; i < A.length; i++) {

                if (A[i] == leader) {
                    count++;
                    l = i;
                }
            }

            if (count > (A.length/2)) {
                result = l;
            }
        }

        return result;
    }

    public int solution(int[] A) {
        int leader = find_leader(A);

        if (leader < 0)
            return 0;
        else
            leader = A[leader];

        //System.out.println(leader);

        int[] sums = new int[A.length];
        sums[0] = (A[0] == leader) ? 1 : 0;

        for (int i = 1; i < A.length; i++)
            sums[i] = sums[i-1] + ((A[i] == leader) ? 1 : 0);

        //System.out.println(Arrays.toString(sums));

        int equiLeaders_count = 0;
        for (int i = 0; i < A.length; i++) {
            int left_count = sums[i];
            int right_count = sums[A.length-1] - left_count;
            int l_size = i+1;
            int r_size = A.length - i - 1;

            //System.out.println("left_count=" + left_count + " l_size=" + l_size);
            //System.out.println("right_count=" + right_count+ " r_size=" + r_size);

            if ((left_count > l_size/2) && (right_count > r_size/2))
                equiLeaders_count++;
        }

        return equiLeaders_count;
    }
}