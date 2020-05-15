package com.lemon.cas.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author itar
 * @mail wuhandzy@gmail.com
 * @date 2020-05-14 03:37:34
 * @since jdk1.8
 */
@Setter
@Getter
public class UserBaseInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String fId;
    /**
     *
     */
    private String fUserName;
    /**
     * 航道
     */
    private String fProductGroup;
    /**
     *
     */
    private Boolean fStatus;
    /**
     *
     */
    private String fPassword;
    /**
     *
     */
    private String fMobile;
    /**
     *
     */
    private String fEmployId;
    /**
     * 名称简写
     */
    private String fShorthand;
    /**
     *
     */
    private String fRealName;
    /**
     *
     */
    private String fUcId;
    /**
     * AD域账号全局唯一名称
     */
    private String fGlobalName;
    /**
     *
     */
    private String fMail;
    /**
     *
     */
    private String fOrgId;
    /**
     * 集团总部/地区公司（XX地产），来源PS的HPS_COM_LEVEL
     */
    private String fCompany;
    /**
     *
     */
    private Boolean fType;
    /**
     *
     */
    private String fJobLevel;
    /**
     *
     */
    private Long fMurphyId;
    /**
     *
     */
    private String fPsId;
    /**
     *
     */
    private String fAdDn;
    /**
     *
     */
    private String fDesc;
    /**
     *
     */
    private Date fLastLoginDate;
    /**
     *
     */
    private String fLastLoginIp;
    /**
     *
     */
    private Date fCreatedDate;
    /**
     *
     */
    private String fCreatorId;
    /**
     * 修改人
     */
    private String fModifierId;
    /**
     * 修改时间
     */
    private Date fModifiedDate;
    /**
     * 预计生效日期
     */
    private Date fEffectiveDate;
    /**
     * 预计失效日期
     */
    private Date fExpiryDate;
    /**
     * 手机号绑定状态，0-未绑定，1-已绑定
     */
    private Boolean fMobileStatus;
    /**
     * 邮箱绑定状态，0-未绑定，1-已绑定
     */
    private Boolean fMailStatus;
    /**
     * 一级部门（职能），来源于PS 的HPS_DEPT_LEVEL
     */
    private String fDeptId;
    /**
     * 二级部门（中心）,来源于PS的 HPS_DEPT_LEVEL3
     */
    private String fRegionId;
    /**
     * 外部数据更新数据
     */
    private Date fExternalModifiedDate;
    /**
     * 入职生效时间
     */
    private Date fEntryModifiedDate;
    /**
     * 账号来源
     */
    private String fOrigin;
    /**
     * 公司名称
     */
    private String fCompanyName;
    /**
     * 直线经理：对应人员主键Id
     */
    private String fLineManagerId;
    /**
     * 部门负责人
     */
    private String fManagerId;
}
