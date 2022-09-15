<img src="https://user-images.githubusercontent.com/74607521/190224554-bbe07219-e82c-4a18-933f-fdccb87890d2.png" width="100%"/>

<p align="left">
<a href="https://jitpack.io/#shruddms/Compose-SwipeView"><img alt="Maven" src="https://jitpack.io/v/shruddms/Compose-SwipeView.svg"/></a>
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>

Compose-SwipeView is a scalable swipe view library made with Jetpack compose.

#### 🤔 How to use it?

1. Add the codes below to your **root** `build.gradle` file (not your module-level build.gradle file):
```gradle
allprojects {
    repositories {
       maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency below to your **module**'s `build.gradle` file:
```gradle
dependencies {
     implementation 'com.github.shruddms:Compose-SwipeView:X.X.X'
}
```

3. You can use simply by using function as the following example below:
```kotlin
private lateinit var imageList: ArrayList<Any>
private var infoContainerMaxHeight = 600.dp
private var infoContainerMinHeight = 120.dp
```

```kotlin
ExpandSwipeView(
    imageList = imageList,
    imageScale = ContentScale.Crop,
    modifier = Modifier,
    infoSheetState = rememberSwipeableState(SheetState.Open),
    infoContainerMaxHeight = infoContainerMaxHeight,
    infoContainerMinHeight = infoContainerMinHeight,
    contentBackgroundColor = MaterialTheme.colors.primary,
    contents = { content() }
)
```

```kotlin
@Composable
fun content() {
    Text(
        text = "Hello Word!",
        color = MaterialTheme.colors.onPrimary,
        style = MaterialTheme.typography.subtitle1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(20.dp)
    )
}
```
<br/>

# Demo projects 🚀
This project using Compose-SwipeView library.<br/>
Surfing dummy data was used.

# Preview ✨
#### * Swipe to expand
![](https://user-images.githubusercontent.com/74607521/189238678-a284d15b-26ac-40be-8234-3ae8010ed5f9.gif)

#### 🌞 Light Mode
<img src="https://user-images.githubusercontent.com/74607521/189238704-b87051e2-1a20-44fc-bbdd-aa4ca87be308.png" width="260"/>

#### 🌙 Dark Mode
<img src="https://user-images.githubusercontent.com/74607521/189238696-aac3ccea-1c40-43d9-9cca-f976a5527bbc.png" width="260"/>

Developed By
------------------------------------
* NoKyungEun - <eunn.dev@gmail.com>

License
------------------------------------
    Copyright 2022 NoKyungEun

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
