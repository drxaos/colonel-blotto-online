package blotto.mail.system.placeholders

import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponseWrapper
import java.lang.reflect.Proxy

class DummyResponse extends HttpServletResponseWrapper {
    private static final HttpServletResponse UNSUPPORTED_RESPONSE = (HttpServletResponse) Proxy.newProxyInstance(
            DummyResponse.class.getClassLoader(), [HttpServletResponse] as Class[], new UnsupportedOperationExceptionInvocationHandler());

    public DummyResponse(PrintWriter writer) {
        super(UNSUPPORTED_RESPONSE);
        this.writer = writer
    }

    PrintWriter writer

    public PrintWriter getWriter() throws IOException {
        return this.writer
    }

}