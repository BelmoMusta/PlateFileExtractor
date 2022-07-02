package org.mustabelmo.plate.file.classes.creator.api.annotation;

public @interface Field {
    String name() default "";
	int begin() default 0;
	int end() default 0;
}
