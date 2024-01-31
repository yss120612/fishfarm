insert ignore into users (login,password,email,active,descr) values("superadmin","1111","sa@yax.ru",1,"Application administrator")
insert ignore into roles (name) values("super_admin")
insert ignore into users_roles (user_id,role_id) select u.id,r.id from users u, roles r where u.login="superadmin" and  r.name="super_admin"
