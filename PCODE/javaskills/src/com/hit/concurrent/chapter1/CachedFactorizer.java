package com.hit.concurrent.chapter1;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * author:Charies Gavin
 * date:2018/10/21,18:52
 * https:github.com/guobinhit
 * description:线程安全类，更细的锁粒度
 */
public class CachedFactorizer implements Servlet {

    private BigInteger lastNumber;
    private BigInteger[] lastFactors;
    private long hits;
    private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized long getCacheHits() {
        return cacheHits;
    }

    @Override
    public synchronized void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(servletResponse, factors);
    }

    private BigInteger extractFromRequest(ServletRequest servletRequest) {
        return BigInteger.ZERO;
    }

    private BigInteger[] factor(BigInteger i) {
        BigInteger[] factors = {BigInteger.ZERO};
        return factors;
    }

    private void encodeIntoResponse(ServletResponse servletResponse, java.math.BigInteger[] factors) {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
}
