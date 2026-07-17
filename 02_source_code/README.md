# MÃ NGUỒN SPRING BOOT - eKYC REGISTRATION API
# ABC Bank Digital - Backend Team
# Java 17 | Spring Boot 3.2.x | PostgreSQL

## Cấu trúc dự án

```
ekyc-registration-service/
├── src/
│   ├── main/
│   │   ├── java/com/abcbank/ekyc/
│   │   │   ├── EkycApplication.java
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── controller/
│   │   │   │   └── RegistrationController.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   └── RegistrationRequest.java
│   │   │   │   └── response/
│   │   │   │       ├── RegistrationResponse.java
│   │   │   │       └── ApiResponse.java
│   │   │   ├── entity/
│   │   │   │   ├── CustomerEntity.java
│   │   │   │   └── AccountStatus.java
│   │   │   ├── exception/
│   │   │   │   ├── BusinessException.java
│   │   │   │   ├── ErrorCode.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── repository/
│   │   │   │   └── CustomerRepository.java
│   │   │   ├── service/
│   │   │   │   ├── RegistrationService.java
│   │   │   │   └── impl/
│   │   │   │       └── RegistrationServiceImpl.java
│   │   │   └── validation/
│   │   │       ├── ValidCitizenId.java
│   │   │       ├── CitizenIdValidator.java
│   │   │       ├── ValidVietnamesePhone.java
│   │   │       └── VietnamesePhoneValidator.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/com/abcbank/ekyc/
│           ├── service/
│           │   └── RegistrationServiceImplTest.java
│           └── controller/
│               └── RegistrationControllerTest.java
├── pom.xml
└── README.md
```
