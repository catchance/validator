package org.chance.validation.constraints.enums;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Contains.List.class)
@Constraint(validatedBy = {ContainsValidator.class})
public @interface Contains {

    String message() default "{org.chance.validation.constraints.enums.Contains.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     *
     * @return
     */
    Class<? extends Enum> target();

    /**
     * the method's name ,which used to validate the enum's value
     *
     * @return method's name
     */
    String field() default "ordinal";

    /**
     * Defines several {@link Contains} annotations on the same element.
     *
     * @see Contains
     */
    @Documented
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @interface List {
        Contains[] value();
    }
}
