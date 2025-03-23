DROP TABLE IF EXISTS db_letter.t_sms_template;
CREATE TABLE db_letter.t_sms_template
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    template_id      VARCHAR(32) COMMENT '模板ID，唯一标识',
    account_id       VARCHAR(18)  COMMENT '业务账号ID',
    template_name    VARCHAR(30)  COMMENT '模板名称',
    template_sign    VARCHAR(18)  COMMENT '短信签名',
    template_content TEXT         COMMENT '短信内容',
    template_auth    INT          COMMENT '模板权限（0：共享，1：专享）',
    verify_status    VARCHAR(2)   default '0' COMMENT '审核状态（1：待审核，2：审核通过，3：审核拒绝, 4、已撤回）',
    version          INT          COMMENT '模板版本号，从1开始递增',
    is_latest        BOOLEAN      DEFAULT TRUE COMMENT '是否是最新版本（TRUE：最新，FALSE：历史版本）',
    is_deleted       BOOLEAN      DEFAULT TRUE COMMENT '是否是删除',
    supplier         VARCHAR(2)   COMMENT '供应商',
    operate_flag     VARCHAR(2)   default '0' COMMENT '操作标识（0：变动、 1、已变更）',
    tem_id           INT   COMMENT '本地模版id',
    create_time      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '模板创建时间',
    update_time      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '模板更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信模板表';


DROP TABLE IF EXISTS db_letter.t_charge;
CREATE TABLE db_letter.t_charge
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    c_id      VARCHAR(32) COMMENT '收费业务id',
    c_type    VARCHAR(4)  COMMENT '收费业务类型',
    c_charge_pre    int  COMMENT '预占费用',
    c_charge    int  COMMENT '费用实收',
    supplier         VARCHAR(2)   COMMENT '供应商',
    tem_id           INT   COMMENT '本地模版id',
    create_time      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计费表';

DROP TABLE IF EXISTS db_letter.t_sms_send;
CREATE TABLE t_sms_send (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,      -- 自增主键 ID
    sms_id VARCHAR(40),                         -- 消息编号
    phone VARCHAR(20),                  -- 发送手机号
    status VARCHAR(2),    			   	-- 发送状态 0：成功；1：失败
    operation_account VARCHAR(50),             -- 操作账号
    letter_id VARCHAR(50),                     -- 函件编号
    tem_id int ,           -- 本地短信模版id
    sms_tem_id int ,           -- 平台短信模版id
    suplier VARCHAR(2),    			   	-- 平台类型
    sms_content TEXT,                         -- 文本内容
    sms_params TEXT,                         -- 参数内容
    resp_message TEXT,                         -- 具体内容描述
    receive_time TIMESTAMP , -- 接收时间
    charging_num int , -- 收费条数
    create_time      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '模板创建时间',
    update_time      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '模板更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE t_sms_send MODIFY COLUMN sms_id VARCHAR(40) COMMENT '消息编号';
ALTER TABLE t_sms_send MODIFY COLUMN phone VARCHAR(20) COMMENT '发送手机号';
ALTER TABLE t_sms_send MODIFY COLUMN status VARCHAR(2) COMMENT '发送状态 0：成功；1：失败';
ALTER TABLE t_sms_send MODIFY COLUMN operation_account VARCHAR(50) COMMENT '操作账号';
ALTER TABLE t_sms_send MODIFY COLUMN letter_id VARCHAR(50) COMMENT '函件编号';
ALTER TABLE t_sms_send MODIFY COLUMN tem_id INT COMMENT '本地短信模版ID';
ALTER TABLE t_sms_send MODIFY COLUMN sms_tem_id INT COMMENT '平台短信模版ID';
ALTER TABLE t_sms_send MODIFY COLUMN suplier VARCHAR(2) COMMENT '平台类型';
ALTER TABLE t_sms_send MODIFY COLUMN sms_content TEXT COMMENT '文本内容';
ALTER TABLE t_sms_send MODIFY COLUMN sms_params TEXT COMMENT '文本内容';
ALTER TABLE t_sms_send MODIFY COLUMN resp_message TEXT COMMENT '返回的短信内容';
ALTER TABLE t_sms_send MODIFY COLUMN receive_time TIMESTAMP COMMENT '接收时间';
ALTER TABLE t_sms_send MODIFY COLUMN charging_num int COMMENT '收费条数';
ALTER TABLE t_sms_send MODIFY COLUMN create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间，默认当前时间';
ALTER TABLE t_sms_send MODIFY COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间，更新时自动修改';