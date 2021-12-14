package com.miracle.overwatch.server.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author QiuKai
 * @date 2021/10/5 4:44 下午
 */
@Data
public class OrderParam {

    private String channelId;
    private Map<String, String> order = new HashMap<>();
}
