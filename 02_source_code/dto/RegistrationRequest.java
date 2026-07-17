package com.abcbank.ekyc.dto.request;

import com.abcbank.ekyc.validation.ValidCitizenId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) chứa thông tin đăng ký mở tài khoản từ phía khách hàng.
 *
 * <p>Tất cả các trường đều có validation chặt chẽ theo business rules của ABC Bank:</p>
 * <ul>
 *   <li>fullName: 2-100 ký tự, không được để trống</li>
 *   <li>phone: Số điện thoại VN hợp lệ (10 số, đầu số 03x/07x/08x/09x)</li>
 *   <li>email: Địa chỉ email hợp lệ theo chuẩn RFC 5322</li>
 *   <li>citizenId: CCCD Việt Nam 12 chữ số (xem {@link ValidCitizenId})</li>
 * </ul>
 *
 * @author ABC Bank Digital - Backend Team
 * @version 1.0
 */
public class RegistrationRequest {

    /**
     * Họ và tên đầy đủ của khách hàng.
     * Phải khớp với thông tin trên CCCD (sau khi đã OCR).
     */
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ và tên phải từ 2 đến 100 ký tự")
    @Pattern(
        regexp = "^[a-zA-ZÀ-ỹà-ỹĂăÂâĐđÊêÔôƠơƯưẠ-ỹ\\s]+$",
        message = "Họ và tên chỉ được chứa chữ cái và khoảng trắng"
    )
    private String fullName;

    /**
     * Số điện thoại di động Việt Nam.
     * Chấp nhận các đầu số: 03x, 07x, 08x, 09x (theo quy hoạch số VNPT/Viettel/MobiFone).
     * Không cần nhập mã quốc gia (+84).
     */
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(
        regexp = "^(03[2-9]|07[06-9]|08[1-9]|09[0-9])[0-9]{7}$",
        message = "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại VN 10 số (VD: 0901234567)"
    )
    private String phone;

    /**
     * Địa chỉ email của khách hàng.
     * Dùng để gửi thông báo kích hoạt và email chào mừng.
     */
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Địa chỉ email không hợp lệ")
    @Size(max = 255, message = "Email không được vượt quá 255 ký tự")
    private String email;

    /**
     * Số Căn cước công dân (CCCD) 12 chữ số.
     * Sử dụng custom annotation {@link ValidCitizenId} để validate nghiêm ngặt.
     */
    @NotBlank(message = "Số CCCD không được để trống")
    @ValidCitizenId
    private String citizenId;

    // ========== Constructors ==========

    public RegistrationRequest() {}

    public RegistrationRequest(String fullName, String phone, String email, String citizenId) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.citizenId = citizenId;
    }

    // ========== Getters & Setters ==========

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCitizenId() { return citizenId; }
    public void setCitizenId(String citizenId) { this.citizenId = citizenId; }

    @Override
    public String toString() {
        // Ẩn citizenId trong log để bảo mật PII (Personally Identifiable Information)
        return "RegistrationRequest{fullName='" + fullName + "', phone='" + phone +
               "', email='" + email + "', citizenId='***MASKED***'}";
    }
}
