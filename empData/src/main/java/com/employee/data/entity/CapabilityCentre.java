package com.employee.data.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum CapabilityCentre {
    @JsonAlias({"product and platform", "Product And Platform"})
    PRODUCT_AND_PLATFORM,

    @JsonAlias({"dep cloud", "Dep Cloud"})
    DEP_CLOUD,

    @JsonAlias({"devaa", "Devaa"})
    DEVAA,

    @JsonAlias({"dep quality", "Dep Quality"})
    DEP_QUALITY;
}
