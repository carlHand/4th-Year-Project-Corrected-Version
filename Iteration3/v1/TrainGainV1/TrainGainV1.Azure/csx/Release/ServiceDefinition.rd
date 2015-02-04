<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="TrainGainV1.Azure" generation="1" functional="0" release="0" Id="28122569-e7ca-4b07-af5a-ea2c05eeb7f6" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
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
        <aCS name="TrainGainV1:StorageConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/MapTrainGainV1:StorageConnectionString" />
          </maps>
        </aCS>
        <aCS name="TrainGainV1:TrainGainDbConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/MapTrainGainV1:TrainGainDbConnectionString" />
          </maps>
        </aCS>
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
        <map name="MapTrainGainV1:StorageConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1/StorageConnectionString" />
          </setting>
        </map>
        <map name="MapTrainGainV1:TrainGainDbConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1/TrainGainDbConnectionString" />
          </setting>
        </map>
        <map name="MapTrainGainV1Instances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1Instances" />
          </setting>
        </map>
      </maps>
      <components>
        <groupHascomponents>
          <role name="TrainGainV1" generation="1" functional="0" release="0" software="C:\Users\carl\Documents\v1\TrainGainV1\TrainGainV1.Azure\csx\Release\roles\TrainGainV1" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaIISHost.exe " memIndex="-1" hostingEnvironment="frontendadmin" hostingEnvironmentVersion="2">
            <componentports>
              <inPort name="Endpoint1" protocol="http" portRanges="80" />
            </componentports>
            <settings>
              <aCS name="StorageConnectionString" defaultValue="" />
              <aCS name="TrainGainDbConnectionString" defaultValue="" />
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
    <implementation Id="bd0a195b-0eda-4535-8b0a-1e489eb3072b" ref="Microsoft.RedDog.Contract\ServiceContract\TrainGainV1.AzureContract@ServiceDefinition">
      <interfacereferences>
        <interfaceReference Id="ca5a8237-b485-47d4-8cb9-883886aae462" ref="Microsoft.RedDog.Contract\Interface\TrainGainV1:Endpoint1@ServiceDefinition">
          <inPort>
            <inPortMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1:Endpoint1" />
          </inPort>
        </interfaceReference>
      </interfacereferences>
    </implementation>
  </implements>
</serviceModel>