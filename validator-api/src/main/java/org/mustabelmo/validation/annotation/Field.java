package org.mustabelmo.validation.annotation;

public @interface Field {
	int begin() default 0;
	int end() default 0;
}
