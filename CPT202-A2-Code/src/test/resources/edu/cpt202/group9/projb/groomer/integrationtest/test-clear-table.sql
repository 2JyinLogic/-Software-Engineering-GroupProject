DELETE FROM `groomer_schedule` WHERE groomer_id IN (SELECT id FROM `groomer`);
DELETE FROM `groomer`;