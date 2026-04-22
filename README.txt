Samvat Dangol, sdangol1@mines.edu
Assignment 3 Android Movie Database

App Description:
The app allows users to search for movies using an external API, save movies locally using a Room database, view detailed movie information,
mark movies as favorites and filter through them in the movie list, and delete movies from the list. It integrates with an IMDb API via
RapidAPI to retrieve movie search information and cast information while all saved movies are stored locally and persist between sessions
through the app's database.

Usage:
The app consists of three main screens. The home screen displays a list of saved movies, where each item shows the movie’s title, description,
and release year. Users can toggle a movie’s favorite status by clicking on the heart icon next to the movie, filter between all movies and
only favorited movies using the filter button in the top bar, and navigate to the search screen using the add button. The movie search screen
allows users to type into a search bar to retrieve autocomplete results from the API. To reduce API calls, I reduced it to trigger autocomplete
only when the query length is at least three characters and for every additional three characters entered. Search results display the movie’s
title, description, and year. Selecting a movie from the scrollable list displays its poster in the bottom half of the screen. Users can save
a selected movie to the local database with the save movie button, which triggers a snackbar confirmation with an undo option to not save the
selected movie. Once a movie is saved, you can go back to the movie list screen to see the movie that was saved and click on the movie to go
to the movie detail screen. The movie detail screen displays detailed information about a selected movie, including its poster, title, year,
IMDb rating, genres, and description. From this screen, users can toggle the favorite status, delete the movie with a confirmation dialog, or
view the movie’s cast using the view cast button. A list of cast members is then displayed, and selecting a cast member opens their IMDb page
in a web browser within the app.

Instructions:
To compile and run the program, the project should be opened in Android Studio. A valid RapidAPI key is required and must be added to the
secrets.properties file before running the application, I wasn't sure if I was required to add my own api key or not, so I did just in case.
The app can then be built and executed. Compilation is handled automatically, so no additional steps are required beyond running the project.

Notes:
I didn't find any bugs in the final state of the project, but I did the extra credit a bit differently to not make a ton of API calls for the
autocomplete. I implemented it so that it started autocompleting at 3 characters, then every third character after that. However, you can change
this by changing the else if statement in MovieSearchViewModel from: else if (query.length % 3 == 0) to: else if (query.length >= 3) for
autocompletion after every character entered after the initial 3 charcters. I also reverted the database version to 1, which I think should be fine.
