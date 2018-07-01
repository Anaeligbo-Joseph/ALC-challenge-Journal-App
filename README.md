# ALC-challenge-Journal-App
A 7daysCodeChallenge from ALC powered by Google and Udacity. 

This is a journal app that authenticates users via google mail before they can use the journal which supports add, save, preview and delete notes.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites
For this project you will need
1. Android Studio to load the project.

To test app
2. Android mobile device or emulator with Google Mail support working.


### Installing
1. For Android Studio, Visit developer.android.com to download and how to install.

For app test
1. Download apk file from the github repository. (github.com/anaeligbojoseph/Journal test app)
2. Install the apk on mobile phone.
3. Accept internet access permission if prompted to.


## Running the tests

To run automated test or UI test, 
1. Load the project in Android Studio.
2. After successful build, From explorer navigate to %FOLDER%\app\src\androidTest\java\com\journalapp\u\alc\auth to find the automated test OR go to C:\%YourLocation%\%folder%\app\src\androidTest\java\com\journalapp\u\alc\auth to find the automated test named GeneraltestCaseSenarios.Java
You can also visit [here](https://github.com/Anaeligbo-Joseph/ALC-challenge-Journal-App/tree/master/app/src/androidTest/java/com/journalapp/u/alc/auth) to view the expresso.
3. Run GeneraltestCaseSenarios.Java

### tests

The test checks Google registration and authentication using Google mail.
```
Users must sign in with a valid Gmail account to use the Journal App.

```
Add, modify and view enteries in the journal.
```
Users are notified by a toast, when modification is made to a note.
All entries are grouped into four groups...GENERAL, WORK, FAMILY, PERSONAL.
GENERAL acts as the default selection and can be modifed.

```

### And coding style tests

Test checks if the features like Add, Modify and Delete works.

```
After typing in editbox, the tick sign in the menu bar adds the note to the database.
To edit, simply tap the note and you are fed the note to edit accordingly. Tap the check to save again.
To delete entry simply swipe the note to LEFT or RIGHT.

```

## Built With

* [firebase](http://www.firebase.google.com/) -Visit https://firebase.google.com/docs/auth/android/google-signin for documentation on implementing Google mail sign in authentication



## Authors
ALC challenge Journal App

* **Joseph Anaeligbo** - *Initial work* - [Anaeligbo-Joseph](https://github.com/Anaeligbo-Joseph)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments
* Thanks for the Google team for expliciting making a sample of implementing [Google mail authentication](https://github.com/firebase/quickstart-android/blob/68a320d32688aea964360f8fd3ede15c9c49dd8a/auth/app/src/main/java/com/google/firebase/quickstart/auth/GoogleSignInActivity.java)
* Thanks to Udacity for giving me an exposure to some hidden treasure within android development and ALC for creating a supportive community for dev. to grow.
* Thanks to stackoverflow for being a lookup resource on how to fix issues.
* Thanks to my --friends for support and inspiration.

