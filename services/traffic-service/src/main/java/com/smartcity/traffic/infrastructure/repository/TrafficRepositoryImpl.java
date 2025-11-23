package com.smartcity.traffic.infrastructure.repository;

import com.smartcity.traffic.domain.Traffic;
import com.smartcity.traffic.domain.repository.TrafficRepository;
import com.smartcity.traffic.infrastructure.persistence.jpa.TrafficJpaEntity;
import com.smartcity.traffic.infrastructure.persistence.jpa.TrafficJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TrafficRepositoryImpl implements TrafficRepository {

    private final TrafficJpaRepository trafficJpaRepository;

    public TrafficRepositoryImpl(TrafficJpaRepository trafficJpaRepository) {
        this.trafficJpaRepository = trafficJpaRepository;
    }

    @Override
    public Traffic save(Traffic traffic) {
        // 使用TrafficJpaEntity的fromDomainEntity静态方法创建实体
        TrafficJpaEntity entity = TrafficJpaEntity.fromDomainEntity(traffic);
        
        entity = trafficJpaRepository.save(entity);
        return entity.toDomainEntity();
    }

    @Override
    public Optional<Traffic> findById(Long id) {
        return trafficJpaRepository.findById(id)
                .map(TrafficJpaEntity::toDomainEntity);
    }

    @Override
    public void deleteById(Long id) {
        trafficJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return trafficJpaRepository.existsById(id);
    }

    @Override
    public List<Traffic> findAll() {
        return trafficJpaRepository.findAll()
                .stream()
                .map(TrafficJpaEntity::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Traffic> findByLocation(String location) {
        return trafficJpaRepository.findByLocation(location)
                .map(TrafficJpaEntity::toDomainEntity);
    }

    @Override
    public List<Traffic> findByStatus(Traffic.TrafficStatus status) {
        return trafficJpaRepository.findByStatus(status.name())
                .stream()
                .map(TrafficJpaEntity::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Traffic> findByCongestionLevelGreaterThanEqual(Integer congestionLevel) {
        return trafficJpaRepository.findByCongestionLevelGreaterThanEqual(congestionLevel)
                .stream()
                .map(TrafficJpaEntity::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByLocation(String location) {
        return trafficJpaRepository.existsByLocation(location);
    }
}