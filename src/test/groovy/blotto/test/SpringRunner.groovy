package blotto.test

import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.InitializationError
import org.junit.runners.model.Statement
import org.spockframework.runtime.model.FeatureMetadata
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

class SpringRunner extends SpringJUnit4ClassRunner {
    SpringRunner(Class<?> clazz) throws InitializationError {
        super(clazz)
    }

    @Override
    protected Statement methodBlock(FrameworkMethod frameworkMethod) {
        System.out.println("Starting TEST: ${frameworkMethod.name} (${testName(frameworkMethod)})");
        return super.methodBlock(frameworkMethod);
    }

    @Override
    protected String testName(FrameworkMethod method) {
        def metadata = method.getAnnotation(FeatureMetadata)
        if (metadata) {
            return metadata.name()
        } else {
            return method.getName();
        }
    }
}
