/*
 * Copyright 2013, devbliss GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

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
    private String year;
    private String journal;
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

    public String getJournal() {
        return journal;
    }

    public String getYear() {
        return year;
    }

    /**
     * Get the first found field value from an array of fields.
     * 
     * e.g. if you search a title you can supply "TI", "J2", "T1", "T2" and "T3" to get the first
     * one in that order with a value.
     * 
     * @param tags seperated list or array of tags
     * 
     * @return field value if any was found or null
     */
    public String getFirstRawFieldDataFromManyFields(String... tags) {
        for (int i = 0; i < tags.length; i++) {
            List<String> values = getRawFieldData(tags[i]);

            if (null != values && values.size() > 0) {
                return values.get(0);
            }

        }
        return null;
    }

    /**
     * Syncs the attributes to the tag map, which contains the raw tag data of the item.
     */
    public void normalize() {
        this.typeOfItem =
                getSingleFirstRawFieldData(RisottoRISFieldDefinitions.FIELD_TYPE_OF_REFERENCE);

        this.title =
                getFirstRawFieldDataFromManyFields(RisottoRISFieldDefinitions.FIELD_PRIMARY_TITLE,
                        RisottoRISFieldDefinitions.FIELD_TITLE);

        this.journal =
                getFirstRawFieldDataFromManyFields(RisottoRISFieldDefinitions.FIELD_JOURNAL,
                        RisottoRISFieldDefinitions.FIELD_ALTERNATE_TITLE,
                        RisottoRISFieldDefinitions.FIELD_SECONDARY_TITLE);

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

        this.year = getSingleFirstRawFieldData(RisottoRISFieldDefinitions.FIELD_YEAR);
    }
}
