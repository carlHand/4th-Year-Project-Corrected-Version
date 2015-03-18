<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="TrainGainV1.Azure" generation="1" functional="0" release="0" Id="83ea876a-d92c-48de-8766-9c306d929edd" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
  <groups>
    <group name="TrainGainV1.AzureGroup" generation="1" functional="0" release="0">
      <componentports>
        <inPort name="TrainGainV1:Endpoint1" protocol="http">
          <inToChannel>
            <lBChannelMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/LB:TrainGainV1:Endpoint1" />
          </inToChannel>
        </inPort>
      </componentports>
      <settings>
        <aCS name="TrainGainV1Instances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/MapTrainGainV1Instances" />
          </maps>
        </aCS>
      </settings>
      <channels>
        <lBChannel name="LB:TrainGainV1:Endpoint1">
          <toPorts>
            <inPortMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1/Endpoint1" />
          </toPorts>
        </lBChannel>
      </channels>
      <maps>
        <map name="MapTrainGainV1Instances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1Instances" />
          </setting>
        </map>
      </maps>
      <components>
        <groupHascomponents>
          <role name="TrainGainV1" generation="1" functional="0" release="0" software="C:\Users\carl\Documents\BackUp3FromUSB\NEWBACKBACKUPCARL3\TrainGainVersion01\TrainGainV1\TrainGainV1.Azure\csx\Debug\roles\TrainGainV1" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaIISHost.exe " memIndex="-1" hostingEnvironment="frontendadmin" hostingEnvironmentVersion="2">
            <componentports>
              <inPort name="Endpoint1" protocol="http" portRanges="80" />
            </componentports>
            <settings>
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;TrainGainV1&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;TrainGainV1&quot;&gt;&lt;e name=&quot;Endpoint1&quot; /&gt;&lt;/r&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
          </role>
          <sCSPolicy>
            <sCSPolicyIDMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1Instances" />
            <sCSPolicyUpdateDomainMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1UpgradeDomains" />
            <sCSPolicyFaultDomainMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1FaultDomains" />
          </sCSPolicy>
        </groupHascomponents>
      </components>
      <sCSPolicy>
        <sCSPolicyUpdateDomain name="TrainGainV1UpgradeDomains" defaultPolicy="[5,5,5]" />
        <sCSPolicyFaultDomain name="TrainGainV1FaultDomains" defaultPolicy="[2,2,2]" />
        <sCSPolicyID name="TrainGainV1Instances" defaultPolicy="[1,1,1]" />
      </sCSPolicy>
    </group>
  </groups>
  <implements>
    <implementation Id="dc0d19f6-5889-46c9-8ad3-4d5c20fa63d1" ref="Microsoft.RedDog.Contract\ServiceContract\TrainGainV1.AzureContract@ServiceDefinition">
      <interfacereferences>
        <interfaceReference Id="8cbfff1d-f171-4780-97e3-97e122b0d88d" ref="Microsoft.RedDog.Contract\Interface\TrainGainV1:Endpoint1@ServiceDefinition">
          <inPort>
            <inPortMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1:Endpoint1" />
          </inPort>
        </interfaceReference>
      </interfacereferences>
    </implementation>
  </implements>
</serviceModel>