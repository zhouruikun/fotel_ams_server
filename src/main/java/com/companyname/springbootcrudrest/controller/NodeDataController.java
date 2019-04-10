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
import com.csvreader.CsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class NodeDataController {

    @Autowired
    private NodeDataRepository nodeDataRepository;
    @Autowired
    private NodeRepository nodeRepository;


    @GetMapping("/get_data_realtime")
    public ResponseCommon getdata(@RequestParam(value = "mac") String mac, @RequestParam(value = "endTime") int time) {
        //获取到JSONObjec
        //判断是否已存在的节点  不存在则添加

        NodeDataItem item = nodeDataRepository.findFirstByNodeMacOrderByUpdateTimeDesc(mac);
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
    public ResponseCommon getdata(@RequestParam(value = "endTime") int endTime, @RequestParam(value = "startTime") int startTime,
                                  @RequestParam(value = "mac") String mac) {
        //获取到JSONObjec
        //判断是否已存在的节点  不存在则添加
        long begintime = System.currentTimeMillis();
        NodeDataItem[] items = nodeDataRepository.findByNodeMacAndUpdateTimeBetween(mac, startTime, endTime);

        long endtinme = System.currentTimeMillis();

        long costTimequery = (endtinme - begintime);
        begintime = System.currentTimeMillis();
        NodeDataArray nodeDataArray = new NodeDataArray();
        ArrayList<NodeDataPoint> nodeDataList = new ArrayList<>();

        for (NodeDataItem item : items) {
            NodeDataPoint points = new NodeDataPoint();
            points.setT(item.getUpdateTime());
            points.setK(item.getData().getAms5915_t());
            points.setS(item.getData().getAms5915_p());
            nodeDataList.add(points);
        }
        endtinme = System.currentTimeMillis();
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
    public void download_data(HttpServletResponse response, @RequestParam(value = "endTime") int endTime,
                              @RequestParam(value = "startTime") int startTime,
                              @RequestParam(value = "mac") String mac) throws Exception {
        //获取到JSONObjec
        NodeDataItem[] items = nodeDataRepository.findByNodeMacAndUpdateTimeBetween(mac, startTime, endTime);
        Node node = nodeRepository.findByMac(mac);
        SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        try {
            File file = File.createTempFile(node.getArialName() + ":" + fdate.format(new Date(startTime * 1000l)) + "至"
                    + fdate.format(new Date(endTime * 1000l)), ".csv");
            CsvWriter csvWriter = new CsvWriter(file.getCanonicalPath(), ',', Charset.forName("UTF-8"));
            csvWriter.writeRecord(new String[]{"Time", "Temperature", "Wind Speed"});

            for (NodeDataItem item : items) {
                long ts = item.getUpdateTime() * 1000l;
                csvWriter.writeRecord(new String[]{fdate.format(ts),
                        String.valueOf(item.getData().getAms5915_t() / 100.0),
                        String.valueOf(item.getData().getAms5915_p() / 100.0)});
            }

            csvWriter.close();

            response.setContentType("application/csv; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    URLEncoder.encode(file.getName(), "utf-8"));
            InputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }


    @GetMapping("/put_data")
    public ResponseCommon putdata(@RequestBody(required = false) NodeDataItem date) {
        //获取到JSONObjec
        //判断是否已存在的节点  不存在则添加

        ResponseCommon res = new ResponseCommon();
        if (nodeRepository.findByMac(date.getNodeMac()) == null) {
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