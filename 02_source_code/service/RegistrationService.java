package com.abcbank.ekyc.service;

import com.abcbank.ekyc.dto.request.RegistrationRequest;
import com.abcbank.ekyc.dto.response.RegistrationResponse;

/**
 * Interface định nghĩa các nghiệp vụ liên quan đến quy trình đăng ký eKYC.
 *
 * @author ABC Bank Digital - Backend Team
 */
public interface RegistrationService {

    /**
     * Xử lý nghiệp vụ đăng ký mở tài khoản.
     *
     * <p><b>Pre-conditions:</b>
     * <ul>
     *   <li>Request hợp lệ (đã pass Bean Validation)</li>
     *   <li>SĐT và CCCD chưa từng đăng ký thành công</li>
     * </ul></p>
     *
     * <p><b>Post-conditions:</b>
     * <ul>
     *   <li>Tài khoản được sinh ra lưu vào database</li>
     *   <li>Trả về thông tin số tài khoản cho khách hàng</li>
     * </ul></p>
     *
     * @param request Dữ liệu đầu vào từ client
     * @return Thông tin tài khoản được khởi tạo
     * @throws com.abcbank.ekyc.exception.BusinessException nếu vi phạm quy tắc nghiệp vụ
     */
    RegistrationResponse register(RegistrationRequest request);
}
