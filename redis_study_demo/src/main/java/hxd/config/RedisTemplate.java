package hxd.config;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;

/**
 * 为了实现分库存储，重写RedisTemplate，加入index为Redis库编号，重写里面的RedisConnection
 * ，加入是否有库值传递进来的逻辑判断
 */
public class RedisTemplate extends org.springframework.data.redis.core.RedisTemplate {
    public static ThreadLocal<Integer> index = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    @Override
    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        try {
            Integer dbIndex = index.get();
            //如果设置了dbIndex
            if (dbIndex != null) {
                if (connection instanceof JedisConnection) {
                    if (((JedisConnection) connection).getNativeConnection().getDB()!= dbIndex.intValue()) {
                        connection.select(dbIndex);
                    }
                } else {
                    connection.select(dbIndex);

                }
            } else {
                connection.select(0);
            }
        } finally {
            index.remove();
        }
        return super.preProcessConnection(connection, existingConnection);
    }
}
