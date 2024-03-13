insert into humans(id, name) values (nextval('humans_seq'), 'Pavel');
insert into pets(id, name, type, human_id) values (nextval('pets_seq'), 'Sphynx', 'CAT', nextval('humans_seq') - 50);
