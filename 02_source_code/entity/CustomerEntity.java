package com.abcbank.ekyc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity đại diện cho thông tin khách hàng trong quy trình eKYC.
 *
 * <p>Bảng này lưu trữ toàn bộ thông tin nhận diện khách hàng
 * sau khi hoàn thành quy trình mở tài khoản trực tuyến (eKYC).
 * Mỗi bản ghi tương ứng với một hồ sơ đăng ký tài khoản.</p>
 *
 * <p><b>Business Rule quan trọng:</b>
 * <ul>
 *   <li>Mỗi số điện thoại (phone) chỉ được đăng ký MỘT lần</li>
 *   <li>Mỗi số CCCD (citizenId) chỉ được đăng ký MỘT lần</li>
 *   <li>Số tài khoản (accountNumber) là duy nhất trong toàn hệ thống</li>
 * </ul>
 * </p>
 *
 * @author ABC Bank Digital - Backend Team
 * @version 1.0
 * @since 2026-07-16
 */
@Entity
@Table(
    name = "customers",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_customer_phone", columnNames = {"phone"}),
        @UniqueConstraint(name = "uk_customer_citizen_id", columnNames = {"citizen_id"}),
        @UniqueConstraint(name = "uk_customer_account_number", columnNames = {"account_number"})
    }
)
public class CustomerEntity {

    /**
     * Khóa chính dạng UUID - tự động sinh khi tạo mới bản ghi.
     * Không sử dụng auto-increment integer để tránh lộ thông tin.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * Họ và tên đầy đủ của khách hàng, trích xuất từ CCCD qua OCR.
     * Độ dài tối đa 100 ký tự theo tiêu chuẩn đặt tên Việt Nam.
     */
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    /**
     * Số điện thoại di động (10 số, đầu số VN hợp lệ).
     * Được sử dụng để xác thực OTP và liên lạc với khách hàng.
     */
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    /**
     * Địa chỉ email của khách hàng.
     * Dùng để gửi thông báo kích hoạt tài khoản.
     */
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    /**
     * Số căn cước công dân (CCCD) 12 chữ số.
     * Đây là mã định danh công dân chính thức theo quy định Bộ Công an.
     * Được mã hóa AES-256 trước khi lưu vào CSDL (PII data).
     */
    @Column(name = "citizen_id", nullable = false, length = 12)
    private String citizenId;

    /**
     * ID nội bộ của tài khoản - dùng cho hệ thống Core Banking tham chiếu.
     */
    @Column(name = "account_id")
    private UUID accountId;

    /**
     * Số tài khoản ngân hàng 16 chữ số (hiển thị cho khách hàng).
     * Format: "ABC" + YY + 11 số random, VD: "ABC2612345678901"
     */
    @Column(name = "account_number", length = 16)
    private String accountNumber;

    /**
     * Trạng thái hiện tại của hồ sơ eKYC / tài khoản.
     * Xem {@link AccountStatus} để biết danh sách các trạng thái.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountStatus status;

    /**
     * Thời điểm tạo bản ghi - tự động set bởi Hibernate.
     * Không cho phép cập nhật sau khi tạo (updatable = false).
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Thời điểm cập nhật gần nhất - tự động set bởi Hibernate khi có thay đổi.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ========== Constructors ==========

    /** Constructor mặc định cho JPA */
    protected CustomerEntity() {}

    /** Constructor đầy đủ tham số */
    public CustomerEntity(String fullName, String phone, String email,
                          String citizenId, AccountStatus status) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.citizenId = citizenId;
        this.status = status;
    }

    // ========== Getters & Setters ==========

    public UUID getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCitizenId() { return citizenId; }
    public void setCitizenId(String citizenId) { this.citizenId = citizenId; }
    public UUID getAccountId() { return accountId; }
    public void setAccountId(UUID accountId) { this.accountId = accountId; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "CustomerEntity{id=" + id + ", phone='" + phone + "', status=" + status + "}";
    }
}
