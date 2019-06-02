create table t_article
(
    id          varchar(36)                          not null
        primary key,
    create_time datetime   default CURRENT_TIMESTAMP null,
    update_time datetime                             null on update CURRENT_TIMESTAMP comment '自动更新时间',
    title       varchar(100)                         not null comment '标题',
    content     mediumtext                           not null comment '文件内容,包含图片等二进制信息, <= 16Mb的内容',
    thumail_id  varchar(36)                          not null comment '缩略图id,列表显示',
    status      tinyint(2) default 1                 null comment '状态 有效 1 无效0 删除-1',
    type        varchar(100)                         not null comment '文章类型, 餐饮资讯 饮食知识 行业动态',
    shop_id     varchar(36)                          null comment '店铺id'
)
    comment '文章表';

INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('14001aaba6d32d2638ecf5222b52319b', '2019-05-13 15:29:44', null, '文章标题', '文章内容', 'imageId', 1, 'xxx', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('414895309dece87a53b70f5a3ddcc4ff', '2019-05-24 01:51:40', '2019-05-24 01:51:40', '文章标题2', '文章内容2', 'imageId2', 1, '2222', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('c637515c7833fa2885b8d2856e2a5a73', '2019-05-13 15:33:37', null, '文章标题2', '文章内容2', 'imageId2', 1, 'xxx2', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('d5cafbf43e7e0c55dbe9fb3e7df9ac90', '2019-05-24 01:52:31', '2019-05-24 01:52:31', '文章标题2', '文章内容2', 'imageId2', 1, '2222', 'shopId');
INSERT INTO foolday_platform.t_article (id, create_time, update_time, title, content, thumail_id, status, type, shop_id) VALUES ('d8f4790c976399a9cf17af26e130e816', '2019-05-25 12:36:03', '2019-05-25 12:36:03', '文章标题2', '文章内容2', 'imageId2', 1, '2222', 'shopId');
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

INSERT INTO foolday_platform.t_banner (id, order_no, status, create_time, update_time, goods_id, description, image_id, price, shop_id) VALUES ('17f884b8cfddf779a9044328a53fd774', 0, 1, '2019-05-28 00:06:31', null, 'string', 'string', 'string', 0, null);
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
    img_ids      varchar(255)                           null comment '图片ids 多个用 英文逗号隔开,最多在3-5张5*36=180',
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

INSERT INTO foolday_platform.t_comment (id, shop_id, img_ids, status, description, order_id, goods_id, user_id, user_name, comment_type, admin_id, admin_name, create_time, update_time, shop_name, star) VALUES ('67198581f8b7501606a13ef9319d79a2', 'testShopId', null, 1, '评论内容', 'd925c621f92e7cef45b1a600e33b7fe2', 'a1ac4181fa6e3e319ac8af0aee4ec8cb', 'testUserId', 'testUserName', 1, '', '', '2019-06-02 01:25:48', null, null, 5);
INSERT INTO foolday_platform.t_comment (id, shop_id, img_ids, status, description, order_id, goods_id, user_id, user_name, comment_type, admin_id, admin_name, create_time, update_time, shop_name, star) VALUES ('8998619ed7aa3be8070a62b34251b43f', 'testShopId', null, 1, '评论内容2', 'd925c621f92e7cef45b1a600e33b7fe2', 'a1ac4181fa6e3e319ac8af0aee4ec8cb', 'testUserId', 'testUserName', 1, '', '', '2019-06-02 12:13:54', null, null, 5);
INSERT INTO foolday_platform.t_comment (id, shop_id, img_ids, status, description, order_id, goods_id, user_id, user_name, comment_type, admin_id, admin_name, create_time, update_time, shop_name, star) VALUES ('c4e056ebd112ab1f693f661ff3146c67', 'testShopId', null, 1, '评论内容2', 'd925c621f92e7cef45b1a600e33b7fe2', 'a702b6d2e0951ed8893832df3ebb81fe', 'testUserId', 'testUserName', 1, '', '', '2019-06-02 12:13:54', null, null, 5);
INSERT INTO foolday_platform.t_comment (id, shop_id, img_ids, status, description, order_id, goods_id, user_id, user_name, comment_type, admin_id, admin_name, create_time, update_time, shop_name, star) VALUES ('db0b5e3904e8b927fdb7ca7f1875c641', 'testShopId', null, 1, '评论内容', 'd925c621f92e7cef45b1a600e33b7fe2', 'a702b6d2e0951ed8893832df3ebb81fe', 'testUserId', 'testUserName', 1, '', '', '2019-06-02 01:25:48', null, null, 5);
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

INSERT INTO foolday_platform.t_coupon (id, create_time, update_time, common_used, description, type, status, name, full_price, sub_price, discnt, start_time, end_time, limit_count, kc_count, shop_id) VALUES ('76234bf0a8021cde4abbae02c63b818b', '2019-06-01 12:53:37', '2019-06-01 12:57:52', 0, 'string', 1, 1, null, 100, 10, 0, '2019-01-11 11:11:11', '2019-06-08 12:54:03', 1, 2, 'testShopId');
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

INSERT INTO foolday_platform.t_coupon_user (id, create_time, update_time, user_id, coupon_id, status, used) VALUES ('7cbea28604f48342fc3eb72a7c3c6379', '2019-06-01 13:41:53', '2019-06-02 00:42:40', 'testUserId', '76234bf0a8021cde4abbae02c63b818b', 0, 1);
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
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('8812fcf45485526cedd28b9766c67c56', '0e8224a69ab54c67b45248bcf52da1bd', '雪碧', 5, 4, 'b902f7d74c26428cbd8ca9c997b7bd15', 100, '雪的口感', 1, '2019-04-21 11:31:52', null, null, null, 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('8cb35e32d351469b3333963f73086ebe', '341601248820411588ec65605d70cd65', '雪碧', 5, 4, '070346ca5a7748b8b2a9b5803845a999', 100, '雪的口感', 0, '2019-04-21 11:33:24', null, null, null, 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('a1ac4181fa6e3e319ac8af0aee4ec8cb', '22ea0531f5aa4d49b6b7238e9e22957c', '大雪碧', 10, 8, '35978f754af2453e837a8509b7c73099', 100, '大雪的口感', 1, '2019-04-09 00:09:16', null, '5273afad6930f06cb63a4e13113520fe', '6', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('a702b6d2e0951ed8893832df3ebb81fe', 'a7a30102ce454a85b949f8008d6f63e0', '可乐', 5, 4.5, '44f395e6cef444d58656de99c3ef9619', 100, '可口可乐的口感', 1, '2019-04-08 23:13:04', null, '5273afad6930f06cb63a4e13113520fd', '6', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('b12b4867b38ddfbf3fce925ac49e98e8', 'testShopId', '雪碧', 12, 10, '1cd5e00450924ed49e545f830935f118', 100, 'string', 1, '2019-04-16 01:21:20', null, 'd7bf960e8951ff618c157ecbea5ad3bd', '0', 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('cf1d5849ada54b277c63c674f42e6bde', '975a8cb7f18b408a9b9401a9ee00f8d3', '雪碧', 5, 4, 'dcd90d157d9e4ba9a9dd51130b30894a', 100, '雪的口感', 0, '2019-04-21 10:54:37', null, null, null, 0);
INSERT INTO foolday_platform.t_goods (id, shop_id, name, price, discnt_price, img_id, kccnt, description, status, create_time, update_time, category_id, unit, discnt_goods) VALUES ('eb99ccced3ee04f287730228a8d0e49d', 'ce915b9df2884e73b5cccc54866c52aa', '雪碧', 5, 4, 'dbe00413c4474dcabf224c7d2ab1e336', 100, '雪的口感', 0, '2019-04-21 12:55:19', null, null, null, 0);
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
INSERT INTO foolday_platform.t_goods_spec (id, name, adjust_price, goods_append_price, create_time, update_time, goods_id, status, order_num, type, shop_id) VALUES ('7a2c1943eda821ec944c2135f52d3333', 'string', 0, 0, '2019-05-21 00:12:24', null, 'string', 1, 0, 0, 'shopId');
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
    height      int                                null
)
    collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height) VALUES ('1cd5e00450924ed49e545f830935f118', 'jpeg', null, null, null, 26558, '2019-04-14 21:41:35', null, 'login2.jpg', 400, 350);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height) VALUES ('a3c7970fbf044b5ebff7674e5534318b', 'jpeg', null, null, null, 323682, '2019-04-16 00:08:44', null, 'DSC_5808.jpg', 1428, 2087);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height) VALUES ('b3cd2daf689b4ab8954b13abb14853c9', 'jpeg', null, null, null, 24099, '2019-04-14 21:39:44', null, 'login2.jpg', 400, 300);
INSERT INTO foolday_platform.t_image (id, type, origin, description, remark, size, create_time, update_time, name, width, height) VALUES ('e9c29317845243ed80b79a51acd8f216', 'jpeg', null, null, null, 168128, '2019-04-14 21:39:07', null, 'login2.jpg', 960, 600);
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
    update_time  datetime                               null on update CURRENT_TIMESTAMP comment '更新时间',
    unread       tinyint(2)                             not null comment '0未读 1已读 -1无效/删除'
)
    comment '消息管理表';

INSERT INTO foolday_platform.t_message (id, sender, callback_url, remark, channel_type, title, content, business_id, to_shop_id, action, create_time, update_time, unread) VALUES ('39a15844d97fa2a745589cf1e4374f78', 'testUserId', '', '', 'message-order-chanel-topic', '用户下单通知', '您已成功下单,点击可查看订单详情', 'e4121d345083ea4eee8242d2593efa79', 'testShopId', 1, '2019-06-02 00:42:13', null, 0);
INSERT INTO foolday_platform.t_message (id, sender, callback_url, remark, channel_type, title, content, business_id, to_shop_id, action, create_time, update_time, unread) VALUES ('7db6fec690c82f780bd6a69b2d7876bc', 'testUserId', '', '', 'message-order-chanel-topic', '用户下单通知', '您已成功下单,点击可查看订单详情', 'd925c621f92e7cef45b1a600e33b7fe2', 'testShopId', 1, '2019-06-02 00:39:46', null, 0);
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

INSERT INTO foolday_platform.t_order (id, shop_id, shop_name, shop_address, eat_type, goods_num, all_price, remark, people_cnt, user_id, groupbuy_id, coupon_id, status, order_no, order_type, seat_no, queue_no, create_time, update_time, other_coupon_id, user_name) VALUES ('d925c621f92e7cef45b1a600e33b7fe2', 'testShopId', 'string', 'string', 1, 0, 0, 'xxxx', 0, 'testUserId', '', '76234bf0a8021cde4abbae02c63b818b', 0, '20190602000000001', 0, 1, '1', '2019-06-02 00:39:46', null, '', 'testUserName');
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
    id          varchar(32)                          not null
        primary key,
    name        varchar(255)                         null comment '店铺名称',
    addr        varchar(255)                         null comment '地址',
    contact     varchar(255)                         null comment '联系方式',
    description varchar(255)                         null comment '描述',
    lnt         float                                null comment '经度',
    lat         float                                null comment '纬度',
    status      tinyint(2) default 0                 null comment '状态，0-正常，1-停用',
    create_time datetime   default CURRENT_TIMESTAMP null,
    update_time datetime                             null on update CURRENT_TIMESTAMP
)
    collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time) VALUES ('213de879c44b14c3d78d5accbe21ae2d', 'xxx', null, 'xxx', null, null, null, 0, null, null);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time) VALUES ('2195dc2348488b17fc4cefe8d7ee8e90', 'xxx', null, 'xxx', null, null, null, 0, null, null);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time) VALUES ('280763b0bc926997b5d0708a6d9db73b', 'xxx', null, 'xxx', null, null, null, 0, null, null);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time) VALUES ('640fcb878c4095a77778cc83c5933249', 'string', 'string', 'string', 'string', 0, 0, 0, null, null);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time) VALUES ('b32e145cac6c58ef33ac6171a0b3356f', 'string', 'string', 'string', 'string', 0, 0, 0, null, null);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time) VALUES ('dd2cdd5f8b396cd357388b7b320d449e', 'xxx', null, 'xxx', null, null, null, 0, null, null);
