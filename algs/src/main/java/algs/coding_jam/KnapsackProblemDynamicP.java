package algs.coding_jam;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.max;

/**
 * """ Задача о ранце (рюкзаке) (англ. Knapsack problem) — одна из NP-полных задач комбинаторной оптимизации.
 *     Название своё получила от максимизационной задачи укладки как можно большего числа ценных вещей в рюкзак при
 *     условии, что общий объём (или вес) всех предметов, способных поместиться в рюкзак, ограничен. Задачи о загрузке
 *     (о рюкзаке) и её модификации часто возникают в экономике, прикладной математике, криптографии, генетике и
 *     логистике для нахождения оптимальной загрузки транспорта (самолёта, поезда, трюма корабля) или склада. В общем
 *     виде задачу можно сформулировать так: из заданного множества предметов со свойствами «стоимость» и «вес»,
 *     требуется отобрать некое число предметов таким образом, чтобы получить максимальную суммарную стоимость при
 *     одновременном соблюдении ограничения на суммарный вес.
 *
 *     Решается разными методами:
 *      - полный перебор
 *      - динамическое программирование
 *      - метод ветвей и границ
 *      - приближёнными алгоритмами (жадным алгоритмом, генетическим алгоритмом)
 * """ wikipedia
 *
 * @author Serj Sintsov
 */
public class KnapsackProblemDynamicP {

    /**
     * частный случай ограниченного рюкзака, когда каждый из предметов может быть взят
     * только в одном экземпляре
     */
    public static Set<Integer> solveBKP(int W, int[] weights, int[] prices) {
        int[][] A = calcPricesMatrix(W, weights, prices);
        return restoreObjects(A, weights);
    }

    public static int[][] calcPricesMatrix(int W, int[] weights, int[] prices) {
        int N = weights.length;
        int[][] A = new int[N+1][W+1];

        for (int k = 0; k < N; k++) {
            for (int w = 1; w <= W; w++) {
                if (w >= weights[k])
                    A[k+1][w] = max(A[k][w], A[k][w - weights[k]] + prices[k]);
                else
                    A[k+1][w] = A[k][w];
            }
        }

        return A;
    }

    public static Set<Integer> restoreObjects(int[][] A, int[] weights) {
        Set<Integer> answer = new HashSet<>();
        int N = A.length-1;
        int k = N;
        int w = A[0].length-1;

        while (A[k][w] != 0) {
            if (A[k][w] == A[k-1][w])
                k -= 1;
            else {
                answer.add(k-1);
                k -= 1;
                w -= weights[k];
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int W = 13;
        int[] weight = {3, 4, 5, 8, 9};
        int[] prices = {1, 6, 4, 7, 6};

        Set<Integer> optimalAnswer = solveBKP(W, weight, prices);
        assert optimalAnswer.size() == 2;
        assert optimalAnswer.contains(1);
        assert optimalAnswer.contains(3);

        int maxPrice = optimalAnswer.stream().reduce((a, b) -> prices[a] + prices[b]).get();
        assert maxPrice == 13;
    }

}
