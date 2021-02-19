package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model;

import java.io.Serializable;

public class GeneralItem extends ListItem implements Serializable {

    private ExpensesAmount expensesAmount;

    public ExpensesAmount getExpensesAmount() {
        return expensesAmount;
    }

    public void setExpensesAmount(ExpensesAmount expensesAmount) {
        this.expensesAmount = expensesAmount;
    }

    @Override
    public int getType() {
        return TYPE_GENERAL;
    }

    @Override
    public int compareTo(ListItem o) {
        return 0;
    }
}
