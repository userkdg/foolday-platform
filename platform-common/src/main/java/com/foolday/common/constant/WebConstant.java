package com.foolday.common.constant;

/**
 * web层常理
 */
public final class WebConstant {
    private WebConstant() {
    }

    public static final String AUTH_AUTHED_TOKEN = "FoolDay-Auth";

    /**
     * 对所有密码加常量盐
     */
    public static final String PLATFORM_PASSWORD_HEX_CHAR = "PASSWORD_HEX_";

    /**
     * 使用session来存储用户的shopId的key
     */
    public static final String USER_SHOP_ID_FROM_SESSION = "USER_SHOP_ID_KEY_";


    /**
     * 针对swagger中返回给前端的通用描述信息
     */
    public static final String RESPONSE_RESULT_MSG = "正常返回，其中FantResult.data[]=>返回的数据;FantResult.ok=false=>FantResult.message的失败信息,FantResult.ok=true=>FantResult.message为成功信息";

    public static final String RESPONSE_PAGE_MSG = "正常返回分页列表，其中FantPage.rows[]=>返回的数据;FantPage.totalCount=>总数量;FantPage.pageSize=>每一页数量;FantPage.pageNo=>当前页码";

    /**
     * 关于redis的key的固定值
     */
    public static final class RedisKey {
        /**
         * 统一前缀
         */
        private static final String REDIS_PREFFIX = "platform:";

        /**
         * 定制access_token
         */
        public static final String REDIS_ACCESS_TOKEN_KEY = REDIS_PREFFIX + "weixin:access_token:";
        /**
         * 存储与redis中的key
         */
        public static final String ADMIN_USER_SHOPID_KEY = "admin:user:shopId";
    }

    /**
     * 系统配置key常量
     */
    public static final class SystemProperty {
        public static final String java_runtime_name = "java.runtime.name";
        public static final String sun_boot_library_path = "sun.boot.library.path";
        public static final String java_vm_version = "java.vm.version";
        public static final String java_vm_vendor = "java.vm.vendor";
        public static final String java_vendor_url = "java.vendor.url";
        public static final String path_separator = "path.separator";
        public static final String java_vm_name = "java.vm.name";
        public static final String file_encoding_pkg = "file.encoding.pkg";
        public static final String user_country = "user.country";
        public static final String user_script = "user.script";
        public static final String sun_java_launcher = "sun.java.launcher";
        public static final String sun_os_patch_level = "sun.os.patch.level";
        public static final String java_vm_specification_name = "java.vm.specification.name";
        public static final String user_dir = "user.dir";
        public static final String java_runtime_version = "java.runtime.version";
        public static final String java_awt_graphicsenv = "java.awt.graphicsenv";
        public static final String java_endorsed_dirs = "java.endorsed.dirs";
        public static final String os_arch = "os.arch";
        public static final String java_io_tmpdir = "java.io.tmpdir";
        public static final String line_separator = "line.separator";
        public static final String java_vm_specification_vendor = "java.vm.specification.vendor";
        public static final String user_variant = "user.variant";
        public static final String os_name = "os.name";
        public static final String sun_jnu_encoding = "sun.jnu.encoding";
        public static final String java_library_path = "java.library.path";
        public static final String java_specification_name = "java.specification.name";
        public static final String java_class_version = "java.class.version";
        public static final String sun_management_compiler = "sun.management.compiler";
        public static final String os_version = "os.version";
        public static final String user_home = "user.home";
        public static final String user_timezone = "user.timezone";
        public static final String java_awt_printerjob = "java.awt.printerjob";
        public static final String file_encoding = "file.encoding";
        public static final String java_specification_version = "java.specification.version";
        public static final String java_class_path = "java.class.path";
        public static final String user_name = "user.name";
        public static final String java_vm_specification_version = "java.vm.specification.version";
        public static final String sun_java_command = "sun.java.command";
        public static final String java_home = "java.home";
        public static final String sun_arch_data_model = "sun.arch.data.model";
        public static final String user_language = "user.language";
        public static final String java_specification_vendor = "java.specification.vendor";
        public static final String awt_toolkit = "awt.toolkit";
        public static final String java_vm_info = "java.vm.info";
        public static final String java_version = "java.version";
        public static final String java_ext_dirs = "java.ext.dirs";
        public static final String sun_boot_class_path = "sun.boot.class.path";
        public static final String java_vendor = "java.vendor";
        public static final String file_separator = "file.separator";
        public static final String java_vendor_url_bug = "java.vendor.url.bug";
        public static final String sun_io_unicode_encoding = "sun.io.unicode.encoding";
        public static final String sun_cpu_endian = "sun.cpu.endian";
        public static final String sun_desktop = "sun.desktop";
        public static final String sun_cpu_isalist = "sun.cpu.isalist";
    }

}
