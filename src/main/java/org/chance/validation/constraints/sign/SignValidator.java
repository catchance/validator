package org.chance.validation.constraints.sign;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Controller入参对象中属性枚举项校验
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-17 16:53:43
 */
public class SignValidator implements ConstraintValidator<Sign, SignBean> {

    private SignValidation signValidation;


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
    public void initialize(Sign constraintAnnotation) {
        try {
            signValidation =  constraintAnnotation.value().newInstance();
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }finally{
            if (signValidation == null) {
                signValidation = new DefaultSignValidation();
            }
        }
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
    public boolean isValid(SignBean value, ConstraintValidatorContext context) {
        return signValidation.validation(value);
    }
}
