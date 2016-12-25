INSERT INTO neighbor_user (id, activation_status, login, pin_code)
VALUES (nextval('hibernate_sequence'), 'ACTIVE', 'admin', 'password');
INSERT INTO neighbor_role (id, user_role, user_id) VALUES (nextval('hibernate_sequence'), 'ROLE_NB_DEV', (SELECT id
                                                                                                          FROM
                                                                                                            neighbor_user
                                                                                                          WHERE login =
                                                                                                                'admin'));
INSERT INTO neighbor_role (id, user_role, user_id) VALUES (nextval('hibernate_sequence'), 'ROLE_NB_ADMIN', (SELECT id
                                                                                                            FROM
                                                                                                              neighbor_user
                                                                                                            WHERE
                                                                                                              login =
                                                                                                              'admin'));
