package com.abcbank.ekyc.dto.response;

import java.time.LocalDateTime;

/**
 * Lớp bọc phản hồi chung (Generic Response Wrapper) cho toàn bộ API.
 * Giúp chuẩn hóa định dạng trả về cho client theo cấu trúc nhất quán.
 *
 * @param <T> Kiểu dữ liệu của payload trả về
 *
 * @author ABC Bank Digital - Backend Team
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Tạo phản hồi thành công.
     *
     * @param data Dữ liệu trả về
     * @param <T>  Kiểu dữ liệu
     * @return ApiResponse thành công
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Thành công", data);
    }

    /**
     * Tạo phản hồi lỗi.
     *
     * @param message Thông báo lỗi
     * @param data    Dữ liệu đính kèm (có thể là danh sách lỗi validation)
     * @param <T>     Kiểu dữ liệu
     * @return ApiResponse lỗi
     */
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
