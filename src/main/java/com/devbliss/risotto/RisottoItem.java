package com.devbliss.risotto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RisottoItem {

    private String typeOfItem;
    private String title;
    private List<String> secondaryTitles;
    private List<String> tertiaryTitles;
    private List<String> authors;
    private List<String> secondaryAuthors;
    private List<String> tertiaryAuthors;
    private List<String> subsidiaryAuthors;
    private String doi;
    private String date;
    private List<String> keywords;
    private String abstractDescription;
    private Map<String, List<String>> rawData = new HashMap<String, List<String>>();

    public List<String> getRawFieldData(String fieldName) {
        List<String> fieldValues = rawData.get(fieldName);

        if (fieldValues == null) {
            return new ArrayList<String>(); // return an empty list
        }

        return fieldValues;
    }

    public String getSingleFirstRawFieldData(String fieldName) {
        List<String> fieldValues = getRawFieldData(fieldName);
        if (fieldValues == null || fieldValues.size() < 1) {
            return null;
        }

        return fieldValues.get(0);
    }

    public Map<String, List<String>> getRawFieldData() {
        return rawData;
    }

    protected void addRawFieldData(String fieldName, String value) {
        List<String> fieldValues = rawData.get(fieldName);

        if (fieldValues == null) {
            fieldValues = new ArrayList<String>();
        }

        if (value != null && !"".equals(value)) {
            fieldValues.add(value);
        }

        rawData.put(fieldName, fieldValues);
    }

    public String getTitle() {
        return title;
    }

    public String getAbstractDescription() {
        return abstractDescription;
    }

    public String getTypeOfItem() {
        return typeOfItem;
    }

    public List<String> getSecondaryTitles() {
        return secondaryTitles;
    }

    public List<String> getTertiaryTitles() {
        return tertiaryTitles;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getSecondaryAuthors() {
        return secondaryAuthors;
    }

    public List<String> getTertiaryAuthors() {
        return tertiaryAuthors;
    }

    public List<String> getSubsidiaryAuthors() {
        return subsidiaryAuthors;
    }

    public String getDoi() {
        return doi;
    }

    public String getDate() {
        return date;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    /**
     * Syncs the attributes to the
     */
    public void normalize() {
        this.title = getSingleFirstRawFieldData(RisottoRISFieldDefinitions.FIELD_TITLE);
        this.typeOfItem =
                getSingleFirstRawFieldData(RisottoRISFieldDefinitions.FIELD_TYPE_OF_REFERENCE);
        this.secondaryTitles = getRawFieldData(RisottoRISFieldDefinitions.FIELD_SECONDARY_TITLE);
        this.tertiaryTitles = getRawFieldData(RisottoRISFieldDefinitions.FIELD_TERTIARY_TITLE);

        this.abstractDescription =
                getSingleFirstRawFieldData(RisottoRISFieldDefinitions.FIELD_ABSTRACT);

        this.authors = getRawFieldData(RisottoRISFieldDefinitions.FIELD_AUTHOR);
        this.secondaryAuthors = getRawFieldData(RisottoRISFieldDefinitions.FIELD_SECONDARY_AUTHOR);
        this.tertiaryAuthors = getRawFieldData(RisottoRISFieldDefinitions.FIELD_TERTIARY_AUTHOR);
        this.subsidiaryAuthors =
                getRawFieldData(RisottoRISFieldDefinitions.FIELD_SUBSIDIARY_AUTHOR);

        this.doi = getSingleFirstRawFieldData(RisottoRISFieldDefinitions.FIELD_DOI);
        this.date = getSingleFirstRawFieldData(RisottoRISFieldDefinitions.FIELD_DATE);

        this.keywords = getRawFieldData(RisottoRISFieldDefinitions.FIELD_KEYWORDS);
    }
}
