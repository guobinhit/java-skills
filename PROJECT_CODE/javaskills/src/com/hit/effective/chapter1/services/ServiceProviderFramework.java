package com.hit.effective.chapter1.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author:Charies Gavin
 * date:2018/5/23,21:05
 * https:github.com/guobinhit
 * description:服务提供者框架
 */
public class ServiceProviderFramework {
    // 私有化构造器，防止实例化
    private ServiceProviderFramework() {
    }

    private static final Map<String, Provider> providers = new ConcurrentHashMap<String, Provider>();

    private static final String DEFAULT_PROVIDER_NAME = "<def>";

    // 服务提供者的注册 API
    public static void registerDefaultProvider(Provider p) {
        providers.put(DEFAULT_PROVIDER_NAME, p);
    }

    public static void registerProvider(String name, Provider p) {
        providers.put(name, p);
    }

    public static Service newInstance() {
        return newInstance(DEFAULT_PROVIDER_NAME);
    }

    public static Service newInstance(String name) {
        Provider p = providers.get(name);
        if (p == null) {
            throw new IllegalArgumentException("No provider registerd with name: " + name);
        }
        return p.newService();
    }
}
