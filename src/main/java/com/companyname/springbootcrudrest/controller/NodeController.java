package com.companyname.springbootcrudrest.controller;

import com.companyname.springbootcrudrest.beans.NodeList;
import com.companyname.springbootcrudrest.beans.ResponseCommon;
import com.companyname.springbootcrudrest.beans.UserList;
import com.companyname.springbootcrudrest.exception.ResourceNotFoundException;
import com.companyname.springbootcrudrest.model.Node;
import com.companyname.springbootcrudrest.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseCommon getAllNodes(
            @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit
            , @RequestParam(value = "sort", required = false) String sort
    ) {
        ResponseCommon res = new ResponseCommon();
        NodeList list = new NodeList();

        Pageable pageable;
        if (!sort.contains("-")) {
            pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        } else {
            pageable = PageRequest.of(page - 1, limit, Sort.Direction.DESC, "id");
        }

        Page<Node> listNode = nodeRepository.findAll(pageable);
        list.setItems(listNode.getContent());
        list.setTotal((int) listNode.getTotalElements());


        res.setCode(20000);
        res.setMessage("success");
        res.setData(list);
        return res;
    }

    @GetMapping("/node/{id}")
    public ResponseEntity<Node> getNodeById(
            @PathVariable(value = "id") Long nodeId) throws ResourceNotFoundException {
        Node node = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Node not found on :: " + nodeId));
        return ResponseEntity.ok().body(node);
    }

    @PostMapping("/node")
    public Node createNode(@Valid @RequestBody Node node) {
        return nodeRepository.save(node);
    }

    @PutMapping("/node/{id}")
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
    public ResponseCommon deleteNode(
            @PathVariable(value = "id") Long nodeId) throws Exception {
        Node node = nodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Node not found on :: " + nodeId));
        ResponseCommon res = new ResponseCommon();
        nodeRepository.delete(node);
        res.setCode(20000);
        res.setMessage("deleteNode success");
        return res;
    }
}