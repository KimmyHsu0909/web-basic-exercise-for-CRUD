package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session"; // 正确
    ;
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    /**
     * 微信登陆
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {

        String openId = getOpenId(userLoginDTO.getCode());
        //判断openid是否为空，如果为空则登陆失败，抛出业务异常
        if (openId == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断用户是否为新用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openId);
        User user = userMapper.selectOne(queryWrapper);

        //如果是新用户，自动注册
        if (user == null) {
            user = User.builder()
                          .createTime(LocalDateTime.now())
                          .openid(openId)
                                  .build();
            userMapper.insert(user);
        }

        //返回用户对象
        return user;
    }

    /**
     * 获取openid
     * @param code
     * @return
     */
    private String getOpenId(String code) {
        //调用微信接口服务，获取当前用户openid
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String result = HttpClientUtil.doGet(WX_LOGIN_URL, map);

        JSONObject jsonObject = JSONObject.parseObject(result);
        String openId = jsonObject.getString("openid");

        return openId;
    }
}
