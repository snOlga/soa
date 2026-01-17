# MuleESB Setup Guide for Teams Service

## Overview
This guide explains how to set up MuleESB to act as a REST-to-SOAP proxy between `teams-service` (REST) and `teams-ejb-service` (SOAP).

## Architecture Flow
```
Frontend → teams-service (REST, port 18082) → MuleESB (REST, port 8081) → teams-ejb-service (SOAP, WildFly:8080)
```

## Step-by-Step Setup

### 1. Deploy SOAP Service to WildFly

First, you need to deploy the SOAP service to WildFly:

```bash
# Build the EJB service
cd teams-ejb-service
mvn clean package

# Copy the JAR to WildFly deployments
# If using Docker:
docker cp teams-ejb-service/target/ejb-0.0.2-SNAPSHOT.jar wildfly:/opt/jboss/wildfly/standalone/deployments/

# Or if WildFly is running locally:
copy teams-ejb-service/target/ejb-0.0.2-SNAPSHOT.jar <WILDFLY_HOME>/standalone/deployments/
```

Wait for WildFly to deploy the service. Check WildFly logs to confirm deployment.

### 2. Get the WSDL URL

Once deployed, access the WSDL:
- **Docker WildFly**: `http://172.20.0.14:8080/ejb-0.0.2-SNAPSHOT/TeamsService?wsdl`
- **Local WildFly**: `http://localhost:8080/ejb-0.0.2-SNAPSHOT/TeamsService?wsdl`

Open this URL in a browser to verify it's accessible.

### 3. Update Mule Configuration

Edit `mule-enterprise-standalone-4.10.2/apps/teams-soap-proxy/mule-config.xml`:

Find line 20 and update the `wsdlUrl` if needed:
```xml
<soap:proxy-config name="TeamsService_Proxy_Config" 
                   wsdlUrl="http://172.20.0.14:8080/ejb-0.0.2-SNAPSHOT/TeamsService?wsdl" 
                   doc:name="SOAP Proxy config">
```

**Note**: You may need to adjust the SOAP namespace mappings in the DataWeave transformations based on the actual WSDL structure.

### 4. Start/Restart MuleESB

MuleESB will auto-deploy applications from the `apps/` directory:
- If MuleESB is running, it should detect the new app automatically
- Check logs: `mule-enterprise-standalone-4.10.2/logs/mule_ee.log`
- Look for deployment success messages

### 5. Verify the Setup

#### Test MuleESB REST Endpoints:
```bash
# Get team by ID
curl http://localhost:8081/teams/1

# Create team
curl -X POST http://localhost:8081/teams \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Team","humans":[],"isDeleted":false}'

# Delete team
curl -X DELETE http://localhost:8081/teams/1
```

#### Test the Full Flow:
1. Start your frontend application
2. The frontend calls `teams-service` on port 18082
3. `teams-service` forwards to MuleESB on port 8081
4. MuleESB transforms REST to SOAP and calls WildFly on port 8080
5. Response flows back through the chain

### 6. Troubleshooting

#### Mule App Not Deploying:
- Check `mule-enterprise-standalone-4.10.2/logs/mule_ee.log` for errors
- Verify XML syntax is correct
- Ensure WSDL URL is accessible

#### SOAP Transformations Failing:
- The DataWeave transformations may need namespace adjustments
- Check the actual WSDL structure and update namespaces in `mule-config.xml`
- Common namespace prefixes: `ns0`, `ns1`, or full namespace URIs

#### Connection Issues:
- Verify WildFly is running and accessible
- Check firewall/network settings
- Verify IP addresses match your Docker network configuration

## Current Configuration

- **MuleESB REST Listener**: `http://localhost:8081`
- **SOAP Service WSDL**: `http://172.20.0.14:8080/ejb-0.0.2-SNAPSHOT/TeamsService?wsdl`
- **teams-service**: Already configured to call `http://localhost:8081`

## Next Steps

1. Deploy the SOAP service to WildFly
2. Verify WSDL is accessible
3. Update WSDL URL in Mule config if needed
4. Restart MuleESB or wait for auto-deployment
5. Test the endpoints
6. Start your frontend and verify end-to-end flow

