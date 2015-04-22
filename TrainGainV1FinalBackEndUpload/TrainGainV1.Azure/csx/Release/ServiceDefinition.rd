<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="TrainGainV1.Azure" generation="1" functional="0" release="0" Id="c5227fa5-4993-4a9f-afe0-00fe19d089f5" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
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
          <role name="TrainGainV1" generation="1" functional="0" release="0" software="C:\Users\carl\Documents\BackUp3FromUSB\NEWBACKBACKUPCARL3\TrainGainVersion01\TrainGainV1\TrainGainV1.Azure\csx\Release\roles\TrainGainV1" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaIISHost.exe " memIndex="-1" hostingEnvironment="frontendadmin" hostingEnvironmentVersion="2">
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
    <implementation Id="378b92b4-b550-4d4d-a331-38cf0d4cfdfb" ref="Microsoft.RedDog.Contract\ServiceContract\TrainGainV1.AzureContract@ServiceDefinition">
      <interfacereferences>
        <interfaceReference Id="3f7116d9-de36-4bed-967a-3d9b6f2ce5e6" ref="Microsoft.RedDog.Contract\Interface\TrainGainV1:Endpoint1@ServiceDefinition">
          <inPort>
            <inPortMoniker name="/TrainGainV1.Azure/TrainGainV1.AzureGroup/TrainGainV1:Endpoint1" />
          </inPort>
        </interfaceReference>
      </interfacereferences>
    </implementation>
  </implements>
</serviceModel>