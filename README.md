# FINT flyt test instance gateway
This implementation of dispatch gateway is intended to be used for testing the instance flow process. It includes a REST API, with endpoints for configuring the default and next statuses that the gateway replies to a case dispatch request.

The FINT flyt test instance gateway can be configured to reply to dispatch requests with one of the following statuses: FAILED, ACCEPTED, REJECTED. This is done through the REST API, which supports reading the reply status configuration, setting the default reply status, setting a queue of the next reply statuses, and resetting the reply status configuration. The system default reply status is ACCEPTED.

## REST API

### Reading the reply status configuration
Reading the reply status configuration is done by sending a GET request to ``<service-address>/api/test/dispatch-gateway/reply-status-configuration``.

#### Example
Request:
```
curl --request GET --url http://localhost:8093/api/test/dispatch-gateway/reply-status-configuration
```
Reply:
```
{
  "defaultReplyStatus": "ACCEPTED",
  "replyStatusQueue": [
    "FAILED",
    "FAILED",
    "DECLINED"
  ],
  "nextReplyStatus": "FAILED"
}
```
### Setting the default reply status
Setting the default reply status is done by sending a PUT request to ``<service-address>/api/test/dispatch-gateway/default-reply-status`` with a single status as the request body.

#### Example
```
curl --request PUT \
--url http://localhost:8093/api/test/dispatch-gateway/default-reply-status \
--header 'Content-Type: application/json' \
--data '"ACCEPTED"'
```

### Setting the next reply statuses
Setting the next reply statuses is done by sending a PUT request to ``<service-address>/api/test/dispatch-gateway/reply-status-queue`` with an array of statuses as the request body.

#### Example
```
curl --request PUT \
  --url http://localhost:8093/api/test/dispatch-gateway/reply-status-queue \
  --header 'Content-Type: application/json' \
  --data '["FAILED", "FAILED", "DECLINED"]'
```

### Resetting the reply status configuration
Resetting the reply status configuration is done by sending a DELETE request to ``<service-address>/api/test/dispatch-gateway/reply-status-configuration``.

#### Example
```
curl --request DELETE --url http://localhost:8093/api/test/dispatch-gateway/reply-status-configuration
```
