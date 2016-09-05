# Sketch Autocompletion Demonstration Application
This application basically demonstrates the auto-completion pipeline for sketched symbols, which is decribed in 
[here](http://iui.ku.edu.tr/sezgin_publications/2012/PR%202012%20Sezgin.pdf).

## Directory structure
`server/`: The source code of sketch classification server (Deprecated, use the latest one available in [this repository](https://github.com/ozymaxx/sketchautocompletion))<br>
The rest: The source code of Android application

## How to run the application
* Install pip by typing `sudo apt-get install pip`
* Set up **numpy** by typing `sudo pip install numpy`
* Set up **sci-py** by typing `sudo pip install scipy`
* Set up the [the feature extractor](https://github.com/ozymaxx/sketchfe) as described in the README of the repository
* Download & set-up the Android SDK & Android Studio from [its official website](https://developer.android.com/studio/index.html)
* Go to **Settings -> Developer options** and enable USB debugging.
* Plug your tablet in your computer
* Open Android Studio and import the application into your Android Studio workspace
* Build the application
* Type `cd server` to enter the directory including the source code of the classification server
* Copy the predictor into the same directory as `server.py`, which is also included in 
[the repository of the autocompletion framework]().
* Copy the necessary models (read the [README of the autocompletion framework]()) into the same directory as `server.py`
* Run the server by typing `python server.py` on terminal
* Open Android Studio and run the application
* Click on ![Change IP](https://s21.postimg.org/fuhblt283/Screenshot_from_2016_09_05_14_00_02.png), and type the IP of the computer where the classification server is running.

The application is now ready! Just draw a sketch on the canvas, and see how it's classified on the left panel.

## How to use the application
![Change IP](https://s9.postimg.org/esz2yhkor/image.png): Click on this button if you want to change the IP address of the computer where the classification server is running.<br>
![Undo](https://s9.postimg.org/fwj7ag5bv/image.png): This is the button to undo the latest change on the canvas.<br>
![Clear Canvas](https://s9.postimg.org/48p5fwg6z/image.png): Using this button, you can clear the entire canvas.<br>
![Remove Stroke](https://s9.postimg.org/nf6xwtm2j/image.png): If you want to remove a stroke, just click on this button and then click on the stroke you want to remove.<br>

## Contact
* Ozan Can Altıok - [Koç University IUI Laboratory](http://iui.ku.edu.tr) - oaltiok15 at ku dot edu dot tr
* Elif Yağmur Eyrice - Bilkent University - yagmureyrice at gmail dot com
* Tuğrulcan Elmas - Bilkent University - tugrulcanelmas95 at gmail dot com
* Amirhossein Sayyafan - Sharif University of Technology - sayyafan at ee dot sharif dot edu
