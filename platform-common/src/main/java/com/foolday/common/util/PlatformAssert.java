package com.foolday.common.util;

import com.foolday.common.exception.PlatformException;
import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

public abstract class PlatformAssert {
    public PlatformAssert() {
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new PlatformException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "表达式计算结果必须为真");
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new PlatformException(message);
        }
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, "表达式计算结果必须为False");
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new PlatformException(message);
        }
    }

    public static void isNull(Object object) {
        isNull(object, "对象必须为Null");
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new PlatformException(message);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "对象不允许为Null");
    }

    public static void hasText(String text, String message) {
        if (StringUtils.length(text) == 0) {
            throw new PlatformException(message);
        }
    }

    public static void hasText(String text) {
        hasText(text, "字符串不能为空");
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.length(textToSearch) > 0 && StringUtils.length(substring) > 0 && textToSearch.contains(substring)) {
            throw new PlatformException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "待搜索字符串不包含指定子字符串");
    }

    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new PlatformException(message);
        }
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "对象数组必须有元素");
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new PlatformException(message);
                }
            }
        }

    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "对象数组没有null元素");
    }

    public static void notEmpty(Collection<?> collection, String message) {

        if (Objects.equal(collection, null) || collection.isEmpty()) {
            throw new PlatformException(message);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "集合对象必须包含有元素");
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new PlatformException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "Map对象必须包含有元素");
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "检查类型不允许为Null");
        if (!type.isInstance(obj)) {
            throw new PlatformException(message);
        }
    }

    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "对象必须是指定类的实例");
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "检查类型不允许为Null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new PlatformException(message);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "SuperType必须是SubType的父类");
    }
}
