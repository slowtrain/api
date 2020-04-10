wsimport -verbose -keep http://icam.koreaaero.com/episode/ws/usergroup.wsdl -d ./bin -s ./src -b http://icam.koreaaero.com/episode/async/usergroup-async-bindings.xml -encoding UTF-8
rem wsimport -verbose -keep http://icamdev.koreaaero.com/episode/ws/usergroup.wsdl -d ./bin -s ./src -b http://icamdev.koreaaero.com/episode/async/usergroup-async-bindings_dev.xml -encoding UTF-8
rem wsimport -verbose -keep http://icamlocal.koreaaero.com:8080/episode/ws/usergroup.wsdl -d ./bin -s ./src -b http://icamlocal.koreaaero.com:8080/episode/async/usergroup-async-bindings_local.xml -encoding UTF-8
