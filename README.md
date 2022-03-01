
# Project Title

A simple application to try out messaging concepts using Kafka and Tibco EMS


## Running Tests

To run tests, run the following command

```bash
  mvn clean test
```


## Support

For support, raise an issue in github under this project

Install the tibco ems jar manually from product to your repo 
e.g.
```shell
mvn install:install-file -Dfile=D:\tibco\ems\10.1\lib\tibjms.jar -DgroupId=com.tibco.tibjms -DartifactId=tibjms -Dversion=10.1 -Dpackaging=jar
```
```shell
# wsl2
mvn install:install-file -Dfile=/mnt/d/tibco/ems/10.1/lib/tibjms.jar -DgroupId=com.tibco.tibjms -DartifactId=tibjms -Dversion=10.1 -Dpackaging=jar
```

## Run Locally

Clone the project

```bash
  git clone https://github.com/rixonmathew/messaging-tryouts.git 
```

Go to the project directory

```bash
  cd messaging-tryouts
```

Create the package

```bash
  mvn clean install
```

Start the server

```bash
  mvn spring-boot:run
```

### Running from ide

Add the below flags to view the actuator endpoints 
```shell
-Dcom.sun.management.jmxremote.port=19898
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
```

## Roadmap

- Support with ksql and other features with Kafka

-

- Add more integrations


## Optimizations

What optimizations did you make in your code? E.g. refactors, performance improvements, accessibility


## License

[MIT](https://choosealicense.com/licenses/mit/)


## Environment Dependencies

To run this project, you will need kafka and tibco ems running locally.


## Authors

- [@rixonmathew](https://www.github.com/rixonmathew)
