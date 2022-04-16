package com.example.farmtrack.model;

import androidx.room.ColumnInfo;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Expense2 {
    public String expense;
    private String userId;
    private Timestamp date;
    private String username;
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

    public Expense2() {
    }


    public Expense2(String expense, String userId, Timestamp timeAdded, String username, int chemicals, int fertiliser, int machinery, int labour, int seedling, int ploughing, int water, int manure, int weeding, int sales) {
        this.expense = expense;
        this.userId = userId;
        this.date = timeAdded;
        this.username = username;
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

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
