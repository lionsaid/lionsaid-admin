package com.lionsaid.admin.web.model.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sys_scheduled_task")
public class ScheduledTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "scheduled_task_id", allocationSize = 1, initialValue = 100000)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String cron;
    private String taskType;
    @Column(nullable = false,columnDefinition = "0停止 1开始")
    private Integer taskStatus;
    private String taskInfo;
}
