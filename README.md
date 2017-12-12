# jBase16

#### Encode data

```java
Base16.encode("this is my data".getBytes()) //returns "74686973206973206D792064617461"
```

#### Verify data

```java
Base16.verifyStrict("74686973206973206D792064617461") // returns true
Base16.verifyStrict("74686973206973206d792064617461") // returns false
Base16.verifyStrict("74686973206973206W792064617461") // returns false
Base16.verifyStrict("74686973206973206D7920646174610") // returns false
Base16.verifyTolerant("74686973206973206d792064617461") // returns true
```

#### Decode data

```java
Base16.decode("74686973206973206D792064617461") // returns "this is my data".getBytes()
```



###### Intellij

This is a checkoutable Intellij project.