INSERT INTO foolday_platform.t_shop (id, name, addr, contact, description, lnt, lat, status, create_time, update_time) VALUES ('testShopId', 'string', 'string', 'string', 'string', 0, 0, 0, null, '2019-06-01 12:18:07');
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

INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('eef7c4ae3380ac901671b09703ae08cf', '/admin', 2, '2019-05-27 00:25:17', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('33c545e06487cd457f77542bc9a9aeb6', '/admin/kaptcha', 1, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('ecfe235f003cd916d3872b9ea428b4d2', '/goods/categor/{id}/edit', 2, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('48f9aa45ceeece09bf32c772d09e63c1', '/goods/categor/list', 1, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2e92b6bcb85aba5dde558cf6ea541289', '/goods/categor/add', 2, '2019-05-27 00:25:18', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4cc7985dcad5a5557c270afc034daa3c', '/goods/categor/{id}/delete', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d6e1db48b88de31d0b71ee2ed76b2b15', '/coupon/edit', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('98507b3b243c728e13bc302efb512104', '/coupon/add', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1baabb0983ab5e36370b6a17a74077eb', '/coupon/list', 2, '2019-05-27 00:25:19', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2f10d9790536529d8199ab2acc2d1986', '/coupon/delete', 2, '2019-05-27 00:25:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c21d92c61dd3ece82b9ef4af55ce29e7', '/role/edit/{id}', 2, '2019-05-27 00:25:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1156160dd966a6818af3198b380403eb', '/role/delete/{id}', 2, '2019-05-27 00:25:20', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('691223df04ba210ef679fb8a47c8ee74', '/role/down/{id}', 2, '2019-05-27 00:25:20', '2019-05-27 21:37:07', -1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('bfb5d04fda025d63538dd4d47564f0f0', '/role/add', 2, '2019-05-27 00:25:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9219f2b7f4275351173fea2719d55f4f', '/role/page', 2, '2019-05-27 00:25:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('92338d172090edde710f8da08c6533b9', '/role/get/{id}', 2, '2019-05-27 00:25:21', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('01b44b66c4c2ba76ee80dab0a8a7a54e', '/order/status/update', 2, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('ee1d3cc01921a8883a902c45c6a37acc', '/order/audit/cancelOrder/list', 1, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b4485b21a098a7c9fc3292a4ed1550e1', '/order/disagressCancel/list', 1, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8818019a322f062ba6d6294e33d04f38', '/order/delete', 2, '2019-05-27 00:25:22', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a9b00ee9d3359084a1d1f5b62bfa7c0f', '/order/agreeCancel/list', 1, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c36d0e6201fcf988470088e577ef8a21', '/order/cancel/audit', 1, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8a94372d8835c037921f3fac1cf0dba7', '/order/page', 2, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d58780008a9d833f2c94129105b99c1c', '/carousel/delete/{id}', 2, '2019-05-27 00:25:23', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('54baaa6ece273471e8741b384c61c591', '/carousel/add', 2, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('6677e5fdb70caecb1d2eea61557233db', '/goods/spe/add', 2, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('dd2ed33dc248c24e6a0f46cc6361d7bd', '/goods/spe/list/rootSpec', 1, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('8f58ae3d231756dd7481d2957f0bd0b9', '/goods/spe/list/{goodsId}/subSpec', 1, '2019-05-27 00:25:24', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('4af9d48b7451a2c9d6eaf42dda7546c4', '/goods/spe/{id}/delete', 2, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f1e882bdc0cce65a4f248db7b14d5577', '/goods/spe/{id}/edit', 2, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1e3a6c1876ce3cd089cf2a457089398c', '/shop/edit', 2, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e5814b8cca28e8677d6fa1eb362335c', '/shop/list', 1, '2019-05-27 00:25:25', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a1c9fab508e96fb2da3c5c91aba41d7a', '/shop/add', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('75dedaf03c046fe0ab5220c7f9fa3790', '/shop/freeze', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('735dfb423497e63c33a0ef641d32133f', '/shop/delete', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1b3ba1e1fec23362acea265f6b50108a', '/comment/replay/{commentId}', 2, '2019-05-27 00:25:26', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e63f6f93e221815b03a64b7d560c389', '/comment/{commentId}/list', 1, '2019-05-27 00:25:27', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a3df9a01bbcf6b0ba011f58610388882', '/comment/list', 2, '2019-05-27 00:25:27', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('87c105ebf8326d6aa2008aec0ad68ed1', '/comment/list/{orderId}', 1, '2019-05-27 00:25:27', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a8ae02cacd945af35b5a5a86b9c0fee8', '/goods/{goodsId}/edit', 2, '2019-05-27 00:25:27', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('599eaec036a709fab0f8a006b9d47089', '/goods/{goodsId}/downStatus', 2, '2019-05-27 00:25:28', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('08ef2ed1afbffe9e4328a62f5c3bb07b', '/goods/{goodsId}/upStatus', 2, '2019-05-27 00:25:28', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('1da26fc92e4cf7cbc2a9b785936a5025', '/goods/add', 2, '2019-05-27 00:25:28', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d9fc6f5287cdfdf0d786c3dd68ba9a6d', '/goods/{goodsId}/deleteStatus', 2, '2019-05-27 00:25:28', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c9de5d67ae9ec28aa14e6a606aaa27cc', '/goods/{goodsCategoryId}/list', 2, '2019-05-27 00:25:29', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a909bef4aeaaaa6cc452db31b8674f7e', '/banner/add', 2, '2019-05-27 00:25:29', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('222fcd2cada86b57482f3b73ffba6048', '/banner/edit/{id}', 2, '2019-05-27 00:25:29', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('049c3feffc7fdbd5eac045a4f507fd49', '/banner/delete/{id}', 2, '2019-05-27 00:25:29', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c865a6ff6743c67810e26c30649788aa', '/table/list', 1, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('0132c0940da9f560d9e55351071af7c5', '/table/addAndBindQrcode', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('bd5d8e65fb59fa53eda5553cc751927e', '/table/edit', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e6079cde9c6341a4bf4326c9966b927a', '/table/add', 2, '2019-05-27 00:25:30', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e090c09b873fb070e34582687757a77a', '/table/delete', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a73497af44831a67c9ca45e574b8b7cf', '/image/{id}/download', 1, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('dab1a6ee17235541d59c9f8da6b64ead', '/image/upload/file', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('479da130f713e5e514c3c3d744ae01d9', '/image/uplaod/files', 2, '2019-05-27 00:25:31', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a5a0156ac6aa523017c9feffb7fd78ac', '/image/{id}/size/{width}/{height}', 1, '2019-05-27 00:25:32', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('39bd6f8b0c2d56c81ef2e51ee56f9431', '/role/noValid/{id}', 2, '2019-05-27 21:37:05', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b8fc47e4c88d3ffcffc46d74a2bb904f', '/role/valid/{id}', 2, '2019-05-27 21:37:05', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('b0596110dc4cba5f18c6ee13bb2e45ca', '/admin/add', 2, '2019-05-27 21:37:05', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('43dc6b67ad6c8cc1b56e874208695d1d', '/admin/delete/{id}', 2, '2019-05-27 21:37:05', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c3fdfc038d932656b1a35c60c22e4d6c', '/admin/edit/{id}', 2, '2019-05-27 21:37:06', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f09a383c126c8cd80b896f1ca3ad0b54', '/admin/noValid/{id}', 2, '2019-05-27 21:37:06', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('c2478508a9deacfc2fefd716ef4c21f4', '/admin/valid/{id}', 2, '2019-05-27 21:37:06', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('a729ab9d4f0ffa75a2d0d11d96ec38f9', '/admin/get/{id}', 2, '2019-05-27 21:37:06', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('763552f840d1d890d4a46c8c85bbc583', '/admin/page', 2, '2019-05-27 21:37:06', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('f5fa63ddee9b62503dd85e8bfea905fd', '/groupBuy/add', 2, '2019-05-27 21:37:07', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('d93400ba7463504e5bebb4854dc57da5', '/menu/edit/{id}', 2, '2019-05-28 00:03:49', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('57a2ec100a162b0fb80084f5c89e9290', '/menu/delete/{id}', 2, '2019-05-28 00:03:49', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('249ba84c228890266b2e811371983e77', '/menu/noValid/{id}', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('2dcc3439366ba24e3b88470811557c80', '/menu/valid/{id}', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('5b13fbbf2018a28188626f720a47ed15', '/menu/add', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('e2e9d7fe8c96ecf0158dd3c600cc0356', '/menu/page', 2, '2019-05-28 00:03:50', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('edfe1121eda6a95659a6722e4421ba17', '/menu/get/{id}', 2, '2019-05-28 00:03:51', null, 1, '/system');
INSERT INTO foolday_platform.t_sys_auth (id, url, auth_http_method, create_time, update_time, status, base_url) VALUES ('9e3c3f746453b20366a5d2ae90f59ef2', '/message/read', 2, '2019-06-02 01:57:21', null, 1, '/system');
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
    shop_id        varchar(36)                             not null
)
    comment '操作日志记录' collate = utf8mb4_unicode_ci;

INSERT INTO foolday_platform.t_sys_log (id, operator, operator_id, operate_status, create_time, update_time, result_msg, resource_name, host, cost, action, content_type, request_body, response_body, request_url, shop_id) VALUES ('5e3d33c123ff74cbd64a032e3857ae5e', 'testUserName', 'testUserId', 'success', '2019-05-25 15:21:50', null, '', '按最新时间获取评论列表', '', '374', 'POST', '', '[]', 'com.foolday.common.dto.FantResult@1a719adb[ok=true,message=<null>,data=[],moreData=<null>]', '/comment/list', '');
INSERT INTO foolday_platform.t_sys_log (id, operator, operator_id, operate_status, create_time, update_time, result_msg, resource_name, host, cost, action, content_type, request_body, response_body, request_url, shop_id) VALUES ('6024e4bd6ca427cb16083081d84e2007', 'testUserName', 'testUserId', 'success', '2019-05-25 15:22:04', null, '', '按最新时间获取评论列表', '', '329', 'POST', '', '[]', 'com.foolday.common.dto.FantResult@4844cb55[ok=true,message=<null>,data=[],moreData=<null>]', '/comment/list', '');
INSERT INTO foolday_platform.t_sys_log (id, operator, operator_id, operate_status, create_time, update_time, result_msg, resource_name, host, cost, action, content_type, request_body, response_body, request_url, shop_id) VALUES ('9968dbb9047bcfa2a65e3b85309ad387', 'testUserName', 'testUserId', 'success', '2019-05-24 02:15:37', null, '', '按最新时间获取评论列表', '', '379', 'POST', '', '[]', 'com.foolday.common.dto.FantResult@291e9b61[ok=true,message=<null>,data=[],moreData=<null>]', '/comment/list', '');
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
    id          varchar(36)             not null
        primary key,
    name        varchar(100)            not null comment '用户名称',
    img_id      varchar(36)             not null comment '用户头像id，无默认取..需定义',
    wxid        varchar(100)            not null comment '微信id',
    longitude   float        default 0  null comment '经度',
    latitude    float        default 0  null comment '纬度',
    status      tinyint(2)   default 1  null comment ' 在线(0),有效(1),无效(2),禁用(3),拉黑(4)',
    create_time datetime                not null,
    update_time datetime                null on update CURRENT_TIMESTAMP comment '自动更新时间',
    open_id     varchar(100) default '' null comment '微信用户每个公众对应一个openid',
    union_id    varchar(100) default '' null comment '微信用户唯一id',
    city        varchar(100)            null comment '所在城市',
    province    varchar(100)            null comment '所在省',
    country     varchar(100)            null comment '所在国家',
    gender      varchar(10)             null,
    tel         varchar(100)            null comment '手机号码',
    shop_id     varchar(36)             null comment '店铺id'
)
    comment '微信用户信息' collate = utf8mb4_unicode_ci;

create index long_lat
    on t_user (longitude, latitude);

create index name_imgId_status
    on t_user (status, img_id, name);

create index wxid
    on t_user (wxid);


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

