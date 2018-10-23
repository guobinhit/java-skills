package com.hit.concurrent.chapter1;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author:Charies Gavin
 * date:2018/10/21,17:16
 * https:github.com/guobinhit
 * description:用现有的原子类来构建线程安全类
 */
public class CountingFactorizer implements Servlet {
    private final AtomicLong count = new AtomicLong(0);

    public long getCount() {
        return count.get();
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = factor(i);
        count.incrementAndGet();
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
