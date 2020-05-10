to setup the envoirnment for both react and the api locally please run the bash script inside the automatedServerSetup/MainApplicationServerSetup this will install everything needed

first you need to setup mysql server using the schema which can be found here
https://studentncirl-my.sharepoint.com/:f:/r/personal/x14729385_student_ncirl_ie/Documents/FinalProject%20schema?csf=1&web=1&e=9KYOtX

then inside of the Api/src/main/resources/application-dev.properties point the applications data source to your database, username and password

the inside the frontend folder run the following to setup and start the frontend
npm install
npm start

for java navigate to the api and run the following
mvn clean verify
java -jar -Dspring.profiles.active=dev target/health.application.com-0.9.jar

for the python server run the bash script inside the pythonSeverSetup
then inside the python script you must tell the server your database address, username and password

then just type python3 Server.py to start up


