package com.ilot.utils.socket;


import com.ilot.utils.CacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    private final CacheUtils cacheUtils;

    public CustomHandshakeHandler(CacheUtils cacheUtils) {
        this.cacheUtils = cacheUtils;
    }

   /* @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // Generate principal with UUID as name
        final String name = UUID.randomUUID().toString();

        // save principal to redis
        final String key = SondeStatsBusiness.buildPrincipalKey(name);
        cacheUtils.cacheData(key, StringUtils.EMPTY, 5);

        return new StompPrincipal(name);
    }*/
}