package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@Api("店铺状态相关接口")
@RestController("userShopController")
@Slf4j
public class ShopController {
    @Autowired
    RedisTemplate redisTemplate;

    private static final String KEY = "SHOP_STATUS";
    /**
     * 查询店铺状态
     * @return
     */
    @GetMapping("/shop")
    @ApiOperation("查询店铺状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("当前店铺状态为:{}",status == 1? "营业中" : "打烊中");
        return Result.success(status);
    }
}
