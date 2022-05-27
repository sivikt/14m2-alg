import unittest
from typing import List

"""
You are given an integer array coins representing coins of different denominations
and an integer amount representing a total amount of money.

Return the number of combinations that make up that amount. If that amount of money
cannot be made up by any combination of the coins, return 0.

You may assume that you have an infinite number of each kind of coin.

The answer is guaranteed to fit into a signed 32-bit integer.

Example 1:
Input: amount = 5, coins = [1,2,5]
Output: 4
Explanation: there are four ways to make up the amount:
5=5
5=2+2+1
5=2+1+1+1
5=1+1+1+1+1

Example 2:
Input: amount = 3, coins = [2]
Output: 0
Explanation: the amount of 3 cannot be made up just with coins of 2.

Example 3:
Input: amount = 10, coins = [10]
Output: 1

Constraints:
    1 <= coins.length <= 300
    1 <= coins[i] <= 5000
    All the values of coins are unique.
    0 <= amount <= 5000

"""
class Solution:
    """The following relation holds:
    the number of ways to change amount A using N kinds of coins equals:
      a) the number of ways to change amount A using all but the first kind of coin, plus
      b) the number of ways to change amount A - D using all N kinds of coins, where D is
         the denomination of the first kind of coin.
    """

    def change_rec(self, memo: dict, amount: int, coins: List[int], kinds_of_coins: int) -> int:
        params = (amount, kinds_of_coins)

        if params in memo:
            print(f'memo access for {params}')
            return memo[params]

        if kinds_of_coins < 0:
            return 0

        if amount == 0:
            return 1
        elif amount < 0:
            return 0
        else:
            changes_without_coin = self.change_rec(memo, amount, coins, kinds_of_coins - 1)
            memo[(amount, kinds_of_coins - 1)] = changes_without_coin

            changes_with_coin = self.change_rec(memo, amount - coins[kinds_of_coins], coins, kinds_of_coins)
            memo[(amount - coins[kinds_of_coins], kinds_of_coins)] = changes_with_coin

            return changes_without_coin + changes_with_coin

    def change_rec_no_memo(self, amount: int, coins: List[int], kinds_of_coins: int) -> int:
        if kinds_of_coins < 0:
            return 0

        if amount == 0:
            return 1
        elif amount < 0:
            return 0
        else:
            changes_without_coin = self.change_rec_no_memo(amount, coins, kinds_of_coins - 1)
            changes_with_coin = self.change_rec_no_memo(amount - coins[kinds_of_coins], coins, kinds_of_coins)

            return changes_without_coin + changes_with_coin

    def change(self, amount: int, coins: List[int]) -> int:
        memo = {}
        return self.change_rec(memo, amount, coins, len(coins) - 1)

    def change2(self, amount: int, coins: List[int]) -> int:
        """very slow since calculate the same subtasks multiple times"""
        return self.change_rec_no_memo(amount, coins, len(coins) - 1)

    def change3(self, amount: int, coins: List[int]) -> int:
        """no recursion"""
        memo = [0] * (amount + 1)
        memo[0] = 1

        for c in coins:
            for a in range(c, amount + 1):
                memo[a] = memo[a] + memo[a - c]

        return memo[amount]


class TestSolution(unittest.TestCase):
    def test_change(self):
        params = [
            (5, [1, 2, 5], 4),
            (5, [5], 1),
            (5, [1], 1),
            (3, [2, 5], 0),
            (5, [2], 0),
            (500, [3, 5, 7, 8, 9, 10, 11], 35502874)
        ]

        for p in params:
            assert Solution().change(amount=p[0], coins=p[1]) == p[2]

        for p in params:
            assert Solution().change2(amount=p[0], coins=p[1]) == p[2]

        for p in params:
            assert Solution().change3(amount=p[0], coins=p[1]) == p[2]


if __name__ == '__main__':
    unittest.main()
