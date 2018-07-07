# Different Java utility

[![Build Status](https://travis-ci.com/antkorwin/common-utils.svg?branch=master)](https://travis-ci.com/antkorwin/common-utils)

This is a utility for simplifying the writing of tests. 

## Getting started

To use this tools, you need to add the next repository:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

and dependency:
```xml
<dependency>
    <groupId>com.github.antkorwin</groupId>
    <artifactId>common-utils</artifactId>
    <version>0.6</version>
</dependency>
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
  