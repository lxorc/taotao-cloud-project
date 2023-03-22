package com.taotao.cloud.job.biz.powerjob.processors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;
import tech.powerjob.common.serialize.JsonUtils;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.TaskResult;
import tech.powerjob.worker.core.processor.sdk.MapReduceProcessor;
import tech.powerjob.worker.log.OmsLogger;

/**
 * MapReduce 处理器示例 控制台参数：{"batchSize": 100, "batchNum": 2}
 */
@Component
public class MapReduceProcessorDemo implements MapReduceProcessor {

	@Override
	public ProcessResult process(TaskContext context) throws Exception {

		OmsLogger omsLogger = context.getOmsLogger();

		System.out.println("============== TestMapReduceProcessor#process ==============");
		System.out.println("isRootTask:" + isRootTask());
		System.out.println("taskContext:" + JsonUtils.toJSONString(context));

		// 根据控制台参数获取MR批次及子任务大小
		final JSONObject jobParams = JSONObject.parseObject(context.getJobParams());

		Integer batchSize = (Integer) jobParams.getOrDefault("batchSize", 100);
		Integer batchNum = (Integer) jobParams.getOrDefault("batchNum", 10);

		if (isRootTask()) {
			System.out.println("==== MAP ====");
			omsLogger.info("[DemoMRProcessor] start root task~");
			List<TestSubTask> subTasks = Lists.newLinkedList();
			for (int j = 0; j < batchNum; j++) {
				for (int i = 0; i < batchSize; i++) {
					int x = j * batchSize + i;
					subTasks.add(new TestSubTask("name" + x, x));
				}
				map(subTasks, "MAP_TEST_TASK");
				subTasks.clear();
			}
			omsLogger.info("[DemoMRProcessor] map success~");
			return new ProcessResult(true, "MAP_SUCCESS");
		} else {
			System.out.println("==== NORMAL_PROCESS ====");
			omsLogger.info("[DemoMRProcessor] process subTask: {}.",
				JSON.toJSONString(context.getSubTask()));
			System.out.println("subTask: " + JsonUtils.toJSONString(context.getSubTask()));
			Thread.sleep(1000);
			if (context.getCurrentRetryTimes() == 0) {
				return new ProcessResult(false, "FIRST_FAILED");
			} else {
				return new ProcessResult(true, "PROCESS_SUCCESS");
			}
		}
	}

	@Override
	public ProcessResult reduce(TaskContext context, List<TaskResult> taskResults) {
		log.info("================ MapReduceProcessorDemo#reduce ================");
		log.info("TaskContext: {}", JSONObject.toJSONString(context));
		log.info("List<TaskResult>: {}", JSONObject.toJSONString(taskResults));
		context.getOmsLogger().info("MapReduce job finished, result is {}.", taskResults);

		boolean success = ThreadLocalRandom.current().nextBoolean();
		return new ProcessResult(success, context + ": " + success);
	}

	public static class TestSubTask {

		private String name;
		private int age;

		public TestSubTask() {
		}

		public TestSubTask(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}
}
