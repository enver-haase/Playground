
package com.infraleap.strutsapp;

import java.util.Random;

public class IndexAction {
    //private String name;

    private static Random random = new Random();

    public String execute() throws Exception {

        if (random.nextDouble() > 0.5) {

            return "success";
        } else {
            return "fail";
        }
    }

    //public String getName() {
    //    return name;
    //}

    //public void setName(String name) {
    //    this.name = name;
    //}
}
