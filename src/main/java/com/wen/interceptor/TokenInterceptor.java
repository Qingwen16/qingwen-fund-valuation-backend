package com.wen.interceptor;

/**
 * @author : rjw
 * @date : 2026-03-20
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.service.AuthInfoService;
import com.wen.service.CacheService;
import com.wen.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Token 拦截器
 * 拦截所有需要登录的接口，校验 token 合法性及黑名单
 * @author jwruan
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    private final AuthInfoService authInfoService;

    private final CacheService cacheService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authHeader = request.getHeader("Authorization");

        // 未携带 token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            writeErrorResponse(response, 401, "未登录，请先登录");
            return false;
        }

        // 校验 token 合法性
        String token = authHeader.substring(7);
        String openId;

        try {
            jwtUtil.parseToken(token);
            openId = jwtUtil.getOpenid(token);
        } catch (Exception exception) {
            writeErrorResponse(response, 401, "token 无效或已过期");
            return false;
        }
        // 检查缓存token是否一致
        String userToken = cacheService.getUserToken(openId);
        if (!token.equals(userToken)) {
            log.info("请求头token [{}] 和缓存token [{}] 不一致", token, userToken);
            return false;
        }

        // 校验 token 是否在黑名单（已退出）
        if (cacheService.hasTokenBlacklist(token)) {
            log.info("Token 黑名单存在 token");
            writeErrorResponse(response, 401, "token 已失效，请重新登录");
            return false;
        }

        return true;
    }

    private void writeErrorResponse(HttpServletResponse response, int code, String message) throws Exception {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}