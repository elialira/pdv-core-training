CREATE TABLE brand (id varchar(255) not null,
                    activated boolean not null,
                    name varchar(255),
                    tenantid varchar(255),
                    version int8,
                    CONSTRAINT brand_pk PRIMARY KEY (id));
