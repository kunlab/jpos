# jpos

## env
JDK 1.8+


## gradle配置

repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.kunlab:jpos:0.0.1'
}


## maven配置
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.kunlab</groupId>
    <artifactId>jpos</artifactId>
    <version>0.0.1</version>
</dependency>
