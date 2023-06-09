package com.mongo.service;

import com.mongo.dto.SearchCampaignRequestDto;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CampaignService {

    List<Document> searchCampaignUsingDateAndChannel(SearchCampaignRequestDto requestDto);

    List<Document> searchCampaignUsingDateAndNoTaxonomy(SearchCampaignRequestDto requestDto);
}