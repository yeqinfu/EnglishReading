/*
 * Created by yeqinfu on 17-9-27 上午11:52
 * Copyright (c) JXT All rights reserved.
 */

package com.ppandroid.readenglish.utils;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NotProguard, Means not proguard something, like class, method, field<br/>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface NotProguard {
}
