package com.mitienda.product_service.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitienda.product_service.model.Coffee;
import com.mitienda.product_service.model.CoffeeVariant;
import com.mitienda.product_service.repository.CoffeeRepository;
import com.mitienda.product_service.repository.CoffeeVariantRepository;

@Service
@Transactional
public class CoffeeService {
    // procesos estandar, cualquier otro cae en "otros"
    private static final List<String> STANDARD_PROCESSES = List.of("lavado", "natural", "honey");

    private final CoffeeRepository coffeeRepository;
    private final CoffeeVariantRepository coffeeVariantRepository;

    public CoffeeService(
        CoffeeRepository coffeeRepository, 
        CoffeeVariantRepository coffeeVariantRepository
    ) {
        this.coffeeRepository = coffeeRepository;
        this.coffeeVariantRepository = coffeeVariantRepository;
    }

    /**
     * Devuelve solo un cafe por su id.
     * @param id identificador de cafe
     * @return (Coffee) entidad cafe
     */
    @Transactional(readOnly = true)
    public Coffee findCoffeeById(Long id) {
        return coffeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto cafe no encontrado"));
    }

    /**
     * Devuelve todos los productos cafe.
     * @return (List) lista de cafes
     */
    @Transactional(readOnly = true)
    public List<Coffee> findAllCoffees() {
        return coffeeRepository.findAll();
    }

    /**
     * Devuelve lista de cafes filtrados por su proceso.
     * @param filter String proceso a buscar
     * @return (List) lista de cafes
     */
    @Transactional(readOnly = true)
    public List<Coffee> findByProcessFilter(String filter) {
        if (filter == null || filter.equalsIgnoreCase("all")) {
            return coffeeRepository.findAll();
        }
        // normaliza el filtro a minuscula
        String normalized = filter.toLowerCase(Locale.ROOT);

        if ("otros".equals(normalized)) {
            return coffeeRepository.findByProcessNotInIgnoreCase(STANDARD_PROCESSES);
        }

        return coffeeRepository.findByProcessIgnoreCase(normalized);
    }


    /**
     * Devuelve solo una variante/formato de producto cafe.
     * @param id identificador de variante o formato
     * @return (CoffeeVariant) entidad de variante
    */
   @Transactional(readOnly = true)
    public CoffeeVariant findCoffeeVariantById(Long id) {
        return coffeeVariantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Formato no encontrado"));
    }




// === CREATE - UPDATES ===

    /**
     * Crea una entidad cafe incluyendo variantes.
     * @param coffee (Coffee) entidad cafe
     * @return
     */
    @Transactional
    public Coffee saveCoffee(Coffee coffee) {
        // vincula cada variante formato con cafe
        if (coffee.getFormats() != null) {
            coffee.getFormats().forEach(variant -> variant.setCoffee(coffee));
        }
        return coffeeRepository.save(coffee);
    }



}
