package org.chance.validation.constraints.sign;

import javax.validation.constraints.NotEmpty;

/**
 * 签名对象 * * @author he * @since 1.0.0
 */
@Sign(message = "验签失败", value = DefaultSignValidation.class)
public class SignBean {

    @NotEmpty
    public String sign;

}
