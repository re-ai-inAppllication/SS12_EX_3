package com.abcbank.ekyc.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation dùng để validate số Căn cước công dân (CCCD) Việt Nam.
 *
 * <p>Quy tắc hợp lệ:
 * <ul>
 *   <li>Phải đúng 12 chữ số</li>
 *   <li>Chỉ chứa các ký tự số (0-9)</li>
 *   <li>Không được là "000000000000" (số CCCD toàn 0)</li>
 * </ul>
 * </p>
 *
 * <p>Ví dụ sử dụng:
 * <pre>
 * {@literal @}ValidCitizenId
 * private String citizenId;
 * </pre>
 * </p>
 *
 * @author ABC Bank Digital - Backend Team
 */
@Documented
@Constraint(validatedBy = CitizenIdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCitizenId {

    /** Thông báo lỗi mặc định khi CCCD không hợp lệ */
    String message() default "Số CCCD phải gồm đúng 12 chữ số và hợp lệ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
