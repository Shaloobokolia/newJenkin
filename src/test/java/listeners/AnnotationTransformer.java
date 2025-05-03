package listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    //For Every Test Method in the TestNG class, this method will be called
    //If the test case fails, then it will retry the test case for 3 times
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod)
    {
        annotation.setRetryAnalyzer(RetryAnalyser.class);
    }

}
