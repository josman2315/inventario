package com.app.backend.inventario.controller;

import com.app.backend.inventario.model.Asset;
import com.app.backend.inventario.service.IAssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private IAssetsService assetsService;

    @GetMapping("/listAll")
    public List<Asset> listAllAssets(){
        return assetsService.getAllAssets();
    }

    @PostMapping("/create")
    public Asset createAsset(@RequestBody Asset asset){
        return assetsService.saveAsset(asset);
    }


    @PutMapping("/update/{id}")
    public Asset updateAsset(@PathVariable Long id, @RequestBody Asset assetDetails){
        return assetsService.updateAssetById(id, assetDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAsset(@PathVariable Long id) {
        assetsService.deleteAssetById(id);
    }

    @GetMapping("/depreciation/{id}")
    public BigDecimal getDepreciation(@PathVariable Long id) {
        return assetsService.calculateDepreciation(id);
    }

}
