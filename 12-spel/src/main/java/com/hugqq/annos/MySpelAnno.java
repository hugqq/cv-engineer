package com.hugqq.annos;


import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MySpelAnno {
    String spelOne() default "";;

    String spelTwo() default "";;

}
