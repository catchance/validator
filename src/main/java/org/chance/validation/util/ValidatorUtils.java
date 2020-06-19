package org.chance.validation.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * ValidatorUtils
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-17 16:51:22
 */
public class ValidatorUtils {

    private ValidatorUtils() {
    }

    //校验工厂
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /*
     * java使用省略号代替多参数（参数类型... 参数名称）
     * */
    private static <T> Map<String, String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(t, groups);
        if (validate.isEmpty()) {
            return Collections.emptyMap();
        } else {
            /*
             * <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>18.0</version>
               </dependency>
             *
             *
             */
            LinkedHashMap errors = Maps.newLinkedHashMap();
            Iterator<ConstraintViolation<T>> iterator = validate.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation violation = iterator.next();
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
    }

    public static Map<String, String> validatelist(Collection<?> collection) {
        //google工具类
        Preconditions.checkNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        Map errors;
        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object next = iterator.next();
            errors = validate(next, new Class[0]);
        } while (errors.isEmpty());
        return errors;
    }

    public static Map<String, String> validateobject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validatelist(Lists.asList(first, objects));
        } else {
            return validate(first, new Class[0]);
        }
    }

    public static <T> void check(T t) throws ConstraintViolationException {

        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<T>> validate = validator.validate(t);

        if (validate.isEmpty()) {
            return;
        } else {
            throw new ConstraintViolationException(validate);
        }
    }

}
