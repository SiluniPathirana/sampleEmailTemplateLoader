package org.custom.sample.internal;
/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.custom.sample.customEmailTemplateLoader;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.utils.Axis2ConfigurationContextObserver;

/**
 * @scr.component name="org.custom.sample" immediate="true"
 * @scr.reference name="registry.service"
 * interface="org.wso2.carbon.registry.core.service.RegistryService" cardinality="1..1"
 * policy="dynamic" bind="setRegistryService" unbind="unsetRegistryService"
 **/
public class TenantObserverServiceComponent {

    private static final Log log = LogFactory.getLog(org.custom.sample.internal.
            TenantObserverServiceComponent.class);

    private static RegistryService registryService;

    protected void activate(ComponentContext componentContext) {

        try {
            if (log.isDebugEnabled()) {
                log.debug("TenantObserver component activated");
            }
            BundleContext bundleContext = componentContext.getBundleContext();

            //Register Tenant service creator to deploy tenant specific common synapse configurations
            customEmailTemplateLoader customEmailTemplateLoader = new customEmailTemplateLoader();
            bundleContext.registerService(Axis2ConfigurationContextObserver.class.getName(), customEmailTemplateLoader,
                    null);
        } catch (Throwable e) {
            log.error("TenantObserver bundle activation Failed", e);
        }
    }

    protected void deactivate(ComponentContext componentContext) {

        if (log.isDebugEnabled()) {
            log.debug("Deactivating TenantObserver component");
        }
    }

    protected void setRegistryService(RegistryService registryService) {

        if (log.isDebugEnabled()) {
            log.debug("Setting the Registry Service");
        }
        TenantObserverServiceComponent.registryService = registryService;
    }

    protected void unsetRegistryService(RegistryService registryService) {

        if (log.isDebugEnabled()) {
            log.debug("UnSetting the Registry Service");
        }
        TenantObserverServiceComponent.registryService = null;
    }

    public static RegistryService getRegistryService() {
        return registryService;
    }
}

