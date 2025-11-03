CREATE TABLE public.my_table (
    id serial4 NOT NULL,
    "name" varchar(255) NOT NULL,
    birthdate date NULL,
    address varchar(255) NULL,
    enabled bool NULL DEFAULT true,
    CONSTRAINT my_table_pkey PRIMARY KEY (id)
);