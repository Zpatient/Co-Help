create table cohelp.activity
(
    id                   int auto_increment comment '主键'
        primary key,
    activity_owner_id    int                                not null comment '活动发布者id',
    activity_title       varchar(255)                       not null comment '活动标题',
    activity_detail      varchar(1024)                      not null comment '活动内容',
    activity_time        datetime                           not null comment '活动时间',
    activity_like        int      default 0                 not null comment '活动点赞量',
    activity_comment     int      default 0                 not null comment '活动评论量',
    activity_label       varchar(255)                       null comment '活动标签',
    activity_collect     int      default 0                 not null comment '活动收藏量',
    activity_state       tinyint  default 0                 not null comment '活动状态（0：正常 1：异常）',
    activity_create_time datetime default CURRENT_TIMESTAMP not null comment '活动发布时间'
)
    comment '活动表';

create table cohelp.collect
(
    id           int auto_increment comment '主键'
        primary key,
    user_id      int      not null comment '用户id',
    topic_type   int      not null comment '话题类型（1：活动 2：互助 3：树洞）',
    topic_id     int      not null comment '话题id',
    collect_time datetime not null comment '收藏时间'
)
    comment '收藏表';

create table cohelp.help
(
    id               int auto_increment comment '主键'
        primary key,
    help_owner_id    int                                not null comment '互助发布者id',
    help_title       varchar(255)                       not null comment '互助标题',
    help_detail      varchar(1024)                      not null comment '互助内容',
    help_paid        tinyint  default 0                 not null comment '互助有/无偿',
    help_like        int      default 0                 not null comment '互助点赞量',
    help_collect     int      default 0                 not null comment '互助收藏量',
    help_comment     int      default 0                 not null comment '互助评论量',
    help_label       varchar(255)                       null comment '互助标签',
    help_state       tinyint  default 0                 not null comment '互助状态（0：正常 1：异常）',
    help_create_time datetime default CURRENT_TIMESTAMP not null comment '互助发布时间'
)
    comment '互助表';

create table cohelp.history
(
    id         int auto_increment comment '主键'
        primary key,
    user_id    int      not null comment '用户id',
    topic_type int      not null comment '话题类型（1：活动 2：互助 3：树洞）',
    topic_id   int      not null comment '话题id',
    view_time  datetime not null comment '查看/浏览时间'
)
    comment '浏览记录表';

create table cohelp.hole
(
    id               int auto_increment comment '主键'
        primary key,
    hole_owner_id    int                                not null comment '树洞发布者id',
    hole_title       varchar(255)                       not null comment '树洞主题',
    hole_detail      varchar(1024)                      not null comment '树洞内容',
    hole_like        int      default 0                 not null comment '树洞点赞量',
    hole_collect     int      default 0                 not null comment '树洞收藏量',
    hole_comment     int      default 0                 not null comment '树洞评论量',
    hole_label       varchar(255)                       null comment '树洞标签',
    hole_state       tinyint  default 0                 not null comment '树洞状态（0：正常 1：异常）',
    hole_create_time datetime default CURRENT_TIMESTAMP not null comment '树洞发布时间'
)
    comment '树洞表';

create table cohelp.image
(
    id           int auto_increment comment '主键'
        primary key,
    image_type   int               not null comment '图片（0：用户 1：活动 2：互助 3：树洞）',
    image_src_id int               not null comment '来源id',
    image_url    varchar(1024)     not null comment '图片url',
    image_state  tinyint default 0 not null comment '图片状态（0：使用中 1：待使用）
用户：更换图像时需要修改该字段（历史头像）'
)
    comment '图片表';

insert into cohelp.image values(1, 0, 0, 'https://img-blog.csdnimg.cn/img_convert/b573b00bed7126db2c209ed01eb35189.png', 0);

create table cohelp.inform
(
    id                     int auto_increment comment '主键'
        primary key,
    inform_type            varchar(255)  not null comment '举报类型',
    informer_id            int           not null comment '举报人id',
    inform_content         varchar(1024) not null comment '举报内容',
    informed_instance_id   int           not null comment '被举报对象的id',
    informed_instance_type int           not null comment '举报对象的类型',
    create_time            datetime      not null comment '举报时间'
)
    comment '举报表';

create table cohelp.remark_activity
(
    id                 int auto_increment comment '主键'
        primary key,
    remark_content     varchar(2048) not null comment '评论内容',
    remark_target_id   int           not null comment '评论对象id',
    remark_like        int default 0 not null comment '评论点赞量',
    remark_activity_id int           not null comment '评论活动id',
    top_id             int default 0 not null comment '顶层id（评论链的根id）',
    target_is_activity tinyint       not null comment '评论对象是否为活动（0：否 1：是）',
    remark_owner_id    int           not null comment '评论拥有者id',
    remark_time        datetime      not null comment '评论发布时间'
)
    comment '活动评论表';

create table cohelp.remark_help
(
    id               int auto_increment comment '主键'
        primary key,
    remark_content   varchar(2048) not null comment '评论内容',
    remark_help_id   int           not null comment '评论互助id',
    remark_like      int default 0 not null comment '评论点赞量',
    remark_target_id int           not null comment '评论对象id',
    top_id           int default 0 not null comment '顶层id（评论链的根id）',
    target_is_help   tinyint       not null comment '评论对象是否为互助（0：否 1：是）',
    remark_owner_id  int           not null comment '评论拥有者id',
    remark_time      datetime      not null comment '评论发布时间'
)
    comment '互助评论表';

create table cohelp.remark_hole
(
    id               int auto_increment comment '主键'
        primary key,
    remark_content   varchar(2048) not null comment '评论内容',
    remark_hole_id   int           not null comment '评论树洞id',
    remark_like      int default 0 not null comment '评论点赞量',
    remark_target_id int           not null comment '评论对象id',
    top_id           int default 0 not null comment '顶层id（评论链的根id）',
    target_is_hole   tinyint       not null comment '评论对象是否为树洞（0：否 1：是）',
    remark_owner_id  int           not null comment '评论拥有者id',
    remark_time      datetime      not null comment '评论发布时间'
)
    comment '树洞评论表';

create table cohelp.user
(
    id               int auto_increment comment '主键'
        primary key,
    user_account     varchar(25)                        not null comment '账号',
    user_name        varchar(25)                        not null comment '昵称',
    user_password    varchar(255)                       not null comment '密码',
    avatar           int      default 1                 not null comment '头像',
    sex              tinyint  default 0                 not null comment '性别（0：男 1：女）',
    phone_number     varchar(25)                        null comment '联系方式',
    user_email       varchar(255)                       not null comment '邮箱',
    school           varchar(100)                       null comment '学校',
    user_role        tinyint  default 0                 not null comment '用户角色（0：普通用户 1：管理员）',
    state            tinyint  default 0                 not null comment '状态（0：正常 1：异常）',
    user_create_time datetime default CURRENT_TIMESTAMP not null comment '用户创建时间（默认当前时间）',
    age              int      default 18                not null comment '年龄'
)
    comment '用户表';


