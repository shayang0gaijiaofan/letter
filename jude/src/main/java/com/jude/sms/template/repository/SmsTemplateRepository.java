package com.jude.sms.template.repository;


import com.jude.sms.template.entity.SmsTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmsTemplateRepository extends JpaRepository<SmsTemplateEntity, Integer>, JpaSpecificationExecutor<SmsTemplateEntity> {
    @Query("SELECT s FROM SmsTemplateEntity s WHERE s.verifyStatus = ?1 and s.isDeleted = false ")
    List<SmsTemplateEntity> findByVerifyStatus(String verifyStatus);
}
