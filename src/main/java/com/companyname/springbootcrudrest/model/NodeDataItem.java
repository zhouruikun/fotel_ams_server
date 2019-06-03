package com.companyname.springbootcrudrest.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

@CompoundIndexes(
        {
                @CompoundIndex(name = "nodeMac_updateTime",def = "{'nodeMac':1,'updateTime':1}")
        })
public class NodeDataItem {

    private String id;
    @Indexed
    private String nodeMac;
    @Indexed
    private int updateTime;
    private String nodeType;
    private int nodeDataCounts;
    private NodeData data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeMac() {
        return nodeMac;
    }

    public void setNodeMac(String nodeMac) {
        this.nodeMac = nodeMac;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public int getNodeDataCounts() {
        return nodeDataCounts;
    }

    public void setNodeDataCounts(int nodeDataCounts) {
        this.nodeDataCounts = nodeDataCounts;
    }

    public NodeData getData() {
        return data;
    }

    public void setData(NodeData data) {
        this.data = data;
    }
}
