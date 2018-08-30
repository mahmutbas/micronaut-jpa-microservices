# Micronaut for Microservices

This application include micronaut project creation, using hibernate and also unit controller tests. 

<p align="left">
  <img src="/src/main/resources/images/micronaut.jpg" width="150" title="Micronaut">
       
  <img src="/src/main/resources/images/hibernate.png" width="140" title="Hibernate">
</p>

## Step By Step
1. We need to install Micronaut CLI. We will get SDKMAN to install Micronaut CLI. 

$ curl -s get.sdkman.io | bash
--open new terminal
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
$ sdk version
$ sdk install micronaut
 
 Going to micronaut CLI; 
 
 $ sudo mn
 
 Then we will create project with hibernate feature. 

$mn create-app micronaut-jpa --features=hibernate-jpa --build=maven
