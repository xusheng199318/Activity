package com.xusheng;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
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
     * act_re_deployment 部署对象表
     * act_re_procdef 流程定义表
     * act_ge_bytearray 资源文件表
     * act_ge_property 主键生成策略表
     */
    @Test
    public void deploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("diagrams/contractAuditing.bpmn")
                .addClasspathResource("diagrams/contractAuditing.png")
                .name("合同审核")
                .category("合同")
                .deploy();
        System.out.println("合同流程部署成功");
    }

    @Test
    public void startProcess() {
        String key = "contract";
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getProcessDefinitionId());
    }

}
