# SMS Operator Service
SMS Operator Service is a micro service that focus on an end to end communication between work scheduling and employees via 
text messages.The service handles inbound and outbound SMS via the Twilio API. The software process the inbound SMS and 
delivers the user's request to the Pencil software, then sends back an SMS confirmation request to the user.

This micro service was built on Spring Boot

Dependencies
1. Casino Streaming Service API (gRPC product for message passing between services)
2. Twilio (REST API for SMS transaction)
3. ngrok (http tunneling)
4. Hibernate using MySql dialect (Data base access)
