create schema if not exists foolday_platform collate utf8_general_ci;

create table if not exists t_admin
(
	id varchar(36) not null
		primary key,
	account varchar(100) not null comment '账号目前约定为手机号码',
	password varchar(100) not null comment '密码 md5加密和加盐',
	status tinyint default 1 not null comment '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间',
	telphone varchar(20) default '' null comment '手机号码',
	nickname varchar(100) default '' null comment '名称'
)
comment '账号表' collate=utf8mb4_unicode_ci;

create index account_password_status_index
	on t_admin (account, password, status);

create table if not exists t_article
(
	id varchar(36) not null
		primary key,
	create_time datetime default CURRENT_TIMESTAMP null,
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间',
	title varchar(100) not null comment '标题',
	content mediumtext not null comment '文件内容,包含图片等二进制信息, <= 16Mb的内容',
	thumail_id varchar(36) not null comment '缩略图id,列表显示',
	status tinyint(2) default 1 null comment '状态 有效 1 无效0 删除-1',
	type varchar(100) not null comment '文章类型, 餐饮资讯 饮食知识 行业动态',
	shop_id varchar(36) null comment '店铺id'
)
comment '文章表';

create table if not exists t_banner
(
	id varchar(36) not null
		primary key,
	order_no int(5) not null comment '排序',
	status tinyint default 1 not null comment '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '更新时间',
	goods_id varchar(36) null comment '商品id',
	description varchar(36) null comment '描述',
	image_id varchar(36) null comment '图id',
	price float(10,2) null comment '价格',
	shop_id varchar(36) null comment '店铺id'
)
comment 'banner管理' collate=utf8mb4_unicode_ci;

create index banner_index
	on t_banner (shop_id, goods_id, status);

create index banner_index_order
	on t_banner (order_no, update_time, status);

create table if not exists t_carouse
(
	id varchar(36) not null
		primary key,
	order_no int(5) not null comment '排序',
	status tinyint default 1 not null comment '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '更新时间',
	image_id varchar(36) null comment '图id',
	shop_id varchar(36) null comment '店铺id'
)
comment 't_carouse管理' collate=utf8mb4_unicode_ci;

create index t_carouse_index
	on t_carouse (shop_id, image_id, status);

create index t_carouse_index_order
	on t_carouse (order_no, update_time, status);

create table if not exists t_comment
(
	id int not null
		primary key,
	shop_id varchar(36) not null comment '店铺id',
	img_ids varchar(255) not null comment '图片ids 多个用 英文逗号隔开,最多在3-5张5*36=180',
	status tinyint default 1 not null comment '有效(1),无效(2),删除(3)',
	description varchar(255) default '' null comment '评论内容 目前约定最多200字',
	order_id varchar(36) not null comment '订单id',
	goods_id varchar(36) not null comment '商品id',
	user_id varchar(36) not null comment '用户id',
	user_name varchar(100) not null comment '用户名称，前端需要限制不显示全名',
	comment_type tinyint(2) default 1 null comment '客户(1),店长(2),超级管理员(3)',
	admin_id varchar(36) default '' null comment '若店内人员回复则记录回复人id',
	admin_name varchar(100) default '' null comment '若店内人员回复则记录回复人名称',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间'
)
comment '评论表' collate=utf8mb4_unicode_ci;

create index commentType
	on t_comment (comment_type);

create index shopId
	on t_comment (shop_id);

create index userId_status_orderId_goodsId
	on t_comment (user_id, status, order_id, goods_id);

create table if not exists t_coupon
(
	id varchar(36) not null
		primary key,
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间',
	common_used tinyint(1) default 0 null comment '1可共用 0不可公用_默认',
	description varchar(255) default '' null comment '使用说明',
	type tinyint(2) default 1 null comment '折扣券1,满减券2,其他优惠券0',
	status tinyint(2) default 1 null comment '有效1, 无效0, 删除-1'
)
comment '优惠券明确商品可以使用那些券' collate=utf8mb4_unicode_ci;

create table if not exists t_goods
(
	id varchar(64) not null
		primary key,
	shop_id varchar(64) not null comment '店铺id',
	name varchar(100) not null comment '商品名称',
	price float not null comment '商品价格',
	discnt_price float not null comment '折扣价格',
	img_id varchar(64) default '' null comment '图片id',
	kccnt int default 0 null comment '库存',
	description varchar(255) default '' null comment '描述内容',
	status int(2) default 1 null comment '1上架 0下架 -1删除',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间',
	category_id varchar(64) null comment '分类id，目前一对一关系',
	unit varchar(36) null comment '商品单位 0份 1杯 2包 3件 4打 5半打 6瓶',
	discnt_goods tinyint(1) default 0 null comment '是否为折扣价 0否 1是 默认0'
)
comment '商品表' collate=utf8mb4_unicode_ci;

