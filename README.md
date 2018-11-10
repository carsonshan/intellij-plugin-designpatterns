# intellij-plugin-designpatterns
Java Design Patterns plugin for Intellij IDEA

At this time, only Builder pattern is available with 2 types of builder:
- classic builder
- builder with interfaces

## Quickstart

Create a class with members, in this example:

```java
public class Employee {
    private String socialSecurityNumber;    // required
    private String firstName;               // required
    private String lastName;                // required
    private LocalDate birthday;             // optional
    private int salary;                     // optional

}
```

Set your cursor inside the class where you want to generate the builder code, then right click and open 'Generate' menu:

![alt text](https://github.com/sbouclier/intellij-plugin-designpatterns/blob/master/public/images/generate.png?raw=true "Generate")

Click on Builder menu, this will open a dialog box. You have choice to generate two types of builder:

### Classic builder

![alt text](https://github.com/sbouclier/intellij-plugin-designpatterns/blob/master/public/images/classic_builder.png?raw=true "Classic builder")

First, select which class members you want to include in constructor builder. You can also override parameter methods by using prefix. Then select members you want to include and click on OK button, this will generate the builder code.
You can now build your class like this:

```java
Employee johnDoe = Employee.builder("123")
    .withFirstName("John")
    .withLastName("Doe")
    .withBirthday(LocalDate.of(1970, 1, 1))
    .withSalary(1000)
    .build();
```

### Builder with interfaces

![alt text](https://github.com/sbouclier/intellij-plugin-designpatterns/blob/master/public/images/builder_with_interfaces.png?raw=true "Builder with interfaces")

Same steps as classic builder except you can now select mandatory/optional parameters. You can only select constructor parameters for mandatory parameters.
You can build your class like this (as classic builder):

```java
Employee johnDoe = Employee.builder("123")
    .withFirstName("John")
    .withLastName("Doe")
    .withBirthday(LocalDate.of(1970, 1, 1))
    .withSalary(1000)
    .build();
```

But using auto completion, Intellij will provide you next valid parameter.

Next mandatory parameter:

![alt text](https://github.com/sbouclier/intellij-plugin-designpatterns/blob/master/public/images/mandatory_parameter_completion.png?raw=true "Auto completion of next mandatory parameter")

Next optional parameter(s):

![alt text](https://github.com/sbouclier/intellij-plugin-designpatterns/blob/master/public/images/optional_parameter_completion.png?raw=true "Auto completion of next optional parameter(s)")
