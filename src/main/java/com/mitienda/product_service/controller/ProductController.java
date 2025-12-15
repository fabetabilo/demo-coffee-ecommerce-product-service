package com.mitienda.product_service.controller;

import com.mitienda.product_service.dto.*;
import com.mitienda.product_service.model.Accessory;
import com.mitienda.product_service.model.Coffee;
import com.mitienda.product_service.model.Product;
import com.mitienda.product_service.service.CoffeeService;
import com.mitienda.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor // inyeccion de dependencias
public class ProductController {

    private final ProductService productService;
    private final CoffeeService coffeeService;

    // ENDPOINTS: LECTURA

    /**
     * Obtiene todos los cafes.
     * Soporta filtro opcional por subcategoria (ej: ?type=packs)
     */
    @GetMapping("/coffees")
    public ResponseEntity<List<CoffeeDto>> getAllCoffees(@RequestParam(required = false) String type) {
        
        List<Coffee> coffees;
        
        if (type != null && !type.isBlank()) {
             coffees = coffeeService.findBySubcategory(type);
        } else {
            coffees = coffeeService.findAllCoffees();
        }

        List<CoffeeDto> dtos = coffees.stream()
                .map(CoffeeDto::fromEntity)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtiene todos los accesorios.
     * Soporta filtro opcional por subcategoria (ej: ?type=molinos)
     */
    @GetMapping("/accessories")
    public ResponseEntity<List<AccessoryDto>> getAllAccessories(@RequestParam(required = false) String type) {
        
        List<Accessory> accessories;

        if (type != null && !type.isBlank()) {
            accessories = productService.findAccessoriesBySubcategory(type);  // !! TEMPORAL: implementar findBySubcategory
        } else {
            accessories = productService.findAllAccessories();
        }

        List<AccessoryDto> dtos = accessories.stream()
                .map(AccessoryDto::fromEntity)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    /**
     * Obtiene un producto por ID.
     * Detecta automaticamente si es cafe o accesorio para devolver el JSON correcto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        
        Product product = productService.findProductById(id);

        if (product instanceof Coffee coffee) {
            return ResponseEntity.ok(CoffeeDto.fromEntity(coffee));
        } else if (product instanceof Accessory accessory) {
            return ResponseEntity.ok(AccessoryDto.fromEntity(accessory));
        } else {
            // fallback
            return ResponseEntity.ok(product);
        }
    }







    // ENDPOINTS: ESCRITURA

    /**
     * Eliminar cualquier producto.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Crear un nuevo cafe.
     */
    @PostMapping("/coffees")
    public ResponseEntity<CoffeeDto> createCoffee(@RequestBody CreateCoffeeDto inputDto) {
        
        Coffee coffeeEntity = inputDto.toEntity();
        
        Coffee savedCoffee = coffeeService.saveCoffee(coffeeEntity);
        
        // Entidad a -> DTO Respuesta
        return new ResponseEntity<>(CoffeeDto.fromEntity(savedCoffee), HttpStatus.CREATED);

    }

    /**
     * Actualizar un cafe existente.
     */
    @PutMapping("/coffees/{id}")
    public ResponseEntity<CoffeeDto> updateCoffee(@PathVariable Long id, @RequestBody CreateCoffeeDto inputDto) {
        // Aseguramos que el ID del body coincida con la URL
        inputDto.setId(id);
        
        Coffee coffeeEntity = inputDto.toEntity();
        Coffee updatedCoffee = coffeeService.saveCoffee(coffeeEntity);
        
        return ResponseEntity.ok(CoffeeDto.fromEntity(updatedCoffee));
    }

    /**
     * Crear un nuevo accesorio.
     */
    @PostMapping("/accessories")
    public ResponseEntity<AccessoryDto> createAccessory(@RequestBody CreateAccessoryDto inputDto) {
        Accessory accessoryEntity = inputDto.toEntity();
        Accessory savedAccessory = productService.saveAccessory(accessoryEntity);
        return new ResponseEntity<>(AccessoryDto.fromEntity(savedAccessory), HttpStatus.CREATED);
    }

    /**
     * Actualizar un accesorio existente.
     */
    @PutMapping("/accessories/{id}")
    public ResponseEntity<AccessoryDto> updateAccessory(@PathVariable Long id, @RequestBody CreateAccessoryDto inputDto) {
        inputDto.setId(id);
        
        Accessory accessoryEntity = inputDto.toEntity();
        Accessory updatedAccessory = productService.saveAccessory(accessoryEntity);
        
        return ResponseEntity.ok(AccessoryDto.fromEntity(updatedAccessory));
    }

}