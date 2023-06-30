package com.app.backend.inventario.repository;

import com.app.backend.inventario.model.Asset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssetRepository extends CrudRepository<Asset, String> {

}
