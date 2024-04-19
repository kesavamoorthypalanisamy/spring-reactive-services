package com.hippo.orderservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hippo.orderservice.entity.PurchaseOrder;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    List<PurchaseOrder> findByUserId(Integer userId);
} 
