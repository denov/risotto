package com.devbliss.risotto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RisottoRisFile {

    private String title;
    private String[] additionalTitles;
    private String[] authors;
    private String[] secondaryAuthors;
    private String[] tertiaryAuthors;
    private String[] subsidiaryAuthors;
    private String doi;
    private String date;
    private String[] keywords;
    private Map<String, List<String>> rawData = new HashMap<String, List<String>>();

    public List<String> getRawFieldData(RisottoRISFieldDefinition field) {
        List<String> fieldValues = rawData.get(field.getFieldName());

        if (fieldValues == null) {
            return Arrays.asList(); // return an empty list
        }

        return fieldValues;
    }

    public String getTitle() {
        return title;
    }

    public String[] getAdditionalTitles() {
        return additionalTitles;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String[] getSecondaryAuthors() {
        return secondaryAuthors;
    }

    public String[] getTertiaryAuthors() {
        return tertiaryAuthors;
    }

    public String[] getSubsidiaryAuthors() {
        return subsidiaryAuthors;
    }

    public String getDoi() {
        return doi;
    }

    public String getDate() {
        return date;
    }

    public String[] getKeywords() {
        return keywords;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setAdditionalTitles(String[] additionalTitles) {
        this.additionalTitles = additionalTitles;
    }

    protected void setAuthors(String[] authors) {
        this.authors = authors;
    }

    protected void setSecondaryAuthors(String[] secondaryAuthors) {
        this.secondaryAuthors = secondaryAuthors;
    }

    protected void setTertiaryAuthors(String[] tertiaryAuthors) {
        this.tertiaryAuthors = tertiaryAuthors;
    }

    protected void setSubsidiaryAuthors(String[] subsidiaryAuthors) {
        this.subsidiaryAuthors = subsidiaryAuthors;
    }

    protected void setDoi(String doi) {
        this.doi = doi;
    }

    protected void setDate(String date) {
        this.date = date;
    }

    protected void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    protected void setRawData(Map<String, List<String>> rawData) {
        this.rawData = rawData;
    }

}
