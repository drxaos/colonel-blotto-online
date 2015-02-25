package blotto.mail.impl

import javax.servlet.*
import javax.servlet.descriptor.JspConfigDescriptor

class DummyServletContext implements ServletContext {
    public DummyServletContext() {
    }

    @Override
    String getContextPath() {
        return null
    }

    @Override
    ServletContext getContext(String uripath) {
        return null
    }

    @Override
    int getMajorVersion() {
        return 0
    }

    @Override
    int getMinorVersion() {
        return 0
    }

    @Override
    int getEffectiveMajorVersion() {
        return 0
    }

    @Override
    int getEffectiveMinorVersion() {
        return 0
    }

    @Override
    String getMimeType(String file) {
        return null
    }

    @Override
    Set<String> getResourcePaths(String path) {
        return null
    }

    @Override
    URL getResource(String path) throws MalformedURLException {
        return null
    }

    @Override
    InputStream getResourceAsStream(String path) {
        return null
    }

    @Override
    RequestDispatcher getRequestDispatcher(String path) {
        return null
    }

    @Override
    RequestDispatcher getNamedDispatcher(String name) {
        return null
    }

    @Override
    Servlet getServlet(String name) throws ServletException {
        return null
    }

    @Override
    Enumeration<Servlet> getServlets() {
        return null
    }

    @Override
    Enumeration<String> getServletNames() {
        return null
    }

    @Override
    void log(String msg) {

    }

    @Override
    void log(Exception exception, String msg) {

    }

    @Override
    void log(String message, Throwable throwable) {

    }

    @Override
    String getRealPath(String path) {
        return null
    }

    @Override
    String getServerInfo() {
        return null
    }

    @Override
    String getInitParameter(String name) {
        return null
    }

    @Override
    Enumeration<String> getInitParameterNames() {
        return null
    }

    @Override
    boolean setInitParameter(String name, String value) {
        return false
    }

    @Override
    Object getAttribute(String name) {
        return null
    }

    @Override
    Enumeration<String> getAttributeNames() {
        return null
    }

    @Override
    void setAttribute(String name, Object object) {

    }

    @Override
    void removeAttribute(String name) {

    }

    @Override
    String getServletContextName() {
        return null
    }

    @Override
    ServletRegistration.Dynamic addServlet(String servletName, String className) {
        return null
    }

    @Override
    ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
        return null
    }

    @Override
    ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
        return null
    }

    @Override
    def <T extends Servlet> T createServlet(Class<T> c) throws ServletException {
        return null
    }

    @Override
    ServletRegistration getServletRegistration(String servletName) {
        return null
    }

    @Override
    Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return null
    }

    @Override
    FilterRegistration.Dynamic addFilter(String filterName, String className) {
        return null
    }

    @Override
    FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        return null
    }

    @Override
    FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
        return null
    }

    @Override
    def <T extends Filter> T createFilter(Class<T> c) throws ServletException {
        return null
    }

    @Override
    FilterRegistration getFilterRegistration(String filterName) {
        return null
    }

    @Override
    Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return null
    }

    @Override
    SessionCookieConfig getSessionCookieConfig() {
        return null
    }

    @Override
    void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {

    }

    @Override
    Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return null
    }

    @Override
    Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return null
    }

    @Override
    void addListener(String className) {

    }

    @Override
    def <T extends EventListener> void addListener(T t) {

    }

    @Override
    void addListener(Class<? extends EventListener> listenerClass) {

    }

    @Override
    def <T extends EventListener> T createListener(Class<T> c) throws ServletException {
        return null
    }

    @Override
    JspConfigDescriptor getJspConfigDescriptor() {
        return null
    }

    @Override
    ClassLoader getClassLoader() {
        return null
    }

    @Override
    void declareRoles(String... roleNames) {

    }

    @Override
    String getVirtualServerName() {
        return null
    }
}