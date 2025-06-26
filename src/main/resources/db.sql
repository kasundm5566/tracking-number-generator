CREATE TABLE tracking_record (
    id SERIAL PRIMARY KEY,
    tracking_number VARCHAR(16) NOT NULL UNIQUE CHECK (tracking_number ~ '^[A-Z0-9]{1,16}$'),
    order_created_at TIMESTAMPTZ NOT NULL,

    origin_country_id CHAR(2) NOT NULL,
    destination_country_id CHAR(2) NOT NULL,

    weight NUMERIC(10, 3) NOT NULL CHECK (weight >= 0),

    customer_id UUID NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_slug VARCHAR(255) NOT NULL,

	tracking_created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    -- Indexes for fast lookup. Might be useful later.
    CONSTRAINT chk_origin_country CHECK (origin_country_id ~ '^[A-Z]{2}$'),
    CONSTRAINT chk_dest_country CHECK (destination_country_id ~ '^[A-Z]{2}$')
);

CREATE SEQUENCE tracking_number_seq START 1;
