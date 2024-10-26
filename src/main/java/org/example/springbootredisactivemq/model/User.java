package org.example.springbootredisactivemq.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("users")
public class User{
    @Id
    private String id;
    private String name;
    private String email;
}
