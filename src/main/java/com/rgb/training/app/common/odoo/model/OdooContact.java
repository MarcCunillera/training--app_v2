package com.rgb.training.app.common.odoo.model;

import com.rgb.training.app.common.odoo.utils.DataTypeParser;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LuisCarlosGonzalez
 */
public class OdooContact {

    public static final String ID_TAG = "id";
    public static final String NAME_TAG = "name";
    public static final String COMMENT_TAG = "comment";
    public static final String TYPE_TAG = "type";
    public static final String COMPANY_TYPE_TAG = "company_type";
    public static final String IS_COMPANY_TAG = "is_company";
    public static final String CUSTOMER_TAG = "customer";
    public static final String TITLE_ID_TAG = "title";
    public static final String STREET_TAG = "street";
    public static final String CITY_TAG = "city";
    public static final String STATE_ID_TAG = "state_id";
    public static final String COUNTRY_ID_TAG = "country_id";
    public static final String ZIP_TAG = "zip";
    public static final String LANGUAGE_TAG = "lang";
    public static final String PHONE_TAG = "phone";
    public static final String MOBILE_TAG = "mobile";
    public static final String FAX_TAG = "fax";
    public static final String EMAIL_TAG = "email";
    public static final String FUNCTION_TAG = "function";
    public static final String COMMERCIAL_AGENT_ID_TAG = "user_id";
    public static final String PARENT_ACCOUNT_ID_TAG = "parent_id";
    public static final String ACTIVE_TAG = "active";

    private Long id;
    private String name;
    private String comment;     //Comentarios
    private String type;
    private String companyType;
    private Boolean isCustomer;
    private Boolean isCompany;
    private Long titleId;
    private String street;
    private String city;
    private Long stateId;
    private Long countryId;
    private String zip;
    private String languageCode;
    private String phone;
    private String mobile;
    private String fax;
    private String email;
    private String function;
    private Long commercialAgentId;
    private Long parentAccountId;
    private Boolean active;

    public OdooContact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public Boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    public Boolean getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(Boolean isCompany) {
        this.isCompany = isCompany;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long title) {
        this.titleId = title;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Long getCommercialAgentId() {
        return commercialAgentId;
    }

    public void setCommercialAgentId(Long commercialAgentId) {
        this.commercialAgentId = commercialAgentId;
    }

    public Long getParentAccountId() {
        return parentAccountId;
    }

    public void setParentAccountId(Long parentAccountId) {
        this.parentAccountId = parentAccountId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Crea un HashMap con el formato (campo -> valor)
     *
     * @return Un <code>HashMap<></code>
     */
    public Map<String, Object> getFieldsAsHashMap() {
        Map result = new HashMap();
        if (name != null) {
            result.put(NAME_TAG, name);
        }
        if (comment != null) {
            result.put(COMMENT_TAG, comment);
        }
        if (type != null) {
            result.put(TYPE_TAG, type);
        }
        if (companyType != null) {
            result.put(COMPANY_TYPE_TAG, companyType);
        }
        if (isCustomer != null) {
            result.put(CUSTOMER_TAG, isCustomer);
        }
        if (isCompany != null) {
            result.put(IS_COMPANY_TAG, isCompany);
        }
        if (titleId != null) {
            result.put(TITLE_ID_TAG, titleId.intValue());   //Cliente XML/RPC no acepta Long por defecto, sólo Integer
        }
        if (street != null) {
            result.put(STREET_TAG, street);
        }
        if (city != null) {
            result.put(CITY_TAG, city);
        }
        if (stateId != null) {
            result.put(STATE_ID_TAG, stateId.intValue());   //Cliente XML/RPC no acepta Long por defecto, sólo Integer
        }
        if (countryId != null) {
            result.put(COUNTRY_ID_TAG, countryId.intValue());  //Cliente XML/RPC no acepta Long por defecto, sólo Integer
        }
        if (zip != null) {
            result.put(ZIP_TAG, zip);
        }
        if (languageCode != null) {
            result.put(LANGUAGE_TAG, languageCode);
        }
        if (phone != null) {
            result.put(PHONE_TAG, phone);
        }
        if (mobile != null) {
            result.put(MOBILE_TAG, mobile);
        }
        if (fax != null) {
            result.put(FAX_TAG, fax);
        }
        if (email != null) {
            result.put(EMAIL_TAG, email);
        }
        if (function != null) {
            result.put(FUNCTION_TAG, function);
        }
        if (commercialAgentId != null) {
            result.put(COMMERCIAL_AGENT_ID_TAG, commercialAgentId.intValue());  //Cliente XML/RPC no acepta Long por defecto, sólo Integer
        }
        if (parentAccountId != null) {
            result.put(PARENT_ACCOUNT_ID_TAG, parentAccountId.intValue());  //Cliente XML/RPC no acepta Long por defecto, sólo Integer
        }
        if (active != null) {
            result.put(ACTIVE_TAG, active);
        }
        return result;
    }

    public void initializeFromHashMap(HashMap fieldMap) {
        this.setId(DataTypeParser.objectToLong(fieldMap.get(ID_TAG)));
        this.setName(DataTypeParser.objectToString(fieldMap.get(NAME_TAG)));
        this.setComment(DataTypeParser.objectToString(fieldMap.get(COMMENT_TAG)));
        this.setType(DataTypeParser.objectToString(fieldMap.get(TYPE_TAG)));
        this.setCompanyType(DataTypeParser.objectToString(fieldMap.get(COMPANY_TYPE_TAG)));
        this.setIsCustomer(DataTypeParser.objectToBoolean(fieldMap.get(CUSTOMER_TAG)));
        this.setIsCompany(DataTypeParser.objectToBoolean(fieldMap.get(IS_COMPANY_TAG)));
        this.setTitleId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(TITLE_ID_TAG))));
        this.setStreet(DataTypeParser.objectToString(fieldMap.get(STREET_TAG)));
        this.setCity(DataTypeParser.objectToString(fieldMap.get(CITY_TAG)));
        this.setStateId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(STATE_ID_TAG))));
        this.setCountryId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(COUNTRY_ID_TAG))));
        this.setZip(DataTypeParser.objectToString(fieldMap.get(ZIP_TAG)));
        this.setLanguageCode(DataTypeParser.objectToString(fieldMap.get(LANGUAGE_TAG)));
        this.setPhone(DataTypeParser.objectToString(fieldMap.get(PHONE_TAG)));
        this.setMobile(DataTypeParser.objectToString(fieldMap.get(MOBILE_TAG)));
        this.setFax(DataTypeParser.objectToString(fieldMap.get(FAX_TAG)));
        this.setEmail(DataTypeParser.objectToString(fieldMap.get(EMAIL_TAG)));
        this.setFunction(DataTypeParser.objectToString(fieldMap.get(FUNCTION_TAG)));
        this.setCommercialAgentId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(COMMERCIAL_AGENT_ID_TAG))));
        this.setParentAccountId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(PARENT_ACCOUNT_ID_TAG))));
        this.setActive(DataTypeParser.objectToBoolean(fieldMap.get(ACTIVE_TAG)));
    }
}
