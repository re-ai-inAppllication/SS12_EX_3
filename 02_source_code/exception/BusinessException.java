package com.abcbank.ekyc.exception;

/**
 * Exception cho các lỗi liên quan đến nghiệp vụ (Business Rules).
 * Kế thừa RuntimeException để Spring tự động rollback transaction.
 *
 * @author ABC Bank Digital - Backend Team
 */
public class BusinessException extends RuntimeException {
    
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(customMessage != null ? customMessage : errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
