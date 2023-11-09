package com.kharzixen.inventoryservice.repository;

import com.kharzixen.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByStorageKeepingUnit(String storageKeepingUnit);
    Inventory findByProductId(String productId);
}
