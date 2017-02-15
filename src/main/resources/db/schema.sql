drop table T_ACCOUNT if exists;
create table T_ACCOUNT(ID bigint identity primary key, USER_ID bigint not null,
                        BALANCE int not null);
