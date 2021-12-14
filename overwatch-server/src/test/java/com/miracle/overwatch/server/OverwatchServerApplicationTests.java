package com.miracle.overwatch.server;

import com.miracle.overwatch.common.domain.StatusInfo;
import com.miracle.overwatch.common.util.NetUtil;
import com.miracle.overwatch.server.mbg.mapper.StatusInfoDao;
import com.miracle.overwatch.server.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class OverwatchServerApplicationTests {

    @Test
    void contextLoads() {
        log.info("端口为:{}", NetUtil.getPort());
    }
    @Autowired
    private StatusInfoDao statusInfoDao;

    @Test
    public void testStatusInfo(){
        assert statusInfoDao != null;
        System.out.println(statusInfoDao.selectCount(null));
    }

}
