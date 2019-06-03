package com.companyname.springbootcrudrest.utils;

import com.companyname.springbootcrudrest.model.Node;
import com.companyname.springbootcrudrest.model.NodeDataItem;
import com.companyname.springbootcrudrest.repository.NodeDataRepository;
import com.companyname.springbootcrudrest.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@EnableAsync        // 2.开启多线程
public class ScheduleTask {
    @Autowired
    private NodeDataRepository nodeDataRepository;
    @Autowired
    private NodeRepository nodeRepository;

    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=6*1000*10)
    public void configureTasks() {
        //判断报警
        List listNode = nodeRepository.findAll();
        if (listNode==null)
        {
            return ;
        }
        Iterator<Node> iter = listNode.iterator();
        while (iter.hasNext()) {  //执行过程中会执行数据锁定，性能稍差，若在循环过程中要去掉某个元素只能调用iter.remove()方法。
            Node node = iter.next();
            NodeDataItem item = nodeDataRepository.
                    findFirstByNodeMacOrderByUpdateTimeDesc(node.getMac());
            Date date = new Date();
            long inter = date.getTime() ;
                    inter -=  item.getUpdateTime() * 1000l;
            if ((inter > (6 * 1000 * 60))&&(node.getStatus().equals("ONLINE"))) {
                //触发离线报警
                Date date_off = new Date(item.getUpdateTime() * 1000l);
                node.setStatus("OFFLINE");
                nodeRepository.save(node);
                Calendar ca = Calendar.getInstance();
                ca.setTime(date_off);
                int year =ca.get(Calendar.YEAR);//获取年份
                int month=ca.get(Calendar.MONTH)+1;//获取月份
                int day=ca.get(Calendar.DATE);//获取日 
                int hour=ca.get(Calendar.HOUR_OF_DAY);//小时    
//                System.out.println(year+"  "+month+"  "+day+"  "+ hour+"");
                String[] phones = node.getPhone().split(",");
                SmsAlert.sendToAll(phones,new String[]{node.getArialName(),
                     "风压",year+"",month+"",day+"",
                        hour+""
                },SmsAlert.TEMPLATE_ID_OFFLINE);
            }
        }
    }
//    @Async
//    @Scheduled(fixedDelay = 1000)  //间隔1秒
//    public void first() throws InterruptedException {
//        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
//        System.out.println();
//        Thread.sleep(1000 * 10);
//    }
//
//    @Async
//    @Scheduled(fixedDelay = 2000)
//    public void second() {
//        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
//        System.out.println();
//    }
}