# PopularMovies
**Version 1.0 2017/09/19**

Popular Movies allows the users to discover the most popular movies playing as provided network resource from TMDb API 

![alt text](https://github.com/skorudzhiev/PopularMovies/blob/master/PopularMovies_land.png)

### Device permissions
*App needs the following user's permissions to provide the featured functionality*
* *Request user's permission to access the internet*
```XML
<uses-permission android:name="android.permission.INTERNET"/>
```
* *Request user's permission to access device network state*
```XML
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
### Tests
> * Tested on 
>   * Nexus 5X API 24(Android 7.0, API 24) 
>   * 10.1 WXGA (Tablet) API 24

## General Usage Notes

```Gradle
defaultConfig {
        minSdkVersion 15
        targetSdkVersion 25
    }
```

* When the app is launched the user is presented with a populated Grid of movie posters
  * screen orientation draws different number of posters on a single row
* User can select the following set of movies
  * most popular
  * highest rating
  * upcoming releases
  * favorite movies (stored as an app content on the local device)
* Upon a movie poster selection, the app loads a detailed screen for the selected movie (contains additional information:)
  * release date
  * rating
  * movie description
  * trailers
  * reviews
  
### Gradle Dependencies

```Gradle
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:recyclerview-v7:25.1.0'
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
}
```


## Features

* fetch data from the Internet **The Old Fashioned Way** (Without use of external libraries) from [TMDb API](https://www.themoviedb.org/documentation/api)
* use of *RecylerView* adapters and custom list layouts to populate list views
* created a database and content provider to store the names and ids of the user's favorite movies (as well as the rest of the information needed to display their favorites collection while offline)
* used intents to play selected trailer
