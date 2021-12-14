package com.miracle.overwatch.server.mbg.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author qiukai
 */
@Data
public class ServerInfo implements Serializable {
    @TableId
    private Integer serverId;

    private String host;

    private Integer port;

    private Date openDate;

    private String name;

    private String localAddress;

    private static final long serialVersionUID = 1L;

    public ServerInfo(String name, String host, int port, Date date, String localAddress) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.openDate = date;
        this.localAddress = localAddress;
    }
}