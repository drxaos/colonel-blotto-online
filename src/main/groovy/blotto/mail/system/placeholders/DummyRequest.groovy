package blotto.mail.system.placeholders

import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes

import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import java.lang.reflect.Proxy

class DummyRequest extends HttpServletRequestWrapper {
    private static final HttpServletRequest UNSUPPORTED_REQUEST = (HttpServletRequest) Proxy.newProxyInstance(
            DummyRequest.class.getClassLoader(), [HttpServletRequest] as Class[], new UnsupportedOperationExceptionInvocationHandler());

    private String requestURI;
    private String contextPath = "";
    private String servletPath;
    private String pathInfo;
    private String queryString;
    private String method;

    def attrs = [:]
    def context

    public DummyRequest(pageScope, context) {
        super(UNSUPPORTED_REQUEST);
        this.attrs[GrailsApplicationAttributes.PAGE_SCOPE] = pageScope
        this.context = context
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Object getAttribute(String name) {
        return attrs[name];
    }

    public String getParameter(String name) {
        return null;
    }

    public void setAttribute(String name, Object o) {
        attrs[name] = o
    }

    public void removeAttribute(String name) {
        attrs.remove(name)
    }

    @Override
    ServletContext getServletContext() {
        return context
    }

    @Override
    Locale getLocale() {
        return Locale.default
    }
}