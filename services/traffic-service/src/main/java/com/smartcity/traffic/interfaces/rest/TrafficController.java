package com.smartcity.traffic.interfaces.rest;

import com.smartcity.traffic.application.dto.*;
import com.smartcity.traffic.application.service.TrafficService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 交通信息REST API控制器
 * 提供交通信息的HTTP接口
 */
@RestController
@RequestMapping("/api/traffic")
public class TrafficController {
    
    private final TrafficService trafficService;
    
    @Autowired
    public TrafficController(TrafficService trafficService) {
        this.trafficService = trafficService;
    }
    
    /**
     * 创建交通信息
     */
    @PostMapping
    public ResponseEntity<TrafficResponse> createTraffic(@Valid @RequestBody CreateTrafficRequest request) {
        TrafficResponse response = trafficService.createTraffic(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * 获取交通信息详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrafficResponse> getTrafficById(@PathVariable("id") Long trafficId) {
        TrafficResponse response = trafficService.getTrafficById(trafficId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 根据位置获取交通信息
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<TrafficResponse> getTrafficByLocation(@PathVariable("location") String location) {
        TrafficResponse response = trafficService.getTrafficByLocation(location);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 更新交通信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrafficResponse> updateTraffic(@PathVariable("id") Long trafficId,
                                                       @Valid @RequestBody UpdateTrafficRequest request) {
        TrafficResponse response = trafficService.updateTraffic(trafficId, request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 更新交通状态
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<TrafficResponse> updateTrafficStatus(@PathVariable("id") Long trafficId,
                                                             @Valid @RequestBody UpdateTrafficStatusRequest request) {
        TrafficResponse response = trafficService.updateTrafficStatus(trafficId, request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 更新拥堵级别
     */
    @PutMapping("/{id}/congestion")
    public ResponseEntity<TrafficResponse> updateCongestionLevel(@PathVariable("id") Long trafficId,
                                                             @Valid @RequestBody UpdateCongestionLevelRequest request) {
        TrafficResponse response = trafficService.updateCongestionLevel(trafficId, request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * 获取所有交通信息
     */
    @GetMapping
    public ResponseEntity<List<TrafficResponse>> getAllTrafficInfo() {
        List<TrafficResponse> responses = trafficService.getAllTrafficInfo();
        return ResponseEntity.ok(responses);
    }
    
    /**
     * 获取拥堵路段
     */
    @GetMapping("/congested")
    public ResponseEntity<List<TrafficResponse>> getCongestedRoads(@RequestParam(defaultValue = "7") Integer threshold) {
        List<TrafficResponse> responses = trafficService.getCongestedRoads(threshold);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * 删除交通信息
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraffic(@PathVariable("id") Long trafficId) {
        trafficService.deleteTraffic(trafficId);
        return ResponseEntity.noContent().build();
    }
}