package com.companyname.springbootcrudrest.controller;

import com.companyname.springbootcrudrest.beans.ExcelData;
import com.companyname.springbootcrudrest.beans.NodeDataArray;
import com.companyname.springbootcrudrest.beans.NodeDataPoint;
import com.companyname.springbootcrudrest.beans.ResponseCommon;
import com.companyname.springbootcrudrest.model.Node;
import com.companyname.springbootcrudrest.model.NodeDataItem;
import com.companyname.springbootcrudrest.repository.NodeDataRepository;
import com.companyname.springbootcrudrest.repository.NodeRepository;
import com.companyname.springbootcrudrest.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class NodeDataController {

    @Autowired
    private NodeDataRepository   nodeDataRepository;
    @Autowired
    private NodeRepository nodeRepository;


    @GetMapping("/get_data_realtime")
    public ResponseCommon getdata( @RequestParam(value = "mac") String mac,@RequestParam(value = "endTime") int time) {
        //获取到JSONObjec
        //判断是否已存在的节点  不存在则添加

        NodeDataItem  item = nodeDataRepository.findFirstByNodeMacOrderByUpdateTimeDesc(mac);
        NodeDataPoint points = new NodeDataPoint();
        points.setT(item.getUpdateTime());
        points.setK(item.getData().getAms5915_t());
        points.setS(item.getData().getAms5915_p());
        ResponseCommon res = new ResponseCommon();
        res.setCode(20000);
        res.setData(points);
        res.setMessage("ok");
        return res;
    }

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
                NodeDataPoint points = new NodeDataPoint();
                points.setT(item.getUpdateTime());
                points.setK(item.getData().getAms5915_t());
                points.setS(item.getData().getAms5915_p());
                nodeDataList.add(points);
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

    @GetMapping("/download_data")
    public void download_data(HttpServletResponse response, @RequestParam(value = "endTime") int endTime, @RequestParam(value = "startTime") int startTime,
                              @RequestParam(value = "mac") String mac)throws Exception {

        //判断是否已存在的节点  不存在则添加
        long begintime = System.currentTimeMillis();

        ExcelData data = new ExcelData();
        data.setName("用户信息数据");
        //获取到JSONObjec
        NodeDataItem [] items = nodeDataRepository.findByNodeMacAndUpdateTimeBetween(mac,startTime,endTime);
        Node node = nodeRepository.findByMac(mac);

        long endtinme=System.currentTimeMillis();
        long costTimequery = (endtinme - begintime);
        System.out.println("get data from database"+costTimequery);
        begintime = endtinme;
        //添加表头

        List<String> titles = new ArrayList();
        //for(String title: excelInfo.getNames())
        titles.add("时间");
        titles.add("温度");
        titles.add("气压");
        data.setTitles(titles);
        List<List<Object>> nodeDataList = new ArrayList<>();
        List<Object> points = null;
        SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        for (NodeDataItem item:items  ) {
            points = new ArrayList();
            long ts= item.getUpdateTime()*1000l;
            points.add(fdate.format(ts));
            points.add(item.getData().getAms5915_t()/100.0);
            points.add(item.getData().getAms5915_p()/100.0);
            nodeDataList.add(points);
        }
        //添加列
        data.setRows(nodeDataList);
        String fileName=node.getArialName()+":"+fdate.format(new Date(startTime*1000l))+"至"+fdate.format(new Date(endTime*1000l))+".xlsx";

        endtinme=System.currentTimeMillis();
        costTimequery = (endtinme - begintime);
        System.out.println("passdata to excel"+costTimequery);
        begintime = endtinme;

        ExcelUtils.exportExcel(response, fileName, data);

        endtinme=System.currentTimeMillis();
        costTimequery = (endtinme - begintime);
        System.out.println("write to client"+costTimequery);
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