### 笔记

#### 1、将外部JAR打包进入当前项目的MVN命令
```shell
mvn install:install-file -Dfile=E:\latrell\plugin\door-spring-boot-starter\target\door-spring-boot-starter-1.0-SNAPSHOT.jar -DgroupId=com.latrell -DartifactId=do
or-spring-boot-starter -Dversion=1.0-SNAPSHOT -Dpackaging=jar
```
`注：将外部JAR打入本地仓库命令 -Dfile 外部JAR绝对路径 -DgroupId -DartifactId -Dversion 对应JAR的信息`
