package org.chance.validation.constraints.sign;

public interface SignValidation {

    /**
     * 验证
     * 对object对象的加签字段进行验证
     *
     * @param signBean
     * @return
     */
    boolean validation(SignBean signBean);
}
