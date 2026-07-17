package com.abcbank.ekyc.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation cho annotation {@link ValidCitizenId}.
 *
 * <p>Thực hiện các kiểm tra:
 * <ol>
 *   <li>Giá trị không null và không rỗng</li>
 *   <li>Phải đúng 12 ký tự</li>
 *   <li>Chỉ gồm các chữ số (0-9)</li>
 *   <li>Không phải toàn số 0 ("000000000000")</li>
 * </ol>
 * </p>
 *
 * @author ABC Bank Digital - Backend Team
 * @see ValidCitizenId
 */
public class CitizenIdValidator implements ConstraintValidator<ValidCitizenId, String> {

    /** Độ dài chuẩn của số CCCD theo quy định Bộ Công an */
    private static final int CITIZEN_ID_LENGTH = 12;

    /** Regex: chỉ chứa chữ số */
    private static final String NUMERIC_PATTERN = "^[0-9]+$";

    /** Số CCCD không hợp lệ (toàn số 0) */
    private static final String INVALID_ALL_ZEROS = "000000000000";

    @Override
    public void initialize(ValidCitizenId constraintAnnotation) {
        // Không cần khởi tạo thêm
    }

    /**
     * Kiểm tra tính hợp lệ của số CCCD.
     *
     * @param citizenId Giá trị CCCD cần kiểm tra
     * @param context   Context của constraint validator
     * @return {@code true} nếu CCCD hợp lệ; {@code false} nếu không hợp lệ
     */
    @Override
    public boolean isValid(String citizenId, ConstraintValidatorContext context) {
        // Bỏ qua kiểm tra nếu giá trị null (để @NotBlank xử lý riêng)
        if (citizenId == null) {
            return true;
        }

        // Kiểm tra độ dài phải đúng 12 ký tự
        if (citizenId.length() != CITIZEN_ID_LENGTH) {
            customizeMessage(context, "Số CCCD phải có đúng " + CITIZEN_ID_LENGTH + " chữ số");
            return false;
        }

        // Kiểm tra chỉ gồm chữ số
        if (!citizenId.matches(NUMERIC_PATTERN)) {
            customizeMessage(context, "Số CCCD chỉ được chứa các chữ số (0-9)");
            return false;
        }

        // Kiểm tra không phải toàn số 0 (số CCCD không tồn tại)
        if (INVALID_ALL_ZEROS.equals(citizenId)) {
            customizeMessage(context, "Số CCCD không hợp lệ");
            return false;
        }

        return true;
    }

    /**
     * Ghi đè message mặc định với message tùy chỉnh chi tiết hơn.
     *
     * @param context Context của constraint validator
     * @param message Message lỗi tùy chỉnh
     */
    private void customizeMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }
}
