package com.abcbank.ekyc.controller;

import com.abcbank.ekyc.dto.request.RegistrationRequest;
import com.abcbank.ekyc.dto.response.ApiResponse;
import com.abcbank.ekyc.dto.response.RegistrationResponse;
import com.abcbank.ekyc.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller xử lý các request liên quan đến đăng ký mở tài khoản.
 *
 * @author ABC Bank Digital - Backend Team
 */
@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * API Đăng ký thông tin mở tài khoản.
     * Nhận request, validate Bean Validation, gọi Service xử lý và trả về response.
     *
     * @param request Dữ liệu đăng ký
     * @return Kết quả đăng ký bọc trong ApiResponse
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationResponse>> register(
            @Valid @RequestBody RegistrationRequest request) {
        RegistrationResponse response = registrationService.register(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
