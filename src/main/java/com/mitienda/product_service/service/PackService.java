package com.mitienda.product_service.service;

import com.mitienda.product_service.model.Pack;
import com.mitienda.product_service.repository.PackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackService {

    private final PackRepository packRepository;

    /**
     * Obtiene todos los packs registrados.
     * @return
     */
    @Transactional(readOnly = true)
    public List<Pack> findAllPacks() {
        return packRepository.findAll();
    }

    /**
     * Obtiene solo los packs con stock disponible.
     * @return
     */
    @Transactional(readOnly = true)
    public List<Pack> findAvailablePacks() {
        return packRepository.findByStockGreaterThan(0);
    }

    /**
     * Busca un pack por ID.
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<Pack> findPackById(Long id) {
        return packRepository.findById(id);
    }




    // === CREATE - UPDATES ===

    /**
     * Guarda un nuevo pack en la base de datos.
     * @return (Pack) pack
     */
    @Transactional
    public Pack savePack(Pack pack) {
        // nota: validaciones
        return packRepository.save(pack);
    }
    
}