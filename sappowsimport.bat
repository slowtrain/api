wsimport -verbose -keep http://icam.koreaaero.com/episode/ws/sappo.wsdl -d ./bin -s ./src -b http://icam.koreaaero.com/episode/async/sappo-async-bindings.xml -encoding UTF-8
rem wsimport -verbose -keep http://icamdev.koreaaero.com/episode/ws/sappo.wsdl -d ./bin -s ./src -b http://icamdev.koreaaero.com/episode/async/sappo-async-bindings_dev.xml -encoding UTF-8
rem wsimport -verbose -keep http://icam.koreaaero.com:8080/episode/ws/sappo.wsdl -d ./bin -s ./src -b http://icam.koreaaero.com:8080/episode/async/sappo-async-bindings_local.xml -encoding UTF-8
