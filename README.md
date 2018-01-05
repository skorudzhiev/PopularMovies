# Project Overview

> Popular Movies allows the users to discover the most popular movies playing

![alt text](https://github.com/skorudzhiev/PopularMovies/blob/master/PopularMovies_land.png)

Development of this app was split in two stages. 



## In stage one was built the core experience of the app

* fetch data from the Internet with [TMDb API](https://www.themoviedb.org/documentation/api)
* use of adapters and custom list layouts to populate list views
* incorporated libraries to simplify the amount of code 

## Stage two incorporated an additional functionality in detail view for individual items

* view and play trailers (either in the youtube app or a web browser)
* read reviews of a selected movie
* mark a movie as a favorite in the details view by tapping a button(star)
* created a database and content provider to store the names and ids of the user's favorite movies (as well as the rest of the information needed to display their favorites collection while offline)
* modify the existing sorting criteria for the main view to include an additional pivot to show favorites collection
