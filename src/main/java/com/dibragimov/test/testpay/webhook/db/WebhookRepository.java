package com.dibragimov.test.testpay.webhook.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookRepository extends JpaRepository<WebhookHolder, String> {
}
