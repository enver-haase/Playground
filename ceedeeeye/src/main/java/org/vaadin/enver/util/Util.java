package org.vaadin.enver.util;

import java.util.Properties;

public class Util {
    public static void dumpSystemProperties(){
        Properties props = System.getProperties();
        for (Object o : props.keySet()) {
            String key = (String) o;
            System.out.println(key + " : " + props.getProperty(key));
        }
    }

    public static void trySleeping(long millis){
        try{
            Thread.sleep(millis);
        }
        catch (InterruptedException ignored){}
    }
}
