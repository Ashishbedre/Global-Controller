package com.example.demo.service;

import com.example.demo.dto.BackendPackage.ProductListResponcedto;
import com.example.demo.dto.ProvisionSiteSSDto;
import com.example.demo.dto.VersionSetProductDto;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface Compatible{
    public JsonNode checkCompatible(ProvisionSiteSSDto provisionDto);
    public JsonNode checkCompatibleUrl(List<VersionSetProductDto> versionControl);

    public List<ProductListResponcedto> processCompatibilityData(JsonNode jsonString);
}
