package com.xusheng;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xusheng on 2018/6/7.
 */
public class HelloAct {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private RuntimeService runtimeService = processEngine.getRuntimeService();
    private TaskService taskService = processEngine.getTaskService();

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
                .name("合同审核")
                .category("合同")
                .deploy();

        System.out.println("合同流程部署成功");
    }

    /**
     * 执行流程
     */
    @Test
    public void startProcess() {
        String key = "contract";
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("instid", 1012);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, variables);
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getProcessDefinitionId());
    }

    /**
     * 查询任务列表
     */
    @Test
    public void queryTask() {
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> tasks = taskQuery.processVariableValueEquals("instid", 1012).listPage(0, 10);
        for (Task task : tasks) {
            System.out.println(task.getAssignee());
        }
    }

    /**
     * 根据指定的任务执行人查询任务
     */
    @Test
    public void queryTaskByAssign() {
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        TaskQuery zsTaskQuery = taskQuery.taskAssignee("lisi");
        List<Task> tasks = zsTaskQuery.list();
        for (Task task : tasks) {
            System.out.println(task.getAssignee());
            System.out.println(task.getId());
            System.out.println(task.getName());
        }
    }

    @Test
    public void completeTask() {
        taskService.complete("10004");
        System.out.println("执行成功");
    }

}
