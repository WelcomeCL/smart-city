package com.smartcity.adminservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统监控控制器
 */
@RestController
@RequestMapping("/api/admin/system")
public class SystemController {

    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.app.description}")
    private String appDescription;

    private final DiscoveryClient discoveryClient;

    public SystemController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * 获取应用信息
     * @return 应用信息
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getAppInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", appName);
        info.put("version", appVersion);
        info.put("description", appDescription);
        return ResponseEntity.ok(info);
    }

    /**
     * 获取系统运行状态
     * @return 系统状态信息
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        Map<String, Object> status = new HashMap<>();
        
        // JVM信息
        Runtime runtime = Runtime.getRuntime();
        status.put("jvm.memory.total", runtime.totalMemory() / (1024 * 1024) + "MB");
        status.put("jvm.memory.free", runtime.freeMemory() / (1024 * 1024) + "MB");
        status.put("jvm.memory.max", runtime.maxMemory() / (1024 * 1024) + "MB");
        
        // 系统负载
        status.put("system.cpu.cores", runtime.availableProcessors());
        status.put("system.uptime", ManagementFactory.getRuntimeMXBean().getUptime() / 1000 + "s");
        
        return ResponseEntity.ok(status);
    }

    /**
     * 获取已注册的服务列表
     * @return 服务列表
     */
    @GetMapping("/services")
    public ResponseEntity<List<String>> getServices() {
        List<String> services = discoveryClient.getServices();
        return ResponseEntity.ok(services);
    }

    /**
     * 获取特定服务的实例信息
     * @param serviceId 服务ID
     * @return 服务实例列表
     */
    @GetMapping("/services/{serviceId}")
    public ResponseEntity<List<ServiceInstance>> getServiceInstances(@org.springframework.web.bind.annotation.PathVariable("serviceId") String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        return ResponseEntity.ok(instances);
    }
}