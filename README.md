# validator简介
java校验包集合，在hibernate-validator的基础上面进行扩展的

# 已经存在的校验器
## validation-api-2.0.0.Final.jar
| Constraint                    | 详细信息                                                                         |
| ----------------------------- | ------------------------------------------------------------------------------------ |
| @AssertFalse                  | 该值必须为False                                                                 |
| @AssertTrue                   | 该值必须为True                                                                  |
| @DecimalMax(value，inclusive) | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 ，inclusive表示是否包含该值 |
| @DecimalMin(value，inclusive) | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值 ，inclusive表示是否包含该值 |
| @Digits                       | 限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction |
| @Email                        | 该值必须为邮箱格式                                                          |
| @Future                       | 被注释的元素必须是一个将来的日期                                     |
| @FutureOrPresent              | 被注释的元素必须是一个现在或将来的日期                            |
| @Max(value)                   | 被注释的元素必须是一个数字，其值必须小于等于指定的最大值 |
| @Min(value)                   | 被注释的元素必须是一个数字，其值必须大于等于指定的最小值 |
| @Negative                     | 该值必须小于0                                                                  |
| @NegativeOrZero               | 该值必须小于等于0                                                            |
| @NotBlank                     | 该值不为空字符串，例如“     ”                                    |
| @NotEmpty                     | 该值不为空字符串                                                             |
| @NotNull                      | 该值不为Null                                                                     |
| @Null                         | 该值必须为Null                                                                  |
| @Past                         | 被注释的元素必须是一个过去的日期                                     |
| @PastOrPresent                | 被注释的元素必须是一个过去或现在的日期                            |
| @Pattern(regexp)              | 匹配正则                                                                         |
| @Positive                     | 该值必须大于0                                                                  |
| @PositiveOrZero               | 该值必须大于等于0                                                            |
| @Size(min,max)                | 数组大小必须在[min,max]这个区间                                           |

## hibernate-validator-6.0.2Final.jar
| Constraint                                                                                                                     | 详细信息                                                                       |
| ------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------- |
| @CNPJ                                                                                                                          | CNPJ是巴西联邦税务局秘书处向巴西公司发放的身份证号码，这个注解校验的就是该号码 |
| @CreditCardNumber(ignoreNonDigitCharacters=)                                                                                   | 被注释的字符串必须通过 Luhn 校验算法，银行卡，信用卡等号码一般都用 Luhn 计算合法性 |
| @Currency(value=)                                                                                                              | 被注释的 javax.money.MonetaryAmount 货币元素是否合规                   |
| @DurationMax(days=, hours=, minutes=, seconds=, millis=, nanos=, inclusive=)                                                   | 被注释的元素不能大于指定日期                                         |
| @DurationMin(days=, hours=, minutes=, seconds=, millis=, nanos=, inclusive=)                                                   | 被注释的元素不能低于指定日期                                         |
| @EAN                                                                                                                           | 被注释的元素是否是一个有效的 EAN 条形码                           |
| @Length(min=, max=)                                                                                                            | 被注释的字符串的大小必须在指定的范围内                          |
| @LuhnCheck(startIndex= , endIndex=, checkDigitIndex=, ignoreNonDigitCharacters=)                                               | Luhn 算法校验字符串中指定的部分                                       |
| @Mod10Check(multiplier=, weight=, startIndex=, endIndex=, checkDigitIndex=, ignoreNonDigitCharacters=)                         | Mod10 算法校验                                                                 |
| @Mod11Check(threshold=, startIndex=, endIndex=, checkDigitIndex=, ignoreNonDigitCharacters=, treatCheck10As=, treatCheck11As=) | Mod11 算法校验                                                                 |
| @Range(min=, max=)                                                                                                             | 被注释的元素必须在合适的范围内                                      |
| @SafeHtml(whitelistType= , additionalTags=, additionalTagsWithAttributes=, baseURI=)                                           | classpath中要有jsoup包，校验是否为安全html，即无注入信息        |
| @ScriptAssert(lang=, script=, alias=, reportOn=)                                                                               | 检查脚本是否可运行                                                        |
| @URL(protocol=, host=, port=, regexp=, flags=)                                                                                 | 被注释的字符串必须是一个有效的url                                   |

## 自定义
| Constraint                    | 详细信息                        |
| ----------------------------- | -----------------------------  |
| @EnumValidation               | 枚举校验器                       |
| @Contains                     | 枚举校验器                       |

# 主要功能
## v0.1.0 
校验框架，基于`hibernate-validator`框架的扩展

# 使用说明
## spring中数据校验的两种表现形式
### spring基于方法级别的校验
基于方法级别的校验Spring默认是并未开启的，需要再类上添加@Validated注解才行。该校验不禁适用于controller,它还能对Service层、Dao层的校验,该校验抛出的异常是javax.validation.ConstraintViolationException，
有一个非常值得注意但是很多人都会忽略的地方：因为我们希望能够代理Controller这个Bean，所以仅仅只在父容器中配置MethodValidationPostProcessor是无效的，必须在子容器（web容器）的配置文件中再配置一个MethodValidationPostProcessor，（SpringBoot有做处理）
### springMvc数据绑定校验(只能校验JavaBean)
Controller提供的使用@Valid便捷校验JavaBean的原理，和Spring方法级别的校验支持的原理是有很大差异的（可类比Spring MVC拦截器和Spring AOP的差异区别），Spring MVC在处理入参的时候，通过入参处理器HandlerMethodArgumentResolver，处理@RequestBody最终使用的实现类是：RequestResponseBodyMethodProcessor（既是个处理返回值的HandlerMethodReturnValueHandler，有是一个处理入参的HandlerMethodArgumentResolver），Spring借助此处理器完成一系列的消息转换器、数据绑定（DataBinder）、数据校验等工作

