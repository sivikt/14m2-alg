class Solution {

    public int[] nextGreaterElements(int[] nums) {
        if (nums.length == 0)
	        return new int[0];

        int[] res = new int[nums.length];

        Deque<Integer> st = new ArrayDeque<>();
        
        for (int i = 0; i < nums.length; i++) 
        {
            if (st.isEmpty()) 
            {
                st.push(i);
                continue;
            }

            while (!st.isEmpty() && nums[st.peek()] < nums[i]) 
                res[st.pop()] = nums[i];

            st.push(i);
        } 
        
        int j = st.peekFirst();
        for (int i = 0; !st.isEmpty() && (i <= j); i++) 
        {
            while (!st.isEmpty() && nums[st.peek()] < nums[i]) 
                res[st.pop()] = nums[i];
        }

        while (!st.isEmpty())
            res[st.pop()] = -1;
        
        return res;
    }
    
}
