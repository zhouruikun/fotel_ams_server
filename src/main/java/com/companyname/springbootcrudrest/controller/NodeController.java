package com.companyname.springbootcrudrest.controller;

import com.companyname.springbootcrudrest.beans.NodeList;
import com.companyname.springbootcrudrest.beans.ResponseCommon;
import com.companyname.springbootcrudrest.beans.UserList;
import com.companyname.springbootcrudrest.exception.ResourceNotFoundException;
import com.companyname.springbootcrudrest.model.Node;
import com.companyname.springbootcrudrest.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class NodeController {

    @Autowired
    private NodeRepository nodeRepository;

    @GetMapping("/nodes")
    public ResponseCommon getAllNodes() {
        ResponseCommon res = new ResponseCommon();
        NodeList list = new NodeList();
        List listNode = nodeRepository.findAll();
        list.setItems(listNode);
        list.setTotal(listNode.size());
        res.setCode(20000);
        res.setMessage("success");
        res.setData(list);
        return res;
    }

    @GetMapping("/nodes/{id}")
    public ResponseEntity<Node> getNodeById(
            @PathVariable(value = "id") Long nodeId) throws ResourceNotFoundException {
        Node node = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Node not found on :: " + nodeId));
        return ResponseEntity.ok().body(node);
    }

    @PostMapping("/nodes")
    public Node createNode(@Valid @RequestBody Node node) {
        return nodeRepository.save(node);
    }

    @PutMapping("/nodes/{id}")
    public ResponseCommon updateNode(
            @PathVariable(value = "id") Long nodeId,
            @Valid @RequestBody Node nodeDetails) throws ResourceNotFoundException {
        Node node = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Node not found on :: " + nodeId));
        ResponseCommon res = new ResponseCommon();
        nodeDetails.setUpdatedAt(new Date());
        final Node updatedNode = nodeRepository.save(nodeDetails);
        res.setCode(20000);
        res.setMessage("update success");
        res.setData(ResponseEntity.ok(updatedNode));
        return res;
    }

    @DeleteMapping("/node/{id}")
    public Map<String, Boolean> deleteNode(
            @PathVariable(value = "id") Long nodeId) throws Exception {
        Node node = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Node not found on :: " + nodeId));

        nodeRepository.delete(node);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}