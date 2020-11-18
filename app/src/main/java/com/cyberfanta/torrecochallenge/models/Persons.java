package com.cyberfanta.torrecochallenge.models;

import java.util.Vector;

public class Persons {
    private String professionalHeadline;
    private String verified;
    private String subjectId;
    private String picture;
    private String name;
    private String id;
    private String pictureThumbnail;
    private String summaryOfBio;
    private String weightGraph;
    private String publicId;

    private Locations locations;
    private Vector<Links> linkss;

    public Persons(String professionalHeadline, String verified, String subjectId, String picture, String name, String id, String pictureThumbnail, String summaryOfBio, String weightGraph, String publicId, Locations locations, Vector<Links> links) {
        this.professionalHeadline = professionalHeadline;
        this.verified = verified;
        this.subjectId = subjectId;
        this.picture = picture;
        this.name = name;
        this.id = id;
        this.pictureThumbnail = pictureThumbnail;
        this.summaryOfBio = summaryOfBio;
        this.weightGraph = weightGraph;
        this.publicId = publicId;
        this.locations = locations;
        this.linkss = links;
    }

    public Persons() {
        this.linkss = new Vector<>(0);
    }

    @Override
    public String toString() {
        return "Persons{" +
                "professionalHeadline='" + professionalHeadline + '\'' +
                ", verified='" + verified + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", pictureThumbnail='" + pictureThumbnail + '\'' +
                ", summaryOfBio='" + summaryOfBio + '\'' +
                ", weightGraph='" + weightGraph + '\'' +
                ", publicId='" + publicId + '\'' +
                ", locations=" + locations +
                ", linkss=" + linkss +
                '}';
    }

    public String getProfessionalHeadline() {
        return professionalHeadline;
    }

    public void setProfessionalHeadline(String professionalHeadline) {
        this.professionalHeadline = professionalHeadline;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureThumbnail() {
        return pictureThumbnail;
    }

    public void setPictureThumbnail(String pictureThumbnail) {
        this.pictureThumbnail = pictureThumbnail;
    }

    public String getSummaryOfBio() {
        return summaryOfBio;
    }

    public void setSummaryOfBio(String summaryOfBio) {
        this.summaryOfBio = summaryOfBio;
    }

    public String getWeightGraph() {
        return weightGraph;
    }

    public void setWeightGraph(String weightGraph) {
        this.weightGraph = weightGraph;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    public Vector<Links> getLinkss() {
        return linkss;
    }

    public void setLinkss(Vector<Links> linkss) {
        this.linkss = linkss;
    }

    public void addLinks(Links links) {
        this.linkss.add(links);
    }

}

