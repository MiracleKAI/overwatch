package com.miracle.overwatch.common.domain;


import com.miracle.overwatch.common.domain.protocol.MsgType;
import com.miracle.overwatch.common.domain.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfo extends Packet implements Serializable {

    private Integer clientId;

    private String clientChannelId;

    private String name;

    private String host;

    private Integer port;

    private String localAddress;

    private Date linkDate;

    private static final long serialVersionUID = 1L;

    @Override
    public Byte getType() {
        return MsgType.CLIENT_INFO;
    }

}