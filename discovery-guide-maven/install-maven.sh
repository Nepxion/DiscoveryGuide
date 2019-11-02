echo 'on'
echo '============================================================='
echo '$                                                           $'
echo '$                     Nepxion Discovery                     $'
echo '$                                                           $'
echo '$                                                           $'
echo '$                                                           $'
echo '$  Nepxion Studio All Right Reserved                        $'
echo '$  Copyright (C) 2017-2050                                  $'
echo '$                                                           $'
echo '============================================================='
echo '.'
echo 'off'

title=Nepxion Discovery Guide [Service A1]
color=0a

mvn clean install -DskipTests -pl discovery-guide-service -am -DMainClass=com.nepxion.discovery.guide.service.DiscoveryGuideServiceA1
mvn clean install -DskipTests -pl discovery-guide-gateway -am -DMainClass=com.nepxion.discovery.guide.gateway.DiscoveryGuideGateway
mvn clean install -DskipTests -pl discovery-guide-zuul    -am -DMainClass=com.nepxion.discovery.guide.zuul.DiscoveryGuideZuul
mvn clean install -DskipTests -pl discovery-guide-console -am -DMainClass=com.nepxion.discovery.guide.console.DiscoveryGuideConsole
mvn clean install -DskipTests -pl discovery-guide-admin   -am -DMainClass=com.nepxion.discovery.guide.console.DiscoveryGuideAdmin