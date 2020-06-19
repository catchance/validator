package org.chance.validation.constraints.enums;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-17 16:50:15
 */
public class ContainsValidator implements ConstraintValidator<Contains, Collection<?>> {

    private Contains annotation;

    /**
     * Initializes the validator in preparation for
     * {@link ContainsValidator#isValid(Collection, ConstraintValidatorContext)} calls.
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
    public void initialize(Contains constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param values  object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Collection<?> values, ConstraintValidatorContext context) {

        if (values == null) {
            return true;
        }

        Object[] objects = annotation.target().getEnumConstants();

        List optional = new ArrayList();

        try {
            Field field = annotation.target().getDeclaredField(annotation.field());
            field.setAccessible(true);
            for (Object o : objects) {
                optional.add(field.get(o));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return optional.containsAll(values);
    }
}
