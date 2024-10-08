package org.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class CustomizeExecutorProperties {

    public static final String PREFIX = "appoint.executor";

    /**
     * 核心线程数
     * 建议：(cpu核心数 * 2) + 有效磁盘数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     * 建议：（最大任务数-队列容量）/每个线程每秒处理能力
     */
    private Integer maximumPoolSize;

    /**
     * 空闲线程在终止前等待新任务的最长时间（单位：秒）
     */
    private Long keepAliveTime;

    /**
     * 队列容量
     * 建议：核心线程数 / 任务消耗时长（秒） * 系统可容忍的响应时长（秒）
     */
    private Integer queueSize;

    private final DefaultExecutorProperties defaultExecutor = new DefaultExecutorProperties();

    public DefaultExecutorProperties getDefaultExecutor() {
        return defaultExecutor;
    }

    public static class DefaultExecutorProperties {

        /**
         * 核心线程数
         * 建议：(cpu核心数 * 2) + 有效磁盘数
         */
        private Integer corePoolSize;

        /**
         * 最大线程数
         * 建议：（最大任务数-队列容量）/每个线程每秒处理能力
         */
        private Integer maximumPoolSize;

        /**
         * 空闲线程在终止前等待新任务的最长时间（单位：秒）
         */
        private Long keepAliveTime;

        /**
         * 队列容量
         * 建议：核心线程数 / 任务消耗时长（秒） * 系统可容忍的响应时长（秒）
         */
        private Integer queueSize;

        public DefaultExecutorProperties() {
            this.corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
            this.queueSize = this.corePoolSize * 5;
            this.maximumPoolSize = (3000 - this.queueSize) * 5;
            this.keepAliveTime = 60L;
        }

        public Integer getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(Integer corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public Integer getMaximumPoolSize() {
            return maximumPoolSize;
        }

        public void setMaximumPoolSize(Integer maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
        }

        public Long getKeepAliveTime() {
            return keepAliveTime;
        }

        public void setKeepAliveTime(Long keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
        }

        public Integer getQueueSize() {
            return queueSize;
        }

        public void setQueueSize(Integer queueSize) {
            this.queueSize = queueSize;
        }

    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }
}
