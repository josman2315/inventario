package com.app.backend.inventario.service;

import com.app.backend.inventario.exception.ResourceNotFoundException;
import com.app.backend.inventario.model.Asset;
import com.app.backend.inventario.repository.IAssetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AssetServiceImplTest {

    @Mock
    private IAssetRepository assetRepository;

    @InjectMocks
    private AssetServiceImpl assetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAssets() {

        List<Asset> fakeAssets = new ArrayList<>();
        fakeAssets.add(new Asset(1L, "SN1", "Description 1", "Asset 1", LocalDate.now(), BigDecimal.valueOf(100)));
        fakeAssets.add(new Asset(2L, "SN2", "Description 2", "Asset 2", LocalDate.now(), BigDecimal.valueOf(200)));

        when(assetRepository.findAll()).thenReturn(fakeAssets);

        List<Asset> result = assetService.getAllAssets();

        Assertions.assertEquals(fakeAssets.size(), result.size());
        Assertions.assertEquals(fakeAssets.get(0), result.get(0));
        Assertions.assertEquals(fakeAssets.get(1), result.get(1));

    }

    @Test
    void testSaveAsset() {
        Asset assetToSave = new Asset(1L, "SN1", "Description 1", "Asset 1", LocalDate.now(), BigDecimal.valueOf(100));

        when(assetRepository.save(assetToSave)).thenReturn(assetToSave);

        Asset savedAsset = assetService.saveAsset(assetToSave);

        Assertions.assertEquals(assetToSave, savedAsset);
    }

    @Test
    void testUpdateAssetById() {

        Asset existingAsset = new Asset(1L, "SN1", "Description 1", "Asset 1", LocalDate.now(), BigDecimal.valueOf(100));

        Asset updatedAssetDetails = new Asset(1L, "SN1-updated", "Updated Description", "Updated Asset", LocalDate.now(), BigDecimal.valueOf(200));

        when(assetRepository.findById(String.valueOf(existingAsset.getId()))).thenReturn(Optional.of(existingAsset));
        when(assetRepository.save(existingAsset)).thenReturn(existingAsset);

        Asset updatedAsset = assetService.updateAssetById(existingAsset.getId(), updatedAssetDetails);

        Assertions.assertEquals(updatedAssetDetails.getSerialNumber(), updatedAsset.getSerialNumber());
        Assertions.assertEquals(updatedAssetDetails.getDescription(), updatedAsset.getDescription());
        Assertions.assertEquals(updatedAssetDetails.getName(), updatedAsset.getName());
        Assertions.assertEquals(updatedAssetDetails.getPurchaseDate(), updatedAsset.getPurchaseDate());
        Assertions.assertEquals(updatedAssetDetails.getPurchaseValue(), updatedAsset.getPurchaseValue());

    }

    @Test
    void testDeleteAssetById() {
        // Crear un activo ficticio existente
        Asset existingAsset = new Asset(1L, "SN1", "Description 1", "Asset 1", LocalDate.now(), BigDecimal.valueOf(100));

        when(assetRepository.findById(String.valueOf(existingAsset.getId()))).thenReturn(Optional.of(existingAsset));

        assetService.deleteAssetById(existingAsset.getId());

        verify(assetRepository, times(1)).findById(String.valueOf(existingAsset.getId()));
        verify(assetRepository, times(1)).delete(existingAsset);
    }

    @Test
    void testDeleteAssetById_NotFound() {
        when(assetRepository.findById("1")).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> assetService.deleteAssetById(1L));

        verify(assetRepository, never()).delete(any());
    }

    @Test
    void testCalculateDepreciation() {
        Asset existingAsset = new Asset(1L, "SN1", "Description 1", "Asset 1", LocalDate.of(2020, 1, 1), BigDecimal.valueOf(1000));

        when(assetRepository.findById(String.valueOf(existingAsset.getId()))).thenReturn(Optional.of(existingAsset));

        BigDecimal depreciationValue = assetService.calculateDepreciation(existingAsset.getId());

        LocalDate currentDate = LocalDate.now();
        int yearsSincePurchase = currentDate.getYear() - existingAsset.getPurchaseDate().getYear();
        BigDecimal depreciationPercentage = BigDecimal.valueOf(0.04);
        BigDecimal depreciationAmount = existingAsset.getPurchaseValue()
                .multiply(depreciationPercentage)
                .multiply(BigDecimal.valueOf(yearsSincePurchase));
        BigDecimal expectedValue = existingAsset.getPurchaseValue().subtract(depreciationAmount);

        Assertions.assertEquals(expectedValue, depreciationValue);

        verify(assetRepository, times(1)).findById(String.valueOf(existingAsset.getId()));
    }

}