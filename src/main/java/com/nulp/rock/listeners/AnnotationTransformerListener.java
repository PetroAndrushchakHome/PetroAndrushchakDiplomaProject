package com.nulp.rock.listeners;

import com.nulp.rock.common.Config;
import com.nulp.rock.common.Logger;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformerListener implements IAnnotationTransformer {

    public void transform(ITestAnnotation arg0, Class arg1, Constructor arg2, Method arg3) {
        System.out.println("Inside Annotation transformer");
        Logger.logInfo("Inside Annotation transformer");
        if (Config.getProperty("skip.tests").contains(arg3.getDeclaringClass().getSimpleName())) {
            Logger.logInfo("Skip test case");
            arg0.setEnabled(false);
        } else {
            Logger.logInfo("Test is not skipped");
        }
    }
}
