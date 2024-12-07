BEGIN;


CREATE TABLE IF NOT EXISTS public.booking
(
    vehicle_id integer NOT NULL,
    booked_at timestamp without time zone NOT NULL,
    start_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone NOT NULL,
    returned_at timestamp without time zone,
    status text COLLATE pg_catalog."default" NOT NULL,
    cost double precision,
    user_id integer NOT NULL,
    id serial NOT NULL,
    CONSTRAINT booking_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id integer NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    phone text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    is_admin boolean NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT user_email_key UNIQUE (email),
    CONSTRAINT user_phone_key UNIQUE (phone)
);

CREATE TABLE IF NOT EXISTS public.vehicle
(
    car_model_id integer NOT NULL,
    serial_number text COLLATE pg_catalog."default",
    color text COLLATE pg_catalog."default",
    id serial NOT NULL,
    CONSTRAINT vehicle_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.car_model
(
    name text COLLATE pg_catalog."default",
    model_year smallint,
    price double precision,
    company text COLLATE pg_catalog."default",
    type text COLLATE pg_catalog."default",
    id serial NOT NULL,
    CONSTRAINT car_model_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.agreement
(
    booking_id integer NOT NULL,
    terms text COLLATE pg_catalog."default",
    issued_at timestamp without time zone NOT NULL,
    id serial NOT NULL,
    CONSTRAINT agreement_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.invoice
(
    booking_id integer NOT NULL,
    total_price double precision,
    issued_at timestamp without time zone NOT NULL,
    late_fees double precision,
    id serial NOT NULL,
    CONSTRAINT invoice_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.booking
    ADD CONSTRAINT booking_user_id_fkey FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.booking
    ADD CONSTRAINT booking_vehicle_id_fkey FOREIGN KEY (vehicle_id)
    REFERENCES public.vehicle (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.vehicle
    ADD CONSTRAINT vehicle_car_model_id_fkey FOREIGN KEY (car_model_id)
    REFERENCES public.car_model (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.agreement
    ADD CONSTRAINT agreement_booking_id_fkey FOREIGN KEY (booking_id)
    REFERENCES public.booking (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.invoice
    ADD CONSTRAINT invoice_booking_id_fkey FOREIGN KEY (booking_id)
    REFERENCES public.booking (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

END;
