CREATE DATABASE IF NOT EXISTS `team_green`;
USE `team_green`;

CREATE TABLE `locations` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `location` varchar(255) DEFAULT NULL,
    `imageURL` varchar(255) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `greenhouses` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `location_id` bigint(20) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `location` varchar(255) DEFAULT NULL,
    `imageURL` varchar(255) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`location_id`) REFERENCES locations(`id`)
);

CREATE TABLE `nodes` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `greenhouse_id` bigint(20) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`location_id`) REFERENCES greenhouses(`id`)
);

CREATE TABLE `sensors` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `data_type` varchar(255) DEFAULT NULL,
    `min_value` double DEFAULT NULL,
    `max_value` double DEFAULT NULL,
    `technology` varchar(255) DEFAULT NULL,
    `working_voltage` double DEFAULT NULL,
    `dimensions` varchar(255) DEFAULT NULL,
    `special_facts` varchar(255) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);


CREATE TABLE `node_sensors` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `node_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `sensor_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `min_value` double DEFAULT NULL,
    `max_value` double DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`node_id`) REFERENCES nodes(`id`),
    FOREIGN KEY (`sensor_id`) REFERENCES sensors(`id`)
);


CREATE TABLE `data` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `node_sensor_id` bigint(20) DEFAULT NULL,
    `data` double DEFAULT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`node_sensor_id`) REFERENCES node_sensors(`id`)
);




