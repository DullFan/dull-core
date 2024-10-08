package com.dullfan.common.utils.encryption;

import com.dullfan.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

import static com.dullfan.common.constant.Constants.COMMON_SEPARATOR;

/**
 * 雪花算法id生成器
 */
@Slf4j
public class IdUtil {

    /**
     * 工作id 也就是机器id
     */
    private static final long workerId;

    /**
     * 数据中心id
     */
    private static final long dataCenterId;

    /**
     * 序列号
     */
    private static long sequence;

    /**
     * 工作id长度为5位
     */
    private static final long workerIdBits = 5L;

    /**
     * 数据中心id长度为5位
     */
    private static final long dataCenterIdBits = 5L;

    /**
     * 工作id最大值
     */
    private static final long maxWorkerId = ~(-1L << workerIdBits);

    /**
     * 数据中心id最大值
     */
    private static final long maxDataCenterId = ~(-1L << dataCenterIdBits);

    /**
     * 序列号长度
     */
    private static final long sequenceBits = 12L;

    /**
     * 序列号最大值
     */
    private static final long sequenceMask = ~(-1L << sequenceBits);

    /**
     * 工作id需要左移的位数，12位
     */
    private static final long workerIdShift = sequenceBits;

    /**
     * 数据id需要左移位数 12+5=17位
     */
    private static final long dataCenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间戳需要左移位数 12+5+5=22位
     */
    private static final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * 上次时间戳，初始值为负数
     */
    private static long lastTimestamp = -1L;

    static {
        workerId = getMachineNum() & maxWorkerId;
        dataCenterId = getMachineNum() & maxDataCenterId;
        sequence = 0L;
    }

    /**
     * 获取机器编号
     */
    private static long getMachineNum() {
        long machinePiece;
        StringBuilder sb = new StringBuilder();
        Enumeration<NetworkInterface> e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            log.error(Arrays.toString(e1.getStackTrace()));
        }
        while (true) {
            assert e != null;
            if (!e.hasMoreElements()) break;
            NetworkInterface ni = e.nextElement();
            sb.append(ni.toString());
        }
        machinePiece = sb.toString().hashCode();
        return machinePiece;
    }

    /**
     * 获取时间戳，并与上次时间戳比较
     */
    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取系统时间戳
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 生成ID
     */
    public synchronized static Long get() {
        long timestamp = timeGen();
        // 获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 获取当前时间戳如果等于上次时间戳
        // 说明：还处在同一毫秒内，则在序列号加1；否则序列号赋值为0，从0开始。
        // 0 - 4095
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        //将上次时间戳值刷新
        lastTimestamp = timestamp;

        long startTimestamp = 1288834974657L;
        return ((timestamp - startTimestamp) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 加密
     * 52567412552 -> jRc3jvdw9zL_WTM1YGBkkA
     */
    public static String encrypt(Long id) {
        if (Objects.nonNull(id)) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(8);
            byteBuffer.putLong(0, id);
            byte[] content = byteBuffer.array();
            byte[] encrypt = AES128Util.aesEncrypt(content);
            // 使用URL安全的Base64编码
            return java.util.Base64.getUrlEncoder().withoutPadding().withoutPadding().encodeToString(encrypt);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 解密
     * jRc3jvdw9zL_WTM1YGBkkA -> 52567412552
     */
    public static Long decrypt(String decryptId) {
        if (StringUtils.isNotBlank(decryptId)) {
            // 使用URL安全的Base64解码
            byte[] encrypt = java.util.Base64.getUrlDecoder().decode(decryptId);
            byte[] content = AES128Util.aesDecode(encrypt);
            if (!(content == null || content.length == 0)) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(content);
                return byteBuffer.getLong();
            }
            throw new ServiceException("AES128Util.aesDecode fail");
        }
        throw new ServiceException("the decryptId can not be empty");
    }

    /**
     * 解密多个加密ID拼接的字符串
     */
    public static List<Long> decryptIdList(String decryptIdStr) {
        if (StringUtils.isBlank(decryptIdStr)) {
            return new ArrayList<>();
        }
        List<String> decryptIdList = splitDecryptId(decryptIdStr);

        if (CollectionUtils.isEmpty(decryptIdList)) {
            return new ArrayList<>();
        }
        return decryptIdList.stream().map(IdUtil::decrypt).collect(Collectors.toList());
    }


    public static List<String> splitDecryptId(String decryptIdStr) {
        if (decryptIdStr == null || decryptIdStr.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(decryptIdStr.split(COMMON_SEPARATOR));
    }

}
