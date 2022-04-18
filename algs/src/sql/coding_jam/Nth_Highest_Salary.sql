-- Write a SQL query to get the nth highest salary from the Employee table.

-- +----+--------+
-- | Id | Salary |
-- +----+--------+
-- | 1  | 100    |
-- | 2  | 200    |
-- | 3  | 300    |
-- +----+--------+
-- For example, given the above Employee table, the nth highest salary where n = 2 is 200. If there is no nth highest salary, then the query should return null.

-- +------------------------+
-- | getNthHighestSalary(2) |
-- +------------------------+
-- | 200                    |
-- +------------------------+

CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  SET N = N-1;
  RETURN (
      # Write your MySQL query statement below.
      select ee.Salary as Salary from Employee as ee 
      	group by ee.Salary order by ee.Salary DESC limit 1 offset N
  );
END


CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  SET N = N-2;
  RETURN (
      # Write your MySQL query statement below.
      select Min(t.Salary) from (
          select ee.Salary as Salary from Employee as ee order by ee.Salary DESC limit 2 offset N
      ) t
  );
END