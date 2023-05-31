DELETE FROM user_service_roles;
DELETE FROM saved_login;

DELETE FROM user;
DELETE FROM service_role;

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
	VALUES ('1234567891234', '1234567891233', 'Stefan', 'Vlajkovic', '1234567891232', '$2a$12$.PDCGAJ9s1U3GZ2j24rB6eK/n86EbJW1bSiad7raMnrFs4PIq7OZS');
INSERT INTO service_role(id, attributes, role, service) VALUES (1, '{"schoolId":1}', "ROLE_ADMIN", "school");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('1234567891234', 1);

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
	VALUES ('9876543214321', '9876543214311', 'Nikola', 'Nikolic', '9876543214111', '$2a$12$DUAUoK9147km1Gd4FpiOce85Iyp74xOGBtm5JTrbjSJoIPRHYxMXW');
INSERT INTO service_role(id, attributes, role, service) VALUES (2, '{"schoolId":2}', "ROLE_ADMIN", "school");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('9876543214321', 2);

INSERT INTO user(username, father_jmbg, first_name, last_name, mother_jmbg, password)
	VALUES ('1102997105029', '1234567891111', 'Jovana', 'Milisic', '1234567892222', '$2a$10$WyGU0850Gt6l9niernBpb.58pCPz8XXEaI4qvOyj5rdEYIygCat/u');
INSERT INTO service_role(id, attributes, role, service) VALUES (3, '{"companyId":0}', "ROLE_ADMIN", "apr");
INSERT INTO user_service_roles(user_username, service_roles_id) VALUES ('1102997105029', 3);

INSERT INTO sso.`user` (username,father_jmbg,first_name,last_name,mother_jmbg,password) VALUES
	 ('111',NULL,'Boro','Borkovic',NULL,'$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e'),
	 ('222',NULL,'Gagi','Pizon',NULL,'$2a$12$abC/Iqm56FM/G4oOZ.xXs.KEuhE5RtQ0oyHQxJngaC1cPUq/S/i.e');

INSERT INTO sso.service_role (id,`attributes`,`role`,service) VALUES
	 (4,'{"id":1,"fakultetId":1}','ROLE_ADMIN','fakultet'),
	 (5,'{"id":2,"fakultetId":1}','ROLE_STUDENT','fakultet');


INSERT INTO sso.user_service_roles (user_username,service_roles_id) VALUES
	 ('111',4),
	 ('222',5);