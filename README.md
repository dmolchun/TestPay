# TestPay
The TestPay Sandbox

# Environment setup
1. Edit etc\hosts. Add \
   <application_server_ip> - api.testpay.com 
   
2. Add server cert to trusted store \
   sudo openssl s_client -connect api.testpay.com:443 -showcerts </dev/null 2>/dev/null | sudo openssl x509 -outform PEM | sudo tee /usr/local/share/ca-certificates/api.testpay.com.crt 
   sudo update-ca-certificates

# Additional
1. Payer e-mail is used as secret for sha2 signature for webhook

2. Sample authorization request
   curl -v https://api.testpay.com/oauth2/token \\ \
-H "Accept: application/json" \\ \
-H "Accept-Language: en_US" \\ \
-u "user:password" \\ \
-d "grant_type=client_credentials"

3. Sample payment request
curl -v https://api.testpay.com/payments/payment \\ \
-H "Content-Type: application/json" \\ \
-H "Authorization: Bearer 65130d3b-544c-45f3-9497-ad8b16ab39b6" \\ \
-d '{ \
"intent": "order", \
"notification_url": "https://example.com/your_notification_url", \
"payer": { \
"email": "test@example.com" \
}, \
"transaction": { \
"external_id": "123456789", \
"amount": { \
"value": "7.47", \
"currency": "USD" \
}, \
"description": "The payment transaction description" \
} \
}'
