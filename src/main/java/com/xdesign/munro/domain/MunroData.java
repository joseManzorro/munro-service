package com.xdesign.munro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({
        "Running No",
        "DoBIH Number",
        "Streetmap",
        "Geograph",
        "Hill-bagging",
        "Name",
        "SMC Section",
        "RHB Section",
        "_Section",
        "Height (m)",
        "Height (ft)",
        "Map 1:50",
        "Map 1:25",
        "Grid Ref",
        "GridRefXY",
        "xcoord",
        "ycoord",
        "1891",
        "1921",
        "1933",
        "1953",
        "1969",
        "1974",
        "1981",
        "1984",
        "1990",
        "1997",
        "Post 1997",
        "Comments"
})
public class MunroData {

    @JsonProperty(value = "Running No")
    private String runningNo;

    @JsonProperty("DoBIH Number")
    private String doBIH;

    @JsonProperty("Streetmap")
    private String streetMap;

    @JsonProperty("Geograph")
    private String geograph;

    @JsonProperty("Hill-bagging")
    private String hillBagging;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("SMC Section")
    private String smcSection;

    @JsonProperty("RHB Section")
    private String rhbSection;

    @JsonProperty("_Section")
    private String section;

    @JsonProperty("Height (m)")
    private Float heightInMeters;

    @JsonProperty("Height (ft)")
    private Float heightInFeet;

    @JsonProperty("Map 1:50")
    private String map150;

    @JsonProperty("Map 1:25")
    private String map125;

    @JsonProperty("Grid Ref")
    private String gridRef;

    @JsonProperty("GridRefXY")
    private String gridRefXY;

    @JsonProperty("xcoord")
    private String xCoord;

    @JsonProperty("ycoord")
    private String yCoord;

    @JsonProperty("1891")
    private String year1891;

    @JsonProperty("1921")
    private String year1921;

    @JsonProperty("1933")
    private String year1933;

    @JsonProperty("1953")
    private String year1953;

    @JsonProperty("1969")
    private String year1969;

    @JsonProperty("1974")
    private String year1974;

    @JsonProperty("1981")
    private String year1981;

    @JsonProperty("1984")
    private String year1984;

    @JsonProperty("1990")
    private String year1990;

    @JsonProperty("1997")
    private String year1997;

    @JsonProperty("Post 1997")
    private String postYear1997;

    @JsonProperty("Comments")
    private String comments;
}
