CREATE SEQUENCE hibernate_sequence start 1 increment 1;

CREATE TABLE association_value_entry (
    id int8 NOT NULL,
    association_key VARCHAR(255) NOT NULL,
    association_value VARCHAR(255),
    saga_id VARCHAR(255) NOT NULL,
    saga_type VARCHAR(255),
    CONSTRAINT association_value_entry_pk PRIMARY KEY (id));

CREATE TABLE domain_event_entry (
    global_index INT8 NOT NULL,
    event_identifier VARCHAR(255) NOT NULL,
    meta_data OID,
    payload OID NOT NULL,
    payload_revision VARCHAR(255),
    payload_type VARCHAR(255) NOT NULL,
    time_stamp VARCHAR(255) NOT NULL,
    aggregate_identifier VARCHAR(255) NOT NULL,
    sequence_number INT8 NOT NULL,
    type VARCHAR(255),
    CONSTRAINT domain_event_entry_pk PRIMARY KEY (global_index));

create table saga_entry (
    saga_id VARCHAR(255) NOT NULL,
    revision VARCHAR(255),
    saga_type VARCHAR(255),
    serialized_saga OID,
    CONSTRAINT saga_entry_pk primary key (saga_id));

create table snapshot_event_entry (
    aggregate_identifier varchar(255) not null,
    sequence_number int8 not null,
    type varchar(255) not null,
    event_identifier varchar(255) not null,
    meta_data oid,
    payload oid not null,
    payload_revision varchar(255),
    payload_type varchar(255) not null,
    time_stamp varchar(255) not null,
    CONSTRAINT snapshot_event_entry_pk primary key (aggregate_identifier, sequence_number, type));

create table token_entry (
    processor_name varchar(255) not null,
    segment int4 not null,
    owner varchar(255),
    timestamp varchar(255) not null,
    token oid, token_type varchar(255),
    CONSTRAINT token_entry_pk primary key (processor_name, segment));

create index IDXk45eqnxkgd8hpdn6xixn8sgft
    on association_value_entry (saga_type, association_key, association_value);

create index IDXgv5k1v2mh6frxuy5c0hgbau94
    on association_value_entry (saga_id, saga_type);

alter table if exists domain_event_entry
    add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number);

alter table if exists domain_event_entry
    add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier);

alter table if exists snapshot_event_entry
    add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier);