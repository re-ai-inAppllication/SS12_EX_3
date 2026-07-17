package com.abcbank.ekyc.repository;

import com.abcbank.ekyc.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface cho thực thể {@link CustomerEntity}.
 * Cung cấp các phương thức thao tác với CSDL (Spring Data JPA).
 *
 * @author ABC Bank Digital - Backend Team
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    /**
     * Tìm khách hàng theo số điện thoại.
     * @param phone Số điện thoại cần tìm
     * @return Optional chứa CustomerEntity nếu tìm thấy
     */
    Optional<CustomerEntity> findByPhone(String phone);

    /**
     * Tìm khách hàng theo số CCCD.
     * @param citizenId Số CCCD cần tìm
     * @return Optional chứa CustomerEntity nếu tìm thấy
     */
    Optional<CustomerEntity> findByCitizenId(String citizenId);

    /**
     * Kiểm tra xem số điện thoại HOẶC số CCCD đã tồn tại trong hệ thống chưa.
     * @param phone     Số điện thoại
     * @param citizenId Số CCCD
     * @return true nếu ít nhất 1 trong 2 đã tồn tại
     */
    boolean existsByPhoneOrCitizenId(String phone, String citizenId);
}
