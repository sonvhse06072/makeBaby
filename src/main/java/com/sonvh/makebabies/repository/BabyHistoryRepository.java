package com.sonvh.makebabies.repository;

import com.sonvh.makebabies.domain.BabyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BabyHistoryRepository extends JpaRepository<BabyHistory, Long> {
}