create index t_tagId_imagId_shopId
	on t_goods (shop_id, img_id, category_id);

create table if not exists t_goods_category
(
	id varchar(64) not null
		primary key,
	name varchar(100) not null comment '分类名称',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间',
	status tinyint default 1 null comment '状态有效(1),无效(0),删除(-1)',
	top_down_status tinyint default 5 null comment '优先排序 9 置顶 5默认按最新来排 0 置底 可以+更新时间进行排序控制',
	shop_id varchar(36) not null comment '哪家商品分类'
)
comment '分类表，目前主要针对商品分类' collate=utf8mb4_unicode_ci;

create table if not exists t_goods_spec
(
	id varchar(36) not null
		primary key,
	name varchar(100) default '' null comment '规格名称',
	adjust_price tinyint(1) default 0 null comment '明确规格的选择是否调整源商品的价格',
	goods_append_price float(9,2) default 0.00 null comment '若reset_goods_price=1,商品以商品加本值(可为正负0)为准,=0,则忽略',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间',
	goods_id varchar(36) not null comment '商品id',
	status tinyint(1) default 1 null comment '状态',
	order_num int default 0 null comment '排序号',
	type tinyint not null comment '规格大类目前以枚举类定义，后续确认规格需求在调整',
	shop_id varchar(36) not null comment '店铺id'
)
comment '商品规格表';

create index goods_id
	on t_goods_spec (goods_id, create_time);

create table if not exists t_groupbuy
(
	id varchar(32) not null
		primary key,
	name varchar(255) null,
	shop_id varchar(32) null,
	condition_num int null comment 'n人拼团',
	ori_price float(10,2) null,
	curr_price float(10,2) null,
	status int null,
	imgIds varchar(255) null,
	group_buy_code varchar(255) null comment '团购券码',
	hx_code varchar(255) null comment '核销码',
	limit_time_second int null,
	include_shop_ids varchar(255) null comment '包含店铺',
	remark varchar(255) null comment '备注',
	start_time datetime null comment '有效期，起始时间',
	end_time datetime null comment '有效期，结束时间',
	use_start_time datetime null comment '使用起始时间（1天）',
	use_end_time datetime null comment '使用结束时间（1天）',
	rule varchar(255) null,
	kccnt int(255) null,
	goods_detail text null,
	repeat_times int null
);

create table if not exists t_image
(
	id varchar(36) not null
		primary key,
	type varchar(10) null,
	origin tinyint null,
	description varchar(255) null,
	remark varchar(255) null,
	size bigint null,
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP,
	name varchar(255) null,
	width int null,
	height int null
)
collate=utf8mb4_unicode_ci;

create table if not exists t_message
(
	id varchar(36) not null
		primary key,
	sender varchar(100) default '' null comment '发给谁，一般是微信的wxid openid',
	callbak_url varchar(500) default '' null comment '回掉链接',
	remark varchar(100) default '' null comment '备注信息',
	channel_type varchar(50) default '' not null comment '目前为字符串的枚举值',
	title varchar(100) default '' not null comment '消息的标题信息',
	content varchar(516) default '' not null comment '消息的主题内容',
	business_id varchar(36) default '' not null comment '消息对应的业务主键id',
	to_shop_id varchar(36) default '' null comment '商铺id',
	action tinyint(5) default 1 null comment '业务消息类型 与字段 business_id有对应关系',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '更新时间'
)
comment '消息管理表';

create table if not exists t_order
(
	id varchar(64) not null
		primary key,
	shop_id varchar(64) not null comment '店铺id',
	shop_name varchar(100) not null comment '店铺名称，冗余字段，便于显示',
	shop_address varchar(255) not null comment '店铺地址，冗余字段，便于显示',
	eat_type tinyint default 1 null comment '1 堂吃 0外带',
	goods_num tinyint default 0 null comment '订单商品数量',
	all_price float default 0 null comment '订单总价格',
	remark varchar(100) default '' null comment '订单备注',
	people_cnt tinyint default 1 null comment '默认为1人用餐',
	user_id varchar(36) not null comment '微信小程序用户系统id,非wxid',
	groupbuy_id varchar(36) default '' null comment '拼团id',
	coupon_id varchar(36) default '' null comment '使用优惠券的id,控制优惠券的状态',
	status tinyint(2) default 0 null comment '普通订单类型:0待付款,1待确认,2待评价,3已完成(已评价);通用:4退款,-1删除,拼团类型:10拼团中,11拼团成功,12拼团失败',
	order_no varchar(50) default '' null comment '订单编号',
	order_type tinyint(2) default 0 null comment '0点餐订单 1拼团订单',
	seat_no int(10) default 0 null comment '座位号，目前只录入数值 不计号,为了按数值排序',
	queue_no varchar(10) default '' null comment '排队号',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间',
	other_coupon_id varchar(36) default '' null comment '其他优惠价对应的优惠标识',
	user_name varchar(100) default '' null comment '用户名称，便于后台管理查询'
)
comment '订单表' collate=utf8mb4_unicode_ci;

