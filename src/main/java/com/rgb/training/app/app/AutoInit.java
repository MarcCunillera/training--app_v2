package com.rgb.training.app.app;

import jakarta.ejb.DependsOn;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

/**
 *
 * @author LuisCarlosGonzalez
 */
//@Singleton
//@Startup
//@DependsOn({"Singleton2"})
public class AutoInit {

    public Long companyId;

    public AutoInit() {
        initBaseVars();
    }

    public void initBaseVars() {
        companyId = 1L;
    }
}
