package com.mongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCampaignRequestDto {

    private String campaignChannelTypeCd;
    private String campaignLastUpdatedStartDate;
    private String campaignLastUpdatedEndDate;
    private String vendorName;
    private String constituentIdentifier;
    private String extTaxonomyIdentifier;
    private Integer pageNumber = 0;
    private Integer pageSize = 50;
}