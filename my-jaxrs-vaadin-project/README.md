# Project Base for Vaadin, CDI and REST with JAX-RS

This project can be used as a starting point to create your own Vaadin Flow application with CDI,
which hosts a REST server (JAX-RS) as well at the very same time.

Run application using
```
mvn clean package wildfly:run
```

Open [http://localhost:8080/my-starter-project-1.0-SNAPSHOT/](http://localhost:8080/my-starter-project-1.0-SNAPSHOT/) in browser.

Also try 
curl http://localhost:8080/my-starter-project-1.0-SNAPSHOT/library/api/1/registered-modules

Or open [http://localhost:8080/my-starter-project-1.0-SNAPSHOT/library/api/1/registered-modules](http://localhost:8080/my-starter-project-1.0-SNAPSHOT/library/api/1/registered-modules) in browser.



For documentation on using Vaadin Flow and CDI, visit [vaadin.com/docs](https://vaadin.com/docs/v14/flow/cdi/tutorial-cdi-basic.html)

For more information on Vaadin Flow, visit https://vaadin.com/flow.

