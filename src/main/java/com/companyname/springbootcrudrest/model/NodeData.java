package com.companyname.springbootcrudrest.model;

import com.companyname.springbootcrudrest.utils.DoubleSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class NodeData {

    @JsonSerialize(using = DoubleSerialize.class)
    double  ams5915_p;

    @JsonSerialize(using = DoubleSerialize.class)
    double  ams5915_t;

    public double getAms5915_p() {
        return ams5915_p;
    }

    public void setAms5915_p(double ams5915_p) {
        this.ams5915_p = ams5915_p;
    }

    public double getAms5915_t() {
        return ams5915_t;
    }

    public void setAms5915_t(double ams5915_t) {
        this.ams5915_t = ams5915_t;
    }
}
