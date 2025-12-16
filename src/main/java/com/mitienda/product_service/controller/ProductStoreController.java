package com.mitienda.product_service.controller;

import com.mitienda.product_service.dto.accessory.AccessoryDto;
import com.mitienda.product_service.dto.coffee.CoffeeDto;
import com.mitienda.product_service.dto.pack.PackDto;
import com.mitienda.product_service.model.Accessory;
import com.mitienda.product_service.model.Coffee;
import com.mitienda.product_service.model.Pack;
import com.mitienda.product_service.model.Product;
import com.mitienda.product_service.service.CoffeeService;
import com.mitienda.product_service.service.PackService;
import com.mitienda.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controller dedicado a la tienda, publico y permitirá sólo acciones de usuarios de tienda.
@RestController
@RequestMapping("/api/v1/store/products")
@RequiredArgsConstructor // inyeccion de dependencias
public class ProductStoreController {

    private final ProductService productService;
    private final CoffeeService coffeeService;
    private final PackService packService;

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
     * Obtiene todos los packs.
     */
    @GetMapping("/packs")
    public ResponseEntity<List<PackDto>> getAllPacks() {
        List<Pack> packs = packService.findAllPacks();
        return ResponseEntity.ok(packs.stream().map(PackDto::fromEntity).toList());
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
        } else if (product instanceof Pack pack) {
            return ResponseEntity.ok(PackDto.fromEntity(pack));
        } else {
            // fallback
            return ResponseEntity.ok(product);
        }
    }




    

}