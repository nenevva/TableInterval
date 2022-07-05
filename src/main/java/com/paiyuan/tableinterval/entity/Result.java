package com.paiyuan.tableinterval.entity;

/*
 * 结果类，包含比较方式、阈值、最大步长、起始行
 */

public class Result {

    private final ComparisonMethods comparison;
    private final double threshold;
    private final int maxLen;
    private final int startPos;

    public Result(ComparisonMethods comparison, double threshold, int maxLen, int startPos) {

        this.comparison = comparison;
        this.threshold = threshold;
        this.maxLen = maxLen;
        this.startPos = startPos;
    }

    public ComparisonMethods getComparison() {
        return comparison;
    }

    public double getThreshold() {
        return threshold;
    }

    public int getMaxLen() {
        return maxLen;
    }

    public int getStartPos() {
        return startPos;
    }
}
