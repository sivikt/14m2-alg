package algs.coding_jam;

import algs.adt.Pair;
import algs.adt.stack.impl.LinkedListStack;
import algs.adt.stack.Stack;

/**
 * Подпоследовательность может и не являться подстрокой (то есть, её элементы не обязательно идут подряд в исходной
 * последовательности). Формально, для строки x длины n необходимо найти максимальное число l и соответствующую ему
 * возрастающую последовательность индексов i1..il, таких что x[ik] < x[i(k+1)], для любого k = [1..l-1] .
 *
 * Наибольшая увеличивающая подпоследовательность имеет применения в физике, математике, теории представления групп,
 * теории случайных матриц. В общем случае известно решение этой задачи за время n logn в худшем случае.
 *
 * Для входа {4,5,7,2,1,13,18} такая подпоследовательность - 4,5,7,13,18.
 *
 * @author Serj Sintsov
 */
public class MaxIncreasingSubsequence {

    /**
     * Простое решение за квадратичное время методом динамического программирования.
     * Возвращает длину этой последовательности и индекс последнего члена последовательности.
     */
    public static Stack<Integer> solve(int[] a) {
        int N = a.length;

        int[] sizes = new int[N];
        int[] links = new int[N];
        sizes[0] = +1;
        links[0] = -1;

        // задача
        for (int i = 1; i < N; i++) {
            // подзадача
            Pair<Integer, Integer> max = findPreviousMax(a, sizes, i);
            sizes[i] = 1 + max.getFirst();
            links[i] = max.getSecond();
        }

        // подзадача
        return answer(links, findMax(sizes));
    }

    /**
     * Вернет текущую максимальную длину последовательности и индекс
     * элемента для которого найден этот максимум.
     */
    private static Pair<Integer, Integer> findPreviousMax(int[] src, int[] sizes, int hi) {
        int max = 0;
        int maxIndex = -1;

        for (int i = hi-1; i >= 0; i--)
            if (src[hi] > src[i] && max < sizes[i]) {
                max = sizes[i];
                maxIndex = i;
            }

        return new Pair<>(max, maxIndex);
    }

    private static int findMax(int[] sizes) {
        int max = 0;

        for (int i = 1; i < sizes.length; i++)
            if (sizes[max] < sizes[i])
                max = i;

        return max;
    }

    private static Stack<Integer> answer(int[] links, int max) {
        Stack<Integer> answer = new LinkedListStack<>();

        while (max != -1) {
            answer.push(max);
            max = links[max];
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] in = {4,5,7,2,1,13,18};
        Stack<Integer> answer = solve(in);
        assert answer.size() == 5;
        assert asStr(answer).equals("0,1,2,5,6,");
    }

    private static String asStr(Iterable it) {
        StringBuilder buf = new StringBuilder();
        for (Object item : it) {
            buf.append(item);
            buf.append(",");
        }
        return buf.toString();
    }
}
