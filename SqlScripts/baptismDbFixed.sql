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