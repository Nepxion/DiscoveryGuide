@echo on
@echo =============================================================
@echo $                                                           $
@echo $                     Nepxion Discovery                     $
@echo $                                                           $
@echo $                                                           $
@echo $                                                           $
@echo $  Nepxion Studio All Right Reserved                        $
@echo $  Copyright (C) 2017-2050                                  $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title Nepxion Discovery Guide
@color 0a

call mvn clean install -DskipTests -pl discovery-guide-service -am -DMainClass=com.nepxion.discovery.guide.service.DiscoveryGuideServiceA1
call mvn clean install -DskipTests -pl discovery-guide-gateway -am -DMainClass=com.nepxion.discovery.guide.gateway.DiscoveryGuideGateway
call mvn clean install -DskipTests -pl discovery-guide-zuul    -am -DMainClass=com.nepxion.discovery.guide.zuul.DiscoveryGuideZuul
call mvn clean install -DskipTests -pl discovery-guide-console -am -DMainClass=com.nepxion.discovery.guide.console.DiscoveryGuideConsole
call mvn clean install -DskipTests -pl discovery-guide-admin   -am -DMainClass=com.nepxion.discovery.guide.console.DiscoveryGuideAdmin

pause