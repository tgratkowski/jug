package org.jug.zgora;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbpm.test.JBPMHelper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;

public class Example1Main {

	public static void main(String[] args) {

		//
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");

		RuntimeManager manager = createRuntimeManager(kbase);
		RuntimeEngine engine = manager.getRuntimeEngine(null);
		KieSession ksession = engine.getKieSession();
		TaskService taskService = engine.getTaskService();

		// ProcessInstance processInstance = ksession
		// .startProcess("org.jug.zgora.sample1");
		// ProcessInstance processInstance = ksession
		// .startProcess("org.jug.zgora.sample2");
		 ProcessInstance processInstance = ksession.startProcess("org.jug.zgora.sample3");

		System.out
				.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		Example1Main.printSingleEndLOG(ksession, taskService, processInstance);

		HashMap<String, Object> params = null;

		/** let mary execute Task 2 */
		 params = new HashMap<String, Object>();
		 params.put("decision", new Boolean(true));
		Example1Main.doTask(taskService, "mary", params);
		Example1Main.printSingleEndLOG(ksession, taskService, processInstance);

		/** let john execute Task 1 */
		params = new HashMap<String, Object>();
		params.put("decision", new Boolean(true));
		Example1Main.doTask(taskService, "john", params);
		Example1Main.printSingleEndLOG(ksession, taskService, processInstance);

		System.out
				.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		manager.disposeRuntimeEngine(engine);
		System.exit(0);
	}

	private static void doTask(TaskService taskService, String taskOwner,
			Map<String, Object> params) {
		List<TaskSummary> list = null;
		TaskSummary task = null;

		list = taskService.getTasksAssignedAsPotentialOwner(taskOwner, "en-UK");
		if (!list.isEmpty()) {
			System.out.println("task list[size:" + list.size() + "]="
					+ Arrays.toString(list.toArray()));

			task = list.get(0);
			System.out.println(taskOwner + " is executing task "
					+ task.getName() + " in ProcessId [" + task.getProcessId()
					+ "] taskID: [" + task.getId() + "]");
			taskService.start(task.getId(), taskOwner);
			taskService.complete(task.getId(), taskOwner, params);
		} else {
			System.err.println(taskOwner + " don't have tasks!");
			return;
		}
	}

	private static RuntimeManager createRuntimeManager(KieBase kbase) {
		JBPMHelper.startH2Server();
		JBPMHelper.setupDataSource();
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("org.jbpm.persistence.jpa");
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory
				.get().newDefaultBuilder().entityManagerFactory(emf)
				.knowledgeBase(kbase);
		return RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(
				builder.get(), "org.jug.zgora:example:1.0");
	}

	protected static void printEndLOG(KieSession ksession,
			TaskService taskService, ProcessInstance proc) {
		System.out.println("\n");
		Example1Main.printSingleEndLOG(ksession, taskService, proc);

		System.out
				.println("#########################################################################################################");
		System.out.println("\t ALL Processes ["
				+ ksession.getProcessInstances().size() + "] in KSESSION ID:"
				+ ksession.getIdentifier());
		for (ProcessInstance procIn : ksession.getProcessInstances()) {
			Example1Main.printSingleEndLOG(ksession, taskService, procIn);
		}
		System.out
				.println("#########################################################################################################\n");
	}

	protected static void printSingleEndLOG(KieSession ksession,
			TaskService taskService, ProcessInstance proc) {
		System.out
				.println("=========================================================================================================");
		for (Long id : taskService.getTasksByProcessInstanceId(proc.getId())) {
			System.out.print("taskID: " + id);
			System.out.print(" task name: "
					+ taskService.getTaskById(id).getNames().get(0).getText());
			System.out.println("  Task Status: "
					+ taskService.getTaskById(id).getTaskData().getStatus());
		}
		System.out
				.println("=========================================================================================================");

	}

}