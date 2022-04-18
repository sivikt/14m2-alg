package algs.coding_jam;

// Write a program that outputs the string representation of numbers from 1 to n.

// But for multiples of three it should output “Fizz” instead of the number and 
// for the multiples of five output “Buzz”. For numbers which are multiples of 
//     both three and five output “FizzBuzz”.

// Example:

// n = 15,

// Return:
// [
//     "1",
//     "2",
//     "Fizz",
//     "4",
//     "Buzz",
//     "Fizz",
//     "7",
//     "8",
//     "Fizz",
//     "Buzz",
//     "11",
//     "Fizz",
//     "13",
//     "14",
//     "FizzBuzz"
// ]


import java.util.LinkedList;
import java.util.List;

class FizzBuzz {
    public List<String> fizzBuzz(int n) {
        List<String> result = new LinkedList<String>();
        
        for (int i = 1; i <= n; i++)
        {
            boolean d3 = i % 3 == 0;
            boolean d5 = i % 5 == 0;
            
            if (d3 && d5)
                result.add("FizzBuzz");
            else if (d3)
                result.add("Fizz");
            else if (d5)
                result.add("Buzz");
            else
                result.add(String.valueOf(i));
        }
        
        return result;
    }
}