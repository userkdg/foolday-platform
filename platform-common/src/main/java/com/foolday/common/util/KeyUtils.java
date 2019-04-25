package com.foolday.common.util;


import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.foolday.common.constant.WebConstant.RedisKey.REDIS_ORDER_NO_EXPIRE_DAY;
import static com.foolday.common.constant.WebConstant.RedisKey.REDIS_ORDER_NO_KEY;

public final class KeyUtils {

    private KeyUtils() {
    }

    /**
     * 生成每天的唯一订单号
     *
     * @param redisTemplate
     * @return
     */
    public static String generateOrderNoOfDay(RedisTemplate<String, String> redisTemplate) {
        return generateOrderNoOfDay(redisTemplate, LocalDateTime.now());
    }


    /**
     * 生成每天的唯一订单号,很好的用于记录一天的单量
     * <p>
     * 20190425000000001
     * 20190425000000002
     * 20190425000000003
     * 20190425000000004
     * 20190425000000005
     * 20190425000000006
     *
     * @param redisTemplate
     * @return
     */
    public static String generateOrderNoOfDay(RedisTemplate<String, String> redisTemplate, LocalDateTime dateTime) {
        PlatformAssert.notNull(redisTemplate, "redis 客户端不可为空");
        String yyyyMMdd = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (Boolean.TRUE.equals(redisTemplate.hasKey(yyyyMMdd))) {
            redisTemplate.opsForValue().increment(yyyyMMdd, 1L);
            return redisTemplate.opsForValue().get(yyyyMMdd);
        } else {
            String dayFirstNo = (yyyyMMdd + "00000000" + 1);
            redisTemplate.opsForValue().set(REDIS_ORDER_NO_KEY + yyyyMMdd, dayFirstNo, REDIS_ORDER_NO_EXPIRE_DAY, TimeUnit.DAYS);
            return dayFirstNo;
        }
    }

    /**
     * 107332201074393128
     * 107332201074393135
     * 107332201074393136
     * 107332201074393137
     * 107332201074393138
     * 107332201074393139
     * 107332201074393140
     * 107332201074393141
     * 107332201074393142
     * 107332201074393143
     * 107332201078587392
     * 107332201078587393
     * 107332201078587394
     * 107332201078587395
     * 107332201078587396
     * 基于雪花算法的唯一值，为long，有别于uuid，用于生订单单号
     *
     * @return
     */
    public static long generateUniqueOrderNo() {
        UniqueOrderGenerate uniqueOrderGenerate = new UniqueOrderGenerate(0, 0);
        return uniqueOrderGenerate.nextId();
    }

    /**
     * Twitter_Snowflake<br>
     * SnowFlake的结构如下(每部分用-分开):<br>
     * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
     * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
     * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
     * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
     * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
     * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
     * 加起来刚好64位，为一个Long型。<br>
     * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
     */
    public static class UniqueOrderGenerate {

        // ==============================Fields===========================================
        /**
         * 开始时间截 (2018-07-03)
         */

        private final long twepoch = 1530607760000L;

        /**
         * 机器id所占的位数
         */
        private final long workerIdBits = 5L;

        /**
         * 数据标识id所占的位数
         */
        private final long datacenterIdBits = 5L;

        /**
         * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
         */
        private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

        /**
         * 支持的最大数据标识id，结果是31
         */
        private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

        /**
         * 序列在id中占的位数
         */
        private final long sequenceBits = 12L;

        /**
         * 机器ID向左移12位
         */
        private final long workerIdShift = sequenceBits;

        /**
         * 数据标识id向左移17位(12+5)
         */
        private final long datacenterIdShift = sequenceBits + workerIdBits;

        /**
         * 时间截向左移22位(5+5+12)
         */
        private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

        /**
         * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
         */
        private final long sequenceMask = -1L ^ (-1L << sequenceBits);

        /**
         * 工作机器ID(0~31)
         */
        private long workerId;

        /**
         * 数据中心ID(0~31)
         */
        private long datacenterId;

        /**
         * 毫秒内序列(0~4095)
         */
        private long sequence = 0L;

        /**
         * 上次生成ID的时间截
         */
        private long lastTimestamp = -1L;

        //==============================Constructors=====================================

        /**
         * 构造函数
         *
         * @param workerId     工作ID (0~31)
         * @param datacenterId 数据中心ID (0~31)
         */
        public UniqueOrderGenerate(long workerId, long datacenterId) {
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }

        // ==============================Methods==========================================

        /**
         * 获得下一个ID (该方法是线程安全的)
         *
         * @return SnowflakeId
         */
        public synchronized long nextId() {
            long timestamp = timeGen();

            //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
            if (timestamp < lastTimestamp) {
                throw new RuntimeException(
                        String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }

            //如果是同一时间生成的，则进行毫秒内序列
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                //毫秒内序列溢出
                if (sequence == 0) {
                    //阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            }
            //时间戳改变，毫秒内序列重置
            else {
                sequence = 0L;
            }

            //上次生成ID的时间截
            lastTimestamp = timestamp;

            //移位并通过或运算拼到一起组成64位的ID
            return (((timestamp - twepoch) << timestampLeftShift) //
                    | (datacenterId << datacenterIdShift) //
                    | (workerId << workerIdShift) //
                    | sequence);
        }

        /**
         * 阻塞到下一个毫秒，直到获得新的时间戳
         *
         * @param lastTimestamp 上次生成ID的时间截
         * @return 当前时间戳
         */
        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        /**
         * 返回以毫秒为单位的当前时间
         *
         * @return 当前时间(毫秒)
         */
        protected long timeGen() {
            return System.currentTimeMillis();
        }

        //==============================Test=============================================

        /**
         * 测试
         */
        public static void main(String[] args) {
            UniqueOrderGenerate idWorker = new KeyUtils.UniqueOrderGenerate(0, 0);
            for (int i = 0; i < 1000; i++) {
                long id = idWorker.nextId();
                //System.out.println(Long.toBinaryString(id));
                System.out.println(id);
            }
        }
    }

}
