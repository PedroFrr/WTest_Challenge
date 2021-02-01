# delloite-challenge

<p align="center">
 WTest Delloite challenge, Hilt, Coroutines, Flow, Jetpack (Room, ViewModel,Navigation LiveData) based on MVVM architecture. Also fetching data from the network and integrating local data in the database via repository pattern.
</p>

## Tech stack - Library
- [Kotlin](https://kotlinlang.org/) , [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) , [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Kotlin-DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- JetPack
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [Room](https://developer.android.com/topic/libraries/architecture/room)
  - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started)
  - [MVVM Architecture]() (View  - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit)
- [Moshi]
- [Kotlin CSV](https://github.com/doyaaaaaken/kotlin-csv)
- [Paging 3] (https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [ViewBinding] (https://developer.android.com/topic/libraries/view-binding)
- [Glide] (https://github.com/bumptech/glide)

## Architecture
The application is based on MVVM architecture and a repository pattern.

![architecture](https://raw.githubusercontent.com/fevziomurtekin/hackernewsapp/master/screenshot/mvvm.png)

## How to change Build Variants (Feature 3 and Feature 5)
Go into Android Studio (or other prefered IDE); left side; select Build Variants; select active Build Variant
<p align="center">
  <img alt="Feature3 Build Variant" src="/images/feature3.PNG" width="45%">
&nbsp; &nbsp; &nbsp; &nbsp;
  <img alt="Feature5 Build Variant" src="/images/feature5.PNG" width="45%">
</p>


## Explanation of features
Due to time constraints I assume the user has internet and enough storage on the device
The application has two features which is done with ProductFlavor: 
- Feature 3: ArticleList has no published date | ArticleDetail has no author avatar and no comments list (this feature is appropriate for two endpoints as pointed on the challenge, they can be interchanged on the Constants.kt file)
- Feature 5: ArticleList has publishedDate date | ArticleDetail has a author avatar and comments list 

On the first startup the application database is populated with the list of postcodes using WorkManager to chain a list of workers (no constraints are set for the Workers - storage or network)
1. CSV is downloaded using the Kotlin CSV library
2. CSV content is read from application media and inserted into database
3. CSV is deleted from the application media
4. While the database is being populated a loading status is shown on the Postcode list page (the user can still go to other pages, such as article or form page)

Paging 3 library is used to retrieve the items from the API with pagination. As the user scrolls on the article list or article comments list a new "page" is retrieved from the API. Here I could've used RemoteMediator to first save the items on the database, but I'm doing it manually as they are retrieved

FullTextSearch from Room is used to search the postcode list.
1. First we need to create a fts table with the columns we want to search in. A 'postalDesignationAscii' column is created which has the original postalDesignation converted to lower case and with no accents
2. As the user searches for a postcode the query is converted to the same format as in the column before and used to search on the previous table

Form validation
- Free text field: Can't be empty
- Numbers field: Can't be empty and doesn't allow anything other than numbers
- Hipen and Verses field: Can't be empty and only allows Verses (Letters in caps) and hiphens (-). Minimum number of characters is 3 and maximum is 7 (including the hiphen)
- Date field: Can't be empty and the date inserted must be in the format dd/MM/yyy. Can't insert a monday or a date in the future
- Options field: Can't be empty and the user can only insert "Mau"; "Satisfatório"; "Bom"; "Muito Bom"; "Excelente"
- Postcode field: Can't be empty and the inserted postcode must be on the postcode application list. Once the postcode is valid the corresponding postal designation is shown on the respective field

All strings on the application are set on the strings file so internationalization is made possible 

Glide is used in order to load the images from an URL. the timeout is set to 1 minute as some images are taking a long time to load. Some images can't be loaded from the API (not valid) and on this cases we have an error and a placeholder image.


