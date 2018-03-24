# TrackAndTrace server application
## Functionality
This application serves as a server for the [TrackAndTrace application](https://github.com/Martijnvos/TrackAndTrace)
client application.  
It connects to the database (information for setting that up provided below) and also doublees as a RMI provider.

## Database
There isn't a live server at the moment as the application has been made with the Fontys servers in mind.  
I did provide the database create scripts in the form of 'createDatabase.sql' and 'createProcedures.sql'.  
This application can be run with any MSSQL database set up with those files.

## Running the application
The application can be run by simply downloading the source code and running it on your machine with your favorite editor.  
Keep in mind that the database has to be set up as well using the scripts provided.  
After running the application you should see a console window highlighting the connection with the database and
the bound RMI classes to the registry.  
:warning: Warning: make sure this and the client application is not run with a VPN or other IP changing service.  
If this is the case it's not guaranteed that the client application can find the registry.