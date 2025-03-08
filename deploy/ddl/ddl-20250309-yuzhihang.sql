DROP TABLE IF EXISTS db_letter.t_sms_template;
CREATE TABLE db_letter.t_sms_template
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    template_id      VARCHAR(32) COMMENT '模板ID，唯一标识',
    account_id       VARCHAR(18) NOT NULL COMMENT '业务账号ID',
    template_name    VARCHAR(30) NOT NULL COMMENT '模板名称',
    template_sign    VARCHAR(18) NOT NULL COMMENT '短信签名',
    template_content TEXT        NOT NULL COMMENT '短信内容',
    template_auth    INT         NOT NULL COMMENT '模板权限（0：共享，1：专享）',
    verify_status     VARCHAR(2)  NOT NULL default '0' COMMENT '审核状态（0：待审核，1：审核通过，2：审核拒绝, 3、已撤回）',
    version          INT         NOT NULL COMMENT '模板版本号，从1开始递增',
    is_latest        BOOLEAN     NOT NULL DEFAULT TRUE COMMENT '是否是最新版本（TRUE：最新，FALSE：历史版本）',
    is_deleted       BOOLEAN     NOT NULL DEFAULT TRUE COMMENT '是否是删除',
    create_time       TIMESTAMP            DEFAULT CURRENT_TIMESTAMP COMMENT '模板创建时间',
    update_time       TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '模板更新时间',

    INDEX            idx_account_id (account_id),
    INDEX            idx_template_name (template_name),
    INDEX            idx_audit_status (audit_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信模板表';
