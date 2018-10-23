package com.hit.concurrent.chapter1;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * author:Charies Gavin
 * date:2018/10/21,16:35
 * https:github.com/guobinhit
 * description:无状态的类是线程安全类
 */
public class StatelessFactorizer implements Servlet {

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = factor(i);
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
