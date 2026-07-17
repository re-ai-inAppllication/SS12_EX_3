package com.abcbank.ekyc.exception;

/**
 * Các mã lỗi nghiệp vụ của hệ thống.
 *
 * @author ABC Bank Digital - Backend Team
 */
public enum ErrorCode {
    PHONE_ALREADY_EXISTS("Số điện thoại đã được sử dụng"),
    CITIZEN_ID_ALREADY_EXISTS("Số CCCD đã được sử dụng"),
    INVALID_CITIZEN_ID("Số CCCD không hợp lệ"),
    SYSTEM_ERROR("Lỗi hệ thống nội bộ");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
