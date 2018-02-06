package com.hit.twitter;

/**
 * author:Charies Gavin
 * date:2018/1/2,20:03
 * https:github.com/guobinhit
 * description:Twitter ID 生成器
 */

public class IdWorker {

    private final long workerId;

    private final static long twepoch = 1361753741828L;

    private long sequence = 0L;

    // 机器标识位数
    private final static long workerIdBits = 4L;

    // 机器ID最大值
    public final static long maxWorkerId = -1L ^ -1L << workerIdBits;

    // 毫秒内自增位
    private final static long sequenceBits = 10L;

    private final static long workerIdShift = sequenceBits;

    private final static long timestampLeftShift = sequenceBits + workerIdBits;

    public final static long sequenceMask = -1L ^ -1L << sequenceBits;

    private long lastTimestamp = -1L;

    /**
     * 构造器
     *
     * @param workerId
     */
    public IdWorker(final long workerId) {
        super();
        /**
         * 限定 ID 有效值范围
         */
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    this.maxWorkerId));

        }
        this.workerId = workerId;
    }

    /**
     * 发号器
     *
     * @return 返回下一个 ID 值
     */
    public synchronized long nextId() {

        long timestamp = this.timeGen();

        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            if (this.sequence == 0) {
                System.out.println("###########" + sequenceMask);
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }

        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(
                        String.format(
                                "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                                this.lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        this.lastTimestamp = timestamp;

        long nextId = ((timestamp - twepoch << timestampLeftShift)) | (this.workerId << this.workerIdShift) | (this.sequence);

        System.out.println("timestamp:" + timestamp + ", timestampLeftShift:"
                + timestampLeftShift + ", nextId:" + nextId + ", workerId:"
                + workerId + ", sequence:" + sequence);

        return nextId;

    }

    private long tilNextMillis(final long lastTimestamp) {

        long timestamp = this.timeGen();

        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * @return 返回当前时间的毫秒值
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        IdWorker idWorker = new IdWorker(10);
        System.out.println("当前序号为：" + idWorker.nextId());
    }
}