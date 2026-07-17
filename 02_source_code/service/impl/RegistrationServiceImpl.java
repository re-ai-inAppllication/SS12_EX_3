package com.abcbank.ekyc.service.impl;

import com.abcbank.ekyc.dto.request.RegistrationRequest;
import com.abcbank.ekyc.dto.response.RegistrationResponse;
import com.abcbank.ekyc.entity.AccountStatus;
import com.abcbank.ekyc.entity.CustomerEntity;
import com.abcbank.ekyc.exception.BusinessException;
import com.abcbank.ekyc.exception.ErrorCode;
import com.abcbank.ekyc.repository.CustomerRepository;
import com.abcbank.ekyc.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final Random random = new Random();

    public RegistrationServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public RegistrationResponse register(RegistrationRequest request) {
        log.info("Bắt đầu xử lý đăng ký cho request: {}", request);

        // 1. Kiểm tra tính Idempotency (Trùng lặp)
        Optional<CustomerEntity> existingCustomer = customerRepository.findByCitizenId(request.getCitizenId());
        if (existingCustomer.isPresent()) {
            CustomerEntity customer = existingCustomer.get();
            if (customer.getPhone().equals(request.getPhone())) {
                log.warn("Dữ liệu trùng lặp. Trả về thông tin tài khoản cũ cho CCCD: {}", request.getCitizenId());
                return new RegistrationResponse(
                        customer.getAccountId(),
                        customer.getAccountNumber(),
                        customer.getStatus(),
                        customer.getCreatedAt()
                );
            } else {
                log.error("Số CCCD đã tồn tại nhưng khác số điện thoại. CCCD: {}", request.getCitizenId());
                throw new BusinessException(ErrorCode.CITIZEN_ID_ALREADY_EXISTS, "Số CCCD đã được đăng ký với SĐT khác");
            }
        }

        // Kiểm tra SĐT đã tồn tại chưa
        if (customerRepository.findByPhone(request.getPhone()).isPresent()) {
            log.error("Số điện thoại đã tồn tại. Phone: {}", request.getPhone());
            throw new BusinessException(ErrorCode.PHONE_ALREADY_EXISTS, "Số điện thoại đã được đăng ký");
        }

        // 2. Validate nghiệp vụ giả lập (Tuổi > 18) - Trong thực tế lấy từ OCR
        // Bỏ qua bước này ở mức mô phỏng để tập trung logic chính

        // 3. Khởi tạo đối tượng CustomerEntity
        CustomerEntity newCustomer = new CustomerEntity(
                request.getFullName(),
                request.getPhone(),
                request.getEmail(),
                request.getCitizenId(),
                AccountStatus.PENDING // Trạng thái mặc định ban đầu
        );

        // 4. Generate số tài khoản theo format ngân hàng
        String accountNumber = generateAccountNumber();
        newCustomer.setAccountNumber(accountNumber);
        newCustomer.setAccountId(UUID.randomUUID());
        
        // 5. Lưu vào CSDL
        try {
            CustomerEntity savedCustomer = customerRepository.save(newCustomer);
            log.info("Lưu thành công hồ sơ đăng ký. Account ID: {}", savedCustomer.getAccountId());
            
            return new RegistrationResponse(
                    savedCustomer.getAccountId(),
                    savedCustomer.getAccountNumber(),
                    savedCustomer.getStatus(),
                    savedCustomer.getCreatedAt()
            );
        } catch (Exception e) {
            log.error("Lỗi khi lưu dữ liệu vào CSDL", e);
            throw new RuntimeException("Lỗi hệ thống khi lưu hồ sơ", e);
        }
    }

    /**
     * Sinh số tài khoản ngân hàng.
     * Format: "ABC" + năm hiện tại (2 số) + 11 số random.
     */
    private String generateAccountNumber() {
        String year = String.valueOf(LocalDate.now().getYear()).substring(2);
        StringBuilder sb = new StringBuilder("ABC");
        sb.append(year);
        for (int i = 0; i < 11; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
