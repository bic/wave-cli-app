(cd maivic-comm ; mvn clean install)
(cd maivic-comm-impl ; mvn clean install)
(cd maivic-comm-protocol ; mvn clean install)
mv ./maivic-comm-protocol/target/maivic-comm-protocol-0.0.1-SNAPSHOT.jar target/
mv ./maivic-comm-impl/maivic-comm-impl/target/maivic-comm-impl-0.0.1-SNAPSHOT.jar target/
mv ./maivic-comm/target/maivic-comm-0.0.1-SNAPSHOT.jar target/
