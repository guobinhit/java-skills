package com.hit.concurrent.chapter1;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * author:Charies Gavin
 * date:2018/10/21,17:28
 * https:github.com/guobinhit
 * description:非线程安全类
 */
public class UnsafeCachingFactorizer implements Servlet {

    private final AtomicReference<BigInteger> lastNumber =
            new AtomicReference<BigInteger>();

    private final AtomicReference<BigInteger[]> lastFactors =
            new AtomicReference<BigInteger[]>();

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(servletResponse, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(servletResponse, factors);
        }
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
