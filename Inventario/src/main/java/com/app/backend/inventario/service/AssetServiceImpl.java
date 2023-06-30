package com.app.backend.inventario.service;

import com.app.backend.inventario.exception.ResourceNotFoundException;
import com.app.backend.inventario.model.Asset;
import com.app.backend.inventario.repository.IAssetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AssetServiceImpl implements IAssetsService {

    @Autowired
    private IAssetRepository assetRepository;


    @Override
    public List<Asset> getAllAssets() {
        return (List<Asset>) assetRepository.findAll();
    }

    @Override
    public Asset saveAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    @Override
    public Asset updateAssetById(Long id, Asset assetDetails) {
        Asset asset = assetRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Activo no encontrado con id: " + id));

        if (!id.equals(assetDetails.getId())) {
            throw new IllegalArgumentException("El ID en la ruta no coincide con el ID en el cuerpo de la solicitud");
        }

        BeanUtils.copyProperties(assetDetails, asset, "id");

        return assetRepository.save(asset);
    }

    public void deleteAssetById(Long id) {
        Asset asset = assetRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Activo no encontrado con id: " + id));

        assetRepository.delete(asset);
    }

    @Override
    public BigDecimal calculateDepreciation(Long id) {
        Asset asset = assetRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Activo no encontrado con id:: " + id));

        LocalDate currentDate = LocalDate.now();
        int yearsSincePurchase = currentDate.getYear() - asset.getPurchaseDate().getYear();

        BigDecimal depreciationPercentage = BigDecimal.valueOf(0.04);
        BigDecimal depreciationAmount = asset.getPurchaseValue()
                .multiply(depreciationPercentage)
                .multiply(BigDecimal.valueOf(yearsSincePurchase));

        return asset.getPurchaseValue().subtract(depreciationAmount);
    }

}
