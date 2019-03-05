package com.companyname.springbootcrudrest.beans;

import java.util.ArrayList;

public class NodeDataArray {

    long dataNumber;
    ArrayList<NodeDataPoint> nodeDataList;

    public long getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(long dataNumber) {
        this.dataNumber = dataNumber;
    }

    public ArrayList<NodeDataPoint> getNodeDataList() {
        return nodeDataList;
    }

    public void setNodeDataList(ArrayList<NodeDataPoint> nodeDataList) {
        this.nodeDataList = nodeDataList;
    }
}
