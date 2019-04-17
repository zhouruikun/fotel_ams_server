package com.companyname.springbootcrudrest.repository;

import com.companyname.springbootcrudrest.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NodeRepository extends JpaRepository<Node, Long>{
    Node findByMac(String mac);

}