"""
Write a function:
    def solution(A)

that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
Given A = [1, 2, 3], the function should return 4.
Given A = [−1, −3], the function should return 1.
Write an efficient algorithm for the following assumptions:

N is an integer within the range [1..100,000];
each element of array A is an integer within the range [−1,000,000..1,000,000].
"""
def solution(A):
    A.sort()
    ans = 1
    for i in range(len(A)):
        if (A[i] == ans):
            ans += 1

    return ans


# buggy
def solution2(A):
    A.sort()

    for i in range(len(A) - 1):
        v = A[i]
        w = A[i + 1]
        if v <= 0 and w <= 0:
            continue
        elif (v != w) and (v + 1 != w) and (w > 1):
            return max(v + 1, 1)

    v = A[-1]

    return v + 1 if v > 0 else 1
