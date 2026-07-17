package com.abcbank.ekyc.dto.response;

import com.abcbank.ekyc.entity.AccountStatus;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) trả về kết quả đăng ký mở tài khoản.
 *
 * <p>Sử dụng Java 17 Record để tạo immutable object ngắn gọn.
 * Dữ liệu trả về không chứa thông tin nhạy cảm (PII) như SĐT, CCCD.</p>
 *
 * @param accountId     ID tham chiếu nội bộ của tài khoản vừa tạo
 * @param accountNumber Số tài khoản (đã sinh ra) hiển thị cho khách hàng (16 số)
 * @param status        Trạng thái của tài khoản (VD: PENDING, ACTIVE)
 * @param createdAt     Thời gian tạo hồ sơ
 *
 * @author ABC Bank Digital - Backend Team
 * @version 1.0
 */
public record RegistrationResponse(
        UUID accountId,
        String accountNumber,
        AccountStatus status,
        LocalDateTime createdAt
) {
    // Record tự động tạo constructor, getters (tên giống field), equals, hashCode, toString
}
