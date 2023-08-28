package junsulime.algorithm.leetcode;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static junsulime.algorithm.Answer.answer;

public class BestTimeToBuyAndSellStock {

    @Test
    void name() {
        answer(enhancedMaxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        answer(enhancedMaxProfit(new int[]{7, 6, 4, 3, 1}));
        answer(enhancedMaxProfit(new int[]{3, 3}));
    }

    /**
     * 시간복잡도: O(n^2)
     * 이중 for loop 를 통해, 최적의 해를 구한다.
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;

        for (int i = 0; i < prices.length; i++) {
            int buy = prices[i];
            for (int j = i + 1; j < prices.length; j++) {
                int sell = prices[j];
                maxProfit = Math.max(maxProfit, sell - buy);
            }
        }
        return maxProfit;
    }

    /**
     * 시간복잡도: O(nlogn)
     * 미래에 얻을 max profit 에 대해 미리 sortedSet 으로 관리한다.
     */
    public int enhancedMaxProfit(int[] prices) {
        int maxProfit = 0;
        TreeSet<Integer> sortedPrice = Arrays.stream(prices)
                .boxed()
                .collect(Collectors.toCollection(TreeSet::new));

        final Map<Integer, Long> priceCount = Arrays.stream(prices)
                .boxed()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        for (int i = 0; i < prices.length - 1; i++) {
            int buy = prices[i];

            final long count = priceCount.get(buy);
            final long remained = count - 1;
            priceCount.put(buy, remained);
            if (remained == 0) {
                sortedPrice.remove(buy);
            }

            maxProfit = Math.max(maxProfit, sortedPrice.last() - buy);
        }
        return maxProfit;
    }
}
