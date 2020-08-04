-- Pre-create the below table in PostgreSQL 

CREATE TABLE listing (
    id             BIGINT NOT NULL,
    listing_url    VARCHAR(250) NOT NULL,
    amenities      VARCHAR(250),
    minimum_nights INTEGER,
    maximum_nights INTEGER,
    price          NUMERIC(7, 2),
	weekly_price   NUMERIC(7, 2),
	city           VARCHAR(100),
	country        VARCHAR(100),
	CONSTRAINT pk_id_listing PRIMARY KEY(id)
);