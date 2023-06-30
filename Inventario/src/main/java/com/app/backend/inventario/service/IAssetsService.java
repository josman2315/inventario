package com.app.backend.inventario.service;

import com.app.backend.inventario.model.Asset;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

public interface IAssetsService {

    public List<Asset> getAllAssets();

    public Asset saveAsset(Asset asset);

    public Asset updateAssetById(Long id, Asset assetDetails);

    public BigDecimal calculateDepreciation(Long id);

    public void deleteAssetById(Long id);

}
