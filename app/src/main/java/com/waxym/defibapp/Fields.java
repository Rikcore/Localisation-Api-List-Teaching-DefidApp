package com.waxym.defibapp;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields implements Serializable
{

    @SerializedName("commune")
    @Expose
    private String commune;
    @SerializedName("accessibilite")
    @Expose
    private String accessibilite;
    @SerializedName("implantation")
    @Expose
    private String implantation;
    @SerializedName("adresse")
    @Expose
    private String adresse;
    @SerializedName("geo_point_2d")
    @Expose
    private List<Double> geoPoint2d = null;
    @SerializedName("nom_site")
    @Expose
    private String nomSite;
    @SerializedName("geo_shape")
    @Expose
    private GeoShape geoShape;
    @SerializedName("type_structure")
    @Expose
    private String typeStructure;
    @SerializedName("id")
    @Expose
    private Integer id;
    private final static long serialVersionUID = 4019991131907912047L;

    private int distance;

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getAccessibilite() {
        return accessibilite;
    }

    public void setAccessibilite(String accessibilite) {
        this.accessibilite = accessibilite;
    }

    public String getImplantation() {
        return implantation;
    }

    public void setImplantation(String implantation) {
        this.implantation = implantation;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Double> getGeoPoint2d() {
        return geoPoint2d;
    }

    public void setGeoPoint2d(List<Double> geoPoint2d) {
        this.geoPoint2d = geoPoint2d;
    }

    public String getNomSite() {
        return nomSite;
    }

    public void setNomSite(String nomSite) {
        this.nomSite = nomSite;
    }

    public GeoShape getGeoShape() {
        return geoShape;
    }

    public void setGeoShape(GeoShape geoShape) {
        this.geoShape = geoShape;
    }

    public String getTypeStructure() {
        return typeStructure;
    }

    public void setTypeStructure(String typeStructure) {
        this.typeStructure = typeStructure;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
