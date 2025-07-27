package com.progressoft.fxDeal.repository;

import com.progressoft.fxDeal.entity.FxDealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FxDealRepository extends JpaRepository<FxDealEntity,Long> {
}
