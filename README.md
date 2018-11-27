# Different Java utility

[![Build Status](https://travis-ci.com/antkorwin/common-utils.svg?branch=master)](https://travis-ci.com/antkorwin/common-utils)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.antkorwin/common-utils/badge.svg)](https://search.maven.org/search?q=g:com.antkorwin%20AND%20a:common-utils)

This is a utility for simplifying the writing of tests. 

## Getting started

Dependency:
```xml
<dependency>
  <groupId>com.antkorwin</groupId>
  <artifactId>common-utils</artifactId>
  <version>1.0</version>
</dependency>
```


## Guard

Guard helps you, when you need to check a some boolean condition and expect a throws Exception if condition is false.

```java
Guard.check( 02 + 010 != 12, CruelJavaWorldException.class, "ooops");

Guard.check( object != null, WrongArgumentException.class, "Object must be not null");
```

## Guard check

You can use this tool while writing a test, 
to make tests for a checking throw of the exception more readable. 

```java
@Test
public void guardWithoutErrorInfo() {

    GuardCheck.check(() -> { throw new IndexOutOfBoundsException(); },
                     IndexOutOfBoundsException.class);
}


@Test
public void testGuardCheck() {

    GuardCheck.check(() -> {
                         throw new NotFoundException(TestErrorInfo.TEST_ERROR);
                     },
                     NotFoundException.class,
                     TestErrorInfo.TEST_ERROR);
}
```

### ErrorInfo

You can use it with an ErrorInfo to accumulate all you domain specific exceptions in an one enum-file:

```java
@ErrorInfoUnique
public enum TestErrorInfo implements ErrorInfo {
    TEST_ERROR("error"),
    TEST_ERROR_ARG("wrong arg");

    private static final int BASE = 1000;
    private final String msg;

    TestErrorInfo(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

    @Override
    public Integer getCode() {
        return BASE + ordinal();
    }
}
```

and sample of a test case :


```java
Guard.check(a != b, TestErrorInfo.TEST_ERROR);
```


### Collision Detector for ErrorInfos

In order to ensure the uniqueness of codes in a whole project, 
you can use enums which implements an ErrorInfo interface.

```java
@Test
public void assertInPackage() {   
    // Act & assert
    ErrorInfoCollisionDetector.assertInPackage("com.demo.project");
}
```

## Utils to work with Garbage Collector

### GcUtils

You can use this tool to run the garbage collector and finalize unused memory 
in your junit tests.

```java
@Test
public void testWeakAfterGC() {
    // Arrange
    String instance = new String("123");
    WeakReference<String> ref = new WeakReference<>(instance);

    // Act
    instance = null;
    GcUtils.fullFinalization();

    // Asserts
    Assertions.assertThat(ref.get()).isNull();
}
```  

### Simple memory LeakDetector

You can check a memory leak in your code for a particular object.

```java
@Test
    public void testWithLeak() {
        // Arrange
        Foo foo = new Foo();
        Foo bar = foo;
        LeakDetector leakDetector = new LeakDetector(foo);

        // Act
        foo = null;

        // Asserts
        leakDetector.assertMemoryLeaksExist();
    }
```
  