package com.dynamiccodegenerator.codeandtablegenerator.service;

import java.lang.reflect.Modifier;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class DynamicEntityCreationExample {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<?> dynamicClass = new ByteBuddy()
                .subclass(Object.class)
                .name("Person")
                .annotateType(AnnotationDescription.Builder.ofType(Entity.class).build())
                .defineField("id", Long.class, Modifier.PRIVATE)
                .annotateField(AnnotationDescription.Builder.ofType(Id.class).build())
                .defineField("name", String.class, Modifier.PRIVATE)
                .defineField("email", String.class, Modifier.PRIVATE)
                .defineField("address", String.class, Modifier.PRIVATE)
                .defineMethod("getId", Long.class, Modifier.PUBLIC)
                .intercept(FieldAccessor.ofBeanProperty())
                .defineMethod("getName", String.class, Modifier.PUBLIC)
                .intercept(FieldAccessor.ofBeanProperty())
                .defineMethod("getEmail", String.class, Modifier.PUBLIC)
                .intercept(FieldAccessor.ofBeanProperty())
                .defineMethod("getAddress", String.class, Modifier.PUBLIC)
                .intercept(FieldAccessor.ofBeanProperty())
                .method(ElementMatchers.named("setId"))
                .intercept(MethodDelegation.toField("id"))
                .method(ElementMatchers.named("setName"))
                .intercept(MethodDelegation.toField("name"))
                .method(ElementMatchers.named("setEmail"))
                .intercept(MethodDelegation.toField("email"))
                .method(ElementMatchers.named("setAddress"))
                .intercept(MethodDelegation.toField("address"))
                .make()
                .load(DynamicEntityCreationExample.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Object instance = dynamicClass.newInstance();
//        Person person = ( Person ) instance;
        System.out.println(dynamicClass.getName());
    }
}