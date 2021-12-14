package com.miracle.overwatch.server.controller;

import com.miracle.overwatch.common.api.R;
import com.miracle.overwatch.common.domain.StatusInfo;
import com.miracle.overwatch.server.mbg.mapper.StatusInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author qiukai
 * @Date 2021/11/4 11:02 上午
 */
@RestController
@RequestMapping("/status")
public class StatusInfoController {

    private final StatusInfoDao statusInfoDao;

    public StatusInfoController(StatusInfoDao statusInfoDao) {
        this.statusInfoDao = statusInfoDao;
    }

    @GetMapping("/{name}")
    public R getStatus(@PathVariable("name") String name){
        final List<StatusInfo> collect = statusInfoDao.selectList(null)
                .stream()
                .filter(statusInfo -> statusInfo.getName().equals(name))
                .collect(Collectors.toList());
        return R.ok().put("data", collect);
    }
}
