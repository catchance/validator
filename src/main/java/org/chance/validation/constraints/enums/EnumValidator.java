package org.chance.validation.constraints.enums;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller入参对象中属性枚举项校验
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-17 16:50:45
 */
public class EnumValidator implements ConstraintValidator<EnumValidation, Object> {

    private EnumValidation annotation;

    /**
     * Initializes the validator in preparation for
     * {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     * <p>
     * The default implementation is a no-op.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(EnumValidation constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        Object[] objects = annotation.target().getEnumConstants();
        try {
            Field field = annotation.target().getDeclaredField(annotation.field());
            field.setAccessible(true);
            for (Object o : objects) {
                if (value.equals(field.get(o))) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
        // 在addConstraintViolation之前把参数放进去，就可以创建出不同的ConstraintViolation了
        // 若不这么做，所有的ConstraintViolation取值都是一样的喽~~~
//        hibernateConstraintValidatorContext.addMessageParameter("foo", "bar");
//        hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate("{foo}")
//                .addConstraintViolation();
        String optionalValue = Stream.of(annotation.target().getEnumConstants()).map(Objects::toString).collect(Collectors.joining(","));
        hibernateConstraintValidatorContext.addMessageParameter("optionalValue", optionalValue);
        // 基本同上，只是上面处理的是{},这里处理的是${}  el表达式
//        HibernateConstraintValidatorContext addExpressionVariable(String name, Object value);
        //        hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate("{foo}")
//                .addConstraintViolation();

        return false;
    }
}
