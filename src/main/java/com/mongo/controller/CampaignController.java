package com.mongo.controller;

import com.mongo.dto.SearchCampaignRequestDto;
import com.mongo.service.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
@Slf4j
@Api(tags = "Campaign Search API", value = "Controller handles different Campaign search functions")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @PostMapping("/taxonomy")
    @ApiOperation(value = "Returns list of campaigns based on ext taxonomy identifier and campaign's last updated date", notes = "Method to return list of campaigns based on ext taxonomy identifier and and campaign's last updated date.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found")})
    public List<Document> searchCampaignUsingDateAndNoTaxonomy(@RequestBody @Validated SearchCampaignRequestDto requestDto) {
        log.info("Request parameters:{}", requestDto);
        return campaignService.searchCampaignUsingDateAndNoTaxonomy(requestDto);
    }

    @PostMapping("/channels")
    @ApiOperation(value = "Returns list of campaigns based on channel and campaign's last updated date", notes = "Method to return list of campaigns based on channel and campaign's last updated date.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found")})
    public List<Document> searchCampaignUsingDateAndChannel(@RequestBody @Validated SearchCampaignRequestDto requestDto) {
        log.info("Request parameters:{}", requestDto);
        return campaignService.searchCampaignUsingDateAndChannel(requestDto);
    }
}