## spring boot 自动注入说明
- ValidationAutoConfiguration

## @Valid & @Validated
- @Valid 是使用hibernate validation的时候使用
-  是只用spring  Validator 校验机制使用
- @Valid是JSR-303标准规定的；@Validated是Spring校验框架提供的
- @Valid可以在方法/成员变量/构造函数/方法参数上使用；@Validated可以在类/方法/方法参数上使用；区别在于成员变量上是否可以使用
- 由于@Valid可以在成员变量上使用，因此可以嵌套校验
- @Valid会把校验不通过的信息交给BindingResult中，因此在controller的方法中，@Valid和BindingResult要同时存在
- @Validated可以在类上使用，可以配合MethodValidationPostProcessor实现校验基本数据包装类型和String类型等单独的对象

## BindException、MethodArgumentNotValidException、ConstraintViolationException
- 基于方法级别的校验会触发ConstraintViolationException异常
- 通过body传参,@RequestBody接收的校验会触发MethodArgumentNotValidException异常 
- query传参，使用JaveBean接收会触发BindException异常

## LocalValidatorFactoryBean & MethodValidationPostProcessor
ps: spring starter 会通过ValidationAutoConfiguration自动注入这连个bean
- LocalValidatorFactoryBean同时实现了Spring的Validator和JSR-303的Validator接口
- LocalValidatorFactoryBean可以完成i18n
- MethodValidationPostProcessor可以实现在方法上校验基本数据包装类型和String类型等单独的对象，但是要在异常处理器中处理ConstraintViolationException异常
两个Bean可以同时存在

## 引入依赖
### gradle
`implementation 'org.chance:validator:0.1.0'`
### maven
```xml
        <dependency>
            <groupId>org.chance</groupId>
            <artifactId>validator</artifactId>
            <version>0.1.0</version>
        </dependency>
```

## 使用示例
### 业务中使用
```java
package org.chance.simple.controller;

import org.chance.simple.enums.StatusEnum;
import org.chance.simple.vo.req.IndexReqVO;
import org.chance.simple.vo.resp.IndexRespVO;
import org.chance.validation.constraints.enums.EnumValidation;
import org.chance.validation.util.ValidatorUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-19 14:52:32
 */
@RestController
@RequestMapping("/validator")
@Validated(Default.class)
public class ValidatorController {

    /**
     * spring基于方法级别的校验，使用aop 需要@Validated注解
     *
     * @param status
     * @return
     */
    @GetMapping("/{status}")
    public IndexRespVO path(@Valid @NotNull @EnumValidation(target = StatusEnum.class, field = "code") @PathVariable("status") Integer status) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * spring基于方法级别的校验，使用aop 需要@Validated注解
     *
     * @param status
     * @return
     */
    @GetMapping
    public IndexRespVO index(@Valid @NotNull @EnumValidation(target = StatusEnum.class, field = "code") Integer status) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * springMvc数据绑定校验(只能校验JavaBean)不需要@Validated注解
     *
     * @param indexReqVO
     * @return
     */
    @GetMapping("/query")
    public IndexRespVO query(@Valid IndexReqVO indexReqVO) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * 表单传参
     * <p>
     * springMvc数据绑定校验(只能校验JavaBean)不需要@Validated注解
     *
     * @param indexReqVO
     * @return
     */
    @PostMapping("/form")
    public IndexRespVO form(@Valid IndexReqVO indexReqVO) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * @param indexReqVO
     * @return
     * @RequestBody注解接收参数 <p>
     * springMvc数据绑定校验(只能校验JavaBean)不需要@Validated注解
     */
    @PostMapping
    public IndexRespVO index(@Valid @RequestBody IndexReqVO indexReqVO) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * 直接使用工具类进行校验
     *
     * @param indexReqVO
     * @return
     */
    @PostMapping("/manual")
    public IndexRespVO manual(@RequestBody IndexReqVO indexReqVO) {
        ValidatorUtils.check(indexReqVO);
        return IndexRespVO.builder().info("success").build();
    }
}
```

# 示例
- [springboot-springmvc-mybatis](https://github.com/chance-study/helloworld/tree/master/simples/springboot-springmvc-mybatis)

# Reference
- [Spring数据校验（LocalValidatorFactoryBean和MethodValidationPostProcessor的区别/@Valid和@Validated的区别）](https://blog.csdn.net/qq_30038111/article/details/104524123)
- [Spring数据校验（LocalValidatorFactoryBean和MethodValidationPostProcessor的区别/@Valid和@Validated的区别）](https://blog.csdn.net/qq_30038111/article/details/104524123)
- [Spring方法级别数据校验：@Validated + MethodValidationPostProcessor优雅的完成数据校验动作](https://blog.csdn.net/f641385712/article/details/97402946)
- [@Validated和@Valid的区别？教你使用它完成Controller参数校验（含级联属性校验）以及原理分析](https://blog.csdn.net/f641385712/article/details/97621783)
- [Spring Boot 自动配置 : ValidationAutoConfiguration](https://blog.csdn.net/andy_zhang2007/article/details/96484918)

# License
Validator is Open Source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).
