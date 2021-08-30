create database frogsoft_cms_db;
create user 'frogsoft_cms_user'@'%' identified by 'frogsoft_cms_password';
grant all on frogsoft_cms_db.* to 'frogsoft_cms_user'@'%';