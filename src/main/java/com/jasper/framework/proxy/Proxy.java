package com.jasper.framework.proxy;

public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;

}
