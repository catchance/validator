package org.chance.validation.constraints.sign;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

/**
 * UserBean
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-17 16:54:01
 */
//@Sign(value = Md5SignValidation.class,message="Md5验证失败")
public class UserBean extends SignBean {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @Range(min = 20, max = 120, message = "年龄在20到120岁之间")
    private int age;


}
