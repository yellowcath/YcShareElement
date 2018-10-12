## Gradle

add Maven
``` groovy
 allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```
add implementation
``` groovy
dependencies {
    implementation 'com.github.yellowcath:YcShareElement:1.1'
}
```