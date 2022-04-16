package com.example.farmtrack.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "expense_table")
public class Expense {
    @ColumnInfo(name = "expense_id")
    @PrimaryKey(autoGenerate = true)
    public long expenseId;
    public String expense;
    @ColumnInfo(name="date")
    public Date date;
    public int chemicals;
    public int fertiliser;
    public int machinery;
    public int labour;
    public int seedling;
    public int ploughing;
    public int water;
    public int manure;
    public int weeding;
    public int sales;

    public Expense() {
    }

    public Expense(String expense,
                   Date date,
                   int chemicals, int fertiliser,
                   int machinery, int labour,
                   int seedling, int ploughing,
                   int water, int manure,
                   int weeding, int sales) {
        this.expense = expense;
        this.date = date;
        this.chemicals = chemicals;
        this.fertiliser = fertiliser;
        this.machinery = machinery;
        this.labour = labour;
        this.seedling = seedling;
        this.ploughing = ploughing;
        this.water = water;
        this.manure = manure;
        this.weeding = weeding;
        this.sales = sales;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(long expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getChemicals() {
        return chemicals;
    }

    public void setChemicals(int chemicals) {
        this.chemicals = chemicals;
    }

    public int getFertiliser() {
        return fertiliser;
    }

    public void setFertiliser(int fertiliser) {
        this.fertiliser = fertiliser;
    }

    public int getMachinery() {
        return machinery;
    }

    public void setMachinery(int machinery) {
        this.machinery = machinery;
    }

    public int getLabour() {
        return labour;
    }

    public void setLabour(int labour) {
        this.labour = labour;
    }

    public int getSeedling() {
        return seedling;
    }

    public void setSeedling(int seedling) {
        this.seedling = seedling;
    }

    public int getPloughing() {
        return ploughing;
    }

    public void setPloughing(int ploughing) {
        this.ploughing = ploughing;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getManure() {
        return manure;
    }

    public void setManure(int manure) {
        this.manure = manure;
    }

    public int getWeeding() {
        return weeding;
    }

    public void setWeeding(int weeding) {
        this.weeding = weeding;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", expense='" + expense + '\'' +
                ", date=" + date +
                ", chemicals=" + chemicals +
                ", fertiliser=" + fertiliser +
                ", machinery=" + machinery +
                ", labour=" + labour +
                ", seedling=" + seedling +
                ", ploughing=" + ploughing +
                ", water=" + water +
                ", manure=" + manure +
                ", weeding=" + weeding +
                ", sales=" + sales +
                '}';
    }
}
