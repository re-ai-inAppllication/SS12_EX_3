package com.abcbank.ekyc.entity;

/**
 * Enum định nghĩa các trạng thái của hồ sơ eKYC / tài khoản ngân hàng.
 *
 * <p>Vòng đời trạng thái (State Machine):
 * <pre>
 * PENDING → ACTIVE (khi xác minh thành công)
 * PENDING → REJECTED (khi xác minh thất bại hoặc nhân viên từ chối)
 * ACTIVE → SUSPENDED (khi phát hiện gian lận hoặc vi phạm)
 * </pre>
 * </p>
 *
 * @author ABC Bank Digital - Backend Team
 * @version 1.0
 */
public enum AccountStatus {

    /**
     * Hồ sơ đang chờ xử lý - eKYC chưa hoàn thành hoặc đang chờ duyệt thủ công.
     * Tài khoản chưa thể sử dụng giao dịch.
     */
    PENDING("Đang chờ xử lý"),

    /**
     * Tài khoản đã được kích hoạt thành công.
     * Khách hàng có thể thực hiện giao dịch ngay lập tức.
     */
    ACTIVE("Đã kích hoạt"),

    /**
     * Hồ sơ bị từ chối - Không đáp ứng điều kiện eKYC.
     * Lý do có thể: CCCD giả, không khớp khuôn mặt, có trong blacklist.
     */
    REJECTED("Bị từ chối"),

    /**
     * Tài khoản bị tạm khóa - Phát hiện hoạt động bất thường hoặc gian lận.
     * Cần liên hệ nhân viên ngân hàng để mở khóa.
     */
    SUSPENDED("Bị tạm khóa");

    /** Mô tả tiếng Việt của trạng thái, dùng trong thông báo cho khách hàng */
    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    /**
     * Lấy mô tả tiếng Việt của trạng thái.
     * @return Chuỗi mô tả bằng tiếng Việt
     */
    public String getDescription() {
        return description;
    }
}
