/*
*Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.automation.distributed.commons;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.wso2.carbon.automation.distributed.beans.Deployment;
import org.wso2.carbon.automation.distributed.utills.GitRepositoryUtil;
import org.wso2.carbon.automation.engine.exceptions.AutomationFrameworkException;
import org.wso2.carbon.automation.engine.frameworkutils.FrameworkPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class handles initiates fundamental platform required for distributed deployment.
 */
public class BaseManager {

    protected Log log = LogFactory.getLog(BaseManager.class);

    public BaseManager()
            throws AutomationFrameworkException, IOException, GitAPIException, InterruptedException{

        String resourceLocation = FrameworkPathUtil.getSystemResourceLocation();

        HashMap<String, Deployment> deploymentHashMap = new DeploymentConfigurationReader().getDeploymentHashMap();
        List<Deployment> deploymentList = new ArrayList<>(deploymentHashMap.values());

        for (Deployment deployment : deploymentList) {
            try {
                log.info("clone git repo =>" + deployment.getRepository() + "  to =>" + resourceLocation
                         + File.separator + "Artifacts"
                         + File.separator + deployment.getName());
                GitRepositoryUtil.gitCloneRepository(deployment.getRepository(), resourceLocation
                                                                                 + File.separator + "Artifacts"
                                                                                 + File.separator
                                                                                 + deployment.getName());
            } catch (GitAPIException e) {
                throw new AutomationFrameworkException("puppet-modules git clone failed.", e);
            }
        }

    }


}

