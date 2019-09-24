# Vaadin Flow and CDI, e.g. to craft a Vaadin 14.0.x WildFly compat fix.


Run application using
```
mvn clean wildfly:run
```
Open [http://localhost:8080/ceedeeeye-1.0-SNAPSHOT/](http://localhost:8080/ceedeeeye-1.0-SNAPSHOT/) in browser

If it does not work, this must be a bug in Vaadin 14.



Run application using
```
mvn clean tomee:run
```
Open [http://localhost:8080](http://localhost:8080) in browser

Which should work. You should see a cyan background - this proves CSS is included correctly.


