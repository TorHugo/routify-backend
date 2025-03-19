# Templates de E-mails.

### Confirmation Customer Account
````http request
POST /api/v1/templates/email
````
```json
{
    "template_key": "TEMPLATE_CONFIRMATION_CUSTOMER_ACCOUNT",
    "subject": "Hey, confirme a sua conta :)",
    "body": "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><style>body{font-family:Arial,sans-serif;line-height:1.6;color:#333;max-width:600px;margin:0 auto;padding:20px}.container{background-color:#fff;border-radius:8px;padding:30px;box-shadow:0 2px 4px rgba(0,0,0,.1)}.header{text-align:center;margin-bottom:30px}.greeting{font-size:24px;color:#2c3e50;margin-bottom:20px}.content{margin-bottom:30px}.code-box{background-color:#f7f9fc;border:1px solid #e1e8ed;border-radius:4px;padding:15px;margin:20px 0;text-align:center}.code{font-size:24px;font-weight:700;color:#3498db;letter-spacing:2px}.expiration{color:#7f8c8d;font-size:14px;margin-top:10px}.footer{text-align:center;font-size:12px;color:#95a5a6;margin-top:30px}</style></head><body><div class=\"container\"><div class=\"header\"></div><div class=\"greeting\">Olá, `@name`!</div><div class=\"content\"><p>Tudo bem? Estamos quase lá! Para confirmar sua conta, utilize o código abaixo:</p><div class=\"code-box\"><div class=\"code\">`@hashcode`</div><div class=\"expiration\">Código válido até: `@expiration-date`</div></div><p>Se você não solicitou este código, por favor ignore este email.</p></div><div class=\"footer\">Este é um email automático, por favor não responda.</div></div></body></html>",
    "is_html": true,
    "version": "v1",
    "parameters": "@name, @expiration-date, @hashcode",
    "active": true
}

```
### Reset Customer Password
````http request
POST /api/v1/templates/email
````
```json
{
  "template_key": "TEMPLATE_PASSWORD_RESET",
  "subject": "Redefinição de senha solicitada",
  "body": "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><style>body{font-family:Arial,sans-serif;line-height:1.6;color:#333;max-width:600px;margin:0 auto;padding:20px}.container{background-color:#fff;border-radius:8px;padding:30px;box-shadow:0 2px 4px rgba(0,0,0,.1)}.header{text-align:center;margin-bottom:30px}.greeting{font-size:24px;color:#2c3e50;margin-bottom:20px}.content{margin-bottom:30px}.code-box{background-color:#f7f9fc;border:1px solid #e1e8ed;border-radius:4px;padding:15px;margin:20px 0;text-align:center}.code{font-size:24px;font-weight:700;color:#3498db;letter-spacing:2px}.expiration{color:#7f8c8d;font-size:14px;margin-top:10px}.footer{text-align:center;font-size:12px;color:#95a5a6;margin-top:30px}</style></head><body><div class=\"container\"><div class=\"header\"></div><div class=\"greeting\">Olá, `@name`!</div><div class=\"content\"><p>Recebemos uma solicitação para redefinir sua senha. Utilize o código abaixo para prosseguir:</p><div class=\"code-box\"><div class=\"code\">`@hashcode`</div><div class=\"expiration\">Código válido até: `@expiration-date`</div></div><p>Se você não solicitou a redefinição de senha, por favor ignore este email.</p></div><div class=\"footer\">Este é um email automático, por favor não responda.</div></div></body></html>",
  "is_html": true,
  "version": "v1",
  "parameters": "@name, @expiration-date, @hashcode",
  "active": true
}
```

### Welcome, Customer to Platform
````http request
POST /api/v1/templates/email
````
```json
{
  "template_key": "TEMPLATE_WELCOME_CUSTOMER",
  "subject": "Conta confirmada!",
  "body": "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><style>body{font-family:Arial,sans-serif;line-height:1.6;color:#333;max-width:600px;margin:0 auto;padding:20px}.container{background-color:#fff;border-radius:8px;padding:30px;box-shadow:0 2px 4px rgba(0,0,0,.1)}.greeting{font-size:24px;color:#2c3e50;margin-bottom:20px}.footer{text-align:center;font-size:12px;color:#95a5a6;margin-top:30px}</style></head><body><div class=\"container\"><div class=\"greeting\">Olá, `@name`!</div><p>Sua conta foi confirmada com sucesso. Seja muito bem-vindo(a) à Routify, <strong>O Melhor Caminho para Seu Destino</strong>.</p><div class=\"footer\">Este é um email automático, por favor não responda.</div></div></body></html>",
  "is_html": true,
  "version": "v1",
  "parameters": "@name",
  "active": true
}
```