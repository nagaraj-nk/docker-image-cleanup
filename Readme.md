# Build

## Step 1

Paste below code in **pom.xml** under project tag 

Change class name with fully qualified name
```
<build>
        <plugins>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.srjons.App</mainClass>
                        </manifest>
                    </archive>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id> <!-- this is used for inheritance merges -->
                        <phase>package</phase> <!-- bind to the packaging phase -->
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</pre>
```

## Step 2

```agsl
mvn clean install assembly:single
```

## Step 3 

Run application using below command

```agsl
java -jar app-name-jar-with-dependencies.jar
```

**Note**: Please run jar file which ends with **-jar-with-dependencies.jar**. Otherwise, it will not work