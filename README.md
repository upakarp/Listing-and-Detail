# Listing&Details

This is a simple single activity application that fetch data from jsonplaceholder api and display in the screen. It caches the data to persist and display the data when there is no internet connection in the device.

## Installation
Simply import the code from bitbucket to your Android Studio. Plug in the device or Fire up the emulator and Run code.

## Built with
 1. Java and XML
 2. Retrofit - Third party library
 3. Android Studio(IDE)

## Screenshot and Explanation

When the application runs for the first time without internet. There is no data cached so, following screen will fire up


![Alt first_time](screenshots/open_first_time.png?raw=true)


When application is loaded with internet access than following screen fires up and data is cached.


![Alt with_internet](screenshots/with_internet.png?raw=true)


When application runs without internet access after data is cached than


![Alt without_internet](screenshots/without_internet.png?raw=true)