package com.rgb.training.app.common.odoo.model;

import com.rgb.training.app.common.odoo.utils.DataTypeParser;
import jakarta.json.bind.annotation.JsonbTransient;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LuisCarlosGonzalez
 */
public class OdooAccount {

    public static final String ID_TAG = "id";
    public static final String NAME_TAG = "name";
    public static final String COMMENT_TAG = "comment";
    //public static final String COMERCIAL_TAG = "comercial";
    public static final String COMPANY_TYPE_TAG = "company_type";
    public static final String IS_COMPANY_TAG = "is_company";
    //public static final String CUSTOMER_TAG = "customer";
    public static final String STREET_TAG = "street";
    public static final String CITY_TAG = "city";
    public static final String STATE_ID_TAG = "state_id";
    public static final String COUNTRY_ID_TAG = "country_id";
    public static final String ZIP_TAG = "zip";
    public static final String PRODUCT_PRICELIST_TAG = "property_product_pricelist";
    public static final String LANGUAGE_TAG = "lang";
    public static final String PHONE_TAG = "phone";
    public static final String MOBILE_TAG = "mobile";
    //public static final String FAX_TAG = "fax";
    public static final String WEBSITE_TAG = "website";
    public static final String VAT_TAG = "vat";
    public static final String EMAIL_TAG = "email";
    public static final String COMMERCIAL_AGENT_ID_TAG = "user_id";
    public static final String PARENT_ACCOUNT_ID_TAG = "parent_id";
    public static final String ACTIVE_TAG = "active"; //TODO:EDU: Se añade gestión de archivado

    private Long id;
    private String name;
    private String comment;
    private String comercial;
    private String companyType;
    private Boolean isCustomer;
    private Boolean isCompany;
    private String street;
    private String city;
    private Long stateId;
    private Long countryId;
    private String zip;
    private Long productPriceListId;
    private String languageCode;
    private String phone;  //phone
    private String mobile;  //mobil
    private String fax;
    private String website;
    private String vat;
    private String email;
    private Long commercialAgentId;
    private Long parentAccountId;
    private Boolean active;

    public OdooAccount() {
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

    public String getComercial() {
        return comercial;
    }

    public void setComercial(String comercial) {
        this.comercial = comercial;
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

    public Long getProductPriceListId() {
        return productPriceListId;
    }

    public void setProductPriceListId(Long productPriceListId) {
        this.productPriceListId = productPriceListId;
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

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @JsonbTransient
    public Map<String, Object> getFieldsAsHashMap() {
        Map result = new HashMap();
        if (name != null) {
            result.put(NAME_TAG, name);
        }
        if (comment != null) {
            result.put(COMMENT_TAG, comment);
        }
        /*if (comercial != null) {
            result.put(COMERCIAL_TAG, comercial);
        }*/
        if (companyType != null) {
            result.put(COMPANY_TYPE_TAG, companyType);
        }
        /*if (isCustomer != null) {
            result.put(CUSTOMER_TAG, isCustomer);
        }*/
        if (isCompany != null) {
            result.put(IS_COMPANY_TAG, isCompany);
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
            result.put(COUNTRY_ID_TAG, countryId.intValue());   //Cliente XML/RPC no acepta Long por defecto, sólo Integer
        }
        if (zip != null) {
            result.put(ZIP_TAG, zip);
        }
        if (productPriceListId != null) {
            result.put(PRODUCT_PRICELIST_TAG, productPriceListId.intValue());   //Cliente XML/RPC no acepta Long por defecto, sólo Integer
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
        /*if (fax != null) {
            result.put(FAX_TAG, fax);
        }*/
        if (website != null) {
            result.put(WEBSITE_TAG, website);
        }
        if (vat != null) {
            result.put(VAT_TAG, vat);
        }
        if (email != null) {
            result.put(EMAIL_TAG, email);
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
        //this.setComercial(DataTypeParser.objectToString(fieldMap.get(COMERCIAL_TAG)));
        this.setCompanyType(DataTypeParser.objectToString(fieldMap.get(COMPANY_TYPE_TAG)));
        //this.setIsCustomer(DataTypeParser.objectToBoolean(fieldMap.get(CUSTOMER_TAG)));
        this.setIsCompany(DataTypeParser.objectToBoolean(fieldMap.get(IS_COMPANY_TAG)));
        this.setStreet(DataTypeParser.objectToString(fieldMap.get(STREET_TAG)));
        this.setCity(DataTypeParser.objectToString(fieldMap.get(CITY_TAG)));
        this.setStateId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(STATE_ID_TAG))));
        this.setCountryId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(COUNTRY_ID_TAG))));
        this.setZip(DataTypeParser.objectToString(fieldMap.get(ZIP_TAG)));
        this.setProductPriceListId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(PRODUCT_PRICELIST_TAG))));
        this.setLanguageCode(DataTypeParser.objectToString(fieldMap.get(LANGUAGE_TAG)));
        this.setPhone(DataTypeParser.objectToString(fieldMap.get(PHONE_TAG)));
        this.setMobile(DataTypeParser.objectToString(fieldMap.get(MOBILE_TAG)));
        //this.setFax(DataTypeParser.objectToString(fieldMap.get(FAX_TAG)));
        this.setWebsite(DataTypeParser.objectToString(fieldMap.get(WEBSITE_TAG)));
        this.setVat(DataTypeParser.objectToString(fieldMap.get(VAT_TAG)));
        this.setEmail(DataTypeParser.objectToString(fieldMap.get(EMAIL_TAG)));
        this.setCommercialAgentId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(COMMERCIAL_AGENT_ID_TAG))));
        this.setParentAccountId(DataTypeParser.objectArrayToLong(DataTypeParser.objectToObjectArray(fieldMap.get(PARENT_ACCOUNT_ID_TAG))));
        this.setActive(DataTypeParser.objectToBoolean(fieldMap.get(ACTIVE_TAG)));
    }
}
