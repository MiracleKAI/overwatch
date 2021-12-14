package com.miracle.overwatch.server.dto;

import com.miracle.overwatch.common.domain.protocol.Task;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author qiukai
 * @Date 2021/11/3 11:13 下午
 */
@Data
@NoArgsConstructor
public class RegisterInfo {

    private int operateCode;
    private String url;
    private Map<String, Object> register = new HashMap<>();


    public RegisterInfo (int operateCode, String url, String method, String service, String theme, int priority, String commit) {
        this.operateCode = operateCode;
        this.url = url;
        this.register.put("Method", method);
        this.register.put("Service", service);
        this.register.put("Theme", theme);
        this.register.put("Priority", priority);
        this.register.put("Commit", commit);
    }

}
