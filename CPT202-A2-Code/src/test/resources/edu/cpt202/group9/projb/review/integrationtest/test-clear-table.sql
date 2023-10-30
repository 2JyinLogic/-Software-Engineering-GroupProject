DELETE FROM `service_reviews` WHERE service_id IN (SELECT id FROM `service`);
DELETE FROM `service`;