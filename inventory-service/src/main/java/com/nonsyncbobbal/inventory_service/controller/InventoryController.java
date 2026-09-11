package com.nonsyncbobbal.inventory_service.controller;

import com.nonsyncbobbal.inventory_service.model.Inventory;
import com.nonsyncbobbal.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    public ResponseEntity<Boolean> getInventoryBySkuCode(@RequestParam String skuCode,
                                                         @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryRepository
                .existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity));
    }
}
