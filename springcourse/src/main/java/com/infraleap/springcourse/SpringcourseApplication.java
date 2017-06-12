package com.infraleap.springcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Also called the "Runner".
 * Place the Configuration bean into the same package!!
 */

@SpringBootApplication
public class SpringcourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcourseApplication.class, args);
	}
}
