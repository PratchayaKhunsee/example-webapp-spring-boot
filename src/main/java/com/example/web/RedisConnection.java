package com.example.web;

import java.util.List;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

public class RedisConnection {

    private static RedisClient client = RedisClient.create(System.getenv("REDIS_URL"));
    private static StatefulRedisConnection<String, String> connection;
    private static Long maxListLength = 50l;


    public static Long push(String key, String value){
        if(connection == null) connection = client.connect();
        var command = connection.sync();
        if(command.llen(key) == maxListLength) command.lpop(key);
        return command.rpush(key, value);
    }

    public static List<String> list(String key){
        if(connection == null) connection = client.connect();
        return connection.sync().lrange(key, 0, maxListLength);
    }
}
