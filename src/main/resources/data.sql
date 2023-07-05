DELETE FROM user_service_roles;
DELETE FROM saved_login;

DELETE FROM user;
DELETE FROM service_role;

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
	VALUES ('1234567891234', NULL, 'Stefan', 'Vlajkovic', NULL, '$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');
INSERT INTO service_role(id, attributes, role, service) VALUES (1, '{"schoolId":1}', "ROLE_ADMIN", "school");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('1234567891234', 1);

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
	VALUES ('9876543214321', NULL, 'Nikola', 'Nikolic', NULL, '$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');
INSERT INTO service_role(id, attributes, role, service) VALUES (2, '{"schoolId":2}', "ROLE_ADMIN", "school");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('9876543214321', 2);

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
	VALUES ('123412341234', NULL, 'Petar', 'Petrovic', NULL, '$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');
INSERT INTO service_role(id, attributes, role, service) VALUES (3, '{"schoolId":2}', "ROLE_STUDENT", "school");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('123412341234', 3);

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
	VALUES ('123412341233', '123412341231', 'Nemanja', 'Murgaski', NULL, '$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');
INSERT INTO service_role(id, attributes, role, service) VALUES (4, '{"schoolId":1}', "ROLE_STUDENT", "school");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('123412341233', 4);

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
	VALUES ('123412341231', NULL, 'Ugljesa', 'Murgaski', NULL, '$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');
INSERT INTO service_role(id, attributes, role, service) VALUES (5, '{"schoolId":1}', "ROLE_STUDENT", "school");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('123412341231', 5);

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
	VALUES ('1102997105029', NULL, 'Jovana', 'Milisic', NULL, '$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');
INSERT INTO service_role(id, attributes, role, service) VALUES (6, '{"companyId":1}', "ROLE_ADMIN", "apr");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('1102997105029', 6);

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
VALUES ('1234567890123', NULL, 'Mara', 'Maric', NULL, '$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');

INSERT INTO sso.`user` (username,father_jmbg,first_name,last_name,mother_jmbg,password) VALUES
	 ('111',NULL,'Boro','Borkovic',NULL,'$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');

INSERT INTO sso.service_role (id,`attributes`,`role`,service) VALUES
	 (7,'{"id":1,"fakultetId":1}','ROLE_ADMIN','fakultet'),
	 (8,'{"id":2,"fakultetId":1}','ROLE_STUDENT','fakultet');

INSERT INTO sso.user_service_roles (user_username,service_roles_id) VALUES
	 ('111',7),
	 ('123412341234',8);

INSERT INTO service_role(id, attributes, role, service) VALUES (9, '{"id":2}', "ROLE_USER", "szz");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('123412341234', 9);
INSERT INTO service_role(id, attributes, role, service) VALUES (10, '{"id":1}', "ROLE_USER", "szz");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('123412341233',10);


INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
VALUES ('12345', NULL, 'Sima', 'Simic', NULL, '$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');

INSERT INTO service_role(id, attributes, role, service) VALUES (11, '{"schoolId":2}', "ROLE_STUDENT", "school");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('12345', 11);


INSERT INTO service_role(id, attributes, role, service) VALUES (12, '{"id":2}', "ROLE_USER", "szz");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('12345',12);

INSERT INTO service_role(id, attributes, role, service) VALUES (13, '{"companyId":null}', "ROLE_USER", "apr");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('123412341234', 13);

INSERT INTO service_role(id, attributes, role, service) VALUES (14, '{"companyId":2}', "ROLE_USER", "apr");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('1234567890123', 14);

INSERT INTO service_role(id, attributes, role, service) VALUES (15, '{"companyId":null}', "ROLE_USER", "apr");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('123412341233', 15);

INSERT INTO service_role(id, attributes, role, service) VALUES (16, '{"companyId":null}', "ROLE_USER", "apr");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('12345', 16);
