## DoEH: Domains of Endless Hunger (WebSocket Server)

### To run the service, the following environment variables are required.
A sample configuration file for running locally can be found at `src/main/resources/config_dev.properties`
```
#Main settings
ws.port=[websocket_port]
debug=[false/true]

#Kafka Settings
acks=1
bootstrap.servers=[kafka_bootstrap_server:server_port]
security.protocol=[You kafka server security protocol]

ssl.keystore.type=[SSL_Keystore_type - Optional]
ssl.keystore.location=[SSL Keystore Location - Optional]
ssl.keystore.password=[SSL Keystore Password - Optional]

ssl.truststore.location=[SSL Truststore Location - Optional]
ssl.truststore.password=[SSL Truststore Password - Optional]

session.timeout.ms=[Kafka Session timeout in ms]
auto.offset.reset=[Kafka auto offset reset]
enable.auto.commit=[Kafka auto commit]
retries=[Kafka retries]
retry.backoff.ms=[Kafka backoff in ms]
delivery.timeout.ms=[Kafka delivery timeout in ms]
reconnect.backoff.max.ms=[Kafka reconnect backoff maximum ms]

#Kafka Topics
player.event.topic=player-event
player.updates.topic=player-update
location.updates.topic=location-update
game.updates.topic=game-update
game.round.time=125

#JWT
jwt.token=[JWT Token Signing Key HMAC-SHA256/SHA512]
```
