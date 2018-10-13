# TestPay
The TestPay Sandbox.

Self-contained testing environment that mimics the live TestPay production environment.
 
The TestPay payment flow
1. When the customer is ready to pay for goods or services on your website, they select the TestPay payment option on your website.
2. You obtain the OAuth access token (if needed)
3. You request the payment by passing customer email and transaction details to the
TestPay
4. TestPay provides you with asynchronous notification, sent to your webhook listener,
confirming the transaction details and status

The TestPay REST API uses the OAuth 2.0 protocol to authorize calls. You pass user credentials in the Authorization​ header in get access token request. In exchange for these credentials, the TestPay authorization server issues tokens called bearer tokens that you use for authorization when you make REST API requests (par.2 in Additional).

With a valid access token, you can make REST API calls (par.3 in Additional). This sample call creates a TestPay account payment. The access token in the call is an OAuth bearer token.
A Payment API call is asynchronous, which lets you show payout details at a later time. After payment processing, you will receive webhook event notification with final payment status. A successful request returns the HTTP 200 OK status code and a JSON response body that shows payment details.
In the responses for failed requests, TestPay returns 4xx or 5xx status codes.

# Application config
1. server.port - port number for ssl
2. server.ssl.key-store - key store location
3. server.ssl.key-store-password - key store password
4. server.ssl.key-password - key password
4. app.username - user login
5. app.password - user password

# Environment setup
1. Edit etc\hosts. Add \
   <application_server_ip> - api.testpay.com 
   
2. Add server cert to trusted store \
   sudo openssl s_client -connect api.testpay.com:443 -showcerts </dev/null 2>/dev/null | sudo openssl x509 -outform PEM | sudo tee /usr/local/share/ca-certificates/api.testpay.com.crt \
   sudo update-ca-certificates

# Additional
1. Payer e-mail is used as secret for sha2 signature for webhook

2. Sample authorization request \
   curl -v https://api.testpay.com/oauth2/token \\\
-H "Accept: application/json" \\\
-H "Accept-Language: en_US" \\\
-u "user:password" \\\
-d "grant_type=client_credentials"

3. Sample payment request \
curl -v https://api.testpay.com/payments/payment \\\
-H "Content-Type: application/json" \\\
-H "Authorization: Bearer f4abdc82-6f56-423d-a8e2-29b720f9340c" \\\
-d '{\
"intent": "order",\
"notification_url": "https://example.com/your_notification_url",\
"payer": {\
"email": "test@example.com"\
},\
"transaction": {\
"external_id": "123456789",\
"amount": {\
"value": "7.47",\
"currency": "USD"\
},\
"description": "The payment transaction description"\
}\
}'
