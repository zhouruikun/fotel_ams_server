package com.companyname.springbootcrudrest.controller;

import com.companyname.springbootcrudrest.beans.NodeDataArray;
import com.companyname.springbootcrudrest.beans.NodeDataPoint;
import com.companyname.springbootcrudrest.beans.ResponseCommon;
import com.companyname.springbootcrudrest.model.Node;
import com.companyname.springbootcrudrest.model.NodeDataItem;
import com.companyname.springbootcrudrest.repository.NodeDataRepository;
import com.companyname.springbootcrudrest.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/")
public class NodeDataController {

    @Autowired
    private NodeDataRepository   nodeDataRepository;
    @Autowired
    private NodeRepository nodeRepository;




    @GetMapping("/get_data")
    public ResponseCommon getdata( @RequestParam(value = "endTime") int endTime,@RequestParam(value = "startTime") int startTime,
                                   @RequestParam(value = "mac") String mac) {
        //获取到JSONObjec
        //判断是否已存在的节点  不存在则添加


        long begintime = System.currentTimeMillis();

        NodeDataItem [] items = nodeDataRepository.findByNodeMacAndUpdateTimeBetween(mac,startTime,endTime);
        long endtinme=System.currentTimeMillis();

        long costTimequery = (endtinme - begintime);
        begintime = System.currentTimeMillis();
        NodeDataArray nodeDataArray  = new NodeDataArray();
        ArrayList<NodeDataPoint> nodeDataList = new ArrayList<>();

        for (NodeDataItem item:items  ) {
            for (int cnt = item.getNodeDataCounts(); cnt>0; cnt--)
            {

                NodeDataPoint points = new NodeDataPoint();
                points.setT(item.getUpdateTime()-(item.getNodeDataCounts()-cnt));
                points.setK(item.getData().getAms5915_t()[cnt-1]);
                points.setS(item.getData().getAms5915_p()[cnt-1]);
                nodeDataList.add(points);
            }
        }
        endtinme=System.currentTimeMillis();
        long costTimedata = (endtinme - begintime);
        nodeDataArray.setNodeDataList(nodeDataList);
        nodeDataArray.setDataNumber(nodeDataList.size());
        ResponseCommon res = new ResponseCommon();
        res.setCode(20000);
        res.setData(nodeDataArray);
        res.setMessage("ok");

        return res;
    }


    @GetMapping("/put_data")
    public ResponseCommon putdata(@RequestBody(required = false) NodeDataItem date) {
        //获取到JSONObjec
        //判断是否已存在的节点  不存在则添加

        ResponseCommon res = new ResponseCommon();
        if (nodeRepository.findByMac(date.getNodeMac())==null)
        {
                Node node = new Node();
                node.setArialName("未命名");
                node.setMac(date.getNodeMac());
                node.setType(date.getNodeType());
                node.setStatus("ONLINE");
                node.setCreatedBy("UPLOAD");
                nodeRepository.save(node);
        }
        nodeDataRepository.save(date);
        res.setCode(20000);
        res.setData(null);
        res.setMessage("ok");
        return res;
    }

}