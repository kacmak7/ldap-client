DROP TABLE fathers;
DROP TABLE mothers;

CREATE TABLE addresses(
    id SERIAL PRIMARY KEY,
    street character varying(40),
    place character varying(50),
    postcode character varying(6),
    first_number character varying(6),
    last_number character varying(6)
);

CREATE TABLE result(
    name character varying(100),
    row_count integer,
    expected_count integer
);

-- Migration preparation --
INSERT INTO result VALUES ('table_persons', null, (SELECT count(1) FROM persons));
INSERT INTO result VALUES ('table_families', null, (SELECT count(1) FROM families));
CREATE TABLE temp(
    val integer
);
INSERT INTO temp SELECT count(1) FROM families;
INSERT INTO temp SELECT count(1) FROM others_wo_families;
INSERT INTO result VALUES ('table_addresses', null, (SELECT sum(val) FROM temp));
DROP TABLE temp;
INSERT INTO result VALUES ('others_wo_families', null, (SELECT count(1) FROM others_wo_families));
INSERT INTO result VALUES ('parents', null, (SELECT count(1) FROM parents));
INSERT INTO result VALUES ('others', null, (SELECT count(1) FROM others));
INSERT INTO result VALUES ('children', null, (SELECT count(1) FROM children));

-- MIGRATION --

-- Persons --

-- No change

-- Families --
ALTER TABLE families ADD address_id bigint;
ALTER TABLE families ADD CONSTRAINT families_addresses_fk FOREIGN KEY (address_id) REFERENCES addresses(id);

ALTER TABLE families DROP CONSTRAINT fk2eeee3022f5920d2;
ALTER TABLE families DROP CONSTRAINT fk2eeee30276839f68;
ALTER TABLE families ADD CONSTRAINT families_wife_fk FOREIGN KEY (wife_id) REFERENCES persons(id) ON DELETE SET NULL;
ALTER TABLE families ADD CONSTRAINT families_husband_fk FOREIGN KEY (husband_id) REFERENCES persons(id) ON DELETE SET NULL;

-- Rewriting family data to addressee table --
ALTER TABLE addresses ADD fam_id bigint;

INSERT INTO addresses (street, place, postcode, first_number, last_number, fam_id)
SELECT street, place, postcode, first_number, last_number, id FROM families;

UPDATE families SET address_id = (SELECT id FROM addresses WHERE families.id = fam_id);

ALTER TABLE addresses DROP COLUMN fam_id;

ALTER TABLE families DROP COLUMN street;
ALTER TABLE families DROP COLUMN place;
ALTER TABLE families DROP COLUMN postcode;
ALTER TABLE families DROP COLUMN first_number;
ALTER TABLE families DROP COLUMN last_number;

-- Rewriting others_wo_families data to addresses table --
ALTER TABLE addresses ADD per_id bigint;

INSERT INTO addresses (street, place, postcode, first_number, last_number, per_id)
SELECT street, place, postcode, first_number, last_number, id FROM others_wo_families;

ALTER TABLE others_wo_families ADD address_id bigint;
ALTER TABLE others_wo_families ADD CONSTRAINT otherswofamilies_addresses_fk FOREIGN KEY (address_id) REFERENCES addresses(id);
UPDATE others_wo_families SET address_id = (SELECT id FROM addresses WHERE others_wo_families.id = addresses.per_id);

ALTER TABLE addresses DROP COLUMN per_id;

-- Intention_types table --
ALTER TABLE INTENTION_TYPES DROP CONSTRAINT color_unique;

-- fixed baptism Tome --
UPDATE remainings SET baptism_id = null WHERE id in (85676, 104733, 127022);
DELETE FROM public.baptismes WHERE id in (85677, 104734, 127021);

INSERT INTO public.baptismes(
	id, file_number, place, confirmation_remarks, marriage_remarks, other_remarks, date, reason)
	VALUES (nextval('hibernate_sequence'), 6, 'par. tut.', 'Brak wpisu', 'Brak wpisu', '', '1997-03-31', '');
UPDATE remainings SET baptism_id = LASTVAL() WHERE id  = 85676;
	
INSERT INTO public.baptismes(
	id, file_number, place, confirmation_remarks, marriage_remarks, other_remarks, date, reason)
	VALUES (nextval('hibernate_sequence'), 79, 'par. tut.', 'brak adnotacji', 'brak adnotacji', '', '1996-10-13', 'Do Sakramentu Bierzmowania');
UPDATE remainings SET baptism_id = LASTVAL() WHERE id  = 104733;
	
INSERT INTO public.baptismes(
	id, file_number, place, confirmation_remarks, marriage_remarks, other_remarks, date, reason)
	VALUES (nextval('hibernate_sequence'), 118, 'par. tut.', 'bierzmowana dn. 24.03.1998, par. tut.', 'brak', '', '1982-09-12', 'do ślubu kościelnego');
UPDATE remainings SET baptism_id = LASTVAL() WHERE id  = 127022;