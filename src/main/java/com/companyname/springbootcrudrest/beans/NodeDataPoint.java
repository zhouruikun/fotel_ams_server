package com.companyname.springbootcrudrest.beans;

public class NodeDataPoint {
    int t;
    double K;
    double s;

    public double getK() {
        return K;
    }

    public void setK(double k) {
        this.K = k;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }
}
