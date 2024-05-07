package com.lionsaid.admin.web.business.model.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scheduled_task_log")
public class ScheduledTaskLog {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private String taskId;
    private int status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long executionTime;
    private String executeInfo;
}
