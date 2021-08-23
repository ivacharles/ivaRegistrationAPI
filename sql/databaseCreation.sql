use userApp;
#     userID varchar(255) generated always as (concat('DXpressway','#',id)),
drop table user;
create table user(
    userID int not null auto_increment,
    userFirstName varchar(250) not null ,
    userLastName varchar(250) not null ,
    userUsername varchar(250) not null ,
    userPhoneNumber varchar(230) not null,
    userPwd varchar(200) not null ,
    userLastPwd varchar(200) not null ,
    isUserEnabled boolean default false,
    isUserLocked boolean default false,
    userCreated timestamp,
    userUpdated timestamp,

    constraint pk_user primary key (userID),
    constraint uk_user unique (userUsername)
);
drop table role;
create table role(
    roleID int auto_increment,
    roleName varchar(30) not null ,

    constraint pk_role primary key (roleID),
    constraint uk_role unique (roleName)

);
insert into role (roleName) value ('USER_ROLE');
insert into role (roleName) value ('ADMIN_ROLE');

drop table user_and_role;
create table user_and_role(
    userRoleID int not null auto_increment,
    userUsername varchar(250) not null,
    roleName varchar(30) not null,

    constraint pk_user_role primary key (userRoleID),
    constraint fk1_user_role foreign key(userUsername) references user (userUsername),
    constraint fk2_user_role foreign key(roleName) references role (roleName)
);
drop table token;
create table token(
    tokenID int not null auto_increment,
    userUsername varchar(250) not null,
    token varchar(250) not null,
    createdAt timestamp not null,
    expiresAt timestamp not null,
    confirmAt timestamp,

    constraint pk_token primary key (tokenID),
    constraint uk_token unique (token),
    constraint fk_token foreign key (userUsername) references user(userUsername)
);