--테이블삭제
drop table product;

---------
--상품관리
--------
create table product(
    product_id  number(10),
    pname       varchar(30),
    quantity    number(10),
    price       number(10),
    cdate       timestamp, --생성일시
    udate       timestamp  --수정일시
);
--기본키
alter table product add constraint product_product_id_pk primary key(product_id);

--시퀀스생성
drop sequence product_product_id_seq;
create sequence product_product_id_seq;

--디폴트
alter table product modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table product modify udate default systimestamp; --운영체제 일시를 기본값으로

--필수 not null
alter table product modify quantity not null;
alter table product modify price not null;

--생성--
insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '컴퓨터', 5, 1000000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '모니터', 5, 500000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '프린터', 1, 300000);
commit;


drop table member;
--회원테이블
create table member(
    member_id   number(10),     --내부관리 아이디
    email       varchar2(50),   --로긴 아이디
    passwd      varchar2(12),   --로긴 비밀번호
    nickname    varchar2(30),   --별칭
    gubun       varchar(11),    --default 'M0101', --회원구분(일반,우수,관리자1,관리자2)..
    pic         blob,           --사진
    cdate       timestamp,      --생성일시,가입일
    udate       timestamp       --수정일시
);
--기본키생성
alter table member add constraint mumber_member_id_pk primary key(member_id);

--제약조건
alter table member modify email constraint member_email_uk unique;

--디폴트
alter table member modify gubun default 'M0101'; --운영체제 일시를 기본값으로
alter table member modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table member modify udate default systimestamp; --운영체제 일시를 기본값으로

--시퀀스
drop sequence member_member_id_seq;
create sequence member_member_id_seq;

--샘플데이터
insert into member (member_id,email,passwd,nickname)
    values(member_member_id_seq.nextval,'user1@kh.com','user1','사용자1');
insert into member (member_id,email,passwd,nickname)
    values(member_member_id_seq.nextval,'user2@kh.com','user2','사용자2');
commit;
