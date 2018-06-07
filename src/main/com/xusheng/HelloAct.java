package com.xusheng;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by xusheng on 2018/6/7.
 */
public class HelloAct {

    private ProcessEngine processEngine;

    @Before
    public void init() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        processEngine = configuration.buildProcessEngine();
    }

    /**
     * 部署流程
     */
    @Test
    public void testDeploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("diagrams/contractAuditing.bpmn")
                .addClasspathResource("diagrams/contractAuditing.png")
                .name("合同审核")
                .category("合同")
                .deploy();
        System.out.println("合同流程部署成功");
    }


}
