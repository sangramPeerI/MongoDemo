package com.mongo.service.impl;

import com.mongo.dto.SearchCampaignRequestDto;
import com.mongo.exception.DateNotValidException;
import com.mongo.repository.CampaignRepository;
import com.mongo.service.CampaignService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoClient mongoClient;

    private MongoCollection<Document> collection;

    public CampaignServiceImpl() {
    }

    public CampaignServiceImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Autowired
    public CampaignServiceImpl(MongoClient mongoClient, @Value("${spring.data.mongodb.database}") String database,
                               @Value("${spring.data.mongodb.collection}") String collection) {
        this.collection = mongoClient.getDatabase(database).getCollection(collection);
    }

    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    /***
     *
     * This method searches for campaigns in the database using last updated date and channel as search criteria.
     * It takes a SearchCampaignRequestDto object as input which contains the campaign last updated start and end dates,
     * campaign channel type code, page number and page size for pagination.
     * It executes a MongoDB aggregation pipeline query with the search criteria and pagination stages, and returns a List of
     * Document objects containing the search results.
     * @param requestDto A SearchCampaignRequestDto object containing search criteria and pagination parameters
     * @return A List of Document objects containing the search results
     * @throws DateNotValidException If there is an error parsing the campaign last updated start or end dates
     */
    public List<Document> searchCampaignUsingDateAndChannel(SearchCampaignRequestDto requestDto) {

        log.debug("Searching for Campaigns using last updated date and channel");

        // Create the date range criteria
        Date endDtae = null;
        Date startDate = null;
        try {
            endDtae = formatter.parse(requestDto.getCampaignLastUpdatedEndDate());
            startDate = formatter.parse(requestDto.getCampaignLastUpdatedStartDate());
        } catch (ParseException e) {
            throw new DateNotValidException("Provided date is not valid.:"+ e.getMessage());
        }
        Criteria dateRangeCriteria = Criteria.where("cmnctn_last_updated_dt").gte(startDate).lte(endDtae);

        // Create the channel filter criteria
        Criteria channelCriteria = Criteria.where("cmnctn_chnl_type_cd").is(requestDto.getCampaignChannelTypeCd());

        // Create the exists criteria
        List<Criteria> existsCriteria = List.of(
                Criteria.where("campaign_name").exists(true),
                Criteria.where("vndr_job_name").exists(true),
                Criteria.where("vndr_nm").exists(true),
                Criteria.where("cmnctn_chnl_type_cd").exists(true),
                Criteria.where("vndr_job_id").exists(true),
                Criteria.where("campaign_identifier").exists(true)
        );

        // Combine the criteria into a compound criteria
        Criteria compoundCriteria = new Criteria()
                .andOperator(dateRangeCriteria, channelCriteria, new Criteria().orOperator(existsCriteria));

        // Create the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(compoundCriteria),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "cmnctn_last_updated_dt")),
                Aggregation.skip((long) ((requestDto.getPageNumber() - 1 < 0) ? 0 : (requestDto.getPageNumber() - 1)) * requestDto.getPageSize()),
                Aggregation.limit(requestDto.getPageSize())
        );

        // Execute the aggregation pipeline and return the results
        List<Document> results = mongoTemplate.aggregate(aggregation, "summary", Document.class).getMappedResults();

        log.info("Found Campaigns ::: {}", results.size());
        return results;
    }


    /***
     * Searches for campaigns using the given last_updated_date and extTaxonomyIdentifier values.
     * @param requestDto the DTO object containing the search criteria
     * @return a list of documents representing the search results
     * @throws DateNotValidException if there is an error while parsing the date fields in the request DTO
     */
    public List<Document> searchCampaignUsingDateAndNoTaxonomy(SearchCampaignRequestDto requestDto) {
        log.debug("Searching for Campaigns using last_updated_date and extTaxonomyIdentifier");

        // Create the date range criteria
        Date endDate = null;
        Date startDate = null;
        try {
            endDate = formatter.parse(requestDto.getCampaignLastUpdatedEndDate());
            startDate = formatter.parse(requestDto.getCampaignLastUpdatedStartDate());
        } catch (ParseException e) {
            throw new DateNotValidException("Provided date is not valid.:" + e.getMessage());
        }
        Criteria dateRangeCriteria = Criteria.where("cmnctn_last_updated_dt").gte(startDate).lte(endDate);

        // Create the vendor name filter criteria
        Criteria vendorNameCriteria = Criteria.where("vndr_nm").is(requestDto.getVendorName());

        // Create the exists criteria
        List<Criteria> existsCriteria = List.of(
                Criteria.where("ext_taxonomy_identifier").is(requestDto.getExtTaxonomyIdentifier()),
                Criteria.where("campaign_name").exists(true),
                Criteria.where("vndr_job_name").exists(true),
                Criteria.where("vndr_nm").exists(true),
                Criteria.where("cmnctn_chnl_type_cd").exists(true),
                Criteria.where("vndr_job_id").exists(true),
                Criteria.where("campaign_identifier").exists(true)
        );

        // Combine the criteria into a compound criteria
        Criteria compoundCriteria = new Criteria()
                .andOperator(dateRangeCriteria, vendorNameCriteria, new Criteria().orOperator(existsCriteria));

        // Create the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(compoundCriteria),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "cmnctn_last_updated_dt")),
                Aggregation.skip((long) ((requestDto.getPageNumber() - 1 < 0) ? 0 : (requestDto.getPageNumber() - 1)) * requestDto.getPageSize()),
                Aggregation.limit(requestDto.getPageSize())
        );

        // Execute the aggregation pipeline and return the results
        List<Document> results = mongoTemplate.aggregate(aggregation, "summary", Document.class).getMappedResults();

        log.info("Found Campaigns ::: {}", results.size());
        return results;
    }
}