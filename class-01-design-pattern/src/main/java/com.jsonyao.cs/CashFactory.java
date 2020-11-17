package com.jsonyao.cs;

/**
 * 简单工厂模式
 */
public class CashFactory {
    public static CashSuper createCashAccept(String cashType){
        CashSuper cs = null;
        switch (cashType) {
            case "正常收费" :
                cs = new CashNormal();
                break;
            case "打8折" :
                cs = new CashRebate(8);
                break;
            case "满300减100" :
                cs = new CashReturn(300, 100);
                break;
            default :
                break;
        }

        return cs;
    }
}