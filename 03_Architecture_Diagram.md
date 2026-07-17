# Kiến trúc hệ thống Spring Boot eKYC (Architecture Diagram)

```mermaid
graph TD
    %% Định nghĩa các tác nhân
    Client[Client \n Mobile App / Web]

    %% Tầng API / Presentation
    subgraph "API Layer (Presentation)"
        Controller[RegistrationController \n @RestController]
        ExceptionHandler[GlobalExceptionHandler \n @ControllerAdvice]
    end

    %% Tầng DTO & Validation
    subgraph "Data Transfer & Validation"
        RequestDTO[RegistrationRequest \n DTO]
        ResponseDTO[RegistrationResponse \n DTO]
        CustomValid[@ValidCitizenId \n @ValidVietnamesePhone]
    end

    %% Tầng Business Logic
    subgraph "Service Layer (Business Logic)"
        ServiceInterface[RegistrationService \n Interface]
        ServiceImpl[RegistrationServiceImpl \n @Service]
        BizException[BusinessException]
    end

    %% Tầng Data Access
    subgraph "Repository Layer (Data Access)"
        RepoInterface[CustomerRepository \n Spring Data JPA]
        Entity[CustomerEntity \n @Entity]
    end

    %% Cơ sở dữ liệu
    Database[(PostgreSQL Database)]

    %% Liên kết các luồng
    Client -- "POST /register" --> Controller
    Controller -. "Validate" .-> RequestDTO
    RequestDTO -. "Check constraints" .-> CustomValid
    
    %% Xử lý lỗi validation
    CustomValid -- "Invalid" --> ExceptionHandler
    
    %% Xử lý thành công
    Controller -- "1. register(request)" --> ServiceInterface
    ServiceInterface -. "implements" .-> ServiceImpl
    
    ServiceImpl -- "2. Check Idempotency \n findByCitizenId()" --> RepoInterface
    ServiceImpl -- "2. Check Duplicate \n findByPhone()" --> RepoInterface
    
    %% Xử lý ngoại lệ nghiệp vụ
    ServiceImpl -- "Duplicate/Invalid" --> BizException
    BizException --> ExceptionHandler
    ExceptionHandler -- "HTTP 400/422" --> Client
    
    %% Lưu dữ liệu
    ServiceImpl -- "3. Map DTO to Entity" --> Entity
    ServiceImpl -- "4. save(entity)" --> RepoInterface
    
    RepoInterface -- "SQL Insert/Select" --> Database
    Database -- "Result" --> RepoInterface
    
    %% Trả kết quả
    RepoInterface -- "Entity" --> ServiceImpl
    ServiceImpl -- "5. Map Entity to DTO" --> ResponseDTO
    ServiceImpl -- "Return" --> Controller
    Controller -- "ApiResponse<T>" --> Client
    
    %% Style
    classDef layer fill:#f9f9f9,stroke:#333,stroke-width:2px;
    classDef client fill:#d4e157,stroke:#333,stroke-width:2px;
    classDef db fill:#81d4fa,stroke:#333,stroke-width:2px;
    
    class Client client;
    class Database db;
```
