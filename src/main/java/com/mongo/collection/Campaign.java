package com.mongo.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

        private String extTaxonomyIdentifier;
        private String taxonomyIdentifier;
        private String taxonomyPortfolio;
        private String taxonomyProgramName;
        private String taxonomyCmnctnOwner;
        private String taxonomyCmnctnFormat;
        private String taxonomyCmnctnGoal;
        private String taxonomyCmnctnContentTopic;
        private String taxonomyCallToAction;
        private String taxonomyAudienceType;
        private String lobNm;
        private String taxonomyRegulatoryType;
        private String taxonomyTrigger;
        private String taxonomyFrequency;
        private Date taxonomyCmnctnSentDt;
        private String cmnctnActivity;
        private String cmnctnIdentifier;
        private String constituentIdentifier;
        private String proxyId;
        private String cmnctnLangName;
        private String cmnctnChnlTypeCd;
        private String cmnctnAddress;
        private String mediaName;
        private String chnlDrctnTypeDesc;
        private String cmnctnMgrName;
        private String cmnctnMgrId;
        private String cmnctnMgrCampaignId;
        private String cmnctnMgrCampaignName;
        private String constituentRoleDesc;
        private String cmnctnJobUniqueId;
        private String campaignIdentifier;
        private String campaignName;
        private String vndrJobId;
        private String vndrJobName;
        private String vndrNm;
        private String srcCmnctnId;
        private String srcCmnctnParentPurposeName;
        private String srcCmnctnChildPurposeName;
        private String srcCmnctnParentDisplayName;
        private String srcCmnctnChildDisplayName;
        private String cmnctnActivityStatusDesc;
        private String cmnctnActivityDts;
        private String cmnctnActivityUrl;
        private String cmnctnContentIndicator;
        private Date cmnctnLastUpdatedDt;
        private String extReferenceIdentifier;
        private String agentName;
        private String agentTypeCode;
        private String agentTypeDisplay;
        private int loadDay;
        private int loadMonth;
        private int loadYear;

}
