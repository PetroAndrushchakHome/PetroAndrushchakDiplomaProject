package com.nulp.rock.ui.components;

import com.nulp.rock.annotation.Description;
import com.nulp.rock.annotation.Page;
import com.nulp.rock.common.Logger;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.List;

public class GoogleFieldDecorator extends DefaultFieldDecorator {

    public GoogleFieldDecorator(final SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }
        if (AbstractPageElement.class.isAssignableFrom(field.getType())) {
            try {

                WebElement proxy = proxyForLocator(loader, locator);
                return field.getType().getConstructor(WebElement.class, String.class,
                        String.class).newInstance(proxy, getName(field),
                        getPage(field));
            } catch (Exception e) {
                Logger.logError("WebElement can't be represented as "
                        + field.getType());
                return null;
            }
        } else if (List.class.isAssignableFrom(field.getType())) {
            ParameterizedType listType = (ParameterizedType) field
                    .getGenericType();
            return proxyForEasWebListLocator(
                    (Class<? extends AbstractPageElement>) listType
                            .getActualTypeArguments()[0],
                    loader, locator, field.getName(), getPage(field));
        } else
            return super.decorate(loader, field);
    }

    protected List<?> proxyForEasWebListLocator(
            Class<? extends AbstractPageElement> type, ClassLoader loader,
            ElementLocator locator, String name, String page) {
        InvocationHandler handler = new LocatingGoogleListHandler(type,
                locator, name, page);
        List<? extends AbstractPageElement> proxy;
        proxy = (List<? extends AbstractPageElement>) Proxy.newProxyInstance(
                loader, new Class[]{List.class}, handler);

        return proxy;
    }

    private String getName(Field field) {
        return field.isAnnotationPresent(Description.class) ? field
                .getAnnotation(Description.class).name() : field.getName();
    }

    private String getPage(Field field) {
        return field.getDeclaringClass().isAnnotationPresent(Page.class) ? field
                .getDeclaringClass().getAnnotation(Page.class).title() : "PAGE NOT DEFINED!!!";
    }
}
