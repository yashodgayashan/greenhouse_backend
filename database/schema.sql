CREATE DATABASE IF NOT EXISTS `team_green`;
USE `team_green`;

CREATE TABLE `locations` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `location` varchar(255) DEFAULT NULL,
    `imageURL` varchar(255) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `latitude` double DEFAULT NULL,
    `longatude` double DEFAULT NULL,
    `province` varchar(255) DEFAULT NULL,
    `district` varchar(255) DEFAULT NULL,
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
    `length` double DEFAULT NULL,
    `width` double DEFAULT NULL,
    `height` double DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `nodes` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `greenhouse_id` bigint(20) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
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
    `node_id` bigint(20) NOT NULL,
    `sensor_id` bigint(20) NOT NULL,
    `min_value` double DEFAULT NULL,
    `max_value` double DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `data` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `node_sensor_id` bigint(20) DEFAULT NULL,
    `data` double DEFAULT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`node_sensor_id`) REFERENCES node_sensors(`id`)
);

CREATE TABLE `plant_info` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `species` varchar(255) DEFAULT NULL,
    `plant_duration` double DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `min_temperature_low` double DEFAULT NULL,
    `min_temperature_high` double DEFAULT NULL,
    `max_temperature_low` double DEFAULT NULL,
    `max_temperature_high` double DEFAULT NULL,
    `spacing` double DEFAULT NULL,
    `plants_per_pot` int(10) DEFAULT NULL,
    `min_no_of_harvest` int(10) DEFAULT NULL,
    `max_no_of_harvest` int(10) DEFAULT NULL,
    `average_weight_of_harvest` double DEFAULT NULL,
    `stage1_duration` double DEFAULT NULL,
    `stage2_duration` double DEFAULT NULL,
    `stage3_duration` double DEFAULT NULL,
    `stage4_duration` double DEFAULT NULL,
    `soil` text DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `fertlizer` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `plant_id` bigint(20) NOT NULL,
    `stage` int(10) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `medium` varchar(255) DEFAULT NULL,
    `quantity` double DEFAULT NULL,
    `frequency` varchar(255) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `disease` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `plant_id` bigint(20) DEFAULT NULL,
    `level` varchar(255) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `disease_reasons` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `disease_id` bigint(20) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `disease_medicines` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `disease_id` bigint(20) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `disease_precausions` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `disease_id` bigint(20) DEFAULT NULL,
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `greenhouse_plant` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `plant_id` bigint(20) DEFAULT NULL,
    `greenhouse_id` bigint(20) DEFAULT NULL,
    `numberOfPlants` bigint(20) DEFAULT NULL,
    `is_completed` tinyint(4) NOT NULL DEFAULT '0',
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
    `start_at` timestamp  DEFAULT NULL,
    `ended_at` timestamp  DEFAULT NULL,
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `plant` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `greenhouse_plant_id` bigint(20) DEFAULT NULL,
    `greenhouse_id` bigint(20) DEFAULT NULL,
    `plant_id` bigint(20) DEFAULT NULL,
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);


CREATE TABLE `plant_disease` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `plant_id` bigint(20) DEFAULT NULL,
    `plant_info_id` bigint(20) DEFAULT NULL,
    `disease_id` bigint(20) DEFAULT NULL,
    `solution_id` bigint(20) DEFAULT NULL,
    `applied_date` timestamp  DEFAULT NULL,
    `resolved_date` timestamp  DEFAULT NULL,
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `plant_harvest` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `plant_id` bigint(20) DEFAULT NULL,
    `count` bigint(20) DEFAULT NULL,
    `date` timestamp  DEFAULT NULL,
    `modified_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

CREATE TABLE `crop` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `plant_id` bigint(20) DEFAULT NULL,
    `length` double DEFAULT NULL,
    `height` double DEFAULT NULL,
    `depth` double DEFAULT NULL
    PRIMARY KEY (`id`)
);

CREATE TABLE `crop_info` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `crop_id` bigint(20) DEFAULT NULL,
    `length` double DEFAULT NULL,
    `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);


