package com.mitienda.product_service.controller;

import com.mitienda.product_service.dto.accessory.AdminAccessoryDto;
import com.mitienda.product_service.dto.accessory.CreateAccessoryDto;
import com.mitienda.product_service.dto.coffee.AdminCoffeeDto;
import com.mitienda.product_service.dto.coffee.CreateCoffeeDto;
import com.mitienda.product_service.dto.pack.AdminPackDto;
import com.mitienda.product_service.dto.pack.CreatePackDto;
import com.mitienda.product_service.model.Accessory;
import com.mitienda.product_service.model.Coffee;
import com.mitienda.product_service.model.Pack;
import com.mitienda.product_service.model.Product;
import com.mitienda.product_service.service.CoffeeService;
import com.mitienda.product_service.service.PackService;
import com.mitienda.product_service.service.ProductService; // Para deletes generales
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador dedicado a acciones de administrador.
@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;
    private final CoffeeService coffeeService;
    private final PackService packService;

    // GLOBALES

    /**
     * GET /api/v1/admin/products/{id}
     * Usado para que el Admin cargue el producto antes de editarlo.
     * Muestra todo lo necesario.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductByIdAdmin(@PathVariable Long id) {
        
        Product product = productService.findProductById(id);

        if (product instanceof Coffee coffee) {
            // ¡OJO! Aquí usamos el DTO de Admin
            return ResponseEntity.ok(AdminCoffeeDto.fromEntity(coffee));
            
        } else if (product instanceof Accessory accessory) {
            return ResponseEntity.ok(AdminAccessoryDto.fromEntity(accessory));
            
        } else if (product instanceof Pack pack) {
            return ResponseEntity.ok(AdminPackDto.fromEntity(pack));
            
        } else {
            return ResponseEntity.ok(product);
        }
    }

    /**
     * Eliminar cualquier producto.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }



    // CAFES

    /**
     * Obtiene cafes con todos sus campos
     */
    @GetMapping("/coffees")
    public ResponseEntity<List<AdminCoffeeDto>> getAllCoffeesAdmin() {
        return ResponseEntity.ok(
            coffeeService.findAllCoffees().stream()
                .map(AdminCoffeeDto::fromEntity)
                .toList()
        );
    }

    /**
     * Crear un nuevo cafe.
     */
    @PostMapping("/coffees")
    public ResponseEntity<AdminCoffeeDto> createCoffee(@RequestBody CreateCoffeeDto inputDto) {
        
        Coffee coffeeEntity = inputDto.toEntity();
        
        Coffee savedCoffee = coffeeService.saveCoffee(coffeeEntity);
        
        // Entidad a -> DTO Respuesta
        return new ResponseEntity<>(AdminCoffeeDto.fromEntity(savedCoffee), HttpStatus.CREATED);

    }

    /**
     * Actualizar un cafe existente.
     */
    @PutMapping("/coffees/{id}")
    public ResponseEntity<AdminCoffeeDto> updateCoffee(@PathVariable Long id, @RequestBody CreateCoffeeDto inputDto) {
        // Aseguramos que el ID del body coincida con la URL
        inputDto.setId(id);
        
        Coffee coffeeEntity = inputDto.toEntity();
        Coffee updatedCoffee = coffeeService.saveCoffee(coffeeEntity);
        
        return ResponseEntity.ok(AdminCoffeeDto.fromEntity(updatedCoffee));
    }



    // ACCESORIOS

    /**
     * Obtiene todos los accesorios con todos sus campos.
    */
    @GetMapping("/accessories")
    public ResponseEntity<List<AdminAccessoryDto>> getAllAccessoriesAdmin() {
        return ResponseEntity.ok(
            productService.findAllAccessories().stream()
                .map(AdminAccessoryDto::fromEntity)
                .toList()
        );
    }


    /**
     * Crear un nuevo accesorio.
     */
    @PostMapping("/accessories")
    public ResponseEntity<AdminAccessoryDto> createAccessory(@RequestBody CreateAccessoryDto inputDto) {
        Accessory accessoryEntity = inputDto.toEntity();
        Accessory savedAccessory = productService.saveAccessory(accessoryEntity);
        return new ResponseEntity<>(AdminAccessoryDto.fromEntity(savedAccessory), HttpStatus.CREATED);
    }

    /**
     * Actualizar un accesorio existente.
     */
    @PutMapping("/accessories/{id}")
    public ResponseEntity<AdminAccessoryDto> updateAccessory(@PathVariable Long id, @RequestBody CreateAccessoryDto inputDto) {
        inputDto.setId(id);
        
        Accessory accessoryEntity = inputDto.toEntity();
        Accessory updatedAccessory = productService.saveAccessory(accessoryEntity);
        
        return ResponseEntity.ok(AdminAccessoryDto.fromEntity(updatedAccessory));
    }



    // PACKS

    /**
     * Obtiene todos los packs con todos sus campos.
     */
    @GetMapping("/packs")
    public ResponseEntity<List<AdminPackDto>> getAllPacksAdmin() {
        return ResponseEntity.ok(
            packService.findAllPacks().stream()
                .map(AdminPackDto::fromEntity)
                .toList()
        );
    }

    /**
     * Crea un nuevo pack.
     */
    @PostMapping("/packs")
    public ResponseEntity<AdminPackDto> createPack(@RequestBody CreatePackDto createDto) {
        Pack saved = packService.savePack(createDto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(AdminPackDto.fromEntity(saved));
    }

    /**
     * Actualizar un pack existente
     */
    @PutMapping("/packs/{id}")
    public ResponseEntity<AdminPackDto> updatePack(@PathVariable Long id, @RequestBody CreatePackDto inputDto) {
        
        inputDto.setId(id);

        Pack packEntity = inputDto.toEntity();
        Pack updatedPack = packService.savePack(packEntity);
        
        return ResponseEntity.ok(AdminPackDto.fromEntity(updatedPack));
    }
}