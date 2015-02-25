package blotto.mail.impl;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

final class UnsupportedOperationExceptionInvocationHandler implements InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        throw new UnsupportedOperationException(method + " is not supported");
    }
}