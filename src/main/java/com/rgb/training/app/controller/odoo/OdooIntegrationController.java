package com.rgb.training.app.controller.odoo;

import com.rgb.training.app.common.odoo.connection.OdooConnection;
import com.rgb.training.app.common.odoo.model.OdooAccount;
import com.rgb.training.app.common.odoo.model.OdooContact;
import com.rgb.training.app.common.odoo.types.Domain;
import com.rgb.training.app.common.odoo.types.Record;
import com.rgb.training.app.common.odoo.types.Recordset;
import com.rgb.training.app.common.odoo.types.Values;
import com.rgb.training.app.config.CustomConfig;
import java.util.Arrays;

/**
 *
 * @author LuisCarlosGonzalez
 */
public class OdooIntegrationController {

    // Odoo connection object
    private OdooConnection odooConnection;

    public OdooAccount getAccount(Long odooAccountId, Boolean isActive) throws Exception {
        OdooAccount odooAccount = null;
        try {
            Domain domain = Domain.create()
                    .filter(OdooAccount.ID_TAG, "=", odooAccountId.intValue())
                    .filter(OdooAccount.ACTIVE_TAG, "=", isActive)
                    .filter(OdooAccount.IS_COMPANY_TAG, "=", true);
            Values fields = this.accountFields();
            Recordset records = odoo().search_read("res.partner", domain, fields);
            if (!records.isEmpty()) {
                odooAccount = new OdooAccount();
                Record record = records.get(0);
                odooAccount.initializeFromHashMap(record);
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return odooAccount;
    }

    public OdooAccount getAccountByVAT(String accountVAT, Boolean isActive) throws Exception {
        OdooAccount odooAccount = null;
        try {
            Domain domain = Domain.create().filter(OdooAccount.VAT_TAG, "=", accountVAT)
                    .filter(OdooAccount.ACTIVE_TAG, "=", isActive);
            Values fields = this.accountFields();
            Recordset records = odoo().search_read("res.partner", domain, fields);
            if (!records.isEmpty()) {
                odooAccount = new OdooAccount();
                Record record = records.get(0);
                odooAccount.initializeFromHashMap(record);
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return odooAccount;
    }

    public OdooContact getContact(Long odooContactId, Boolean isActive) throws Exception {
        OdooContact odooContact = null;
        try {
            Domain domain = Domain.create().filter(OdooAccount.ID_TAG, "=", odooContactId.intValue())
                    .filter(OdooContact.ACTIVE_TAG, "=", isActive)
                    .filter(OdooContact.IS_COMPANY_TAG, "=", false);
            Values fields = this.contactFields();
            Recordset records = odoo().search_read("res.partner", domain, fields);
            if (!records.isEmpty()) {
                odooContact = new OdooContact();
                Record record = records.get(0);
                odooContact.initializeFromHashMap(record);
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return odooContact;
    }

    public Integer createAccount(OdooAccount account) throws Exception {
        Integer accountId = null;
        try {
            Values fields = Values.create(Arrays.asList(account.getFieldsAsHashMap()));
            accountId = odoo().create("res.partner", fields);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return accountId;
    }

    public Boolean updateAccount(OdooAccount account) throws Exception {
        Boolean result = null;
        try {
            Values ids = Values.create(account.getId().intValue());
            Values fields = Values.create(ids, account.getFieldsAsHashMap());
            result = odoo().update("res.partner", fields);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return result;
    }

    public Integer createContact(OdooContact contact) throws Exception {
        Integer accountId = null;
        try {
            Values fields = Values.create(Arrays.asList(contact.getFieldsAsHashMap()));
            accountId = odoo().create("res.partner", fields);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return accountId;
    }

    public Boolean updateContact(OdooContact contact) throws Exception {
        Boolean result = null;
        try {
            Values ids = Values.create(contact.getId().intValue());
            Values fields = Values.create(ids, contact.getFieldsAsHashMap());
            result = odoo().update("res.partner", fields);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return result;
    }

    private Values accountFields() {
        Values fields = Values.create(OdooAccount.NAME_TAG,
                OdooAccount.COMMENT_TAG,
                OdooAccount.COMPANY_TYPE_TAG,
                OdooAccount.IS_COMPANY_TAG,
                OdooAccount.STREET_TAG,
                OdooAccount.CITY_TAG,
                OdooAccount.STATE_ID_TAG,
                OdooAccount.COUNTRY_ID_TAG,
                OdooAccount.ZIP_TAG,
                OdooAccount.PRODUCT_PRICELIST_TAG,
                OdooAccount.LANGUAGE_TAG,
                OdooAccount.PHONE_TAG,
                OdooAccount.MOBILE_TAG,
                OdooAccount.WEBSITE_TAG,
                OdooAccount.VAT_TAG,
                OdooAccount.EMAIL_TAG,
                OdooAccount.COMMERCIAL_AGENT_ID_TAG,
                OdooAccount.PARENT_ACCOUNT_ID_TAG,
                OdooAccount.ACTIVE_TAG);
        return fields;
    }

    private Values contactFields() {
        Values fields = Values.create(OdooContact.NAME_TAG,
                OdooContact.COMMENT_TAG,
                OdooContact.TYPE_TAG,
                OdooContact.COMPANY_TYPE_TAG,
                OdooContact.TITLE_ID_TAG,
                OdooContact.STREET_TAG,
                OdooContact.CITY_TAG,
                OdooContact.STATE_ID_TAG,
                OdooContact.COUNTRY_ID_TAG,
                OdooContact.ZIP_TAG,
                OdooContact.LANGUAGE_TAG,
                OdooContact.PHONE_TAG,
                OdooContact.MOBILE_TAG,
                OdooContact.EMAIL_TAG,
                OdooContact.FUNCTION_TAG,
                OdooContact.COMMERCIAL_AGENT_ID_TAG,
                OdooContact.PARENT_ACCOUNT_ID_TAG,
                OdooContact.ACTIVE_TAG);
        return fields;
    }

    synchronized OdooConnection odoo() throws Exception {
        if (this.odooConnection == null) {
            try {
                this.odooConnection = new OdooConnection(CustomConfig.getODOO_URL(), CustomConfig.getODOO_DB_NAME(), CustomConfig.getODOO_USER_ID(), CustomConfig.getODOO_PASSWORD());
            } catch (Exception error) {
                System.out.println("Can't create Odoo connection.");
                error.printStackTrace();
                throw new Exception(error);
            }
        }
        return this.odooConnection;
    }
}
