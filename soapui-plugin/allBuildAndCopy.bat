pushd ..
call mvn install
popd
call mvn clean
call mvn package -Pstandalone
copy target\soapui-plugin-0.1-SNAPSHOT.jar C:\Users\k5\.soapuios\plugins