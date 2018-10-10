# TestPay
The TestPay Sandbox

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
