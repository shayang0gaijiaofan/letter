package com.jude.sms.record.send.repository;


import com.jude.sms.record.send.entity.SmsSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SmsSendRepository extends JpaRepository<SmsSend, Integer>, JpaSpecificationExecutor<SmsSend> {
    @Query("SELECT s FROM SmsSend  s WHERE  s.smsId = ?1  ")
    SmsSend findBySmsIds(String smsId);
}
