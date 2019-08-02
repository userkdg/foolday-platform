create table t_article
(
    id          varchar(36)                          not null
        primary key,
    create_time datetime   default CURRENT_TIMESTAMP null,
    update_time datetime                             null on update CURRENT_TIMESTAMP comment '自动更新时间',
    title       varchar(100)                         not null comment '标题',
    content     mediumtext                           not null comment '文件内容,包含图片等二进制信息, <= 16Mb的内容',
    thumail_id  varchar(36)                          null comment '缩略图id,列表显示',
    status      tinyint(2) default 1                 null comment '状态 有效 1 无效0 删除-1',
    type        varchar(100)                         not null comment '文章类型, 餐饮资讯 饮食知识 行业动态',
    shop_id     varchar(36)                          null comment '店铺id'
)
    comment '文章表';

INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('121943c89c164ef51c16c1181b34976e', '2019-07-18 15:07:13', null, '文章1', '内容啊实打实大撒大所大所大所多', 'asdas', 1, '啊实打实大', '640fcb878c4095a77778cc83c5933249');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('14001aaba6d32d2638ecf5222b52319b', '2019-05-13 15:29:44', '2019-07-17 21:04:18', '撒大声地', '阿什顿撒多', 'asdas', 1, '啊实打实大', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('414895309dece87a53b70f5a3ddcc4ff', '2019-05-24 01:51:40', '2019-05-24 01:51:40', '文章标题2', '文章内容2', 'imageId2', 1, '2222', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('651eebdc0dbf0a343f6500d1b40613a4', '2019-07-11 23:41:29', null, '文章标题2', '内容2', null, 1, '餐饮行业', '280763b0bc926997b5d0708a6d9db73b');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('8bbf5966c610e256e260b1b582b075f1', '2019-07-17 21:00:19', null, 'sadassa', '阿什顿撒多', 'asdas', 1, '啊实打实大', '640fcb878c4095a77778cc83c5933249');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('b0b65f9a9236932038210fa23196b524', '2019-07-17 20:59:39', null, '撒大声地', '阿什顿撒多', 'asdas', 1, '啊实打实大', '640fcb878c4095a77778cc83c5933249');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('bef94d05467a4b5fcc23d7d37e38b272', '2019-07-18 16:00:38', null, '文章2', '内容啊实打实大撒大所大所大所多', 'asdas', 1, 'news', '640fcb878c4095a77778cc83c5933249');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('bf8f71e4defc00febddfd8d92dd4121d', '2019-07-11 23:41:20', null, '文章标题', '内容', null, 1, '餐饮行业', '280763b0bc926997b5d0708a6d9db73b');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('c637515c7833fa2885b8d2856e2a5a73', '2019-05-13 15:33:37', null, '文章标题2', '文章内容2', 'imageId2', 1, 'xxx2', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('d5cafbf43e7e0c55dbe9fb3e7df9ac90', '2019-05-24 01:52:31', '2019-05-24 01:52:31', '文章标题2', '文章内容2', 'imageId2', 1, '2222', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('d8f4790c976399a9cf17af26e130e816', '2019-05-25 12:36:03', '2019-05-25 12:36:03', '文章标题2', '文章内容2', 'imageId2', 1, '2222', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('d95b7c5626eec7801fc067560166b6e4', '2019-07-18 16:00:50', null, '文章3', '内容啊实打实大撒大所大所大所多', 'asdas', 1, 'school', '640fcb878c4095a77778cc83c5933249');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('e537b6ba9a493aa31f36b2068f480f10', '2019-05-24 01:52:54', '2019-05-24 01:52:55', '文章标题2', '文章内容2', 'imageId2', 1, '2222', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('f77481215d4699d74006c4df3c474a29', '2019-05-15 08:58:01', '2019-05-15 08:58:01', '文章标题2', '文章内容2', 'imageId2', 1, '2222', 'shopId');
create table t_banner
(
    id          varchar(36)                        not null
        primary key,
    order_no    int(5)                             not null comment '排序',
    status      tinyint  default 1                 not null comment '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '更新时间',
    goods_id    varchar(36)                        null comment '商品id',
    description varchar(36)                        null comment '描述',
    image_id    varchar(36)                        null comment '图id',
    price       float(10, 2)                       null comment '价格',
    shop_id     varchar(36)                        null comment '店铺id'
)
    comment 'banner管理' collate = utf8mb4_unicode_ci;

create index banner_index
    on t_banner (shop_id, goods_id, status);

create index banner_index_order
    on t_banner (order_no, update_time, status);

INSERT INTO foolday_platform.t_banner (id, order_no, status, create_time, update_time, goods_id, description, image_id, price, shop_id) VALUES ('17f884b8cfddf779a9044328a53fd774', 0, 0, '2019-05-28 00:06:31', '2019-07-17 17:27:36', 'string', 'string', '85ba0e35ee204e1491e96752dfdfe946', 0, 'b32e145cac6c58ef33ac6171a0b3356e');
INSERT INTO foolday_platform.t_banner (id, order_no, status, create_time, update_time, goods_id, description, image_id, price, shop_id) VALUES ('4cf75bfa5cc50e7afdc37095c5651065', 0, 1, '2019-07-11 23:31:04', '2019-07-17 17:24:50', 'string', 'banner', '85ba0e35ee204e1491e96752dfdfe946', 10, 'b32e145cac6c58ef33ac6171a0b3356e');
create table t_carouse
(
    id          varchar(36)                        not null
        primary key,
    order_no    int(5)                             not null comment '排序',
    status      tinyint  default 1                 not null comment '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '更新时间',
    image_id    varchar(36)                        null comment '图id',
    shop_id     varchar(36)                        null comment '店铺id'
)
    comment 't_carouse管理' collate = utf8mb4_unicode_ci;

create index t_carouse_index
    on t_carouse (shop_id, image_id, status);

create index t_carouse_index_order
    on t_carouse (order_no, update_time, status);


create table t_comment
(
    id           varchar(36)                            not null
        primary key,
    shop_id      varchar(36)                            not null comment '店铺id',
    img_ids      varchar(255)                           not null comment '图片ids 多个用 英文逗号隔开,最多在3-5张5*36=180',
    status       tinyint      default 1                 not null comment '有效(1),无效(2),删除(3)',
    description  varchar(255) default ''                null comment '评论内容 目前约定最多200字',
    order_id     varchar(36)                            not null comment '订单id',
    goods_id     varchar(36)                            not null comment '商品id',
    user_id      varchar(36)                            not null comment '用户id',
    user_name    varchar(100)                           not null comment '用户名称，前端需要限制不显示全名',
    comment_type tinyint(2)   default 1                 null comment '客户(1),店长(2),超级管理员(3)',
    admin_id     varchar(36)  default ''                null comment '若店内人员回复则记录回复人id',
    admin_name   varchar(100) default ''                null comment '若店内人员回复则记录回复人名称',
    create_time  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime                               null on update CURRENT_TIMESTAMP comment '自动更新时间',
    shop_name    varchar(255)                           null,
    star         tinyint                                null comment '评星<=5'
)
    comment '评论表' collate = utf8mb4_unicode_ci;

create index commentType
    on t_comment (comment_type);

create index shopId
    on t_comment (shop_id);

create index userId_status_orderId_goodsId
    on t_comment (user_id, status, order_id, goods_id);


create table t_coupon
(
    id          varchar(36)                            not null
        primary key,
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                               null on update CURRENT_TIMESTAMP comment '自动更新时间',
    common_used tinyint(1)   default 0                 null comment '1可共用 0不可公用_默认',
    description varchar(255) default ''                null comment '使用说明',
    type        tinyint(2)   default 1                 null comment '折扣券1,满减券2,其他优惠券0',
    status      tinyint(2)   default 1                 null comment '有效1, 无效0, 删除-1',
    name        varchar(100)                           null comment '优惠券',
    full_price  float(8, 3)                            null comment '满多少',
    sub_price   float(8, 3)                            null comment '减多少',
    discnt      float(8, 3)                            null comment '满多少打多少折',
    start_time  datetime                               not null comment '优惠有效时间段',
    end_time    datetime                               null comment '优惠有效时间段',
    limit_count int                                    null comment '卷上限',
    kc_count    int                                    null comment '库存量',
    shop_id     varchar(36)                            not null
)
    comment '优惠券明确商品可以使用那些券' collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_coupon (id, create_time, update_time, common_used, description, type, status, name, full_price, sub_price, discnt, start_time, end_time, limit_count, kc_count, shop_id) VALUES ('0eefffd1a88a1e0cbc6390474a335d73', '2019-07-12 09:16:38', null, 0, 'string', 1, 1, null, 10, 10, 10, '2019-01-11 11:11:11', '2019-01-11 11:11:11', 10, 10, '280763b0bc926997b5d0708a6d9db73b');
INSERT INTO foolday_platform.t_coupon (id, create_time, update_time, common_used, description, type, status, name, full_price, sub_price, discnt, start_time, end_time, limit_count, kc_count, shop_id) VALUES ('76234bf0a8021cde4abbae02c63b818b', '2019-06-01 12:53:37', '2019-06-01 12:57:52', 0, 'string', 1, 1, null, 100, 10, 0, '2019-01-11 11:11:11', '2019-06-08 12:54:03', 1, 2, 'testShopId');
INSERT INTO foolday_platform.t_coupon (id, create_time, update_time, common_used, description, type, status, name, full_price, sub_price, discnt, start_time, end_time, limit_count, kc_count, shop_id) VALUES ('b64b4accd2b5b8f591f5391254052309', '2019-07-12 09:17:23', null, 0, '商品对象', 1, 1, null, 10, 10, 10, '2019-01-11 11:11:11', '2019-02-11 11:11:11', 10, 20, '280763b0bc926997b5d0708a6d9db73b');
create table t_coupon_user
(
    id          varchar(36)                        not null
        primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间',
    user_id     varchar(255)                       not null comment '客户id',
    coupon_id   varchar(255)                       not null comment '优惠券',
    status      tinyint(2)                         not null comment '有效1 无效0 删除-1',
    used        tinyint(1)                         not null comment '使用了没'
)
    comment '客户优惠券';

INSERT INTO foolday_platform.t_coupon_user (id, create_time, update_time, user_id, coupon_id, status, used) VALUES ('7cbea28604f48342fc3eb72a7c3c6379', '2019-06-01 13:41:53', null, 'testUserId', '76234bf0a8021cde4abbae02c63b818b', 1, 0);
INSERT INTO foolday_platform.t_coupon_user (id, create_time, update_time, user_id, coupon_id, status, used) VALUES ('acaf4eb50db14e402e59521e120914e8', '2019-07-17 20:31:51', null, '58957b2e7a633c2d3c08ea978527ed2d', '0eefffd1a88a1e0cbc6390474a335d73', 1, 0);
create table t_goods
(
    id           varchar(64)                            not null
        primary key,
    shop_id      varchar(64)                            not null comment '店铺id',
    name         varchar(100)                           not null comment '商品名称',
    price        float                                  not null comment '商品价格',
    discnt_price float                                  not null comment '折扣价格',
    img_id       varchar(64)  default ''                null comment '图片id',
    kccnt        int          default 0                 null comment '库存',
    description  varchar(255) default ''                null comment '描述内容',
    status       int(2)       default 1                 null comment '1上架 0下架 -1删除',
    create_time  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime                               null on update CURRENT_TIMESTAMP comment '自动更新时间',
    category_id  varchar(64)                            null comment '分类id，目前一对一关系',
    unit         varchar(36)                            null comment '商品单位 0份 1杯 2包 3件 4打 5半打 6瓶',
    discnt_goods tinyint(1)   default 0                 null comment '是否为折扣价 0否 1是 默认0'
)
    comment '商品表' collate = utf8mb4_unicode_ci;

create index t_tagId_imagId_shopId
    on t_goods (shop_id, img_id, category_id);

INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('3acb51698cc1fbf5534f4e723bc2f6b2', '48fea0a0d879490cbd1d39e337e7d004', '雪碧', 5, 4, 'f6a6f0edb9ed432c82c853653d805d3b', 100, '雪的口感', 0, '2019-04-21 12:56:36', null, null, null, 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('3e99f859d26825f02c701b0ecc3c3071', 'b656bd087029467f8648f1b5d58f9e36', '雪碧', 5, 4, '4a537397fac64e349fe9b19105b8f01e', 100, '雪的口感', 1, '2019-04-20 23:47:49', null, null, null, 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('5430171824d9004228da377ef94c631d', '8aa19be83c354ace8857c577cf21c1c6', '雪碧', 5, 4, '82dda451d65241a698f8bdbc5d1be34a', 100, '雪的口感', 1, '2019-04-20 23:33:35', null, null, null, 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('5606398a34787e00f157647e2612b2e1', 'd359fcdd6e4348aba7ebb7ea8de068d6', '雪碧', 5, 4, 'da713ed2b5fc4718944aa54077bdce24', 100, '雪的口感', 0, '2019-04-21 13:12:53', null, null, null, 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('70b1d215e122ab0182b62eb98a525057', '53bcdf2893264db6afda26696a500e41', '雪碧', 5, 4, '21f855db321b49fc97d8a10838b62d63', 100, '雪的口感', 1, '2019-04-08 23:54:52', null, '5273afad6930f06cb63a4e13113520fd', '6', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('8cb35e32d351469b3333963f73086ebe', '341601248820411588ec65605d70cd65', '雪碧', 5, 4, '070346ca5a7748b8b2a9b5803845a999', 100, '雪的口感', 0, '2019-04-21 11:33:24', null, null, null, 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('a1ac4181fa6e3e319ac8af0aee4ec8cb', '22ea0531f5aa4d49b6b7238e9e22957c', '大雪碧', 10, 8, '35978f754af2453e837a8509b7c73099', 100, '大雪的口感', 1, '2019-04-09 00:09:16', null, '5273afad6930f06cb63a4e13113520fe', '6', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('a702b6d2e0951ed8893832df3ebb81fe', 'a7a30102ce454a85b949f8008d6f63e0', '可乐', 5, 4.5, '44f395e6cef444d58656de99c3ef9619', 100, '可口可乐的口感', 1, '2019-04-08 23:13:04', null, '5273afad6930f06cb63a4e13113520fd', '6', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('b12b4867b38ddfbf3fce925ac49e98e8', '640fcb878c4095a77778cc83c5933249', '雪碧', 12, 10, '1cd5e00450924ed49e545f830935f118', 100, 'string', 1, '2019-04-16 01:21:20', '2019-07-18 23:00:59', 'd7bf960e8951ff618c157ecbea5ad3bd', '0', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('bb9ab73b9fcd4534610cb716d3991da8', '280763b0bc926997b5d0708a6d9db73b', '商品名称2', 222, 102, '584ce8e69a82457f8fe7648c8407271b', 200, '商品描述内容', 1, '2019-07-11 23:35:41', null, '1869d412c308ae8383c4c769938f110e', '0', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('bd078474bfe77ffa7f91491287ba52e5', '280763b0bc926997b5d0708a6d9db73b', '商品名称', 111, 10, 'cf16199c1c79463e8d7411fa4a727766', 100, '商品描述内容', 1, '2019-07-11 23:35:22', null, '1869d412c308ae8383c4c769938f110e', '0', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('cf1d5849ada54b277c63c674f42e6bde', '640fcb878c4095a77778cc83c5933249', '雪碧', 5, 4, 'dcd90d157d9e4ba9a9dd51130b30894a', 100, '雪的口感', 0, '2019-04-21 10:54:37', '2019-07-18 23:00:55', null, null, 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('d668576dccf535c27ea374f031ece562', '640fcb878c4095a77778cc83c5933249', '商品名称3', 333, 33, '7369c7510d8a4f6987aeabdd99bf4e17', 300, '商品描述内容3', 1, '2019-07-11 23:37:26', '2019-07-18 23:00:56', '836bcc1df91d57425ee3614494fb65ca', '0', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('eb99ccced3ee04f287730228a8d0e49d', '640fcb878c4095a77778cc83c5933249', '雪碧', 5, 4, 'dbe00413c4474dcabf224c7d2ab1e336', 100, '雪的口感', 0, '2019-04-21 12:55:19', '2019-07-18 23:00:56', null, null, 0);
create table t_goods_category
(
    id              varchar(64)                        not null
        primary key,
    name            varchar(100)                       not null comment '分类名称',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间',
    status          tinyint  default 1                 null comment '状态有效(1),无效(0),删除(-1)',
    top_down_status tinyint  default 5                 null comment '优先排序 9 置顶 5默认按最新来排 0 置底 可以+更新时间进行排序控制',
    shop_id         varchar(36)                        not null comment '哪家商品分类'
)
    comment '分类表，目前主要针对商品分类' collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_goods_category (id, name, create_time, update_time, status, top_down_status, shop_id) VALUES ('1869d412c308ae8383c4c769938f110e', '优惠区', '2019-07-11 23:32:09', null, 1, 9, '280763b0bc926997b5d0708a6d9db73b');
INSERT INTO foolday_platform.t_goods_category (id, name, create_time, update_time, status, top_down_status, shop_id) VALUES ('836bcc1df91d57425ee3614494fb65ca', '主菜单', '2019-07-11 23:36:57', null, 1, 9, '280763b0bc926997b5d0708a6d9db73b');
INSERT INTO foolday_platform.t_goods_category (id, name, create_time, update_time, status, top_down_status, shop_id) VALUES ('d7bf960e8951ff618c157ecbea5ad3bd', '热门推荐', '2019-04-12 23:50:56', '2019-04-12 23:50:57', 1, 5, '');
INSERT INTO foolday_platform.t_goods_category (id, name, create_time, update_time, status, top_down_status, shop_id) VALUES ('e97c76cd1a816e4b1a56192734ff3ef7', '热门推荐', '2019-04-21 11:05:27', '2019-04-21 11:05:34', 0, 9, '');
INSERT INTO foolday_platform.t_goods_category (id, name, create_time, update_time, status, top_down_status, shop_id) VALUES ('ee57963c22ad23201e60720c4a58f04f', '今日热推', '2019-04-12 23:50:57', '2019-04-12 23:50:58', 1, 9, '');
INSERT INTO foolday_platform.t_goods_category (id, name, create_time, update_time, status, top_down_status, shop_id) VALUES ('f3e55e0d3cebdd66fa27fabfca38f908', '今日热推', '2019-04-21 11:05:31', '2019-04-21 11:05:34', 1, 9, '');
create table t_goods_spec
(
    id                 varchar(36)                            not null
        primary key,
    name               varchar(100) default ''                null comment '规格名称',
    adjust_price       tinyint(1)   default 0                 null comment '明确规格的选择是否调整源商品的价格',
    goods_append_price float(9, 2)  default 0.00              null comment '若reset_goods_price=1,商品以商品加本值(可为正负0)为准,=0,则忽略',
    create_time        datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime                               null on update CURRENT_TIMESTAMP comment '自动更新时间',
    goods_id           varchar(36)                            not null comment '商品id',
    status             tinyint(1)   default 1                 null comment '状态',
    order_num          int          default 0                 null comment '排序号',
    type               tinyint                                not null comment '规格大类目前以枚举类定义，后续确认规格需求在调整',
    shop_id            varchar(36)                            not null comment '店铺id'
)
    comment '商品规格表';

create index goods_id
    on t_goods_spec (goods_id, create_time);

INSERT INTO foolday_platform.t_goods_spec (id, name, adjust_price, goods_append_price, create_time, update_time, goods_id, status, order_num, type, shop_id) VALUES ('07e9646cc5dabc1df1f6afe8e84bea5a', 'string', 0, 0, '2019-05-21 00:08:01', null, 'string', 1, 0, 0, 'shopId');
INSERT INTO foolday_platform.t_goods_spec (id, name, adjust_price, goods_append_price, create_time, update_time, goods_id, status, order_num, type, shop_id) VALUES ('10977f57e27e001a97e5e584d8944153', 'string', 0, 0, '2019-05-21 00:16:15', null, 'string', 1, 0, 0, 'shopId');
INSERT INTO foolday_platform.t_goods_spec (id, name, adjust_price, goods_append_price, create_time, update_time, goods_id, status, order_num, type, shop_id) VALUES ('171892972f2a5a8fe86279052b0ee120', '微辣', 0, 10, '2019-07-11 23:39:04', null, 'd668576dccf535c27ea374f031ece562', 1, 0, 0, '280763b0bc926997b5d0708a6d9db73b');
INSERT INTO foolday_platform.t_goods_spec (id, name, adjust_price, goods_append_price, create_time, update_time, goods_id, status, order_num, type, shop_id) VALUES ('7a2c1943eda821ec944c2135f52d3333', 'string', 0, 0, '2019-05-21 00:12:24', null, 'string', 1, 0, 0, 'shopId');
INSERT INTO foolday_platform.t_goods_spec (id, name, adjust_price, goods_append_price, create_time, update_time, goods_id, status, order_num, type, shop_id) VALUES ('adcca5a3d61230e74e4e06b6491ab454', '微辣', 0, 10, '2019-07-11 23:38:34', null, 'string', 1, 0, 0, '280763b0bc926997b5d0708a6d9db73b');
INSERT INTO foolday_platform.t_goods_spec (id, name, adjust_price, goods_append_price, create_time, update_time, goods_id, status, order_num, type, shop_id) VALUES ('fb17bd8face5f4df1783ec2a5b321df4', '少辣', 0, 10, '2019-07-11 23:39:13', null, 'd668576dccf535c27ea374f031ece562', 1, 0, 0, '280763b0bc926997b5d0708a6d9db73b');
create table t_groupbuy
(
    id                varchar(32)  not null
        primary key,
    name              varchar(255) null,
    shop_id           varchar(32)  null,
    condition_num     int          null comment 'n人拼团',
    ori_price         float(10, 2) null,
    curr_price        float(10, 2) null,
    status            int          null,
    img_ids           varchar(255) null,
    group_buy_code    varchar(255) null comment '团购券码',
    hx_code           varchar(255) null comment '核销码',
    limit_time_second int          null,
    include_shop_ids  varchar(255) null comment '包含店铺',
    remark            varchar(255) null comment '备注',
    start_time        datetime     null comment '有效期，起始时间',
    end_time          datetime     null comment '有效期，结束时间',
    use_start_time    varchar(32)  null comment '使用起始时间（1天）',
    use_end_time      varchar(32)  null comment '使用结束时间（1天）',
    rule              varchar(255) null,
    kc_count          int(255)     null,
    goods_detail      text         null,
    repeat_times      int          null
);

INSERT INTO foolday_platform.t_groupbuy (id, name, shop_id, condition_num, ori_price, curr_price, status, img_ids, group_buy_code, hx_code, limit_time_second, include_shop_ids, remark, start_time, end_time, use_start_time, use_end_time, rule, kc_count, goods_detail, repeat_times) VALUES ('2e3082816908c361721ad997aa92f2eb', '新团购，三人拼团', 'b580ecf2f4c60c4a4ab3e7c1b7a7c07f', 8, 33, 20.5, 1, 'a3c7970fbf044b5ebff7674e5534318b', '201905262103599361', '201905262103599361', 3600, '[{"name":"包含","goodsList":{"num":"2","id":"8812fcf45485526cedd28b9766c67c56"}},{"name":"二选一","goodsList":[{"num":"1","id":"a1ac4181fa6e3e319ac8af0aee4ec8cb"},{"num":"1","id":"a702b6d2e0951ed8893832df3ebb81fe"}]}]', 'park车免费', '2019-05-05 00:00:00', '2019-06-05 00:00:00', '08:00', '20:00', '店内使用', 500, '这个描述拼团详情，是一个大的文本', 3);
INSERT INTO foolday_platform.t_groupbuy (id, name, shop_id, condition_num, ori_price, curr_price, status, img_ids, group_buy_code, hx_code, limit_time_second, include_shop_ids, remark, start_time, end_time, use_start_time, use_end_time, rule, kc_count, goods_detail, repeat_times) VALUES ('afacfc87de8de92bb78ec3e5ba9530e3', '修改名字', 'b580ecf2f4c60c4a4ab3e7c1b7a7c07f', 8, 33, 20.5, 1, 'a3c7970fbf044b5ebff7674e5534318b', '201905261603575100', '201905261603575100', 0, '[{"name":"包含","goodsList":{"num":"2","id":"8812fcf45485526cedd28b9766c67c56"}},{"name":"二选一","goodsList":[{"num":"1","id":"a1ac4181fa6e3e319ac8af0aee4ec8cb"},{"num":"1","id":"a702b6d2e0951ed8893832df3ebb81fe"}]}]', 'park车免费', '2019-05-05 00:00:00', '2019-06-05 00:00:00', '08:00', '20:00', '店内使用', 0, '这个描述拼团详情，是一个大的文本', 0);
create table t_groupbuy_task
(
    id          varchar(32) not null
        primary key,
    groupbuy_id varchar(32) null,
    user_id     varchar(32) null,
    status      int         null,
    create_time datetime    null
);

INSERT INTO foolday_platform.t_groupbuy_task (id, groupbuy_id, user_id, status, create_time) VALUES ('721632d7ee32f8fb56a5800ef6120d91', '2e3082816908c361721ad997aa92f2eb', 'asdlkj123oiusdf098xcvpoi', 0, '2019-05-26 22:44:21');
create table t_image
(
    id          varchar(36)                        not null
        primary key,
    type        varchar(10)                        null,
    origin      tinyint                            null,
    description varchar(255)                       null,
    remark      varchar(255)                       null,
    size        bigint                             null,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP,
    name        varchar(255)                       null,
    width       int                                null,
    height      int                                null,
    file_path   varchar(516)                       null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('01d49472338444beb3493c0c0b3c5f75', 'jpeg', null, null, null, 676834, '2019-07-17 16:36:54', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.pxObz8kECPOR32d75ad519b75fa773e80f82b9019c57.jpg', 2100, 1851, '/home/image/01d49472338444beb3493c0c0b3c5f75wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.pxObz8kECPOR32d75ad519b75fa773e80f82b9019c57.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('04842478974044238645513c0cd0b3cd', 'jpeg', null, null, null, 472781, '2019-07-18 14:41:40', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Z7c3rxRmlLLz2402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/04842478974044238645513c0cd0b3cdwxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Z7c3rxRmlLLz2402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('10ecf74025734f2d9ce893d38c318a00', 'jpeg', null, null, null, 587336, '2019-07-06 16:05:44', null, '2345截图20190423212914.jpg', 1385, 842, 'F:\\home\\image\\10ecf74025734f2d9ce893d38c318a002345截图20190423212914.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('1328253eca5545f6b6743806387d51e7', 'jpeg', null, null, null, 36084, '2019-07-11 23:33:13', null, 'xxx.jpg', 200, 200, 'F:\\home\\image\\1328253eca5545f6b6743806387d51e7xxx.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('1b380d4178a7437a9d1331d215d247b5', 'jpeg', null, null, null, 36084, '2019-07-11 22:46:22', null, 'xxx.jpg', 200, 200, 'F:\\home\\image\\1b380d4178a7437a9d1331d215d247b5xxx.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('1cd5e00450924ed49e545f830935f118', 'jpeg', null, null, null, 26558, '2019-04-14 21:41:35', null, 'login2.jpg', 400, 350, null);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('1dcc2d34c0a348d197ecfc1d7c083c74', 'jpeg', null, null, null, 2866, '2019-07-17 16:30:04', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Gs3elIIn4b7K913db620163ec60c778692f8d1339d23.jpg', 100, 100, null);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('2b8af0b58e5648e98941f949ed867841', 'jpeg', null, null, null, 708390, '2019-07-17 16:57:39', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.5Mz70d9ZYkD8913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/2b8af0b58e5648e98941f949ed867841wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.5Mz70d9ZYkD8913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('2cc3b902798543db895adcbc9a670076', 'jpeg', null, null, null, 708390, '2019-07-18 14:46:12', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Tl0vMIHsdB2G913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/2cc3b902798543db895adcbc9a670076wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Tl0vMIHsdB2G913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('338a3ead9cb04ca5b8ce2fa5cf551a8f', 'jpeg', null, null, null, 472781, '2019-07-17 16:57:39', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.50NuOyDtD9uS2402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/338a3ead9cb04ca5b8ce2fa5cf551a8fwxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.50NuOyDtD9uS2402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('3b2f436b8c89495ca06830687e06a70a', 'jpeg', null, null, null, 10348, '2019-07-12 09:20:43', null, '2345截图20190423212914.jpg', 100, 100, null);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('3f0d9035fc7044fbacc63af0fc544ef9', 'jpeg', null, null, null, 587336, '2019-07-06 16:06:41', null, '2345截图20190423212914.jpg', 1385, 842, 'F:\\home\\image\\3f0d9035fc7044fbacc63af0fc544ef92345截图20190423212914.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('422a2e81dcd34d02a426b28cdb85d3b1', 'jpeg', null, null, null, 708390, '2019-07-17 17:11:02', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.x7tlmW6cBPEe913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/422a2e81dcd34d02a426b28cdb85d3b1wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.x7tlmW6cBPEe913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('43f2290702e048cf8d46f3a2edce9136', 'jpeg', null, null, null, 708390, '2019-07-17 20:43:59', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.YYzlsnkKQ90s913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/43f2290702e048cf8d46f3a2edce9136wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.YYzlsnkKQ90s913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('44eb13312d1f4a65a28f763c9bdb46f6', 'jpeg', null, null, null, 708390, '2019-07-17 17:02:39', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.upTC40UC6BEz913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/44eb13312d1f4a65a28f763c9bdb46f6wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.upTC40UC6BEz913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('454f71789fcd4ff3b062bccb8d0f44bb', 'jpeg', null, null, null, 472781, '2019-07-17 16:55:31', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.9nuWhtlZG1n72402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/454f71789fcd4ff3b062bccb8d0f44bbwxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.9nuWhtlZG1n72402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('4978819f2c744a1589868020591f5b37', 'jpeg', null, null, null, 708390, '2019-07-17 17:05:19', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.5aCNc9Ooa2OW913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/4978819f2c744a1589868020591f5b37wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.5aCNc9Ooa2OW913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('5219c1b3aa0047ef8c01f8114161efd6', 'jpeg', null, null, null, 472781, '2019-07-17 16:57:22', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.50NuOyDtD9uS2402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/5219c1b3aa0047ef8c01f8114161efd6wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.50NuOyDtD9uS2402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('57663bbe71e74213bea8785566950102', 'jpeg', null, null, null, 708390, '2019-07-17 17:12:42', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.ajUR0mYig4vO913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/57663bbe71e74213bea8785566950102wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.ajUR0mYig4vO913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('584ce8e69a82457f8fe7648c8407271b', 'jpeg', null, null, null, 36084, '2019-07-11 23:35:41', null, 'xxx.jpg', 200, 200, 'F:\\home\\image\\584ce8e69a82457f8fe7648c8407271bxxx.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('7369c7510d8a4f6987aeabdd99bf4e17', 'jpeg', null, null, null, 36084, '2019-07-11 23:37:26', null, 'xxx.jpg', 200, 200, 'F:\\home\\image\\7369c7510d8a4f6987aeabdd99bf4e17xxx.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('79b7b520aab445b5a738828392b69307', 'jpeg', null, null, null, 708390, '2019-07-17 16:51:41', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.vNFmkWQ1En8z913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/79b7b520aab445b5a738828392b69307wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.vNFmkWQ1En8z913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('7d2d50ca208b4a488ba1ae4900a8f0d8', 'jpeg', null, null, null, 708390, '2019-07-18 14:46:15', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Tl0vMIHsdB2G913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/7d2d50ca208b4a488ba1ae4900a8f0d8wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Tl0vMIHsdB2G913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('7f8e04e35d6d4d7e934a5c4d955f34d6', 'jpeg', null, null, null, 708390, '2019-07-17 17:10:55', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.x7tlmW6cBPEe913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/7f8e04e35d6d4d7e934a5c4d955f34d6wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.x7tlmW6cBPEe913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('821bd36d7b00415f951191a5d1eb1701', 'jpeg', null, null, null, 708390, '2019-07-17 17:07:54', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.VDUxd3D8IR6b913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/821bd36d7b00415f951191a5d1eb1701wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.VDUxd3D8IR6b913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('85ba0e35ee204e1491e96752dfdfe946', 'jpeg', null, null, null, 708390, '2019-07-17 16:17:35', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Gs3elIIn4b7K913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/85ba0e35ee204e1491e96752dfdfe946wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Gs3elIIn4b7K913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('881e051882404f51956084d47e3d0d23', 'jpeg', null, null, null, 472781, '2019-07-17 17:07:54', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.LRwQODoLi8fL2402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/881e051882404f51956084d47e3d0d23wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.LRwQODoLi8fL2402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('8ea294b8e3a5493bac0e610129ac8f6c', 'jpeg', null, null, null, 472781, '2019-07-17 16:55:25', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.XJP946FZtp2O2402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/8ea294b8e3a5493bac0e610129ac8f6cwxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.XJP946FZtp2O2402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('8f9f0828468a4b74bff73445c7f43b7a', 'jpeg', null, null, null, 472781, '2019-07-18 14:46:15', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.sG2VySGg7Noy2402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/8f9f0828468a4b74bff73445c7f43b7awxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.sG2VySGg7Noy2402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('a3c7970fbf044b5ebff7674e5534318b', 'jpeg', null, null, null, 323682, '2019-04-16 00:08:44', null, 'DSC_5808.jpg', 1428, 2087, null);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('ac9de270aef548e189dcd508acc2fe68', 'jpeg', null, null, null, 472781, '2019-07-17 17:05:19', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.r8zIjfDWQyz42402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/ac9de270aef548e189dcd508acc2fe68wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.r8zIjfDWQyz42402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('af934102be814445b8b86961883b8cc4', 'jpeg', null, null, null, 2866, '2019-07-17 20:57:54', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Gs3elIIn4b7K913db620163ec60c778692f8d1339d23.jpg', 100, 100, null);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('b24e55c29d454ccfa6daa2eed5006a2b', 'jpeg', null, null, null, 708390, '2019-07-17 17:01:11', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.SPDXTO8zNRHI913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/b24e55c29d454ccfa6daa2eed5006a2bwxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.SPDXTO8zNRHI913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('b3cd2daf689b4ab8954b13abb14853c9', 'jpeg', null, null, null, 24099, '2019-04-14 21:39:44', null, 'login2.jpg', 400, 300, null);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('c00a9c10a08946af8acef4f830d2f21d', 'jpeg', null, null, null, 708390, '2019-07-17 17:05:11', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.5aCNc9Ooa2OW913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/c00a9c10a08946af8acef4f830d2f21dwxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.5aCNc9Ooa2OW913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('c623f37ff92b4a4b99e2a5c7035175ed', 'jpeg', null, null, null, 708390, '2019-07-17 16:59:05', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.3jNwKGlCDUH6913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/c623f37ff92b4a4b99e2a5c7035175edwxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.3jNwKGlCDUH6913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('caeaee711cec430aaba58d6011f313a1', 'jpeg', null, null, null, 472781, '2019-07-17 16:55:31', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.XJP946FZtp2O2402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/caeaee711cec430aaba58d6011f313a1wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.XJP946FZtp2O2402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('cf16199c1c79463e8d7411fa4a727766', 'jpeg', null, null, null, 36084, '2019-07-11 23:35:21', null, 'xxx.jpg', 200, 200, 'F:\\home\\image\\cf16199c1c79463e8d7411fa4a727766xxx.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('e9c29317845243ed80b79a51acd8f216', 'jpeg', null, null, null, 168128, '2019-04-14 21:39:07', null, 'login2.jpg', 960, 600, null);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('ef8ea4b785ed48cc82553543c3a9466a', 'jpeg', null, null, null, 472781, '2019-07-17 17:11:02', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.pa1PXXm7E7ig2402e298bf17bc50766e7f9ef4e346d5.jpg', 1134, 1200, '/home/image/ef8ea4b785ed48cc82553543c3a9466awxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.pa1PXXm7E7ig2402e298bf17bc50766e7f9ef4e346d5.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('f261a98538a14956888f06fefdce8d58', 'jpeg', null, null, null, 708390, '2019-07-17 17:07:51', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.VDUxd3D8IR6b913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/f261a98538a14956888f06fefdce8d58wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.VDUxd3D8IR6b913db620163ec60c778692f8d1339d23.jpg');
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('fd094c1dd11f489b8e12087bdb2f7f44', 'jpeg', null, null, null, 2866, '2019-07-17 20:57:25', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.Gs3elIIn4b7K913db620163ec60c778692f8d1339d23.jpg', 100, 100, null);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height, file_path) VALUES ('fd8458673cf543ae95cb95171e8a794c', 'jpeg', null, null, null, 708390, '2019-07-17 17:03:48', null, 'wxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.T1OWAbDGtgGs913db620163ec60c778692f8d1339d23.jpg', 2000, 1446, '/home/image/fd8458673cf543ae95cb95171e8a794cwxe8650214ca46a5c9.o6zAJs7nHY-EKnJJqeNrqHHuNq18.T1OWAbDGtgGs913db620163ec60c778692f8d1339d23.jpg');
create table t_message
(
    id           varchar(36)                            not null
        primary key,
    sender       varchar(100) default ''                null comment '发给谁，一般是微信的wxid openid',
    callback_url varchar(500) default ''                null comment '回掉链接',
    remark       varchar(100) default ''                null comment '备注信息',
    channel_type varchar(50)  default ''                not null comment '目前为字符串的枚举值',
    title        varchar(100) default ''                not null comment '消息的标题信息',
    content      varchar(516) default ''                not null comment '消息的主题内容',
    business_id  varchar(36)  default ''                not null comment '消息对应的业务主键id',
    to_shop_id   varchar(36)  default ''                null comment '商铺id',
    action       tinyint(5)   default 1                 null comment '业务消息类型 与字段 business_id有对应关系',
    create_time  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime                               null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '消息管理表';


create table t_order
(
    id              varchar(64)                            not null
        primary key,
    shop_id         varchar(64)                            not null comment '店铺id',
    shop_name       varchar(100)                           not null comment '店铺名称，冗余字段，便于显示',
    shop_address    varchar(255)                           not null comment '店铺地址，冗余字段，便于显示',
    eat_type        tinyint      default 1                 null comment '1 堂吃 0外带',
    goods_num       tinyint      default 0                 null comment '订单商品数量',
    all_price       float        default 0                 null comment '订单总价格',
    remark          varchar(100) default ''                null comment '订单备注',
    people_cnt      tinyint      default 1                 null comment '默认为1人用餐',
    user_id         varchar(36)                            not null comment '微信小程序用户系统id,非wxid',
    groupbuy_id     varchar(36)  default ''                null comment '拼团id',
    coupon_id       varchar(36)  default ''                null comment '使用优惠券的id,控制优惠券的状态',
    status          tinyint(2)   default 0                 null comment '普通订单类型:0待付款,1待确认,2待评价,3已完成(已评价);通用:4退款,-1删除,拼团类型:10拼团中,11拼团成功,12拼团失败',
    order_no        varchar(50)  default ''                null comment '订单编号',
    order_type      tinyint(2)   default 0                 null comment '0点餐订单 1拼团订单',
    seat_no         int(10)      default 0                 null comment '座位号，目前只录入数值 不计号,为了按数值排序',
    queue_no        varchar(10)  default ''                null comment '排队号',
    create_time     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime                               null on update CURRENT_TIMESTAMP comment '自动更新时间',
    other_coupon_id varchar(36)  default ''                null comment '其他优惠价对应的优惠标识',
    user_name       varchar(100) default ''                null comment '用户名称，便于后台管理查询'
)
    comment '订单表' collate = utf8mb4_unicode_ci;

create index couponId
    on t_order (coupon_id);

create index groupbuy_id
    on t_order (groupbuy_id);

create index shopId_userId_eatType_orderType
    on t_order (shop_id, user_id, eat_type, order_type, status);

INSERT INTO foolday_platform.t_order (id, shop_id, shop_name, shop_address, eat_type, goods_num, all_price, remark, people_cnt, user_id, groupbuy_id, coupon_id, status, order_no, order_type, seat_no, queue_no, create_time, update_time, other_coupon_id, user_name) VALUES ('d925c621f92e7cef45b1a600e33b7fe2', 'testShopId', 'string', 'string', 1, 0, 0, 'xxxx', 0, '58957b2e7a633c2d3c08ea978527ed2d', '', '76234bf0a8021cde4abbae02c63b818b', 8, '20190602000000001', 0, 1, '1', '2019-06-02 00:39:46', '2019-07-17 20:06:42', '', 'testUserName');
INSERT INTO foolday_platform.t_order (id, shop_id, shop_name, shop_address, eat_type, goods_num, all_price, remark, people_cnt, user_id, groupbuy_id, coupon_id, status, order_no, order_type, seat_no, queue_no, create_time, update_time, other_coupon_id, user_name) VALUES ('e4121d345083ea4eee8242d2593efa79', 'testShopId', 'string', 'string', 1, 0, 0, 'xxxx', 0, 'testUserId', '', '76234bf0a8021cde4abbae02c63b818b', 0, '20190602000000001', 0, 1, '1', '2019-06-02 00:42:12', null, '', 'testUserName');
create table t_order_detail
(
    id           varchar(36)                            not null
        primary key,
    order_id     varchar(36)                            not null comment '订单id',
    goods_desc   varchar(100) default ''                null comment '商品描述',
    goods_name   varchar(50)                            not null comment '商品名称',
    goods_img_id varchar(36)  default ''                null comment '商品图片id',
    goods_id     varchar(36)                            not null comment '商品id',
    all_price    float        default 0                 null comment '订单总价格',
    price        float        default 0                 null comment '实际价格',
    cnt          tinyint      default 0                 null comment '数量',
    create_time  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime                               null on update CURRENT_TIMESTAMP comment '自动更新时间'
)
    comment '订单商品列表' collate = utf8mb4_unicode_ci;

create index orderId_goodsId
    on t_order_detail (order_id, goods_id)
    comment '订单+商品id';

INSERT INTO foolday_platform.t_order_detail (id, order_id, goods_desc, goods_name, goods_img_id, goods_id, all_price, price, cnt, create_time, update_time) VALUES ('32ac5763c81a37c236f97e40973a314d', 'd925c621f92e7cef45b1a600e33b7fe2', '可口可乐的口感', '可乐', '44f395e6cef444d58656de99c3ef9619', 'a702b6d2e0951ed8893832df3ebb81fe', 100, 5, 20, '2019-06-02 00:57:17', null);
INSERT INTO foolday_platform.t_order_detail (id, order_id, goods_desc, goods_name, goods_img_id, goods_id, all_price, price, cnt, create_time, update_time) VALUES ('3bf43e2537bbfaa25d96afc6f3f891be', 'e4121d345083ea4eee8242d2593efa79', '大雪的口感', '大雪碧', '35978f754af2453e837a8509b7c73099', 'a1ac4181fa6e3e319ac8af0aee4ec8cb', 100, 10, 10, '2019-06-02 00:42:13', null);
INSERT INTO foolday_platform.t_order_detail (id, order_id, goods_desc, goods_name, goods_img_id, goods_id, all_price, price, cnt, create_time, update_time) VALUES ('6afdcd62925bdf72cfa39f1456c1fb37', 'd925c621f92e7cef45b1a600e33b7fe2', '大雪的口感', '大雪碧', '35978f754af2453e837a8509b7c73099', 'a1ac4181fa6e3e319ac8af0aee4ec8cb', 100, 10, 10, '2019-06-02 00:39:46', null);
create table t_qrcode
(
    id      varchar(32)  not null
        primary key,
    content text         null comment '二维码内容',
    path    varchar(255) null comment '路径',
    name    varchar(255) null comment '下载二维码时图片命名（默认取桌位名）'
);

INSERT INTO foolday_platform.t_qrcode (id, content, path, name) VALUES ('2c312764604c40118a9e3489647acfdd', 'http://www.alipay.com', 'e:\\qrcode\\11a0e2a0ab5f4d65ad395d267160ebb7.png', '11');
INSERT INTO foolday_platform.t_qrcode (id, content, path, name) VALUES ('6c829545a9744b4e8691968438a166a7', 'http://www.alipay.com', 'e:\\qrcode\\dc9380325e4f47fd8a555241e70208ae.png', '第002桌');
INSERT INTO foolday_platform.t_qrcode (id, content, path, name) VALUES ('74d7e111b21f4bc6b04a0d4752027605', 'http://www.google.com', 'e:\\qrcode\\4c700f22a5fa45738f679e9164a40e6f.png', '1');
INSERT INTO foolday_platform.t_qrcode (id, content, path, name) VALUES ('78aba071603c4ae1bd1606ddfd24917e', 'http://www.baidu.com', 'e:\\qrcode\\b21c6c1d67a748a19300060a74e0722e.png', '第001桌');
INSERT INTO foolday_platform.t_qrcode (id, content, path, name) VALUES ('99a4058d8c4f4a4a8c4a77011991596a', 'http://www.baidu.com', 'E:\\img.png', '11');
INSERT INTO foolday_platform.t_qrcode (id, content, path, name) VALUES ('bcb39758e4ab47c7bce14aff8f91e492', 'http://www.baidu.com', 'e:\\qrcode\\8dcbf4b6c4744bbcb4bb7b5d5303cffa.png', '11');
INSERT INTO foolday_platform.t_qrcode (id, content, path, name) VALUES ('e7c06f5592e7476a8ea3fc8ab2c5454d', 'http://www.alipay.com', 'e:\\qrcode\\a2e7496c1851466d9b8b402815082cf7.png', '第002桌');
create table t_shop
(
    id           varchar(32)                          not null
        primary key,
    name         varchar(255)                         null comment '店铺名称',
    addr         varchar(255)                         null comment '地址',
    contact      varchar(255)                         null comment '联系方式',
    description  varchar(255)                         null comment '描述',
    lnt          float(10, 6)                         null comment '经度',
    lat          float(9, 6) unsigned zerofill        null comment '纬度',
    status       tinyint(2) default 0                 null comment '状态，0-正常，1-停用',
    create_time  datetime   default CURRENT_TIMESTAMP null,
    update_time  datetime                             null on update CURRENT_TIMESTAMP,
    default_shop tinyint    default 0                 null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time, default_shop) VALUES ('213de879c44b14c3d78d5accbe21ae2d', 'xxx', null, 'xxx', null, null, null, 0, null, '2019-07-17 14:42:51', 0);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time, default_shop) VALUES ('2195dc2348488b17fc4cefe8d7ee8e90', 'xxx', null, 'xxx', null, null, null, 0, null, '2019-07-18 23:20:51', 0);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time, default_shop) VALUES ('280763b0bc926997b5d0708a6d9db73b', '店铺名', '地址', '12832312', 'string', 116.11699, 24.30996, 0, null, '2019-07-18 23:19:47', 1);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time, default_shop) VALUES ('280763b0bc926997b5d0708a6d9db7c', 'xxx', null, 'xxx', null, 24.30996, 116.11699, 0, null, '2019-07-18 23:19:47', 0);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time, default_shop) VALUES ('640fcb878c4095a77778cc83c5933249', 'string', 'string', 'string', 'string', null, null, 1, null, '2019-07-18 23:20:51', 0);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time, default_shop) VALUES ('b32e145cac6c58ef33ac6171a0b3356f', 'string', 'string', 'string', 'string', null, null, 0, null, '2019-07-18 23:20:51', 0);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time, default_shop) VALUES ('string', 'xxx', null, 'xxx', null, null, null, 0, null, '2019-07-18 23:20:51', 0);
create table t_sys_admin
(
    id          varchar(36)                            not null
        primary key,
    account     varchar(100)                           not null comment '账号目前约定为手机号码',
    password    varchar(100)                           not null comment '密码 md5加密和加盐',
    status      tinyint      default 1                 not null comment '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                               null on update CURRENT_TIMESTAMP comment '自动更新时间',
    telphone    varchar(20)  default ''                null comment '手机号码',
    nickname    varchar(100) default ''                null comment '名称',
    shop_id     varchar(36)                            not null
)
    comment '账号表' collate = utf8mb4_unicode_ci;

create index account_password_status_index
    on t_sys_admin (account, password, status);

INSERT INTO foolday_platform.t_sys_admin (id, account, password, status, create_time, update_time, telphone, nickname, shop_id) VALUES ('1', '18813975053', 'PASSWORD_HEX_e10adc3949ba59abbe56e057f20f883e', 1, '2019-06-07 19:03:19', '2019-05-29 23:01:43', '18813975053', '', 'shopId');
INSERT INTO foolday_platform.t_sys_admin (id, account, password, status, create_time, update_time, telphone, nickname, shop_id) VALUES ('2d3f8ca8a60fb77e16057e7d2d86156f', 'admin', '123456', 1, '2019-05-27 21:55:37', '2019-05-27 21:56:28', '18813975053', 'admin', 'shopId');
create table t_sys_admin_auth
(
    id          varchar(36)                        not null
        primary key,
    user_id     varchar(36)                        not null comment '用户id',
    url_id      varchar(36)                        not null comment 'url id',
    create_time datetime default CURRENT_TIMESTAMP null,
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间'
)
    comment '用户url 中间表';

INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('0e3adf3fb79f883d845fac8b577cef7c', 'testUserId', '0e3adf3fb79f883d845fac8b577cef7c', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('1454a3b5eaad799b50a20aaab762e8f0', 'testUserId', '1454a3b5eaad799b50a20aaab762e8f0', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('16e895d969afe134919e12df5005e27a', 'testUserId', '16e895d969afe134919e12df5005e27a', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('185b67ec014b1fcd8e8b10888e22c3e2', 'testUserId', '185b67ec014b1fcd8e8b10888e22c3e2', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('1f00a58bc729af61adc214e15858cdd1', 'testUserId', '1f00a58bc729af61adc214e15858cdd1', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('1f951b5d9e6b1af67941d67795434f06', 'testUserId', '1f951b5d9e6b1af67941d67795434f06', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('258407ba5d68d6ae9e00871dce6db792', 'testUserId', '258407ba5d68d6ae9e00871dce6db792', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('29e2ab9215a598946762c2ba84357d2d', 'testUserId', '29e2ab9215a598946762c2ba84357d2d', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('2a6737f1bc0b33cd9d452697213a5505', 'testUserId', '2a6737f1bc0b33cd9d452697213a5505', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('32d418041e9d8e1ed52453aa6443e0d9', 'testUserId', '32d418041e9d8e1ed52453aa6443e0d9', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('342e5858e1d879399968843f5f40ca1d', 'testUserId', '342e5858e1d879399968843f5f40ca1d', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('3b601a69587206dedac2db38a943c5bf', 'testUserId', '3b601a69587206dedac2db38a943c5bf', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('3daca4c1692cd64b804bfac14f62a218', 'testUserId', '3daca4c1692cd64b804bfac14f62a218', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('40737d28a77ef24cfede94d2c2a18c40', 'testUserId', '40737d28a77ef24cfede94d2c2a18c40', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('435346e930a3345c76e8e0e5af189172', 'testUserId', '435346e930a3345c76e8e0e5af189172', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('48e0a4f8c7ee34f046a6f7fc2c4b5ca3', 'testUserId', '48e0a4f8c7ee34f046a6f7fc2c4b5ca3', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('4979dd22c9e25805b0212e002db1372a', 'testUserId', '4979dd22c9e25805b0212e002db1372a', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('4a199608f00c3772038df4e134b13a26', 'testUserId', '4a199608f00c3772038df4e134b13a26', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('51d3e0afc9aa6170a53dd7684e038ea8', 'testUserId', '51d3e0afc9aa6170a53dd7684e038ea8', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('56d8b5ed730597924ef69cf0c125e330', 'testUserId', '56d8b5ed730597924ef69cf0c125e330', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('587f4a9e71341d547f606142c483db33', 'testUserId', '587f4a9e71341d547f606142c483db33', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('5cea04e80bceb38fcc1e1eb98338450f', 'testUserId', '5cea04e80bceb38fcc1e1eb98338450f', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('66af2b2f2149b099bf811c24e562ee32', 'testUserId', '66af2b2f2149b099bf811c24e562ee32', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('6f3544a91e9f00d393575f848eed9568', 'testUserId', '6f3544a91e9f00d393575f848eed9568', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('6f639d6997892cbfc679630cc5ab8649', 'testUserId', '6f639d6997892cbfc679630cc5ab8649', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('724ea638da7f13d21ee736882b380f5a', 'testUserId', '724ea638da7f13d21ee736882b380f5a', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('7603129c8e924ef3b2fdcb5154e1ab58', 'testUserId', '7603129c8e924ef3b2fdcb5154e1ab58', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('7639d79184cac96e3f64fe48b3682825', 'testUserId', '7639d79184cac96e3f64fe48b3682825', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('7ef0320094d71cda22d32f1270536dde', 'testUserId', '7ef0320094d71cda22d32f1270536dde', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('82ff5fc4bd20e28e8a7799fd3517842b', 'testUserId', '82ff5fc4bd20e28e8a7799fd3517842b', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('883e93afe98bb595579e44e7b6d87700', 'testUserId', '883e93afe98bb595579e44e7b6d87700', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('8a72ff2a723dd4c334cdc42df140eae2', 'testUserId', '8a72ff2a723dd4c334cdc42df140eae2', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('8adbdd61d0ec4299cf954560e4c4bbd9', 'testUserId', '8adbdd61d0ec4299cf954560e4c4bbd9', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('8affe0a97d67f4968d6f7c67f8cf5391', 'testUserId', '8affe0a97d67f4968d6f7c67f8cf5391', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('8d5135f0f1be5de184fad74f6f73e474', 'testUserId', '8d5135f0f1be5de184fad74f6f73e474', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('9138360798ffc9be69127f9ee7ec62d4', 'testUserId', '9138360798ffc9be69127f9ee7ec62d4', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('91b8d20a52c5d42a58c91857bdde2de6', 'testUserId', '91b8d20a52c5d42a58c91857bdde2de6', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('9aceae1f6642e10b5a16ea5a782a0e61', 'testUserId', '9aceae1f6642e10b5a16ea5a782a0e61', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('9bdc291a72e9c1a571d851f5ba80f799', 'testUserId', '9bdc291a72e9c1a571d851f5ba80f799', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('9d4f3df1dcbfc912c60eea18eb9fae17', 'testUserId', '9d4f3df1dcbfc912c60eea18eb9fae17', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('9e8916b1848116b6b871c3df42cc6208', 'testUserId', '9e8916b1848116b6b871c3df42cc6208', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('a7be09dda69e9cdc13d580777b0f1c17', 'testUserId', 'a7be09dda69e9cdc13d580777b0f1c17', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('a948e2e5905eff9a623a94aee0c598c6', 'testUserId', 'a948e2e5905eff9a623a94aee0c598c6', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('b46b99240068a927f1ec223694add09c', 'testUserId', 'b46b99240068a927f1ec223694add09c', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('ba7183ece3a80238f6627138166204b4', 'testUserId', 'ba7183ece3a80238f6627138166204b4', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('bdcb82a31e216bb363c43d7caa72050f', 'testUserId', 'bdcb82a31e216bb363c43d7caa72050f', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('c05f3bfffa57f06ad545ec75fda3c1d4', 'testUserId', 'c05f3bfffa57f06ad545ec75fda3c1d4', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('c0bc0de9651db28e9788636ad2f3c266', 'testUserId', 'c0bc0de9651db28e9788636ad2f3c266', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('c24f5a989c5565b3566755945fde976d', 'testUserId', 'c24f5a989c5565b3566755945fde976d', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('cd7bada56e7afa5b04fdbe9613bdcba5', 'testUserId', 'cd7bada56e7afa5b04fdbe9613bdcba5', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('f74a1ab1de3206056378f8d32f775ef6', 'testUserId', 'f74a1ab1de3206056378f8d32f775ef6', '2019-05-25 15:20:02', null);
INSERT INTO foolday_platform.t_sys_admin_auth (id, user_id, url_id, create_time, update_time) VALUES ('fb3ac6d4a1a9a4806ddb7255384faf4f', 'testUserId', 'fb3ac6d4a1a9a4806ddb7255384faf4f', '2019-05-25 15:20:02', null);
create table t_sys_admin_menu
(
    id          varchar(36)                        not null
        primary key,
    user_id     varchar(36)                        not null comment '用户id',
    menu_id     varchar(36)                        not null comment '菜单id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间',
    shop_id     varchar(36)                        not null
)
    comment '系统用户与菜单中间表' collate = utf8mb4_unicode_ci;


create table t_sys_admin_role
(
    id          varchar(36)                        not null
        primary key,
    user_id     varchar(36)                        not null comment '用户id',
    role_id     varchar(36)                        not null comment '角色 id',
    create_time datetime default CURRENT_TIMESTAMP null,
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间'
)
    comment '用户角色中间表';

INSERT INTO foolday_platform.t_sys_admin_role (id, user_id, role_id, create_time, update_time) VALUES ('61f5ccafab6e4043725ba28237a0913f', '2d3f8ca8a60fb77e16057e7d2d86156f', 'd7a991d7db1374751cf25aadd24b9b27', '2019-05-27 21:55:37', null);
create table t_sys_auth
(
    id               varchar(36)                        not null,
    url              varchar(500)                       not null comment '控制器的url',
    auth_http_method tinyint(2)                         null comment '请求方式 httpMethod',
    create_time      datetime default CURRENT_TIMESTAMP null,
    update_time      datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间',
    status           tinyint(2)                         null comment 'uri是否有效',
    base_url         varchar(100)                       null comment '基础uri eg：/system || /weChat if null get url else base_url+url'
)
    comment '用户权限url';

INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('eef7c4ae3380ac901671b09703ae08cf', '/admin', 2, '2019-05-27 00:25:17', '2019-07-11 22:31:23', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('33c545e06487cd457f77542bc9a9aeb6', '/admin/kaptcha', 1, '2019-05-27 00:25:18', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('ecfe235f003cd916d3872b9ea428b4d2', '/goods/categor/{id}/edit', 2, '2019-05-27 00:25:18', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('48f9aa45ceeece09bf32c772d09e63c1', '/goods/categor/list', 1, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2e92b6bcb85aba5dde558cf6ea541289', '/goods/categor/add', 2, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4cc7985dcad5a5557c270afc034daa3c', '/goods/categor/{id}/delete', 2, '2019-05-27 00:25:19', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d6e1db48b88de31d0b71ee2ed76b2b15', '/coupon/edit', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('98507b3b243c728e13bc302efb512104', '/coupon/add', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1baabb0983ab5e36370b6a17a74077eb', '/coupon/list', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2f10d9790536529d8199ab2acc2d1986', '/coupon/delete', 2, '2019-05-27 00:25:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c21d92c61dd3ece82b9ef4af55ce29e7', '/role/edit/{id}', 2, '2019-05-27 00:25:20', '2019-07-11 22:31:23', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1156160dd966a6818af3198b380403eb', '/role/delete/{id}', 2, '2019-05-27 00:25:20', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('691223df04ba210ef679fb8a47c8ee74', '/role/down/{id}', 2, '2019-05-27 00:25:20', '2019-05-27 21:37:07', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('bfb5d04fda025d63538dd4d47564f0f0', '/role/add', 2, '2019-05-27 00:25:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9219f2b7f4275351173fea2719d55f4f', '/role/page', 2, '2019-05-27 00:25:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('92338d172090edde710f8da08c6533b9', '/role/get/{id}', 2, '2019-05-27 00:25:21', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('01b44b66c4c2ba76ee80dab0a8a7a54e', '/order/status/update', 2, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('ee1d3cc01921a8883a902c45c6a37acc', '/order/audit/cancelOrder/list', 1, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b4485b21a098a7c9fc3292a4ed1550e1', '/order/disagressCancel/list', 1, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8818019a322f062ba6d6294e33d04f38', '/order/delete', 2, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a9b00ee9d3359084a1d1f5b62bfa7c0f', '/order/agreeCancel/list', 1, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c36d0e6201fcf988470088e577ef8a21', '/order/cancel/audit', 1, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8a94372d8835c037921f3fac1cf0dba7', '/order/page', 2, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d58780008a9d833f2c94129105b99c1c', '/carousel/delete/{id}', 2, '2019-05-27 00:25:23', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('54baaa6ece273471e8741b384c61c591', '/carousel/add', 2, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('6677e5fdb70caecb1d2eea61557233db', '/goods/spe/add', 2, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('dd2ed33dc248c24e6a0f46cc6361d7bd', '/goods/spe/list/rootSpec', 1, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8f58ae3d231756dd7481d2957f0bd0b9', '/goods/spe/list/{goodsId}/subSpec', 1, '2019-05-27 00:25:24', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4af9d48b7451a2c9d6eaf42dda7546c4', '/goods/spe/{id}/delete', 2, '2019-05-27 00:25:25', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f1e882bdc0cce65a4f248db7b14d5577', '/goods/spe/{id}/edit', 2, '2019-05-27 00:25:25', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1e3a6c1876ce3cd089cf2a457089398c', '/shop/edit', 2, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e5814b8cca28e8677d6fa1eb362335c', '/shop/list', 1, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a1c9fab508e96fb2da3c5c91aba41d7a', '/shop/add', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('75dedaf03c046fe0ab5220c7f9fa3790', '/shop/freeze', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('735dfb423497e63c33a0ef641d32133f', '/shop/delete', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1b3ba1e1fec23362acea265f6b50108a', '/comment/replay/{commentId}', 2, '2019-05-27 00:25:26', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e63f6f93e221815b03a64b7d560c389', '/comment/{commentId}/list', 1, '2019-05-27 00:25:27', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a3df9a01bbcf6b0ba011f58610388882', '/comment/list', 2, '2019-05-27 00:25:27', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('87c105ebf8326d6aa2008aec0ad68ed1', '/comment/list/{orderId}', 1, '2019-05-27 00:25:27', '2019-07-11 22:31:23', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a8ae02cacd945af35b5a5a86b9c0fee8', '/goods/{goodsId}/edit', 2, '2019-05-27 00:25:27', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('599eaec036a709fab0f8a006b9d47089', '/goods/{goodsId}/downStatus', 2, '2019-05-27 00:25:28', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('08ef2ed1afbffe9e4328a62f5c3bb07b', '/goods/{goodsId}/upStatus', 2, '2019-05-27 00:25:28', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1da26fc92e4cf7cbc2a9b785936a5025', '/goods/add', 2, '2019-05-27 00:25:28', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d9fc6f5287cdfdf0d786c3dd68ba9a6d', '/goods/{goodsId}/deleteStatus', 2, '2019-05-27 00:25:28', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c9de5d67ae9ec28aa14e6a606aaa27cc', '/goods/{goodsCategoryId}/list', 2, '2019-05-27 00:25:29', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a909bef4aeaaaa6cc452db31b8674f7e', '/banner/add', 2, '2019-05-27 00:25:29', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('222fcd2cada86b57482f3b73ffba6048', '/banner/edit/{id}', 2, '2019-05-27 00:25:29', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('049c3feffc7fdbd5eac045a4f507fd49', '/banner/delete/{id}', 2, '2019-05-27 00:25:29', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c865a6ff6743c67810e26c30649788aa', '/table/list', 1, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('0132c0940da9f560d9e55351071af7c5', '/table/addAndBindQrcode', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('bd5d8e65fb59fa53eda5553cc751927e', '/table/edit', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e6079cde9c6341a4bf4326c9966b927a', '/table/add', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e090c09b873fb070e34582687757a77a', '/table/delete', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a73497af44831a67c9ca45e574b8b7cf', '/image/{id}/download', 1, '2019-05-27 00:25:31', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('dab1a6ee17235541d59c9f8da6b64ead', '/image/upload/file', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('479da130f713e5e514c3c3d744ae01d9', '/image/uplaod/files', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a5a0156ac6aa523017c9feffb7fd78ac', '/image/{id}/size/{width}/{height}', 1, '2019-05-27 00:25:32', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('39bd6f8b0c2d56c81ef2e51ee56f9431', '/role/noValid/{id}', 2, '2019-05-27 21:37:05', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b8fc47e4c88d3ffcffc46d74a2bb904f', '/role/valid/{id}', 2, '2019-05-27 21:37:05', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b0596110dc4cba5f18c6ee13bb2e45ca', '/admin/add', 2, '2019-05-27 21:37:05', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('43dc6b67ad6c8cc1b56e874208695d1d', '/admin/delete/{id}', 2, '2019-05-27 21:37:05', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c3fdfc038d932656b1a35c60c22e4d6c', '/admin/edit/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f09a383c126c8cd80b896f1ca3ad0b54', '/admin/noValid/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c2478508a9deacfc2fefd716ef4c21f4', '/admin/valid/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a729ab9d4f0ffa75a2d0d11d96ec38f9', '/admin/get/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('763552f840d1d890d4a46c8c85bbc583', '/admin/page', 2, '2019-05-27 21:37:06', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f5fa63ddee9b62503dd85e8bfea905fd', '/groupBuy/add', 2, '2019-05-27 21:37:07', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d93400ba7463504e5bebb4854dc57da5', '/menu/edit/{id}', 2, '2019-05-28 00:03:49', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('57a2ec100a162b0fb80084f5c89e9290', '/menu/delete/{id}', 2, '2019-05-28 00:03:49', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('249ba84c228890266b2e811371983e77', '/menu/noValid/{id}', 2, '2019-05-28 00:03:50', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2dcc3439366ba24e3b88470811557c80', '/menu/valid/{id}', 2, '2019-05-28 00:03:50', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('5b13fbbf2018a28188626f720a47ed15', '/menu/add', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e2e9d7fe8c96ecf0158dd3c600cc0356', '/menu/page', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('edfe1121eda6a95659a6722e4421ba17', '/menu/get/{id}', 2, '2019-05-28 00:03:51', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('eef7c4ae3380ac901671b09703ae08cf', '/admin', 2, '2019-05-27 00:25:17', '2019-07-11 22:31:23', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('33c545e06487cd457f77542bc9a9aeb6', '/admin/kaptcha', 1, '2019-05-27 00:25:18', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('ecfe235f003cd916d3872b9ea428b4d2', '/goods/categor/{id}/edit', 2, '2019-05-27 00:25:18', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('48f9aa45ceeece09bf32c772d09e63c1', '/goods/categor/list', 1, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2e92b6bcb85aba5dde558cf6ea541289', '/goods/categor/add', 2, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4cc7985dcad5a5557c270afc034daa3c', '/goods/categor/{id}/delete', 2, '2019-05-27 00:25:19', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d6e1db48b88de31d0b71ee2ed76b2b15', '/coupon/edit', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('98507b3b243c728e13bc302efb512104', '/coupon/add', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1baabb0983ab5e36370b6a17a74077eb', '/coupon/list', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2f10d9790536529d8199ab2acc2d1986', '/coupon/delete', 2, '2019-05-27 00:25:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c21d92c61dd3ece82b9ef4af55ce29e7', '/role/edit/{id}', 2, '2019-05-27 00:25:20', '2019-07-11 22:31:23', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1156160dd966a6818af3198b380403eb', '/role/delete/{id}', 2, '2019-05-27 00:25:20', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('691223df04ba210ef679fb8a47c8ee74', '/role/down/{id}', 2, '2019-05-27 00:25:20', '2019-05-27 21:37:07', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('bfb5d04fda025d63538dd4d47564f0f0', '/role/add', 2, '2019-05-27 00:25:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9219f2b7f4275351173fea2719d55f4f', '/role/page', 2, '2019-05-27 00:25:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('92338d172090edde710f8da08c6533b9', '/role/get/{id}', 2, '2019-05-27 00:25:21', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('01b44b66c4c2ba76ee80dab0a8a7a54e', '/order/status/update', 2, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('ee1d3cc01921a8883a902c45c6a37acc', '/order/audit/cancelOrder/list', 1, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b4485b21a098a7c9fc3292a4ed1550e1', '/order/disagressCancel/list', 1, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8818019a322f062ba6d6294e33d04f38', '/order/delete', 2, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a9b00ee9d3359084a1d1f5b62bfa7c0f', '/order/agreeCancel/list', 1, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c36d0e6201fcf988470088e577ef8a21', '/order/cancel/audit', 1, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8a94372d8835c037921f3fac1cf0dba7', '/order/page', 2, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d58780008a9d833f2c94129105b99c1c', '/carousel/delete/{id}', 2, '2019-05-27 00:25:23', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('54baaa6ece273471e8741b384c61c591', '/carousel/add', 2, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('6677e5fdb70caecb1d2eea61557233db', '/goods/spe/add', 2, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('dd2ed33dc248c24e6a0f46cc6361d7bd', '/goods/spe/list/rootSpec', 1, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8f58ae3d231756dd7481d2957f0bd0b9', '/goods/spe/list/{goodsId}/subSpec', 1, '2019-05-27 00:25:24', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4af9d48b7451a2c9d6eaf42dda7546c4', '/goods/spe/{id}/delete', 2, '2019-05-27 00:25:25', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f1e882bdc0cce65a4f248db7b14d5577', '/goods/spe/{id}/edit', 2, '2019-05-27 00:25:25', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1e3a6c1876ce3cd089cf2a457089398c', '/shop/edit', 2, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e5814b8cca28e8677d6fa1eb362335c', '/shop/list', 1, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a1c9fab508e96fb2da3c5c91aba41d7a', '/shop/add', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('75dedaf03c046fe0ab5220c7f9fa3790', '/shop/freeze', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('735dfb423497e63c33a0ef641d32133f', '/shop/delete', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1b3ba1e1fec23362acea265f6b50108a', '/comment/replay/{commentId}', 2, '2019-05-27 00:25:26', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e63f6f93e221815b03a64b7d560c389', '/comment/{commentId}/list', 1, '2019-05-27 00:25:27', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a3df9a01bbcf6b0ba011f58610388882', '/comment/list', 2, '2019-05-27 00:25:27', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('87c105ebf8326d6aa2008aec0ad68ed1', '/comment/list/{orderId}', 1, '2019-05-27 00:25:27', '2019-07-11 22:31:23', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a8ae02cacd945af35b5a5a86b9c0fee8', '/goods/{goodsId}/edit', 2, '2019-05-27 00:25:27', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('599eaec036a709fab0f8a006b9d47089', '/goods/{goodsId}/downStatus', 2, '2019-05-27 00:25:28', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('08ef2ed1afbffe9e4328a62f5c3bb07b', '/goods/{goodsId}/upStatus', 2, '2019-05-27 00:25:28', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1da26fc92e4cf7cbc2a9b785936a5025', '/goods/add', 2, '2019-05-27 00:25:28', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d9fc6f5287cdfdf0d786c3dd68ba9a6d', '/goods/{goodsId}/deleteStatus', 2, '2019-05-27 00:25:28', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c9de5d67ae9ec28aa14e6a606aaa27cc', '/goods/{goodsCategoryId}/list', 2, '2019-05-27 00:25:29', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a909bef4aeaaaa6cc452db31b8674f7e', '/banner/add', 2, '2019-05-27 00:25:29', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('222fcd2cada86b57482f3b73ffba6048', '/banner/edit/{id}', 2, '2019-05-27 00:25:29', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('049c3feffc7fdbd5eac045a4f507fd49', '/banner/delete/{id}', 2, '2019-05-27 00:25:29', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c865a6ff6743c67810e26c30649788aa', '/table/list', 1, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('0132c0940da9f560d9e55351071af7c5', '/table/addAndBindQrcode', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('bd5d8e65fb59fa53eda5553cc751927e', '/table/edit', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e6079cde9c6341a4bf4326c9966b927a', '/table/add', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e090c09b873fb070e34582687757a77a', '/table/delete', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a73497af44831a67c9ca45e574b8b7cf', '/image/{id}/download', 1, '2019-05-27 00:25:31', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('dab1a6ee17235541d59c9f8da6b64ead', '/image/upload/file', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('479da130f713e5e514c3c3d744ae01d9', '/image/uplaod/files', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a5a0156ac6aa523017c9feffb7fd78ac', '/image/{id}/size/{width}/{height}', 1, '2019-05-27 00:25:32', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('39bd6f8b0c2d56c81ef2e51ee56f9431', '/role/noValid/{id}', 2, '2019-05-27 21:37:05', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b8fc47e4c88d3ffcffc46d74a2bb904f', '/role/valid/{id}', 2, '2019-05-27 21:37:05', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b0596110dc4cba5f18c6ee13bb2e45ca', '/admin/add', 2, '2019-05-27 21:37:05', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('43dc6b67ad6c8cc1b56e874208695d1d', '/admin/delete/{id}', 2, '2019-05-27 21:37:05', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c3fdfc038d932656b1a35c60c22e4d6c', '/admin/edit/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f09a383c126c8cd80b896f1ca3ad0b54', '/admin/noValid/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c2478508a9deacfc2fefd716ef4c21f4', '/admin/valid/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a729ab9d4f0ffa75a2d0d11d96ec38f9', '/admin/get/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('763552f840d1d890d4a46c8c85bbc583', '/admin/page', 2, '2019-05-27 21:37:06', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f5fa63ddee9b62503dd85e8bfea905fd', '/groupBuy/add', 2, '2019-05-27 21:37:07', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d93400ba7463504e5bebb4854dc57da5', '/menu/edit/{id}', 2, '2019-05-28 00:03:49', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('57a2ec100a162b0fb80084f5c89e9290', '/menu/delete/{id}', 2, '2019-05-28 00:03:49', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('249ba84c228890266b2e811371983e77', '/menu/noValid/{id}', 2, '2019-05-28 00:03:50', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2dcc3439366ba24e3b88470811557c80', '/menu/valid/{id}', 2, '2019-05-28 00:03:50', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('5b13fbbf2018a28188626f720a47ed15', '/menu/add', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e2e9d7fe8c96ecf0158dd3c600cc0356', '/menu/page', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('edfe1121eda6a95659a6722e4421ba17', '/menu/get/{id}', 2, '2019-05-28 00:03:51', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e3c3f746453b20366a5d2ae90f59ef2', '/message/read', 2, '2019-06-02 01:57:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('eef7c4ae3380ac901671b09703ae08cf', '/admin', 2, '2019-05-27 00:25:17', '2019-07-11 22:31:23', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('33c545e06487cd457f77542bc9a9aeb6', '/admin/kaptcha', 1, '2019-05-27 00:25:18', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('ecfe235f003cd916d3872b9ea428b4d2', '/goods/categor/{id}/edit', 2, '2019-05-27 00:25:18', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('48f9aa45ceeece09bf32c772d09e63c1', '/goods/categor/list', 1, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2e92b6bcb85aba5dde558cf6ea541289', '/goods/categor/add', 2, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4cc7985dcad5a5557c270afc034daa3c', '/goods/categor/{id}/delete', 2, '2019-05-27 00:25:19', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d6e1db48b88de31d0b71ee2ed76b2b15', '/coupon/edit', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('98507b3b243c728e13bc302efb512104', '/coupon/add', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1baabb0983ab5e36370b6a17a74077eb', '/coupon/list', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2f10d9790536529d8199ab2acc2d1986', '/coupon/delete', 2, '2019-05-27 00:25:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c21d92c61dd3ece82b9ef4af55ce29e7', '/role/edit/{id}', 2, '2019-05-27 00:25:20', '2019-07-11 22:31:23', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1156160dd966a6818af3198b380403eb', '/role/delete/{id}', 2, '2019-05-27 00:25:20', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('691223df04ba210ef679fb8a47c8ee74', '/role/down/{id}', 2, '2019-05-27 00:25:20', '2019-05-27 21:37:07', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('bfb5d04fda025d63538dd4d47564f0f0', '/role/add', 2, '2019-05-27 00:25:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9219f2b7f4275351173fea2719d55f4f', '/role/page', 2, '2019-05-27 00:25:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('92338d172090edde710f8da08c6533b9', '/role/get/{id}', 2, '2019-05-27 00:25:21', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('01b44b66c4c2ba76ee80dab0a8a7a54e', '/order/status/update', 2, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('ee1d3cc01921a8883a902c45c6a37acc', '/order/audit/cancelOrder/list', 1, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b4485b21a098a7c9fc3292a4ed1550e1', '/order/disagressCancel/list', 1, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8818019a322f062ba6d6294e33d04f38', '/order/delete', 2, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a9b00ee9d3359084a1d1f5b62bfa7c0f', '/order/agreeCancel/list', 1, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c36d0e6201fcf988470088e577ef8a21', '/order/cancel/audit', 1, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8a94372d8835c037921f3fac1cf0dba7', '/order/page', 2, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d58780008a9d833f2c94129105b99c1c', '/carousel/delete/{id}', 2, '2019-05-27 00:25:23', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('54baaa6ece273471e8741b384c61c591', '/carousel/add', 2, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('6677e5fdb70caecb1d2eea61557233db', '/goods/spe/add', 2, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('dd2ed33dc248c24e6a0f46cc6361d7bd', '/goods/spe/list/rootSpec', 1, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8f58ae3d231756dd7481d2957f0bd0b9', '/goods/spe/list/{goodsId}/subSpec', 1, '2019-05-27 00:25:24', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4af9d48b7451a2c9d6eaf42dda7546c4', '/goods/spe/{id}/delete', 2, '2019-05-27 00:25:25', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f1e882bdc0cce65a4f248db7b14d5577', '/goods/spe/{id}/edit', 2, '2019-05-27 00:25:25', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1e3a6c1876ce3cd089cf2a457089398c', '/shop/edit', 2, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e5814b8cca28e8677d6fa1eb362335c', '/shop/list', 1, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a1c9fab508e96fb2da3c5c91aba41d7a', '/shop/add', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('75dedaf03c046fe0ab5220c7f9fa3790', '/shop/freeze', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('735dfb423497e63c33a0ef641d32133f', '/shop/delete', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1b3ba1e1fec23362acea265f6b50108a', '/comment/replay/{commentId}', 2, '2019-05-27 00:25:26', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e63f6f93e221815b03a64b7d560c389', '/comment/{commentId}/list', 1, '2019-05-27 00:25:27', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a3df9a01bbcf6b0ba011f58610388882', '/comment/list', 2, '2019-05-27 00:25:27', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('87c105ebf8326d6aa2008aec0ad68ed1', '/comment/list/{orderId}', 1, '2019-05-27 00:25:27', '2019-07-11 22:31:23', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a8ae02cacd945af35b5a5a86b9c0fee8', '/goods/{goodsId}/edit', 2, '2019-05-27 00:25:27', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('599eaec036a709fab0f8a006b9d47089', '/goods/{goodsId}/downStatus', 2, '2019-05-27 00:25:28', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('08ef2ed1afbffe9e4328a62f5c3bb07b', '/goods/{goodsId}/upStatus', 2, '2019-05-27 00:25:28', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1da26fc92e4cf7cbc2a9b785936a5025', '/goods/add', 2, '2019-05-27 00:25:28', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d9fc6f5287cdfdf0d786c3dd68ba9a6d', '/goods/{goodsId}/deleteStatus', 2, '2019-05-27 00:25:28', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c9de5d67ae9ec28aa14e6a606aaa27cc', '/goods/{goodsCategoryId}/list', 2, '2019-05-27 00:25:29', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a909bef4aeaaaa6cc452db31b8674f7e', '/banner/add', 2, '2019-05-27 00:25:29', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('222fcd2cada86b57482f3b73ffba6048', '/banner/edit/{id}', 2, '2019-05-27 00:25:29', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('049c3feffc7fdbd5eac045a4f507fd49', '/banner/delete/{id}', 2, '2019-05-27 00:25:29', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c865a6ff6743c67810e26c30649788aa', '/table/list', 1, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('0132c0940da9f560d9e55351071af7c5', '/table/addAndBindQrcode', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('bd5d8e65fb59fa53eda5553cc751927e', '/table/edit', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e6079cde9c6341a4bf4326c9966b927a', '/table/add', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e090c09b873fb070e34582687757a77a', '/table/delete', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a73497af44831a67c9ca45e574b8b7cf', '/image/{id}/download', 1, '2019-05-27 00:25:31', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('dab1a6ee17235541d59c9f8da6b64ead', '/image/upload/file', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('479da130f713e5e514c3c3d744ae01d9', '/image/uplaod/files', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a5a0156ac6aa523017c9feffb7fd78ac', '/image/{id}/size/{width}/{height}', 1, '2019-05-27 00:25:32', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('39bd6f8b0c2d56c81ef2e51ee56f9431', '/role/noValid/{id}', 2, '2019-05-27 21:37:05', '2019-07-11 22:31:26', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b8fc47e4c88d3ffcffc46d74a2bb904f', '/role/valid/{id}', 2, '2019-05-27 21:37:05', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b0596110dc4cba5f18c6ee13bb2e45ca', '/admin/add', 2, '2019-05-27 21:37:05', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('43dc6b67ad6c8cc1b56e874208695d1d', '/admin/delete/{id}', 2, '2019-05-27 21:37:05', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c3fdfc038d932656b1a35c60c22e4d6c', '/admin/edit/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f09a383c126c8cd80b896f1ca3ad0b54', '/admin/noValid/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:24', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c2478508a9deacfc2fefd716ef4c21f4', '/admin/valid/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a729ab9d4f0ffa75a2d0d11d96ec38f9', '/admin/get/{id}', 2, '2019-05-27 21:37:06', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('763552f840d1d890d4a46c8c85bbc583', '/admin/page', 2, '2019-05-27 21:37:06', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f5fa63ddee9b62503dd85e8bfea905fd', '/groupBuy/add', 2, '2019-05-27 21:37:07', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d93400ba7463504e5bebb4854dc57da5', '/menu/edit/{id}', 2, '2019-05-28 00:03:49', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('57a2ec100a162b0fb80084f5c89e9290', '/menu/delete/{id}', 2, '2019-05-28 00:03:49', '2019-07-11 22:31:28', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('249ba84c228890266b2e811371983e77', '/menu/noValid/{id}', 2, '2019-05-28 00:03:50', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2dcc3439366ba24e3b88470811557c80', '/menu/valid/{id}', 2, '2019-05-28 00:03:50', '2019-07-11 22:31:27', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('5b13fbbf2018a28188626f720a47ed15', '/menu/add', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e2e9d7fe8c96ecf0158dd3c600cc0356', '/menu/page', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('edfe1121eda6a95659a6722e4421ba17', '/menu/get/{id}', 2, '2019-05-28 00:03:51', '2019-07-11 22:31:25', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e3c3f746453b20366a5d2ae90f59ef2', '/message/read', 2, '2019-06-02 01:57:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a0db60ed7777f016b58cef0386c9cfa3', '/goods/spe/delete', 2, '2019-07-11 22:31:10', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a58ce0f1ba015856aecae4f5680cf4a9', '/goods/spe/list/subSpec', 1, '2019-07-11 22:31:10', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('5ecc954ecb64c5aa927296245cc6d985', '/goods/spe/edit', 2, '2019-07-11 22:31:10', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('74789c78e0d515937bf42e4a3fb85286', '/goods/spe/get', 1, '2019-07-11 22:31:10', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('92c6ba1524e81c470d9b53cfeb1e0a60', '/menu/valid', 2, '2019-07-11 22:31:11', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('87f31b3348bdf99db150d11f42ceebc6', '/menu/noValid', 2, '2019-07-11 22:31:11', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2dec45c764a0a116918d8873aa7ba86a', '/menu/get', 2, '2019-07-11 22:31:11', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('fda96b57b47b028a072930640087f4a9', '/menu/delete', 2, '2019-07-11 22:31:11', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4ba12a4e12ca3255293ec19d6ee726ef', '/menu/edit', 2, '2019-07-11 22:31:12', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('22435c61ff99d337c5112188ebca52bd', '/goods/categor/delete', 2, '2019-07-11 22:31:12', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('25353dc0f1696c2221a797ec1425f30f', '/goods/categor/edit', 2, '2019-07-11 22:31:12', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('225bb86db60fb7bf945563f7d42890df', '/goods/categor/get', 1, '2019-07-11 22:31:12', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b3a54e9bf8e733a002b11cf6323ddb02', '/carousel/delete}', 2, '2019-07-11 22:31:13', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c24999c6df9abab90ecf8ce7cb065187', '/carousel/list', 1, '2019-07-11 22:31:13', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('6099fcca6186829cc84121cfd0315c93', '/carousel/get', 1, '2019-07-11 22:31:13', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b5104aac5510e6a66f1761581f8c38a1', '/kaptcha', 1, '2019-07-11 22:31:13', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2ade993f0fef3a7da994b92fd7dfe7c1', '/login', 2, '2019-07-11 22:31:14', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('453ce4b3b8b8804f0afa133f66d661f0', '/banner/edit', 2, '2019-07-11 22:31:14', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a147c4706e71c4fa5675d9a52b7871f6', '/banner/list', 1, '2019-07-11 22:31:14', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a27a1ddf120f8afa7f21304a1683fd57', '/banner/delete', 2, '2019-07-11 22:31:14', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('26374cef403c1beda5d438d9aa17bfb6', '/banner/get', 1, '2019-07-11 22:31:15', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('10fccea9cfdf317cd09d00cc9b621ea9', '/comment/listById', 1, '2019-07-11 22:31:15', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('da691464ad4191cda8716275a1544b38', '/comment/replay', 2, '2019-07-11 22:31:15', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('84a1b024ed4cd0a4fb5cef7ddc15d96e', '/comment/listByOrderId', 1, '2019-07-11 22:31:15', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('76b57af30954975bd02667d8fd266b56', '/article/list', 1, '2019-07-11 22:31:16', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('fd63f9d554b1b57b3587550fb2b36687', '/article/down', 2, '2019-07-11 22:31:16', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9026c35567eb60c33136b86baa385387', '/article/up', 2, '2019-07-11 22:31:16', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('47f52cea004fc027d36e358a3a33c606', '/article/delete', 2, '2019-07-11 22:31:16', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('5430cbf68007291663ccfa2b44982307', '/article/add', 2, '2019-07-11 22:31:17', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2a7065e696d1e2e3bdbdfee56ca16824', '/article/edit', 2, '2019-07-11 22:31:17', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('cde24d93a336ae6371c6f9c8f11d03d4', '/article/get', 1, '2019-07-11 22:31:17', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('fdee6068c58d6e3f3ebc918dc3be2f0a', '/goods/shop/list', 2, '2019-07-11 22:31:17', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('34fe29bbd3bb800849c8f107d2c82e8d', '/goods/get', 1, '2019-07-11 22:31:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('6084ee0328468296f04df3063a75a769', '/goods/upStatus', 2, '2019-07-11 22:31:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4c86aef34c274e967a3cc8f9161d8ef0', '/goods/downStatus', 2, '2019-07-11 22:31:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('32773d8083ef07cb27c1622c95b4301f', '/goods/deleteStatus', 2, '2019-07-11 22:31:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('bace2767ec42b1f554ffe37954822d68', '/goods/edit', 2, '2019-07-11 22:31:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8615c767614d62e1f1b494b1a5f1209f', '/goods/list', 2, '2019-07-11 22:31:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('7806f9528f05b3c9c98952499eb53553', '/auth/list', 1, '2019-07-11 22:31:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('cb95b3647b7b925976d2c9409e5b9e89', '/role/edit', 2, '2019-07-11 22:31:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('95d267fb676ee73b9865787d46dcd310', '/role/noValid', 2, '2019-07-11 22:31:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('84e82e5cf07952f1f6f7f7f2ecef47ab', '/role/get', 2, '2019-07-11 22:31:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a754bd56a6ec01f1866c254693d58dea', '/role/delete', 2, '2019-07-11 22:31:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('62e01f2769c0f56b46d34b6734a3b77d', '/role/valid', 2, '2019-07-11 22:31:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('12675f82a68f607435fb4a9c9725900f', '/image/list', 1, '2019-07-11 22:31:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('6bf26cf59596c27dbc1d4f50e01840e6', '/image/viewImage/size', 1, '2019-07-11 22:31:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b9b11fb42c7b9502c55177ff70c0d2a3', '/image/download', 1, '2019-07-11 22:31:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e37fcd4b9e3972fd868fd85aa9aa950d', '/admin/edit', 2, '2019-07-11 22:31:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('cabcc356ce028f9bbfc6efd9ee0191a5', '/admin/get', 2, '2019-07-11 22:31:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4b1ca9331f57994b1c9f4ac3a2c09475', '/admin/noValid', 2, '2019-07-11 22:31:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('567df18d672cb430dd33fea4d639cca5', '/admin/valid', 2, '2019-07-11 22:31:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8bb532ab03a4105dee8d8a2f38a7791e', '/admin/delete', 2, '2019-07-11 22:31:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('978ffaca03e0e6052903ac0769eb7e8a', '/message/list', 2, '2019-07-11 22:31:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('01dfeabfb1e3730c58c6d80ac1ec6e72', '/message/get', 1, '2019-07-11 22:31:23', null, 1, '/system');
create table t_sys_log
(
    id             varchar(36)                             not null
        primary key,
    operator       varchar(100)                            not null comment '操作人',
    operator_id    varchar(100)                            not null comment '操作人',
    operate_status varchar(50)                             not null,
    create_time    datetime      default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime                                null on update CURRENT_TIMESTAMP comment '自动更新时间',
    result_msg     varchar(1000) default ''                null comment '请求情况信息',
    resource_name  varchar(100)  default ''                null comment '资源名称',
    host           varchar(50)   default ''                null,
    cost           varchar(20)   default ''                null comment '请求耗时',
    action         varchar(20)   default ''                null comment '动作，http method',
    content_type   varchar(500)  default ''                null,
    request_body   varchar(1000) default ''                null,
    response_body  varchar(1000) default ''                null,
    request_url    varchar(500)                            null,
    shop_id        varchar(36)                             null
)
    comment '操作日志记录' collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_sys_log (id, operator, operator_id, operate_status, create_time, update_time, result_msg, resource_name, host, cost, action, content_type, request_body, response_body, request_url, shop_id) VALUES ('18a49b084ac4f322374639c5b5a1e8f9', 'Eric', '13a9cb603f02453e8d3701f5ceb8fad3', 'success', '2019-07-19 22:44:22', null, '', '', '0:0:0:0:0:0:0:1', '271', 'POST', '', '[]', 'VjERAQ3XnnXpfi841oy5EA==', '/goods/shop/list', '280763b0bc926997b5d0708a6d9db73b');
INSERT INTO foolday_platform.t_sys_log (id, operator, operator_id, operate_status, create_time, update_time, result_msg, resource_name, host, cost, action, content_type, request_body, response_body, request_url, shop_id) VALUES ('38fb50595e3af5e90fad7eac178c4e7b', 'Eric', '13a9cb603f02453e8d3701f5ceb8fad3', 'success', '2019-07-19 23:01:08', null, '', '', '120.230.57.93', '53', 'POST', '', '[]', 'FG+Z3QScevOEdk7ur2mHKQ==', '/goods/shop/list', '280763b0bc926997b5d0708a6d9db73b');
INSERT INTO foolday_platform.t_sys_log (id, operator, operator_id, operate_status, create_time, update_time, result_msg, resource_name, host, cost, action, content_type, request_body, response_body, request_url, shop_id) VALUES ('fac64bd3434da062f9719f11459149da', 'Eric', '13a9cb603f02453e8d3701f5ceb8fad3', 'success', '2019-07-19 22:41:44', null, '', '', '0:0:0:0:0:0:0:1', '273', 'POST', '', '[]', 'cNxuJpXJVImeCmy+IYRw/Q==', '/goods/shop/list', '280763b0bc926997b5d0708a6d9db73b');
create table t_sys_menu
(
    id          varchar(36)                        not null
        primary key,
    name        varchar(100)                       not null comment '菜单名称',
    icon_url    varchar(100)                       null comment '菜单图标',
    remark      varchar(100)                       null comment '备注',
    pid         varchar(36)                        null comment '上级菜单',
    status      tinyint  default 1                 not null comment '1为有效,0为无效，-1为删除',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间',
    shop_id     varchar(36)                        not null
)
    comment '系统菜单表' collate = utf8mb4_unicode_ci;


create table t_sys_role
(
    id          varchar(36)                        not null
        primary key,
    name        varchar(100)                       not null comment '角色名称',
    status      tinyint  default 1                 not null comment '1为有效,0为无效，-1为删除',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间',
    shop_id     varchar(36)                        not null
)
    comment '系统角色表' collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_sys_role (id, name, status, create_time, update_time, shop_id) VALUES ('d7a991d7db1374751cf25aadd24b9b27', '系统管理员', 1, '2019-05-26 21:37:34', '2019-05-27 00:28:52', 'shopId');
create table t_sys_role_auth
(
    id          varchar(36)                        not null
        primary key,
    role_id     varchar(36)                        not null comment '角色id',
    auth_id     varchar(36)                        not null comment 'url 权限的id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间'
)
    comment '系统角色与权限url的中间表' collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_sys_role_auth (id, role_id, auth_id, create_time, update_time) VALUES ('684989e0992c672a419c026d21ebe701', 'd7a991d7db1374751cf25aadd24b9b27', 'c21d92c61dd3ece82b9ef4af55ce29e7', '2019-05-27 00:46:14', null);
INSERT INTO foolday_platform.t_sys_role_auth (id, role_id, auth_id, create_time, update_time) VALUES ('7e23a6ca16a9df4b3057008be71da1ca', 'd7a991d7db1374751cf25aadd24b9b27', '92338d172090edde710f8da08c6533b9', '2019-05-27 00:46:14', null);
INSERT INTO foolday_platform.t_sys_role_auth (id, role_id, auth_id, create_time, update_time) VALUES ('cbbfb6b2e87baeee29ba1f6ece1fef6c', 'd7a991d7db1374751cf25aadd24b9b27', '9219f2b7f4275351173fea2719d55f4f', '2019-05-27 00:46:14', null);
INSERT INTO foolday_platform.t_sys_role_auth (id, role_id, auth_id, create_time, update_time) VALUES ('d7355ff459d88f245456b82dc4718ea1', 'd7a991d7db1374751cf25aadd24b9b27', '1156160dd966a6818af3198b380403eb', '2019-05-27 00:46:14', null);
INSERT INTO foolday_platform.t_sys_role_auth (id, role_id, auth_id, create_time, update_time) VALUES ('e5a34a2f6d913574c0b60a0944c3c24f', 'd7a991d7db1374751cf25aadd24b9b27', 'bfb5d04fda025d63538dd4d47564f0f0', '2019-05-27 00:46:13', null);
create table t_table
(
    id        varchar(32)  not null
        primary key,
    shop_id   varchar(32)  null comment '店铺ID',
    name      varchar(255) null comment '名称',
    status    int          null comment '状态',
    qrcode_id varchar(32)  null comment '二维码ID'
)
    collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_table (id, shop_id, name, status, qrcode_id) VALUES ('4ef549c666b196bb8957ea25c9205ff4', 'b580ecf2f4c60c4a4ab3e7c1b7a7c07f', '第005号桌', 0, null);
INSERT INTO foolday_platform.t_table (id, shop_id, name, status, qrcode_id) VALUES ('ba47a85392d0ba5c0e0a4fd9406909a5', 'b580ecf2f4c60c4a4ab3e7c1b7a7c07f', '第003桌', 0, null);
create table t_tags
(
    id             varchar(64)      not null
        primary key,
    name           varchar(100)     not null comment '标签名称',
    type           int(2) default 1 not null comment '1为商品类型 0为订单类 -1为删除类',
    create_time    datetime         not null,
    update_time    datetime         null,
    priority_level int(5) default 0 null comment '定义标签优先级'
)
    comment '标签表，目前主要针对商品分类' collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_tags (id, name, type, create_time, update_time, priority_level) VALUES ('5273afad6930f06cb63a4e13113520fd', '热门推荐', 1, '2019-04-08 22:34:27', null, 2);
INSERT INTO foolday_platform.t_tags (id, name, type, create_time, update_time, priority_level) VALUES ('5273afad6930f06cb63a4e13113520fe', '今日优惠', 1, '2019-04-08 22:34:27', null, 1);
create table t_user
(
    id           varchar(36)             not null
        primary key,
    name         varchar(100)            not null comment '用户名称',
    img_id       varchar(516)            null comment '用户头像id，无默认取..需定义',
    wxid         varchar(100)            null comment '微信id',
    longitude    float        default 0  null comment '经度',
    latitude     float        default 0  null comment '纬度',
    status       tinyint(2)   default 1  null comment ' 在线(0),有效(1),无效(2),禁用(3),拉黑(4)',
    create_time  datetime                not null,
    update_time  datetime                null on update CURRENT_TIMESTAMP comment '自动更新时间',
    open_id      varchar(100) default '' null comment '微信用户每个公众对应一个openid',
    union_id     varchar(100) default '' null comment '微信用户唯一id',
    city         varchar(100)            null comment '所在城市',
    province     varchar(100)            null comment '所在省',
    country      varchar(100)            null comment '所在国家',
    gender       varchar(10)             null,
    tel          varchar(100)            null comment '手机号码',
    shop_id      varchar(36)             null comment '店铺id',
    country_code varchar(100)            null
)
    comment '微信用户信息' collate = utf8mb4_unicode_ci;

create index long_lat
    on t_user (longitude, latitude);

create index wxid
    on t_user (wxid);

INSERT INTO foolday_platform.t_user (id, name, img_id, wxid, longitude, latitude, status, create_time, update_time, open_id, union_id, city, province, country, gender, tel, shop_id, country_code) VALUES ('01387933b5db1937353f744e3ca294c3', 'Eric', 'https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqAqX1wxd7jpIGQCWGz1m9kGQ50l3uZ5tusB8SOKdt8iaRialM3UiaHUaEIPzrwrcdzE2avz7Znk20Bg/132', null, 0, 0, 1, '2019-07-12 00:43:52', null, 'oTeUN5Mz09IIvYtMAREUUm1fsGnA', '', '', '', 'Aruba', '1', null, '640fcb878c4095a77778cc83c5933249', null);
create table t_user_address_history
(
    id          varchar(36)                        not null
        primary key,
    address     varchar(500)                       not null comment '地址名称',
    status      tinyint  default 1                 not null comment '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '更新时间',
    user_id     varchar(36)                        null comment '用户id',
    shop_id     varchar(36)                        null comment '店铺id'
)
    comment 't_user_address_history用户地址使用过的记录管理' collate = utf8mb4_unicode_ci;

create index t_user_address_history_index
    on t_user_address_history (user_id, shop_id, status);

INSERT INTO foolday_platform.t_user_address_history (id, address, status, create_time, update_time, user_id, shop_id) VALUES ('7cc9d48730727c8e5b0c3a6824a295a5', 'jjhghjghjghj', -1, '2019-07-17 19:33:47', '2019-07-17 19:37:03', '58957b2e7a633c2d3c08ea978527ed2d', '640fcb878c4095a77778cc83c5933249');
create table t_user_advice
(
    id          varchar(36)                        not null
        primary key,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '自动更新时间',
    user_id     varchar(255)                       not null comment '意见发起人id',
    content     varchar(255)                       not null comment '意见内容',
    img_ids     varchar(255)                       not null comment '反馈图片ids 用英文逗号隔开 区分多个图片',
    shop_id     varchar(36)                        not null comment '店铺id,针对哪家店反馈'
)
    comment '客户意见反馈';

INSERT INTO foolday_platform.t_user_advice (id, create_time, update_time, user_id, content, img_ids, shop_id) VALUES ('6066ae5daceb38e2eb7b6753e8ea196a', '2019-07-17 20:54:20', null, '58957b2e7a633c2d3c08ea978527ed2d', 'sadsadsadsad', 'sadsad,sadsad,asdasd', '640fcb878c4095a77778cc83c5933249');