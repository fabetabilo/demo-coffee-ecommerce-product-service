package com.mitienda.product_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitienda.product_service.model.Accessory;
import com.mitienda.product_service.model.Product;
import com.mitienda.product_service.repository.AccessoryRepository;
import com.mitienda.product_service.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository; // inmutable, moderno
    private final AccessoryRepository accessoryRepository;

    public ProductService(
        ProductRepository productRepository,
        AccessoryRepository accessoryRepository
    ) {
        this.productRepository = productRepository;
        this.accessoryRepository = accessoryRepository;
    }

    /**
     * Devuelve un producto por su id.
     * @param id identificador de producto
     * @return Producto
     */
    @Transactional(readOnly = true)
    public Product findProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    /**
     * Devuelve todos los productos del catalogo.
     * @return (List) lista de productos completa del catalogo
     */
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Devuelve solo un accesorio por su id.
     * @param id identificador de accesorio
     * @return (Accessory) entidad accesorio
     */
    @Transactional(readOnly = true)
    public Accessory findAccessoryById(Long id) {
        return accessoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Accesorio no encontrado"));
    }

    /**
     * Devuelve todos los productos accesorio.
     * @return (List) lista de accesorios
     */
    @Transactional(readOnly = true)
    public List<Accessory> findAllAccessories() {
        return accessoryRepository.findAll();
    }




// === CREATE - DELETE - UPDATE ===

    /**
     * Crea o actualiza un accesorio (Accessory).
     * @param accessory Entidad accesorio
     * @return (Accessory) guardado
     */
    public Accessory saveAccessory(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    /**
     * Elimina un producto por su id unico en el sistema cualquiera sea la herencia.
     * Coffee, Accessory, etc.
     * @param id identificador de producto
     */
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Producto no encontrado con ID: " + id);
        }
        productRepository.deleteById(id);
    }


}
