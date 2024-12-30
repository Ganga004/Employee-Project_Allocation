package com.employee.data.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum Designation {
    @JsonAlias({"Principal Engineer","principal engineer"})
    PRINCIPAL_ENGINEER,

    @JsonAlias({"Staff Engineer","staff engineer"})
    STAFF_ENGINEER,

    @JsonAlias({"Technical Lead","technical lead"})
    TECHNICAL_LEAD,

    @JsonAlias({"Architect","architect"})
    ARCHITECT,

    @JsonAlias({"Senior Engineer","senior engineer"})
    SENIOR_ENGINEER,

    @JsonAlias({"Engineer","engineer"})
    ENGINEER,

    @JsonAlias({"Assoc Engineer","assoc engineer"})
    ASSOC_ENGINEER;
}
