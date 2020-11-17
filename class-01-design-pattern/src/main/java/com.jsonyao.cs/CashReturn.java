package com.jsonyao.cs;

public class CashReturn implements CashSuper {
    /**
     * 应收金额
     */
    private double moneyCondition;
    /**
     * 返利金额
     */
    private double moneyReturn;

    public CashReturn(double moneyCondition, double moneyReturn) {
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moneyReturn;
    }

    @Override
    public double acceptCash(double money) {
        if (money >= moneyCondition) {
            money = money - moneyReturn;
        }
        return money;
    }

}