https://grpc.io/docs/tutorials/basic/java.html

引入依赖和插件：https://github.com/grpc/grpc-java/blob/master/README.md
https://github.com/google/protobuf-gradle-plugin

跟java同级目录下创建proto文件夹，里面放置proto文件
执行gradlew generateProto即可自动生成java代码，代码放置在./build/generated里


打包：https://blog.csdn.net/ymaini/article/details/81248705


如果发现引用不了生成的代码，那么需要在build.gradle配置如下：
// Inform IDEs like IntelliJ IDEA, Eclipse or NetBeans about the generated code.
sourceSets {
	main {
		java {
			srcDirs 'build/generated/source/proto/main/grpc'
			srcDirs 'build/generated/source/proto/main/java'
		}
	}
}