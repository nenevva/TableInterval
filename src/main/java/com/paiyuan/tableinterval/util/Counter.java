package com.paiyuan.tableinterval.util;

import com.paiyuan.tableinterval.entity.ComparisonMethods;
import com.paiyuan.tableinterval.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/*
 * 寻找符合要求的最大步长区间
 */

@Component
public class Counter {

    //阈值
    @Value("${threshold}")
    private double threshold;

    //比较方式：bigger、less
    @Value("${comparison}")
    private ComparisonMethods comparison;

    public Result calculate(ArrayList<Double> dataList, int rowStartPos) {

        dataList.add(Double.MIN_VALUE);

        int maxLen = 0;
        int curLen = 0;
        int startPos = -1;
        boolean recordFlag = false;

        System.out.println("Counter: threshold = " + threshold);
        System.out.println("Counter: dataList.size() = " + (dataList.size() - 1));
        System.out.println("Counter: dataList.getFirst() = " + dataList.get(0));
        System.out.println("Counter: dataList.getLast() = " + dataList.get(dataList.size() - 2));

        if (comparison.equals(ComparisonMethods.bigger)) {
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i) >= threshold) {
                    curLen++;
                    recordFlag = true;
                } else if (dataList.get(i) < threshold && recordFlag) {
                    if (curLen > maxLen) {
                        maxLen = curLen;
                        startPos = i - maxLen + rowStartPos + 1;
                    }
                    curLen = 0;
                    recordFlag = false;
                }
            }
        }
        else if (comparison.equals(ComparisonMethods.less)) {
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i) <= threshold) {
                    curLen++;
                    recordFlag = true;
                } else if (dataList.get(i) > threshold && recordFlag) {
                    if (curLen > maxLen) {
                        maxLen = curLen;
                        startPos = i - maxLen + rowStartPos + 1;
                    }
                    curLen = 0;
                    recordFlag = false;
                }
            }
        }

        System.out.println("Counter: maxLen = " + maxLen);
        System.out.println("Counter: startPos = " + startPos);

        return new Result(comparison, threshold, maxLen, startPos);
    }
}
