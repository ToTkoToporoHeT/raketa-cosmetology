/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel.instruments;

import by.ban.cosmetology.model.Staff;

/**
 *
 * @author alabkovich
 */
public class StaffProvidedServices{
    private int numRBCit = 0;
    private int numForeignCit = 0;
    private double sumCost = 0.0;

    public StaffProvidedServices() {
    }

    public StaffProvidedServices(int numRBCit, int numForeignCit) {
        this.numRBCit = numRBCit;
        this.numForeignCit = numForeignCit;
    }
    
    public StaffProvidedServices(int numRBCit, 
                                 int numForeignCit, 
                                 double sumCost) {
        this.numRBCit = numRBCit;
        this.numForeignCit = numForeignCit;
        this.sumCost = sumCost;
    }

    public int getNumRBCit() {
        return numRBCit;
    }

    public int getNumForeignCit() {
        return numForeignCit;
    }

    public double getSumCost() {
        return sumCost;
    }

    public void plusNumRBCit(int numRBCit) {
        this.numRBCit += numRBCit;
    }

    public void plusNumForeignCit(int numForeignCit) {
        this.numForeignCit += numForeignCit;
    }

    public void plusSumCost(double sumCost) {
        this.sumCost += sumCost;
    }   
    
    public void plusStaffPS(StaffProvidedServices staffPS) {
        this.numRBCit       += staffPS.getNumRBCit();
        this.numForeignCit  += staffPS.getNumForeignCit();
        this.sumCost        += staffPS.getSumCost();
    }

    @Override
    public StaffProvidedServices clone() throws CloneNotSupportedException {
        StaffProvidedServices sps = new StaffProvidedServices(
                this.numRBCit,
                this.numForeignCit,
                this.sumCost
        );
        return sps;
    }
}
