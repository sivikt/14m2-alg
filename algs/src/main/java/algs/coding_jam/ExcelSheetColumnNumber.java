package algs.coding_jam;

// Given a column title as appear in an Excel sheet, return its corresponding column number.

// For example:

//     A -> 1
//     B -> 2
//     C -> 3
//     ...
//     Z -> 26
//     AA -> 27
//     AB -> 28 
//     ...
// Example 1:

// Input: "A"
// Output: 1
// Example 2:

// Input: "AB"
// Output: 28
// Example 3:

// Input: "ZY"
// Output: 701


class ExcelSheetColumnNumber {
    public int titleToNumber(String s) {
        int res = 0;
        int mul = 1;
        
        for (int i = s.length()-1; i >= 0; i--)
        {
            char c = s.charAt(i);
            res = mul*(c - 'A' + 1) + res;
            mul *= 26;
        }
        
        return res;
    }
}