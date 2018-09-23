# TestPay
The TestPay Sandbox

# Environment setup

1. Edit etc\hosts. Add

   <application_server_ip> - api.testpay.com 
   
2. Add server cert to trusted store

   sudo openssl s_client -connect api.testpay.com:443 -showcerts </dev/null 2>/dev/null | sudo openssl x509 -outform PEM | sudo tee /usr/local/share/ca-certificates/api.testpay.com.crt
   
   sudo update-ca-certificates

