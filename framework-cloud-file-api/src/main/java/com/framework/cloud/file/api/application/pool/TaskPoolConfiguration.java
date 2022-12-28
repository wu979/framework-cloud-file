package com.framework.cloud.file.api.application.pool;

import com.framework.cloud.executors.feature.ExecutorsFeature;
import com.framework.cloud.file.common.constant.TaskPoolConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;

/**
 * 线程池配置器
 *
 * @author wusiwei
 */
@Configuration
@RequiredArgsConstructor
public class TaskPoolConfiguration {

    private final ExecutorsFeature executorsFeature;

    @Bean(TaskPoolConstant.FILE)
    public AsyncTaskExecutor filePool() {
        return executorsFeature.executor(TaskPoolConstant.FILE);
    }
}
