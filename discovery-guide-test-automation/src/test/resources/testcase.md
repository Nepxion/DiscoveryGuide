网关层灰度路由
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <!-- <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}</version> -->
        <!-- <version>1.0</version> -->
        <!-- <region>{"discovery-guide-service-a":"dev", "discovery-guide-service-b":"dev"}</region> -->
        <!-- <region>dev</region> -->
        <!-- <address>{"discovery-guide-service-a":"192.168.43.101:3001", "discovery-guide-service-b":"192.168.43.101:4001"}</address> -->
        <!-- <version-weight>{"discovery-guide-service-a":"1.0=90;1.1=10", "discovery-guide-service-b":"1.0=90;1.1=10"}</version-weight> -->
        <!-- <version-weight>1.0=90;1.1=10</version-weight> -->
        <!-- <region-weight>{"discovery-guide-service-a":"dev=85;qa=15", "discovery-guide-service-b":"dev=85;qa=15"}</region-weight> -->
        <!-- <region-weight>dev=85;qa=15</region-weight> -->
    </strategy>

    <strategy-customization>
        <conditions>
            <condition id="2" header="a=1" version-weight-id="f"/>
            <condition id="1" header="a=1;b=2" version-weight-id="d"/>
            <!-- <condition id="1" header="a=1;b=2" version-id="a" region-id="b" address-id="c" version-weight-id="d" region-weight-id="e"/> -->
            <!-- <condition id="2" header="c=3" version-id="a" region-id="b"/> -->
        </conditions>

        <routes>
            <route id="a" type="version">{"discovery-guide-service-a":"1.1", "discovery-guide-service-b":"1.1"}</route>
            <route id="b" type="region">{"discovery-guide-service-a":"qa", "discovery-guide-service-b":"qa"}</route>
            <route id="c" type="address">{"discovery-guide-service-a":"192.168.43.101:3001", "discovery-guide-service-b":"192.168.43.101:4001"}</route>
            <route id="d" type="version-weight">{"discovery-guide-service-a":"1.0=90;1.1=10", "discovery-guide-service-b":"1.0=90;1.1=10"}</route>
            <route id="e" type="region-weight">{"discovery-guide-service-a":"dev=85;qa=15", "discovery-guide-service-b":"dev=85;qa=15"}</route>
            <route id="f" type="version">{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.1"}</route>
        </routes>
    </strategy-customization>
</rule>

网关层灰度权重路由
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <!-- <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}</version> -->
        <!-- <version>1.0</version> -->
        <!-- <region>{"discovery-guide-service-a":"dev", "discovery-guide-service-b":"dev"}</region> -->
        <!-- <region>dev</region> -->
        <!-- <address>{"discovery-guide-service-a":"192.168.43.101:3001", "discovery-guide-service-b":"192.168.43.101:4001"}</address> -->
        <!-- <version-weight>{"discovery-guide-service-a":"1.0=90;1.1=10", "discovery-guide-service-b":"1.0=90;1.1=10"}</version-weight> -->
        <!-- <version-weight>1.0=90;1.1=10</version-weight> -->
        <!-- <region-weight>{"discovery-guide-service-a":"dev=85;qa=15", "discovery-guide-service-b":"dev=85;qa=15"}</region-weight> -->
        <!-- <region-weight>dev=85;qa=15</region-weight> -->
    </strategy>

    <strategy-customization>
        <conditions>
            <condition id="2" header="a=1" version-weight-id="f"/>
            <condition id="1" header="a=1;b=2" version-weight-id="d"/>
            <!-- <condition id="1" header="a=1;b=2" version-id="a" region-id="b" address-id="c" version-weight-id="d" region-weight-id="e"/> -->
            <!-- <condition id="2" header="c=3" version-id="a" region-id="b"/> -->
        </conditions>

        <routes>
            <route id="a" type="version">{"discovery-guide-service-a":"1.1", "discovery-guide-service-b":"1.1"}</route>
            <route id="b" type="region">{"discovery-guide-service-a":"qa", "discovery-guide-service-b":"qa"}</route>
            <route id="c" type="address">{"discovery-guide-service-a":"192.168.43.101:3001", "discovery-guide-service-b":"192.168.43.101:4001"}</route>
            <route id="d" type="version-weight">{"discovery-guide-service-a":"1.0=90;1.1=10", "discovery-guide-service-b":"1.0=90;1.1=10"}</route>
            <route id="e" type="region-weight">{"discovery-guide-service-a":"dev=85;qa=15", "discovery-guide-service-b":"dev=85;qa=15"}</route>
            <route id="f" type="version">{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.1"}</route>
        </routes>
    </strategy-customization>
</rule>

全链路权重灰度发布
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <discovery>
        <!-- <version>
            <service consumer-service-name="discovery-guide-service-a" provider-service-name="discovery-guide-service-b" consumer-version-value="1.0" provider-version-value="1.0"/>
            <service consumer-service-name="discovery-guide-service-a" provider-service-name="discovery-guide-service-b" consumer-version-value="1.1" provider-version-value="1.1"/>
        </version> -->

        <weight>
            <service consumer-service-name="discovery-guide-gateway" provider-service-name="discovery-guide-service-a" provider-weight-value="1.0=90;1.1=10" type="version"/>
            <service consumer-service-name="discovery-guide-zuul" provider-service-name="discovery-guide-service-a" provider-weight-value="1.0=91;1.1=9" type="version"/>
            <service provider-service-name="discovery-guide-service-a" provider-weight-value="1.0=92;1.1=8" type="version"/>
            <service provider-service-name="discovery-guide-service-b" provider-weight-value="1.0=93;1.1=7" type="version"/>
            <version provider-weight-value="1.0=94;1.1=6"/>

            <service consumer-service-name="discovery-guide-gateway" provider-service-name="discovery-guide-service-a" provider-weight-value="dev=95;qa=5" type="region"/>
            <service consumer-service-name="discovery-guide-zuul" provider-service-name="discovery-guide-service-a" provider-weight-value="dev=96;qa=4" type="region"/>
            <service provider-service-name="discovery-guide-service-a" provider-weight-value="dev=97;qa=3" type="region"/>
            <service provider-service-name="discovery-guide-service-b" provider-weight-value="dev=98;qa=2" type="region"/>
            <region provider-weight-value="dev=99;qa=1"/>
        </weight>
    </discovery>
</rule>