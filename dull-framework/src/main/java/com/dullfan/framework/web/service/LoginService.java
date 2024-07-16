package com.dullfan.framework.web.service;

import com.dullfan.common.constant.CacheConstants;
import com.dullfan.common.constant.UserConstants;
import com.dullfan.common.core.redis.RedisCache;
import com.dullfan.common.entity.po.LoginBody;
import com.dullfan.common.entity.po.LoginUser;
import com.dullfan.common.entity.po.User;
import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.DateUtils;
import com.dullfan.common.utils.StringUtils;
import com.dullfan.common.utils.ip.IpUtils;
import com.dullfan.framework.security.context.AuthenticationContextHolder;
import com.dullfan.system.service.ConfigService;
import com.dullfan.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 登录校验方法
 */
@Component
public class LoginService {
    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserService userService;

    @Resource
    private ConfigService configService;

    /**
     * 登录验证
     *
     * @param loginBody 用户登录信息
     * @return 结果
     */
    public String login(LoginBody loginBody) {
        // 验证码校验
        validateCaptcha(loginBody.getCode(), loginBody.getUuid());
        // 登录前置校验
        loginPreCheck(loginBody.getUsername(), loginBody.getPassword());
        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword());
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new ServiceException("账号或密码错误");
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = tokenService.createToken(loginUser);

        recordLoginInfo(loginUser.getUserId());
        return token;
    }

    /**
     * 登录校验条件
     * @param username 用户名
     * @param password 密码
     */
    private void loginPreCheck(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ServiceException("用户名或密码错误");
        }
        // 密码如果不在指定范围内错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new ServiceException(StringUtils.format("密码长度在{}到{}之间", UserConstants.PASSWORD_MIN_LENGTH, UserConstants.PASSWORD_MAX_LENGTH));
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new ServiceException(StringUtils.format("用户名长度在{}到{}之间", UserConstants.USERNAME_MIN_LENGTH, UserConstants.USERNAME_MAX_LENGTH));
        }
    }

    /**
     * 校验验证码
     *
     * @param code     验证码
     * @param uuid     唯一标识
     */
    private void validateCaptcha(String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null) {
                throw new ServiceException("验证码已过期");
            }
            if (!code.equalsIgnoreCase(captcha)) {
                throw new ServiceException("验证码异常");
            }
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setLoginIp(IpUtils.getIpAddr());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUser(user);

    }
}
