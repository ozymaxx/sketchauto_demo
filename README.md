# Sketch Autocompletion Demonstration Application
This application basically demonstrates the auto-completion pipeline for sketched symbols, which is decribed in 
[here](http://iui.ku.edu.tr/sezgin_publications/2012/PR%202012%20Sezgin.pdf).

## How to run the application
* Set-up *numpy*, *sci-py*, and [the feature extractor](https://github.com/ozymaxx/sketchfe) 
* Download & set-up the Android SDK & Android Studio from [its official website](https://developer.android.com/studio/index.html)
* Go to "Settings -> Developer options" and enable USB debugging.
* Plug your tablet in your computer
* Open Android Studio and import the application into your Android Studio workspace
* Build the application
* Copy the predictor into the same directory as `server.py`, which is also included in 
[the repository of the autocompletion framework]().
* Copy the necessary models (read the [README of the autocompletion framework]()) into the same directory as `server.py`
* Run the server by typing `python server.py` on terminal
* Open Android Studio and run the application

The application is now ready!

## Contact
* Ozan Can Altıok - [Koç University IUI Laboratory](http://iui.ku.edu.tr) - oaltiok15 at ku dot edu dot tr
* Elif Yağmur Eyrice - Bilkent University - yagmureyrice at gmail dot com
* Tuğrulcan Elmas - Bilkent University - tugrulcanelmas95 at gmail dot com
* Amirhossein Sayyafan - Sharif University of Technology - sayyafan at ee dot sharif dot edu
