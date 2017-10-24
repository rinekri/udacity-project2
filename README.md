[![Build Status](https://travis-ci.org/Mahtalitet/udacity-project2.svg?branch=master)](https://travis-ci.org/Mahtalitet/udacity-project2)

![Udacity logo](https://www.kartikarora.me/img/blog/nanodegree/andnano.jpeg)

# Android Developer Nanodegree
Udacity Project - Popular Movies, Stage 2

## How set API key
1. Create file **gradle.properties** in the _poject/app_ directory.
2. Add API KEY like this:
`THE_MOVIE_DB_API_KEY = 111111`

where **111111** corresponding api key.

## Goals | Main
Goal | Progress
------------ | -------------
UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: **most popular**, **highest rated**. | Resolved
Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails. | Resolved
UI contains a screen for displaying the details for a selected movie. | Resolved
Movie Details layout contains **title**, **release date**, **movie poster**, **vote average**, and **plot synopsis**. | Resolved
Movie Details layout contains a section for displaying trailer videos and user reviews. | Resolved
When a user changes the sort criteria (most popular, highest rated, and **favorites**) the main view gets updated correctly. | Resolved
When a movie poster thumbnail is selected, the movie details screen is launched. | Resolved
When a trailer is selected, app uses an Intent to launch the trailer. | Resolved
In the movies detail screen, a user can tap a button (for example, a star) to mark it as a Favorite. | Resolved
In a background thread, app queries the **/movie/popular** or **/movie/top_rated** API for the sort criteria specified in the settings menu. | Resolved
App requests for related videos for a selected movie via the **/movie/{id}/videos** endpoint in a background thread and displays those details when the user selects a movie. | Resolved
App requests for user reviews for a selected movie via the **/movie/{id}/reviews** endpoint in a background thread and displays those details when the user selects a movie. | Resolved
The titles and ids of the user's favorite movies are stored in a **ContentProvider backed by a SQLite database**. This ContentProvider is updated whenever the user favorites or unfavorites a movie. | Resolved
When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the ContentProvider. | Resolved

## Goals | Additional
Goal | Progress
------------ | -------------
Use Service's | Plan
Use BroadcastReceiver's | Plan
Use JobDispatcher (or alternative) | Plan
Use push notifications | Plan
Use ContentProvider/Resolver | Plan
Use SharedPreferences | Plan
Use AsynkTaskLoader | Plan
Use PreferenceFragment | Plan

## Libs, plugins, extensions
Retrofit</br>
Moshi</br>
OkHttp3</br>
Moxy</br>
AutoValue + AutoParcel</br>
Dagger2</br>
Picasso</br>
Timber</br>
Calligraphy</br>
ButterKnife</br>
Support Libs (ConstraintLayout too)</br>
StreamsSupport</br>
retrolambda</br>
Epoxy</br>
