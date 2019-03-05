package com.companyname.springbootcrudrest.repository;



import com.companyname.springbootcrudrest.model.NodeDataItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeDataRepository extends MongoRepository<NodeDataItem, String> {
    NodeDataItem[] findByNodeMac(String mac);
    NodeDataItem[] findByNodeMacAndUpdateTimeBetween(String mac,int start, int end);
}