package com.lionsaid.admin.web.business.model.po;

import com.lionsaid.admin.web.common.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sys_mail_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMailLog extends Auditable {
    @Id
    private String id;
    private String toAddress;
    @Lob
    private String mailMessage;
    private String type;
    private LocalDate localDate;
    private LocalDateTime expiredDateTime;
}
