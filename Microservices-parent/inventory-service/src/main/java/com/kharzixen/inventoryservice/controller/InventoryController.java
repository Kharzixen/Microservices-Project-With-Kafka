package com.kharzixen.inventoryservice.controller;

import com.kharzixen.inventoryservice.dto.incomming.InventoryInDto;
import com.kharzixen.inventoryservice.dto.outgoing.InventoryOutDto;
import com.kharzixen.inventoryservice.error_handling.error.ErrorResponse;
import com.kharzixen.inventoryservice.error_handling.exception.InventoryNotFoundException;
import com.kharzixen.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryOutDto> createInventory(@RequestBody InventoryInDto inventoryInDto) {
        InventoryOutDto inventoryOutDto = inventoryService.createInventory(inventoryInDto);
        return new ResponseEntity<>(inventoryOutDto, HttpStatus.CREATED);
    }


//    @GetMapping
//    public ResponseEntity<?> getInventoryForProduct(
//            @RequestParam(value = "productId", required = false) String productId,
//            @RequestParam(value = "skuCode", required = false) String skuCode) {
//
//        if (productId == null && skuCode == null) {
//            List<InventoryOutDto> fullInventory = inventoryService.getFullInventory();
//            return new ResponseEntity<>(fullInventory, HttpStatus.OK);
//        }
//
//        else if (productId != null) {
//            Optional<InventoryOutDto> optionalInventoryOutDto = inventoryService.getInventoryByProductId(productId);
//            if (optionalInventoryOutDto.isPresent()) {
//                return new ResponseEntity<>(optionalInventoryOutDto.get(), HttpStatus.OK);
//            } else {
//                throw new InventoryNotFoundException("productId",productId);
//            }
//        } else {
//            Optional<InventoryOutDto> optionalInventoryOutDto = inventoryService.getInventoryBySkuCode(skuCode);
//            if (optionalInventoryOutDto.isPresent()) {
//                return new ResponseEntity<>(optionalInventoryOutDto.get(), HttpStatus.OK);
//            } else {
//                throw new InventoryNotFoundException("skuCode", skuCode);
//            }
//        }
//
//    }

    @GetMapping
    public ResponseEntity<List<InventoryOutDto>> getMultipleInventoryByMultipleIds(
            @RequestParam(value = "productIds", required = false) List<String> productIds){
        Optional<List<InventoryOutDto>> res = inventoryService.getInventoryListByProductIdList(productIds);
        return res.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(List.of()));
    }

    @PatchMapping
    public ResponseEntity<InventoryOutDto> updateQuantity(
            @RequestBody InventoryInDto inventoryInDto,
            @RequestParam(value = "productId", required = false) String productId,
            @RequestParam(value = "skuCode", required = false) String skuCode) {

        if (productId == null && skuCode == null) {
            throw new InventoryNotFoundException();
        }

        else if (productId != null) {
            Optional<InventoryOutDto> optionalInventoryOutDto = inventoryService.patchInventoryByProductId(productId, inventoryInDto);
            if (optionalInventoryOutDto.isPresent()) {
                return new ResponseEntity<>(optionalInventoryOutDto.get(), HttpStatus.OK);
            } else {
                throw new InventoryNotFoundException("productId", productId);
            }
        } else {
            Optional<InventoryOutDto> optionalInventoryOutDto = inventoryService.patchInventoryBySkuCode(skuCode, inventoryInDto);
            if (optionalInventoryOutDto.isPresent()) {
                return new ResponseEntity<>(optionalInventoryOutDto.get(), HttpStatus.OK);
            } else {
                throw new InventoryNotFoundException("skuCode", skuCode);
            }
        }
    }


    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryNotFoundException(InventoryNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
