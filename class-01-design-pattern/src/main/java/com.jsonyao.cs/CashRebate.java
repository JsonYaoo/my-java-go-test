package com.jsonyao.cs;

public class CashRebate implements CashSuper {
     private double moneyRebate;
     
 
     /**
      * @param moneyRebate 折扣率
      */
     public CashRebate(double moneyRebate) {
         this.moneyRebate = moneyRebate;
     }
 
 
     /* (non-Javadoc)
      * @see day_20_cash.CashSuper#acceptCash(double)
      */
     @Override
     public double acceptCash(double money) {
         
         return money * (moneyRebate / 10);
     }
 
 }