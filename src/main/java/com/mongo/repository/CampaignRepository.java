package com.mongo.repository;

import com.mongo.collection.Campaign;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends MongoRepository<Campaign, String> {

    List<Campaign> getCampaignByCampaignNameOrVndrJobName();
}