package com.fu.demo.controller;

import com.fu.demo.aop.IgnoreResAnnotate;
import com.fu.demo.entity.*;
import com.fu.demo.mapper.LoginMapper;
import com.fu.demo.util.RSAUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Log4j2
@RestController
public class LoginController {
    @Value("${token-overtime}")
    private int tokenOvertime;
    @Resource
    RedisTemplate<String,Object> redisTemplate;
    @Resource
    LoginMapper loginMapper;

    @IgnoreResAnnotate //不反回Res
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    /**
     * 用户不在用户输入框的时候校验用户ID是否存在
     *
     * @param userId 用户ID
     * @return
     */
    @GetMapping("checkUserId")
    public User checkUserIdOrUserName(@RequestParam Long userId) {
        if (userId == null) throw new Err("用户ID不能为空");
        User user = loginMapper.checkUserId(userId);
        if (user == null) {
            throw new Err("用户不存在");
        } else {
            return user;
        }
    }

    /**
     * 模拟前端加密
     *
     * @param password  用户输入的密码
     * @param salt      返回给前端的UUID盐
     * @param publicKey 返回给前端的RSA公钥
     * @return
     * @throws Exception
     */
    @IgnoreResAnnotate
    @GetMapping("front")
    public String front(@RequestParam String password, @RequestParam String salt, @RequestParam String publicKey) throws Exception {
        String passwordAndSaltForMD5 = Base64.getEncoder().encodeToString(MessageDigest.getInstance("MD5").digest((password + salt).getBytes(StandardCharsets.UTF_8)));//原始密码+UUID盐生成MD5
        return RSAUtil.encrypt(passwordAndSaltForMD5, publicKey.replaceAll(" ", "+"));//（原始密码+UUID盐生成MD5）+publicKey公钥再加密
    }

    /**
     * 登录
     *
     * @param userId   用户ID
     * @param password （初始密码+saltMD5加密）+RSA公钥加密后的密码
     * @return
     */
    @PostMapping("login")
    public TokenInfo login(@RequestParam Long userId, @RequestParam String password){
        User user = loginMapper.checkUserId(userId);
        if (user == null) {
            throw new Err("用户不存在");
        } else if (!RSAUtil.decrypt(password.replaceAll(" ", "+"), user.getPrivateKey()).equals(user.getPassword())) {
            throw new Err("密码错误");
        } else {
            //把当前登录用户的信息存储到redis并返回给前端
            String token = UUID.randomUUID().toString();
            List<Role> roles = loginMapper.selectUserRoles(user.getId());//角色组
            TokenInfo tokenInfo = new TokenInfo(token,user.getId(),user.getName(),loginMapper.selectUserDepts(user.getId()),roles,roles.size() <= 0 ? null:loginMapper.selectUserMenus(roles));
            redisTemplate.opsForValue().set(token,tokenInfo,tokenOvertime, TimeUnit.SECONDS);//把当前登录用户的信息存储到redis
            return tokenInfo;
        }
    }

    /**
     * 登出
     * @param token
     * @return
     */
    @PostMapping("logout")
    public String logout(@RequestParam String token){
        if (StringUtils.isBlank(token)){
            throw new Err("token不能为空");
        }else if (redisTemplate.hasKey(token)){
            throw new Err("token不存在");
        }else {
            redisTemplate.delete(token);
            return "登出成功！";
        }
    }
}