create index couponId
	on t_order (coupon_id);

create index groupbuy_id
	on t_order (groupbuy_id);

create index shopId_userId_eatType_orderType
	on t_order (shop_id, user_id, eat_type, order_type, status);

create table if not exists t_order_detail
(
	id varchar(36) not null
		primary key,
	order_id varchar(36) not null comment '订单id',
	goods_desc varchar(100) default '' null comment '商品描述',
	goods_name varchar(50) not null comment '商品名称',
	goods_img_id varchar(36) default '' null comment '商品图片id',
	goods_id varchar(36) not null comment '商品id',
	all_price float default 0 null comment '订单总价格',
	price float default 0 null comment '实际价格',
	cnt tinyint default 0 null comment '数量',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间'
)
comment '订单商品列表' collate=utf8mb4_unicode_ci;

create index orderId_goodsId
	on t_order_detail (order_id, goods_id)
	comment '订单+商品id';

create table if not exists t_qrcode
(
	id varchar(32) not null
		primary key,
	content text null comment '二维码内容',
	path varchar(255) null comment '路径',
	name varchar(255) null comment '下载二维码时图片命名（默认取桌位名）'
);

create table if not exists t_shop
(
	id varchar(32) not null
		primary key,
	name varchar(255) null comment '店铺名称',
	addr varchar(255) null comment '地址',
	contact varchar(255) null comment '联系方式',
	description varchar(255) null comment '描述',
	lnt float null comment '经度',
	lat float null comment '纬度',
	status tinyint(2) default 0 null comment '状态，0-正常，1-停用',
	createtime datetime null,
	updatetime datetime null,
	create_time datetime null on update CURRENT_TIMESTAMP,
	update_time datetime null on update CURRENT_TIMESTAMP
)
collate=utf8mb4_unicode_ci;

create table if not exists t_table
(
	id varchar(32) not null
		primary key,
	shop_id varchar(32) null comment '店铺ID',
	name varchar(255) null comment '名称',
	status int null comment '状态',
	qrcode_id varchar(32) null comment '二维码ID'
)
collate=utf8mb4_unicode_ci;

create table if not exists t_tags
(
	id varchar(64) not null
		primary key,
	name varchar(100) not null comment '标签名称',
	type int(2) default 1 not null comment '1为商品类型 0为订单类 -1为删除类',
	create_time datetime not null,
	update_time datetime null,
	priority_level int(5) default 0 null comment '定义标签优先级'
)
comment '标签表，目前主要针对商品分类' collate=utf8mb4_unicode_ci;

create table if not exists t_user
(
	id varchar(36) not null
		primary key,
	name varchar(100) not null comment '用户名称',
	img_id varchar(36) not null comment '用户头像id，无默认取..需定义',
	wxid varchar(100) not null comment '微信id',
	longitude float default 0 null comment '经度',
	latitude float default 0 null comment '纬度',
	status tinyint(2) default 1 null comment ' 在线(0),有效(1),无效(2),禁用(3),拉黑(4)',
	create_time datetime not null,
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间',
	open_id varchar(100) default '' null comment '微信用户每个公众对应一个openid',
	union_id varchar(100) default '' null comment '微信用户唯一id',
	city varchar(100) null comment '所在城市',
	province varchar(100) null comment '所在省',
	country varchar(100) null comment '所在国家',
	gender varchar(10) null,
	tel varchar(100) null comment '手机号码',
	shop_id varchar(36) null comment '店铺id'
)
comment '微信用户信息' collate=utf8mb4_unicode_ci;

create index long_lat
	on t_user (longitude, latitude);

create index name_imgId_status
	on t_user (status, img_id, name);

create index wxid
	on t_user (wxid);

create table if not exists t_user_address_history
(
	id varchar(36) not null
		primary key,
	address varchar(500) not null comment '地址名称',
	status tinyint default 1 not null comment '1为有效,2为无效，3为禁用，4为拉黑，-1为删除',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '更新时间',
	user_id varchar(36) null comment '用户id',
	shop_id varchar(36) null comment '店铺id'
)
comment 't_user_address_history用户地址使用过的记录管理' collate=utf8mb4_unicode_ci;

create index t_user_address_history_index
	on t_user_address_history (user_id, shop_id, status);

create table if not exists t_user_advice
(
	id varchar(36) not null
		primary key,
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime null on update CURRENT_TIMESTAMP comment '自动更新时间',
	user_id varchar(255) not null comment '意见发起人id',
	content varchar(255) not null comment '意见内容',
	img_ids varchar(255) not null comment '反馈图片ids 用英文逗号隔开 区分多个图片',
	shop_id varchar(36) not null comment '店铺id,针对哪家店反馈'
)
comment '客户意见反馈';

